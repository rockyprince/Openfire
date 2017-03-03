package org.jivesoftware.openfire.plugin.rest.entity;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class MessageExt. //rockyprince
 */
@XmlRootElement(name = "ext")
@XmlType(propOrder = {"from_id", "from_name", "at_name", "from_avater", "target_type", "at_users", "timestamp", "lng", "lat" })
public class MessageExt {

	/** The fromId. */
	private String fromId;
        
	/** The fromName. */
	private String fromName;
        
	/** The atName. */
	private String atName;
        
	/** The fromAvater. */
	private String fromAvater;
    
	/** The targetType. */
	private String targetType;

	/** The atUsers. */
	private List<String> atUsers;

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
	 * @param fromId
	 *            the fromId
	 * @param fromName
	 *            the fromName
	 * @param atName
	 *            the atName
	 * @param fromAvater
	 *            the fromAvater
	 * @param targetType
	 *            the targetType
	 * @param atUsers
	 *            the atUsers
	 * @param timestamp
	 *            the timestamp
	 * @param lng
	 *            the lng
	 * @param lat
	 *            the lat
	 */
	public MessageExt(String fromId, String fromName, String atName, String fromAvater, String targetType, List<String> atUsers, String timestamp, String lng, String lat) {
		this.fromId = fromId;
		this.fromName = fromName;
                this.atName = atName;
                this.fromAvater = fromAvater;
		this.targetType = targetType;
		this.atUsers = atUsers;
                this.timestamp = timestamp;
                this.lng = lng;
                this.lat = lat;
	}

	/**
	 * Gets the fromId.
	 *
	 * @return the fromId
	 */
        @XmlElement
	public String getFromId() {
		return fromId;
	}

	/**
	 * Sets the fromId.
	 *
	 * @param fromId
	 *            the new fromId
	 */
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	/**
	 * Gets the fromName.
	 *
	 * @return the fromName
	 */
        @XmlElement
	public String getFromName() {
		return fromName;
	}

	/**
	 * Sets the fromName.
	 *
	 * @param fromName
	 *            the new fromName
	 */
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
        
	/**
	 * Gets the atName.
	 *
	 * @return the atName
	 */
        @XmlElement
	public String getAtName() {
		return atName;
	}

	/**
	 * Sets the atName.
	 *
	 * @param atName
	 *            the new atName
	 */
	public void setAtName(String atName) {
		this.atName = atName;
	}
        
	/**
	 * Gets the fromAvater.
	 *
	 * @return the fromAvater
	 */
        @XmlElement
	public String getFromAvater() {
		return fromAvater;
	}

	/**
	 * Sets the fromAvater.
	 *
	 * @param fromAvater
	 *            the new fromAvater
	 */
	public void setFromAvater(String fromAvater) {
		this.fromAvater = fromAvater;
	}
        
	/**
	 * Gets the targetType.
	 *
	 * @return the targetType
	 */
        @XmlElement
	public String getTargetType() {
		return targetType;
	}

	/**
	 * Sets the targetType.
	 *
	 * @param targetType
	 *            the new targetType
	 */
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	/**
	 * Gets the atUsers.
	 *
	 * @return the atUsers
	 */
        @XmlElement
	public List<String> getAtUsers() {
		return atUsers;
	}

	/**
	 * Sets the atUsers.
	 *
	 * @param atUsers
	 *            the new atUsers
	 */
	public void setAtUsers(List<String> atUsers) {
		this.atUsers = atUsers;
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
