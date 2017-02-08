package org.jivesoftware.openfire.plugin.rest.entity;

import java.util.List; //rockyprince
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType; //rockyprince

/**
 * The Class MessageEntity.
 */
@XmlRootElement(name = "message")
@XmlType(propOrder = { "id", "from", "tos", "subject", "body", "ext" }) //rockyprince
public class MessageEntity {
     
	/** The id. */
	private String id; //rockyprince
        
	/** The from JID. */
	private String from; //rockyprince
        
	/** The to JID. */
	private List<String> tos; //rockyprince
    
	/** The subject. */
	private String subject; //rockyprince
        
	/** The body. */
	private String body;

        /** The properties. */
	private MessageExt ext; //rockyprince
        
	/**
	 * Instantiates a new message entity.
	 */
	public MessageEntity() {
	}
        
        //rockyprince
	/**
	 * Instantiates a new user entity.
	 *
	 * @param id
	 *            the id
	 * @param from
	 *            the from
	 * @param tos
	 *            the tos
	 * @param subject
	 *            the subject
	 * @param body
	 *            the body
	 * @param ext
	 *            the ext
	 */
	public MessageEntity(String id, String from, List<String> tos, String subject, String body, MessageExt ext) {
		this.id = id;
		this.from = from;
		this.tos = tos;
		this.subject = subject;
		this.body = body;
                this.ext = ext;
	}

	/**
	 * Gets the from JID.
	 *
	 * @return the from JID
	 */
	@XmlElement
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
        
	/**
	 * Gets the from JID.
	 *
	 * @return the from JID
	 */
	@XmlElement
	public String getFrom() {
		return from;
	}

	/**
	 * Sets the from JID.
	 *
	 * @param from JID
	 *            the new from JID
	 */
	public void setFrom(String from) {
		this.from = from;
	}
        
	/**
	 * Gets the to JID.
	 *
	 * @return the to JID
	 */
	@XmlElement
	public List<String> getTos() {
		return tos;
	}

	/**
	 * Sets the tos JID.
	 *
	 * @param tos JID
	 *            the new tos JID
	 */
	public void setTos(List<String> tos) {
		this.tos = tos;
	}
        
	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	@XmlElement
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the subject.
	 *
	 * @param subject
	 *            the new subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
        //rockyprince
        
	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	@XmlElement
	public String getBody() {
		return body;
	}

	/**
	 * Sets the body.
	 *
	 * @param body
	 *            the new body
	 */
	public void setBody(String body) {
		this.body = body;
	}
        
        //rockyprince
	/**
	 * Gets the ext.
	 *
	 * @return the ext
	 */
	@XmlElement
	public MessageExt getExt() {
		return ext;
	}

	/**
	 * Sets the ext.
	 *
	 * @param ext
	 *            the new ext
	 */
	public void setExt(MessageExt ext) {
		this.ext = ext;
	}
        //rockyprince
}
