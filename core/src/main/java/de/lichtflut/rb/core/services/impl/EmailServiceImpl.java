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
package de.lichtflut.rb.core.services.impl;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.eh.RBException;
import de.lichtflut.rb.core.config.EmailConfiguration;
import de.lichtflut.rb.core.messaging.MessageDescription;
import de.lichtflut.rb.core.messaging.MessageType;
import de.lichtflut.rb.core.messaging.TextModules;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.EmailService;

/**
 * <p>
 *  Implementation od {@link EmailService}.
 * </p>
 *
 * <p>
 * 	Created Jan 20, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class EmailServiceImpl implements EmailService {

	private final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	private final EmailConfiguration emailConf;
	
	// ---------------- Constructor -------------------------
	
	public EmailServiceImpl(EmailConfiguration conf){
		this.emailConf = conf;
	}
	
	// ------------------------------------------------------

	/** 
	 * {@inheritDoc}
	 * @throws RBException 
	 */
	@Override
	public void sendPasswordInformation(RBUser user, String password, Locale locale) throws RBException {
		MessageDescription desc = new MessageDescription(locale);
		desc.setType(MessageType.PASSWORD_INFORMATION_MAIL);
		desc.setRecipient(user.getEmail());
		desc.setRecipientName(user.getName());
		desc.setSender(emailConf.getApplicationEmail());
		desc.setSenderName(emailConf.getApplicationSupportName());
		desc.setPassword(password);
		new TextModules().insertMailFor(desc);
		sendMail(desc, emailConf);
	}


	/** 
	 * {@inheritDoc}
	 * @throws RBException 
	 */
	@Override
	public void sendRegistrationConfirmation(RBUser user, Locale locale) throws RBException {
		MessageDescription desc = new MessageDescription(locale);
		desc.setType(MessageType.REGISTRATION_CONFIRMATION_MAIL);
		desc.setRecipient(user.getEmail());
		desc.setRecipientName(user.getName());
		desc.setSender(emailConf.getApplicationSupportEmail());
		desc.setSenderName(emailConf.getApplicationSupportName());
		new TextModules().insertMailFor(desc);
		sendMail(desc, emailConf);
	}

	/** 
	 * {@inheritDoc}
	 * @throws RBException 
	 */
	@Override
	public void sendAccountActivatedInformation(RBUser user, Locale locale) throws RBException {
		MessageDescription desc = new MessageDescription(locale);
		desc.setType(MessageType.ACCOUNT_ACTIVATED_MAIL);
		desc.setRecipient(user.getEmail());
		desc.setRecipientName(user.getName());
		new TextModules().insertMailFor(desc);
		sendMail(desc, emailConf);
	}
	
	// ------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 * @throws RBException 
	 */
	private void sendMail(MessageDescription desc, EmailConfiguration conf) throws RBException {
		try {
			Message mail = new MimeMessage(initSession(conf));
			mail.setFrom(new InternetAddress(desc.getSender(), desc.getSenderName()));
			mail.addRecipient(RecipientType.TO, new InternetAddress(desc.getRecipient(), desc.getRecipientName()));
			mail.setSubject(desc.getSubject());
			mail.setText(desc.getContent());
			Transport.send(mail);
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException");
			throw new RBException(ErrorCodes.EMAIL_SERVICE_EXCEPTION, "UnsupportedEncodingException", e);
		} catch (MessagingException e) {
			logger.error("MessagingException");
			throw new RBException(ErrorCodes.EMAIL_SERVICE_EXCEPTION, "MessagingException", e);
		}
		logger.info("Send email '" + desc.getSubject() + "' from " + desc.getSender() + " to " + desc.getRecipient());
	}
	
	protected Session initSession(final EmailConfiguration conf){
		Properties props = new Properties();
		fillSessionProperties(props, conf);
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(conf.getSmtpUser(), conf.getSmtpPassword());
			}
		});
		logger.info("Initialized mail session: " + props);
		return session;
	}
	
	private void fillSessionProperties(Properties props, EmailConfiguration cfg) {
		props.put("mail.smtp.host", cfg.getSmtpServer());
		props.setProperty("mail.smtp.port", "25");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.submitter", cfg.getSmtpUser());
	}

}
