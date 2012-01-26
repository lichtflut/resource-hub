/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.messaging;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Jan 26, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class TextModules {

	private Locale locale;

	// ---------------- Constructor -------------------------

	/**
	 * Default Constructor.
	 * {@link Locale} will be set to Engslish.
	 */
	public TextModules() {
		this.locale = Locale.ENGLISH;
	}

	/**
	 * Constructor.
	 * @param locale
	 */
	public TextModules(Locale locale){
		this.locale = locale;
	}

	public String getMailFor(MessageType type){
		String content = "";
		ResourceBundle bundle = ResourceBundle.getBundle("de/lichtflut/rb/core/messaging/EmailTextModules", locale);
		switch (type){
			case PASSWORD_INFORMATION_MAIL:
				content = bundle.getString("password-information");
				break;
		}
		return content;
	}
	
}
