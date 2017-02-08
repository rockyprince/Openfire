/**
 * Copyright (C) 2004-2008 Jive Software. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rockyprince;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import org.apache.commons.lang.StringUtils;

import org.jivesoftware.database.DbConnectionManager;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.interceptor.InterceptorManager;
import org.jivesoftware.openfire.privacy.PrivacyList;
import org.jivesoftware.openfire.privacy.PrivacyListManager;
import org.jivesoftware.openfire.user.User;
import org.jivesoftware.openfire.interceptor.PacketInterceptor;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.session.Session;
import org.jivesoftware.openfire.user.UserManager;
import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;
import org.dom4j.Element;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;





/**
 * <b>function:</b> push msg notification to iOS plugin
 * @author rockyprince
 * @createDate 2017-01-04 
 * @project OpenfirePlugin
 * @blog none
 * @email none
 * @version 1.0
 */
public class Apns implements PacketInterceptor, Plugin {

        private static final Logger log = LoggerFactory.getLogger(Apns.class);
        
        /**
         * The expected value is a boolean, if true APNS notification is enabled.
         */
        public static final String APNS_SERVICE_ENABLED_PROPERTY = "plugin.apns.enabled";
        
        /**
         * The expected value is a boolean, if true debug will log into file
         * otherwise no debug will be logged.
         */
        public static final String APNS_DEBUG_ENABLED_PROPERTY = "plugin.apns.debug.enabled";

        /**
         * The expected value is a sound mode. The default value is "default".
         */
        public static final String APNS_NOTIFICATION_SOUND_PROPERTY = "plugin.apns.sound";

        /**
         * The expected value is a path of APNS certification.
         */
        public static final String APNS_CERT_PATH_PROPERTY = "plugin.apns.certPath";

        /**
         * The expected value is a password of APNS certification.
         */
        public static final String APNS_CERT_KEY_PROPERTY = "plugin.apns.certKey";
        
        /**
         * The expected value is a boolean, if true APNS will be in production mode.
         */
        public static final String APNS_IS_PRODUCTION_PROPERTY = "plugin.apns.isProduct";

        /**
         * flags if enable log debug infomation
         */
	private boolean serviceEnabled;
        
        /**
         * flags if enable log debug infomation
         */
	private boolean debugEnabled;
        
        /**
         * the APNS notification sound
         */
	private String notificationSound;
        
        /**
         * the APNS certification flie path
         */
	private String certPath;
        
        /**
         * the APNS certification password
         */
	private String certKey;
        
        /**
         * flags if enable production mode
         */
	private boolean isProduction;
        
        //Hook for intercpetorn
        private InterceptorManager interceptorManager;   
        private static PluginManager pluginManager;
        private UserManager userManager;
        
        /**
         * Run thread.
         */
        private Thread runThread;
        
        private Packet apnsPacket;
        private Session apnsSession;
        private boolean apnsIncoming;
        private boolean apnsProcessed;
    
        public Apns() {
                interceptorManager = InterceptorManager.getInstance();
                XMPPServer server = XMPPServer.getInstance();
                userManager = server.getUserManager();
        }
        
        public void debug(String str){
            if( debugEnabled ){
                log.debug(str);
            }
        }
    
        public void initializePlugin(PluginManager manager, File pluginDirectory) {
                // configure this plugin
                initFilter();
                
                interceptorManager.addInterceptor(this);

                pluginManager = manager;
                
                this.debug("start APNS plugin");
        }
        
        private void initFilter() {
            // default to false
            serviceEnabled = JiveGlobals.getBooleanProperty(APNS_SERVICE_ENABLED_PROPERTY, false);
            
            // default to false
            debugEnabled = JiveGlobals.getBooleanProperty(APNS_DEBUG_ENABLED_PROPERTY, false);

            // default to "default"
            notificationSound = JiveGlobals.getProperty(APNS_NOTIFICATION_SOUND_PROPERTY, "default");

            // default to ""
            certPath = JiveGlobals.getProperty(APNS_CERT_PATH_PROPERTY, "");

            // default to ""
            certKey = JiveGlobals.getProperty(APNS_CERT_KEY_PROPERTY, "");

            // default to false
            isProduction = JiveGlobals.getBooleanProperty(APNS_IS_PRODUCTION_PROPERTY, false);
        }

