package org.jivesoftware.openfire.plugin.rest.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class DeviceTokenEntity. //rockyprince
 */
@XmlRootElement(name = "device_token")
@XmlType(propOrder = { "username", "token" })
public class DeviceTokenEntity {

	/** The name. */
	private String username;

	/** The token. */
	private String token;

	/**
	 * Instantiates a new device token entity.
	 */
	public DeviceTokenEntity() {

	}

	/**
	 * Instantiates a new device token entity.
	 *
	 * @param username
	 *            the username
	 * @param token
	 *            the token
	 */
	public DeviceTokenEntity(String username, String token) {
		this.username = username;
		this.token = token;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	@XmlElement
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username
	 *            the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	@XmlElement
	public String getToken() {
		return token;
	}

	/**
	 * Sets the token.
	 *
	 * @param token
	 *            the new token
	 */
	public void setToken(String token) {
		this.token = token;
	}

}
