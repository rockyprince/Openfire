package org.jivesoftware.openfire.plugin.rest.entity;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class MessageExt. //rockyprince
 */
@XmlRootElement(name = "item")
@XmlType(propOrder = { "type", "value", "action", "order", "blockType" })
public class PrivacyListItem {

	/** The type. */
	private String type;

	/** The value. */
	private String value;

        /** The action. */
	private String action;

        /** The order. */
	private String order;
        
        /** The order. 1:message,2:presence-in,3:presence-out,4:iq*/
	private List<String> blockType;

	/**
	 * Instantiates a new privacy list item.
	 */
	public PrivacyListItem() {

	}

	/**
	 * Instantiates a new message ext.
	 *
	 * @param type
	 *            the type
	 * @param value
	 *            the value
	 * @param action
	 *            the action
	 * @param order
	 *            the order
	 * @param blockType
	 *            the blockType
	 */
	public PrivacyListItem(String type, String value, String action, String order, List<String> blockType) {
		this.type = type;
		this.value = value;
                this.action = action;
                this.order = order;
                this.blockType = blockType;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
        @XmlElement
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
        @XmlElement
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}
        
	/**
	 * Gets the action.
	 *
	 * @return the action
	 */
        @XmlElement
	public String getAction() {
		return action;
	}

	/**
	 * Sets the action.
	 *
	 * @param action
	 *            the new action
	 */
	public void setAction(String action) {
		this.action = action;
	}
        
	/**
	 * Gets the order.
	 *
	 * @return the order
	 */
        @XmlElement
	public String getOrder() {
		return order;
	}

	/**
	 * Sets the order.
	 *
	 * @param order
	 *            the new order
	 */
	public void setOrder(String order) {
		this.order = order;
	}
        
	/**
	 * Gets the blockType.
	 *
	 * @return the blockType
	 */
        @XmlElement
	public List<String> getBlockType() {
		return blockType;
	}

	/**
	 * Sets the blockType.
	 *
	 * @param blockType
	 *            the new blockType
	 */
	public void setBlockType(List<String> blockType) {
		this.blockType = blockType;
	}
}
