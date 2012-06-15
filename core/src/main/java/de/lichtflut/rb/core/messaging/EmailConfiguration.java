/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.messaging;

import de.lichtflut.rb.core.services.EmailService;

/**
 * <p>
 *  This interface provides the {@link EmailService} with all neccessary informations concerning Email 
 * </p>
 *
 * <p>
 * 	Created Jan 23, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public interface EmailConfiguration {

	/**
	 * @return the smtpServer
	 */
	String getSmtpServer();

	/**
	 * @return the smtpUser
	 */
	String getSmtpUser();

	/**
	 * @return the smtpPassword
	 */
	String getSmtpPassword();
	
	String getApplicationSupportName();
	
	String getApplicationSupportEmail();
	
	String getApplicationName();
	
	String getApplicationEmail();
}
