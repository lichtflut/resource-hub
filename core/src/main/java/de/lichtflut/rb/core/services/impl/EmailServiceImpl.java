/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.arastreju.sge.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public abstract class EmailServiceImpl implements EmailService {

	private final String DEFAULT_SENDER = getDefaultSender();

	private final String MESSAGE_ENCODING = getMessageEncoding();

	private static final String DEFAULT_SENDER_TEXT = "lichtflut";

	private final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	// ------------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void sendNewPasswordforUser(User user) {
		Session session = initSession();
		String msg = ResourceBundle.getBundle("EmailServiceImpl").getString("reset-password");
		System.out.println(" ################ " + msg);
		try {
			Message mail = new MimeMessage(session);
			mail.setFrom(new InternetAddress("rknox@lichtflut.de", "lichtflut.de - Ravi Knox"));
			mail.addRecipient(RecipientType.TO, new InternetAddress("recipient@google.com", "Reci Pient"));
			mail.setSubject("Test");
			mail.setText("Dies ist eine Testmail");
			Transport.send(mail);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("########## sent new password for user " + user.getEmail() + "#############");
	}

	protected abstract String getDefaultSender();
	
	protected String getMessageEncoding(){
		return "UTF-8";
	}

	protected Session initSession(){
		Properties props = new Properties();
		Session session = Session.getInstance(props, null);
		Configuration cfg = Configuration.INSTANCE;
		fillSessionProperties(props, cfg);
		logger.info("Initialized mail session: " + props);
		return session;
	}

	/**
	 * @param props
	 * @param cfg
	 */
	private void fillSessionProperties(Properties props, Configuration cfg) {
		props.put("mail.smtp.host", cfg.getSmtpServer());
		props.setProperty("mail.smtp.port", "25");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.submitter", cfg.getSmtpUser());
	}


}
