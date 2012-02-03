/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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

	public static String getMailFor(MessageType type, Locale locale){
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