        public void destroyPlugin() {
                InterceptorManager.getInstance().removeInterceptor(this);
                this.debug("stop APNS plugin");
        }

        /**
         * intercept message
         */
        @Override
        public void interceptPacket(Packet packet, Session session, boolean incoming, boolean processed) throws PacketRejectedException {
                //return if service is not enabled
                if (!serviceEnabled){
                    return;
                }
                this.debug("interceptPacket");
                JID recipient = packet.getTo();
                if (recipient != null) {
                        String username = recipient.getNode();
                        // if broadcast message or user is not exist
                        if (username == null || !UserManager.getInstance().isRegisteredUser(recipient)) {
                                this.debug("user not exist: "+ username);
                                return;
                        } else if (!XMPPServer.getInstance().getServerInfo().getXMPPDomain().equals(recipient.getDomain())) {
                                //not from the same domain
                                this.debug("not from the same domain");
                                return;
                        } else if ("".equals(recipient.getResource())) {

                        }
                }
                apnsPacket = packet;
                apnsSession = session;
                apnsIncoming = incoming;
                apnsProcessed = processed;
                runThread = new Thread() {
                        @Override
                        public void run() {
                                doAction(apnsPacket, apnsIncoming, apnsProcessed, apnsSession);
//                                Thread.sleep(500);
                        }
                };
                runThread.start();
        }
    
    
        /**
         * <b>push notification msg from this function </b>
         */
        private void doAction(Packet packet, boolean incoming, boolean processed, Session session) {
            
                if (isValidTargetPacket(packet, incoming, processed)) {
                        this.debug("isValidTargetPacket");
                        Packet copyPacket = packet.createCopy();
                        
                        if (packet instanceof Message) {
                                Message message = (Message) copyPacket;
                                // Do not push messages if communication is blocked
                                PrivacyList list =
                                        PrivacyListManager.getInstance().getDefaultPrivacyList(message.getTo().getNode());
                                if (list != null && list.shouldBlockPacket(message)) {
                                    this.debug("in blacklists");
                                    return;
                                }
                                if (message.getBody() == null || message.getBody().isEmpty()) {
                                        this.debug("not message body");
                                        return;
                                }
                                this.debug("message body:" + message.getBody());
                                if (message.getType() == Message.Type.chat) {
                                        //get message
                                        try {
                                                Document targetDoc = DocumentHelper.parseText(copyPacket.toXML());
                                                Element targetRootElm = targetDoc.getRootElement();
                                                Element extElm = targetRootElm.element("ext");
                                                String targetType = extElm.elementText("target_type");
                                                this.debug("target_type: " + targetType);
                                                
                                                if (targetType.equals("0")) {
                                                        String content;
                                                        String subject = message.getSubject();
                                                        subject = StringUtils.abbreviate(subject, 30);
                                                        String body = message.getBody();
                                                        JID from = message.getFrom();
                                                        User user = userManager.getUser(from.getNode());
                                                        String userName = user.getName();
                                                        JID recipient = message.getTo();
                                                        String msgType = StringUtils.substringBetween(body, "<msg_type>", "</msg_type>");
                                                        this.debug("msg_type: " + msgType);
                                                        if (targetType.equals("0")) {
                                                                if (msgType.equals("img")){
                                                                    content = userName + "发来一张图片";
                                                                } else if (msgType.equals("video")){
                                                                    content = userName + "发来一段小视频";
                                                                } else if (msgType.equals("notice")){
                                                                    content = subject;
                                                                } else if (msgType.equals("emoji")){
                                                                    content = userName + ": " + "发来一个表情";
                                                                } else {
                                                                    content = userName + ": " + subject;
                                                                }
                                                        } else {
                                                                if (msgType.equals("img")){
                                                                    content = userName + "在大厅中发了一张图片";
                                                                } else if (msgType.equals("video")){
                                                                    content = userName + "在大厅中发了一段小视频";
                                                                } else if (msgType.equals("notice")){
                                                                    content = subject;
                                                                } else if (msgType.equals("emoji")){
                                                                    content = userName + "在大厅中发了一个表情";
                                                                } else {
                                                                    content = userName + ": " + subject;
                                                                }
                                                        }

                                                        if (content == null || content.isEmpty()) {
                                                                content = "你收到了一条新消息";
                                                        }

                                                        String deviceToken = getDeviceToken(recipient.getNode());
                                                        this.debug("deviceToken: "+deviceToken);
                                                        if (isApple(deviceToken)) {
                                                                pns(deviceToken, content, targetType);
                                                        }
                                                }
                                        } catch (Exception e) {
                                                e.printStackTrace();
                                        }
                                } else if (message.getType() ==  Message.Type.groupchat) {

                                        List<?> els = message.getElement().elements("x");
                                        if (els != null && !els.isEmpty()) {

                                        } else {
                                        }
                                }
                        }
                }
            this.debug("isNotValidTargetPacket");
        }
        
