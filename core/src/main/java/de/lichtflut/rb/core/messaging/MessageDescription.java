/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.core.messaging;

import java.util.Locale;


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
	private String password;
	private Locale locale;
	private MessageType type;

	// ---------------- Constructor -------------------------
	
	/**
	 * Constructor.
	 * @param locale - if locale is <code>null</code>, locale will be set to Locale.ENGLISH
	 */
	public MessageDescription(Locale locale){
		if(locale == null){
			locale = Locale.ENGLISH;
		}
		this.locale = locale;
	}
	
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

	/**
	 * @return the type
	 */
	public MessageType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(MessageType type) {
		this.type = type;
	}

	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return
	 */
	public Locale getLocale() {
		return locale;
	}
	
}
