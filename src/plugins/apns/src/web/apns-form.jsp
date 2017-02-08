<%--
  -	$Revision: $
  -	$Date: $
  -
  - Copyright (C) 2005-2008 Jive Software. All rights reserved.
  -
  - Licensed under the Apache License, Version 2.0 (the "License");
  - you may not use this file except in compliance with the License.
  - You may obtain a copy of the License at
  -
  -     http://www.apache.org/licenses/LICENSE-2.0
  -
  - Unless required by applicable law or agreed to in writing, software
  - distributed under the License is distributed on an "AS IS" BASIS,
  - WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  - See the License for the specific language governing permissions and
  - limitations under the License.

--%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.*,
                 com.rockyprince.Apns,
                 org.jivesoftware.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>

<%
    boolean save = request.getParameter("save") != null;
    boolean success = request.getParameter("success") != null;
    
    //APNS options
    boolean serviceEnabled = ParamUtils.getBooleanParameter(request, "service_enabled");
    String sound =  ParamUtils.getParameter(request, "sound");
    String certPath =  ParamUtils.getParameter(request, "certpath");
    String certKey =  ParamUtils.getParameter(request, "certkey");
    List<String> modeOptions = Arrays.asList(ParamUtils.getParameters(request, "modecb"));
    boolean productEnabled = modeOptions.contains("productenabled");
    boolean debugEnabled = modeOptions.contains("debugenabled");

    //input validation
    Map<String, String> errors = new HashMap<String, String>();
    
    if (save) {
        if (serviceEnabled) {

            if (sound == null || sound.isEmpty()) {
                errors.put("missingSound", "missingSound");
            }

            if (certPath == null || certPath.isEmpty()) {
                errors.put("missingCertPath", "missingCertPath");
            }

            if (certKey == null || certKey.isEmpty()) {
                errors.put("missingCertKey", "missingCertKey");
            }
        }
        if (errors.size() == 0) {
            JiveGlobals.setProperty(Apns.APNS_SERVICE_ENABLED_PROPERTY, serviceEnabled ? "true" : "false");
            JiveGlobals.setProperty(Apns.APNS_NOTIFICATION_SOUND_PROPERTY, sound);
            JiveGlobals.setProperty(Apns.APNS_CERT_PATH_PROPERTY, certPath);
            JiveGlobals.setProperty(Apns.APNS_CERT_KEY_PROPERTY, certKey);
            JiveGlobals.setProperty(Apns.APNS_IS_PRODUCTION_PROPERTY, productEnabled ? "true" : "false");
            JiveGlobals.setProperty(Apns.APNS_DEBUG_ENABLED_PROPERTY, debugEnabled ? "true" : "false");
            response.sendRedirect("apns-form.jsp?success=true");
            return;
        }
    } else {
        sound = JiveGlobals.getProperty(Apns.APNS_NOTIFICATION_SOUND_PROPERTY, "default"); //Apns.getNotificationSound();
        certPath = JiveGlobals.getProperty(Apns.APNS_CERT_PATH_PROPERTY, ""); //Apns.getCertPath();
        certKey = JiveGlobals.getProperty(Apns.APNS_CERT_KEY_PROPERTY, ""); //Apns.getCertKey();
    }

    if (errors.size() == 0) {
        sound = JiveGlobals.getProperty(Apns.APNS_NOTIFICATION_SOUND_PROPERTY, "default"); //Apns.getNotificationSound();
        certPath = JiveGlobals.getProperty(Apns.APNS_CERT_PATH_PROPERTY, ""); //Apns.getCertPath();
        certKey = JiveGlobals.getProperty(Apns.APNS_CERT_KEY_PROPERTY, ""); //Apns.getCertKey();
        serviceEnabled = JiveGlobals.getBooleanProperty(Apns.APNS_SERVICE_ENABLED_PROPERTY, false); //Apns.isServiceEnabled();
        productEnabled = JiveGlobals.getBooleanProperty(Apns.APNS_IS_PRODUCTION_PROPERTY, false); //Apns.isProductEnabled();
        debugEnabled = JiveGlobals.getBooleanProperty(Apns.APNS_DEBUG_ENABLED_PROPERTY, false); //Apns.isDebugEnabled();
    }

