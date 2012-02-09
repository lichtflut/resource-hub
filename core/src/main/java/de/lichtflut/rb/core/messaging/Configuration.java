/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.messaging;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Jan 23, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class Configuration {

	
	private static final String SMTP_SERVER = "de.glasnost.smtp.server";
	
	private static final String SMTP_USER = "de.glasnost.smtp.user";
	
	private static final String SMTP_PASSWORD = "de.glasnost.smtp.password";
	
	public static final String APPLICATION_SUPPORT_NAME = "Glasnost Support Team";
	
	public static final String APPLICATION_SUPPORT_EMAIL = "support@glasnost.de";
	
	private static Configuration INSTANCE;
	
	// ---------------- Constructor -------------------------

	/**
	 * Hide Constructor.
	 */
	private Configuration(){}

	// ------------------------------------------------------

	/**
	 * @return the conf
	 */
	public Configuration getConf() {
		return INSTANCE;
	}
	
	/**
	 * @return
	 */
	public static Configuration getInstance() {
		if(INSTANCE == null){
			INSTANCE = new Configuration();
		}
		return INSTANCE;
	}

	/**
	 * @return the smtpServer
	 */
	public String getSmtpServer() {
		return System.getProperty(SMTP_SERVER, "smtp.glasnost.de");
	}

	/**
	 * @return the smtpUser
	 */
	public String getSmtpUser() {
		 return System.getProperty(SMTP_USER, "noreply");
	}

	/**
	 * @return the smtpPassword
	 */
	public String getSmtpPassword() {
		return System.getProperty(SMTP_PASSWORD, "password-noreply");
	}

}
