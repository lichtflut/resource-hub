/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.arastreju.sge.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.messaging.Configuration;
import de.lichtflut.rb.core.messaging.MessageDescription;
import de.lichtflut.rb.core.services.EmailService;

/**
 * <p>
 *  [DESCRIPTION]
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
	
	// ------------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void sendPasswordInformation(User user, String password, Locale locale) {
		MessageDescription desc = new MessageDescription(locale);
		desc.setType(MessageType.PASSWORD_INFORMATION_MAIL);
		desc.setRecipient(user.getEmail());
		desc.setRecipientName(user.getName());
		desc.setSenderName(Configuration.APPLICATION_SUPPORT_NAME);
		desc.setSender(Configuration.APPLICATION_SUPPORT_EMAIL);
		desc.setPassword(password);
		desc.setContent(TextModules.getMailFor(desc));
		sendMail(desc);
	}


	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void sendRegistrationConfirmation(User user, Locale locale) {
		MessageDescription desc = new MessageDescription(locale);
		desc.setType(MessageType.REGISTRATION_CONFIRMATION_MAIL);
		desc.setRecipient(user.getEmail());
		desc.setRecipientName(user.getName());
		desc.setSender(Configuration.APPLICATION_SUPPORT_EMAIL);
		desc.setSenderName(Configuration.APPLICATION_SUPPORT_NAME);
		desc.setContent(TextModules.getMailFor(desc));
		sendMail(desc);
	}
	

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void sendAccountActivatedInformation(User user, Locale locale) {
		MessageDescription desc = new MessageDescription(locale);
		desc.setType(MessageType.ACCOUNT_ACTIVATED_MAIL);
		desc.setRecipient(user.getEmail());
		desc.setRecipientName(user.getName());
		desc.setSender(Configuration.APPLICATION_SUPPORT_EMAIL);
		desc.setSenderName(Configuration.APPLICATION_SUPPORT_NAME);
		desc.setContent(TextModules.getMailFor(desc));
		sendMail(desc);
	}
	
	// ------------------------------------------------------
	
	protected Session initSession(){
		Properties props = new Properties();
		final Configuration cfg = Configuration.getInstance();
		fillSessionProperties(props, cfg);
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(cfg.getSmtpUser(), cfg.getSmtpPassword());
			}
		});
		logger.info("Initialized mail session: " + props);
		return session;
	}
	
	/**
	 * {@inheritDoc}
	 */
	private void sendMail(MessageDescription desc) {
		try {
			Message mail = new MimeMessage(initSession());
			mail.setFrom(new InternetAddress(desc.getSender(), desc.getSenderName()));
			mail.addRecipient(RecipientType.TO, new InternetAddress(desc.getRecipient(), desc.getRecipientName()));
			mail.setSubject(desc.getSubject());
			mail.setText(desc.getContent());
			// TODO ENABLE EMAIL 
//			Transport.send(mail);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Send email " + desc.getSubject() + " to " + desc.getRecipient());
	}

	private void fillSessionProperties(Properties props, Configuration cfg) {
		props.put("mail.smtp.host", cfg.getSmtpServer());
		props.setProperty("mail.smtp.port", "25");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.submitter", cfg.getSmtpUser());
	}

}
