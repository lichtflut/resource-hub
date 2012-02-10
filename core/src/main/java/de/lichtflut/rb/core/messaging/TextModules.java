/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.messaging;

import java.util.ResourceBundle;

/**
 * <p>
 *  Various predefined Email texts can be found here.
 * </p>
 *
 * <p>
 * 	Created Jan 26, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class TextModules {

	private static final String RECIPIENT = "recipient";
	private static final String PASSWORD = "password";
	private static final String SENDER = "sender";
	
	// ------------------------------------------------------

	/**
	 * The subject and content is set according to the {@link MessageType} provided by the {@link MessageDescription}.
	 * @param desc
	 * @param locale
	 * @return an Emailtext for a given Locale.
	 */
	public static String getMailFor(MessageDescription desc){
		String content = "";
		ResourceBundle bundle = ResourceBundle.getBundle("de/lichtflut/rb/core/messaging/EmailTextModules", desc.getLocale());
		switch (desc.getType()){
			case PASSWORD_INFORMATION_MAIL:
				content = bundle.getString("password-information");
				content = replaceVariables(desc, content);
				desc.setSubject(bundle.getString("password-information-subject"));
				break;
			case REGISTRATION_CONFIRMATION_MAIL:
				content = bundle.getString("registration-confirmation");
				content = replaceVariables(desc, content);
				desc.setSubject(bundle.getString("registration-confirmation-subject"));
				break;
			case ACCOUNT_ACTIVATED_MAIL:
				content = bundle.getString("account-activated-information");
				content = replaceVariables(desc, content);
				desc.setSubject(bundle.getString("account-activated-information-subject"));
		}
		desc.setContent(content);
		return content;
	}

	/**
	 * Replaces
	 * @param desc - {@link MessageDescription}
	 * @param content
	 */
	private static String replaceVariables(MessageDescription desc, String content) {
		content = replace(RECIPIENT, desc.getRecipientName(), content);
		content = replace(PASSWORD, desc.getPassword(), content);
		content = replace(SENDER, desc.getSenderName(), content);
		return content;
	}

	/**
	 * @param desc
	 * @param content
	 * @return
	 */
	private static String replace(String old, String replacement, String content) {
		if(replacement == null){
			replacement = "";
		}
		content = content.replaceAll("\\$\\{" + old + "\\}", replacement);
		return content;
	}
	
}
