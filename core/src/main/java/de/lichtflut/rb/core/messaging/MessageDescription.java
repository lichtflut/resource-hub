/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.messaging;

/**
 * <p>
 *  This class wrappes various data needed for sending a message.
 * </p>
 * <p>
 * 	Created Jan 26, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class MessageDescription {
	
	private String charset = "UTF-8";
	
	private String subject;
	
	private String content;
	
	private String sender;
	
	private String recipient;
	
	private String recipientName;
	
	private String senderName;
	
	// ----------------------------------------------------
	
	/**
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}
	
	/**
	 * @param charset the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}
	
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}
	
	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	/**
	 * @return the recipient
	 */
	public String getRecipient() {
		return recipient;
	}
	
	/**
	 * @param recipient the recipient to set
	 */
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	/**
	 * @return the recipientName
	 */
	public String getRecipientName() {
		return recipientName;
	}
	
	/**
	 * @param recipientName the recipientName to set
	 */
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	
	/**
	 * @return the senderName
	 */
	public String getSenderName() {
		return senderName;
	}
	
	/**
	 * @param senderName the senderName to set
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	
}
