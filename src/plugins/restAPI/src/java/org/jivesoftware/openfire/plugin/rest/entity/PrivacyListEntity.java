package org.jivesoftware.openfire.plugin.rest.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class UserEntity. //rockyprince
 */
@XmlRootElement(name = "privacy_list")
@XmlType(propOrder = { "name", "items" })
public class PrivacyListEntity {

	/** The name. */
	private String name;

	/** The items. */
	private List<PrivacyListItem> items;

	/**
	 * Instantiates a new privacy list entity.
	 */
	public PrivacyListEntity() {

	}

	/**
	 * Instantiates a new privacy list entity.
	 *
	 * @param name
	 *            the name
	 * @param items
	 *            the items
	 */
	public PrivacyListEntity(String name, List<PrivacyListItem> items) {
		this.name = name;
		this.items = items;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@XmlElement
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	@XmlElement
	public List<PrivacyListItem> getItems() {
		return items;
	}

	/**
	 * Sets the items.
	 *
	 * @param items
	 *            the new items
	 */
	public void setItems(List<PrivacyListItem> items) {
		this.items = items;
	}

}
