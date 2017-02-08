package org.jivesoftware.openfire.plugin.rest.controller;

import java.util.Date; //rockyprince
import java.util.List; //rockyprince
import javax.ws.rs.core.Response;

import org.jivesoftware.openfire.SessionManager;
import org.jivesoftware.openfire.plugin.rest.entity.MessageEntity;
import org.jivesoftware.openfire.plugin.rest.entity.MessageExt; //rockyprince
import org.jivesoftware.openfire.plugin.rest.exceptions.ExceptionType;
import org.jivesoftware.openfire.plugin.rest.exceptions.ServiceException;
import org.jivesoftware.openfire.XMPPServer; //rockyprince
import org.jivesoftware.openfire.PacketRouter; //rockyprince
import org.xmpp.packet.JID; //rockyprince
import org.xmpp.packet.Message; //rockyprince
import org.xmpp.packet.PacketExtension; //rockyprince
import org.dom4j.Element; //rockyprince
import org.jivesoftware.openfire.RoutingTable; //rockyprince

/**
 * The Class MessageController.
 */
public class MessageController {
	/** The Constant INSTANCE. */
	public static final MessageController INSTANCE = new MessageController();
//rockyprince
	/** The xmpp server. */
	private XMPPServer server;

	/** The packet router. */
	private PacketRouter packetRouter;
        
	/** The packet router. */
//	private RoutingTable routingTable;

	/** The server name. */
	private String serverName;
//rockyprince	
	/**
	 * Gets the single instance of MessageController.
	 *
	 * @return single instance of MessageController
	 */
	public static MessageController getInstance() {
		return INSTANCE;
	}
//rockyprince        
	/**
	 * Instantiates a new message controller.
	 */
	private MessageController() {
		server = XMPPServer.getInstance();
//		routingTable = server.getRoutingTable();
                packetRouter = server.getPacketRouter();
		serverName = server.getServerInfo().getXMPPDomain();
	}
//rockyprince
	/**
	 * Send broadcast message.
	 *
	 * @param messageEntity
	 *            the message entity
	 * @throws ServiceException
	 *             the service exception
	 */
	public void sendBroadcastMessage(MessageEntity messageEntity) throws ServiceException {
		if (messageEntity.getBody() != null && !messageEntity.getBody().isEmpty()) {
			SessionManager.getInstance().sendServerMessage(null, messageEntity.getBody());
		} else {
			throw new ServiceException("Message content/body is null or empty", "",
					ExceptionType.ILLEGAL_ARGUMENT_EXCEPTION,
					Response.Status.BAD_REQUEST);
		}
	}
//rockyprince        
	/**
	 * Send message to a user.
	 *
	 * @param messageEntity
	 *            the message entity
	 * @throws ServiceException
	 *             the service exception
	 */
	public void sendSingleMessage(MessageEntity messageEntity) throws ServiceException {
		if (messageEntity.getId() != null && !messageEntity.getId().isEmpty() && messageEntity.getFrom() != null && !messageEntity.getFrom().isEmpty() 
                        && messageEntity.getTos() != null && messageEntity.getBody() != null && !messageEntity.getBody().isEmpty()
                        && messageEntity.getExt() != null) {
                        JID from_jid = new JID(messageEntity.getFrom());
			sendMessageToUser(messageEntity.getId(), from_jid, messageEntity.getTos(), messageEntity.getSubject(), messageEntity.getBody(), messageEntity.getExt());
		} else {
			throw new ServiceException("Message id or from or to or content/body or ext is null or empty", "",
					ExceptionType.ILLEGAL_ARGUMENT_EXCEPTION,
					Response.Status.BAD_REQUEST);
		}
	}
        
        private void sendMessageToUser(String id, JID from, List<String> tos, String subject, String body, MessageExt ext) {
                Message packet = createMessageToUser(id, from, subject, body, ext);

                for ( String to : tos){
                        JID to_jid = new JID(to);
                        if (to_jid.getResource() != null
                           && SessionManager.getInstance().isActiveRoute(to_jid.getNode(), to_jid.getResource())) {
                             packet.setTo(to_jid);
                        }
                        else {
                             // resource is offline => send to bare JID
                             packet.setTo(new JID(to_jid.getNode(), serverName, null, true));
                        }
//                        routingTable.routePacket(new JID(to_jid.getNode(), serverName, null, true), packet, false);
//                        XMPPServer.getInstance().getMessageRouter().route(packet);
                        packetRouter.route(packet);
                }
        }
        
        private Message createMessageToUser(String id, JID from, String subject, String body, MessageExt ext) {
            Message message = new Message();
            PacketExtension extElement = new PacketExtension("ext", "");
            
            message.setID(id);
            message.setFrom(from);
            if (subject != null) {
                message.setSubject(subject);
            }
            message.setBody(body);
            message.setType(Message.Type.chat);
            if (ext != null){
                Element root = extElement.getElement();
                Element targetTypeElement = root.addElement("target_type");
                if (ext.getTarget_type() != null && !ext.getTarget_type().isEmpty()){
                    targetTypeElement.setText(ext.getTarget_type());
                } else {
                    targetTypeElement.setText("1");
                }
                List<String> at_users = ext.getAt_users();
                if (at_users != null){
                    for (String at_user : at_users) {
                        Element atUsersElement = root.addElement("at_users");
                        atUsersElement.setText(at_user);
                    }
                }
                Element timeStampElement = root.addElement("timestamp");
                if (ext.getTarget_type() != null && !ext.getTarget_type().isEmpty()){
                    timeStampElement.setText(ext.getTimestamp());
                } else {
                    Date date = new Date();
                    Long datetime = date.getTime();
                    timeStampElement.setText(datetime.toString());
                }
                
                if (ext.getLng() != null && !ext.getLng().isEmpty() && ext.getLat() != null && !ext.getLat().isEmpty()){
                    Element lngElement = root.addElement("lng");
                    lngElement.setText(ext.getLng());
                    Element latElement = root.addElement("lat");
                    latElement.setText(ext.getLat());
                }
                message.addExtension(extElement);
            }
            return message;
        }
}
//rockyprince