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

	private static TextModules INSTANCE;

	// ---------------- Constructor -------------------------

	/**
	 * Default Constructor.
	 */
	public TextModules(){}

	// ------------------------------------------------------

	public static TextModules getInstance(){
		if(INSTANCE == null){
			INSTANCE = new TextModules();
		}
		return INSTANCE;
	}

	/**
	 * The subject and content is set according to the {@link MessageType} provided by the {@link MessageDescription}.
	 * @param desc
	 * @param locale
	 */
	public void insertMailFor(final MessageDescription desc){
		String content = "";
		ResourceBundle bundle = getResourceBundle(desc.getLocale());
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
				break;
			default:
				break;
		}
		desc.setContent(content);
	}

	/**
	 * @param locale
	 * @return a {@link ResourceBundle} containing email related content
	 */
	protected ResourceBundle getResourceBundle(final Locale locale){
		return ResourceBundle.getBundle("de/lichtflut/rb/core/messaging/EmailTextModules", locale);
	}

	/**
	 * Replaces
	 * @param desc - {@link MessageDescription}
	 * @param content
	 */
	private String replaceVariables(final MessageDescription desc, String content) {
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
	private static String replace(final String old, String replacement, String content) {
		if(replacement == null){
			replacement = "";
		}
		content = content.replaceAll("\\$\\{" + old + "\\}", replacement);
		return content;
	}

}