%>

<html>
	<head>
		<title>ANPS</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="pageID" content="apns" />
	</head>
        <body>

<p>
Use the form below to edit APNS settings.<br>
</p>

<%  if (success) { %>

    <div class="jive-success">
    <table cellpadding="0" cellspacing="0" border="0">
    <tbody>
        <tr>
	        <td class="jive-icon"><img src="images/success-16x16.gif" width="16" height="16" border="0" alt=""></td>
	        <td class="jive-icon-label">Settings updated successfully.</td>
        </tr>
    </tbody>
    </table>
    </div><br>

<%  } else if (errors.size() > 0) { %>

    <div class="jive-error">
    <table cellpadding="0" cellspacing="0" border="0">
    <tbody>
        <tr>
        	<td class="jive-icon"><img src="images/error-16x16.gif" width="16" height="16" border="0" alt=""></td>
        	<td class="jive-icon-label">Error saving the settings.</td>
        </tr>
    </tbody>
    </table>
    </div><br>

<%  } %>

<form action="apns-form.jsp" method="post">

<fieldset>
    <legend>APNS service</legend>
    <div>
    
    <p>
    To enable the APNS you need to set up some regular configurations.
    </p>
    
    <table cellpadding="3" cellspacing="0" border="0" width="100%">
    <tbody>
    <tr>
        <td width="1%">
            <input type="radio" name="service_enabled" value="false" id="not01"
             <%= ((serviceEnabled) ? "" : "checked") %>>
        </td>
        <td width="99%">
            <label for="not01"><b>Disabled</b></label> - Packets will not be notificated by APNS.
        </td>
    </tr>
    <tr>
        <td width="1%">
            <input type="radio" name="service_enabled" value="true" id="not02"
             <%= ((serviceEnabled) ? "checked" : "") %>>
        </td>
        <td width="99%">
            <label for="not02"><b>Enabled</b></label> - Packets will be notificated by APNS.
        </td>
    </tr>

    <tr>
       	<td>&nbsp;</td>
        <td align="left">Sound:&nbsp;
	    <input type="text" size="100" maxlength="100" name="sound" value="<%= (sound != null ? sound : "") %>">
	      	<% if (errors.containsKey("missingSound")) { %>
	        <span class="jive-error-text">
	            <br>Please enter a sound.
	        </span>
	        <% } %>
	</td>
    </tr>
    <tr>
       	<td>&nbsp;</td>
        <td align="left">Certification Path:&nbsp;
	    <input type="text" size="100" maxlength="100" name="certpath" value="<%= (certPath != null ? certPath : "") %>">
	      	<% if (errors.containsKey("missingCertPath")) { %>
	        <span class="jive-error-text">
	            <br>Please enter a certification path.
	        </span>
	        <% } %>
	</td>
    </tr>
    <tr>
       	<td>&nbsp;</td>
        <td align="left">Certification Password:&nbsp;
	    <input type="text" size="100" maxlength="100" name="certkey" value="<%= (certKey != null ? certKey : "") %>">
	      	<% if (errors.containsKey("missingCertKey")) { %>
	        <span class="jive-error-text">
	            <br>Please enter a certification password.
	        </span>
	        <% } %>
	</td>
    </tr>
    <tr>
	<td>&nbsp;</td>
        <td>
            <input type="checkbox" name="modecb" value="productenabled" <%= productEnabled ? "checked" : "" %>/>Enable product mode.
            <input type="checkbox" name="modecb" value="debugenabled" <%= debugEnabled ? "checked" : "" %>/>Enable debug log.
        </td>
    </tr>

    </tbody>
    </table>
    </div>
</fieldset>

<br><br>

<input type="submit" name="save" value="Save settings">
</form>

<br><br>

</body>
</html>
