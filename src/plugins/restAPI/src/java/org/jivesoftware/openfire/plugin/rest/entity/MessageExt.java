package org.jivesoftware.openfire.plugin.rest.entity;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class MessageExt. //rockyprince
 */
@XmlRootElement(name = "ext")
@XmlType(propOrder = { "target_type", "at_users", "timestamp", "lng", "lat" })
public class MessageExt {

	/** The key. */
	private String target_type;

	/** The value. */
	private List<String> at_users;

        /** The timestamp. */
	private String timestamp;
        
        /** The lng. */
	private String lng;
        
        /** The lat. */
	private String lat;

	/**
	 * Instantiates a new message ext.
	 */
	public MessageExt() {

	}

	/**
	 * Instantiates a new message ext.
	 *
	 * @param target_type
	 *            the target_type
	 * @param at_users
	 *            the at_users
	 * @param timestamp
	 *            the timestamp
	 * @param lng
	 *            the lng
	 * @param lat
	 *            the lat
	 */
	public MessageExt(String target_type, List<String> at_users, String timestamp, String lng, String lat) {
		this.target_type = target_type;
		this.at_users = at_users;
                this.timestamp = timestamp;
                this.lng = lng;
                this.lat = lat;
	}

	/**
	 * Gets the msg_type.
	 *
	 * @return the msg_type
	 */
        @XmlElement
	public String getTarget_type() {
		return target_type;
	}

	/**
	 * Sets the target_type.
	 *
	 * @param target_type
	 *            the new target_type
	 */
	public void setTarget_type(String target_type) {
		this.target_type = target_type;
	}

	/**
	 * Gets the at_users.
	 *
	 * @return the at_users
	 */
        @XmlElement
	public List<String> getAt_users() {
		return at_users;
	}

	/**
	 * Sets the at_users.
	 *
	 * @param at_users
	 *            the new at_users
	 */
	public void setAt_users(List<String> at_users) {
		this.at_users = at_users;
	}
        
	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
        @XmlElement
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp.
	 *
	 * @param timestamp
	 *            the new timestamp
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the lng.
	 *
	 * @return the lng
	 */
        @XmlElement
	public String getLng() {
		return lng;
	}

	/**
	 * Sets the lng.
	 *
	 * @param lng
	 *            the new lng
	 */
	public void setLng(String lng) {
		this.lng = lng;
	}
        
	/**
	 * Gets the lat.
	 *
	 * @return the lat
	 */
        @XmlElement
	public String getLat() {
		return lat;
	}

	/**
	 * Sets the lat.
	 *
	 * @param lat
	 *            the new lat
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}

}
