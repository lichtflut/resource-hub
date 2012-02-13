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
	public String getSmtpServer();

	/**
	 * @return the smtpUser
	 */
	public String getSmtpUser();

	/**
	 * @return the smtpPassword
	 */
	public String getSmtpPassword();
	
	public String getApplicationSupportName();
	
	public String getApplicationSupportEmail();
	
	public String getApplicationName();
	
	public String getApplicationEmail();
}