        // 过滤指定包
        private boolean isValidTargetPacket(Packet packet, boolean read, boolean processed) {
            if (read) {
                this.debug("isValidTargetPacket: read true");
            } else {
                this.debug("isValidTargetPacket: read false");
            }
            if (processed) {
                this.debug("isValidTargetPacket: processed true");
            } else {
                this.debug("isValidTargetPacket: processed false");
            }//read true processed false
                return  !processed && read && packet instanceof Message;
        }
        
        
        /**
         * 判断是否苹果
         *
         * @param deviceToken
         * @return
         */
        private boolean isApple(String deviceToken) {
                if (deviceToken != null && deviceToken.length() > 0) {
                    return true;
                }
                return false;
        }

        public String getDeviceToken(String userId) {
                String deviceToken = ""; //4e0375f3c57e33e37854c1a5ee20544c32115f9b987d4d8c00a286ccbdb4ee66
                Connection con = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;
                this.debug("getDeviceToken: "+userId);
                try {
                    con = DbConnectionManager.getConnection();
                    pstmt = con.prepareStatement("SELECT devicetoken FROM ofAPNS where username = ?");
                    pstmt.setString(1, userId);
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        deviceToken = rs.getString(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    DbConnectionManager.closeConnection(rs, pstmt, con);
                }
                return deviceToken;
        }

        public void pns(String token, String msg, String targetType) {
                this.debug("pns: "+token);
                
                try {
                    PushNotificationPayload payLoad = new PushNotificationPayload();
                    payLoad.addAlert(msg); // 消息内容
                    if (!StringUtils.isBlank(notificationSound)) {
                            payLoad.addSound(notificationSound);// 铃音
                    }
                    if (targetType.equals("0")) {
                            payLoad.addCustomDictionary("type", "private_msg");
                            payLoad.addBadge(1); // iphone应用图标上小红圈上的数值
                    } else {
                            payLoad.addCustomDictionary("type", "hall_msg");
                    }
                    PushNotificationManager pushManager = new PushNotificationManager();
                    // true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
                    pushManager.initializeConnection(new AppleNotificationServerBasicImpl(certPath, certKey, isProduction));
                    // 发送push消息
                    Device device = new BasicDevice();
                    device.setToken(token);
                    PushedNotification notification = pushManager.sendNotification(device, payLoad, true);
                    pushManager.stopConnection();
//                    List<PushedNotification> notifications = new ArrayList<PushedNotification>();
//                    notifications.add(notification);
//                    List<PushedNotification> successfulNotifications = PushedNotification.findSuccessfulNotifications(notifications);
//                    int successful = successfulNotifications.size();
//                    if (successful > 0){
//                        this.debug("successfulNotification");
//                    } else{
//                        this.debug("failedNotification");
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    
}
