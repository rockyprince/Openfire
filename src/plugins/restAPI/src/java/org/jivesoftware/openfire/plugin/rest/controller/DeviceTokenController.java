package org.jivesoftware.openfire.plugin.rest.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.ws.rs.core.Response;

import org.jivesoftware.openfire.plugin.rest.entity.DeviceTokenEntity;
import org.jivesoftware.openfire.plugin.rest.exceptions.ExceptionType;
import org.jivesoftware.openfire.plugin.rest.exceptions.ServiceException;
import org.jivesoftware.database.DbConnectionManager;

/**
 * The Class PrivacyListController. //rockyprince, the file
 */
public class DeviceTokenController {
	/** The Constant INSTANCE. */
	public static final DeviceTokenController INSTANCE = new DeviceTokenController();

        private static final String DEVICE_TOKEN_COUNT =
                "SELECT count(*) from ofAPNS";
        private static final String LOAD_DEVICE_TOKEN =
                "SELECT devicetoken FROM ofAPNS WHERE username=?";
        private static final String DELETE_DEVICE_TOKEN =
                "DELETE FROM ofAPNS WHERE username=?";
        private static final String UPDATE_DEVICE_TOKEN =
                "UPDATE ofAPNS SET devicetoken=? WHERE username=?";
        private static final String INSERT_DEVICE_TOKEN =
                "INSERT INTO ofAPNS (username, devicetoken) VALUES (?, ?)";
    
	/**
	 * Gets the single instance of DeviceTokenController.
	 *
	 * @return single instance of DeviceTokenController
	 */
	public static DeviceTokenController getInstance() {
		return INSTANCE;
	}
        
	/**
	 * Instantiates a new device token controller.
	 */
	private DeviceTokenController() {

	}
        
	/**
	 * Creates the device token.
	 *
	 * @param deviceTokenEntity
	 *            the device token entity
	 * @throws ServiceException
	 *             the service exception
	 */
	public void createDeviceToken(DeviceTokenEntity deviceTokenEntity) throws ServiceException {
		if (deviceTokenEntity != null) {
                        String username = deviceTokenEntity.getUsername();
                        
			if (username == null || username.isEmpty()) {
				throw new ServiceException("Could not create new device token, because username is null",
						"DeviceTokens", "UsernameIsNull", Response.Status.BAD_REQUEST);
			}
//                                    String deviceToken = null;
                        Connection con = null;
                        PreparedStatement pstmt = null;
                        ResultSet rs = null;
                        try {
                                con = DbConnectionManager.getConnection();
                                pstmt = con.prepareStatement(LOAD_DEVICE_TOKEN);
                                pstmt.setString(1, deviceTokenEntity.getUsername());
                                rs = pstmt.executeQuery();
                                if (rs.next()) {
//                                               deviceToken = rs.getString(1);
                                        pstmt = con.prepareStatement(UPDATE_DEVICE_TOKEN);
                                        pstmt.setString(1, username);
                                        pstmt.setString(2, deviceTokenEntity.getToken());
                                        pstmt.executeUpdate();
                                } else {
                                        pstmt = con.prepareStatement(INSERT_DEVICE_TOKEN);
                                        pstmt.setString(1, username);
                                        pstmt.setString(2, deviceTokenEntity.getToken());
                                        pstmt.executeUpdate();
                                }
                        }
                        catch (Exception e) {
                                e.printStackTrace();
                                throw new ServiceException("Could not create privacy list", username,
                                        e.toString(), Response.Status.CONFLICT);
                        }
                        finally {
                                DbConnectionManager.closeConnection(rs, pstmt, con);
                        }
		} else {
			throw new ServiceException("Could not create new device token",
					"DeviceTokens", ExceptionType.ILLEGAL_ARGUMENT_EXCEPTION, Response.Status.BAD_REQUEST);
		}
	}
        
	/**
	 * Delete the device token.
	 *
	 * @param username
	 *            the username
	 * @throws ServiceException
	 *             the service exception
	 */
	public void deleteDeviceToken(String username) throws ServiceException {
		if (username != null && !username.isEmpty()) {
                        Connection con = null;
                        PreparedStatement pstmt = null;
                        try {
                                con = DbConnectionManager.getConnection();
                                pstmt = con.prepareStatement(DELETE_DEVICE_TOKEN);
                                pstmt.setString(1, username);
                                pstmt.executeUpdate();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            throw new ServiceException("Could not delete device token", username,
        				ExceptionType.DEVICE_TOKEN_NOT_FOUND, Response.Status.NOT_FOUND);
                        }
                        finally {
                                DbConnectionManager.closeConnection(pstmt, con);
                        }
		} else {
			throw new ServiceException("Could not delete new device token",
					"DeviceTokens", ExceptionType.ILLEGAL_ARGUMENT_EXCEPTION, Response.Status.BAD_REQUEST);
		}
	}
        
        
}