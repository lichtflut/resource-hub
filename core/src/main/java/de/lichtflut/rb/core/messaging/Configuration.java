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

	
	public static final String SMTP_SERVER = "de.glasnost.smtp.server";
	
	public static final String SMTP_USER = "de.glasnost.smtp.user";
	
	public static final String SMTP_PASSWORD = "de.glasnost.smtp.password";
	
	
	private static Configuration INSTANCE;
	
	// ---------------- Constructor -------------------------

	private Configuration(){}

	// ------------------------------------------------------

	public static Configuration getInstance(){
		if(INSTANCE == null){
			INSTANCE = new Configuration();
		}
		return INSTANCE;
	}

	public String getSmtpServer(){
		return System.getProperty(SMTP_SERVER, "smtp.glasnost.de");
	}

	public String getSmtpUser() {
		return System.getProperty(SMTP_USER, "noreply");
	}

	public String getSmtpPassword() {
		return System.getProperty("SMTP_PASSWORD", "password-noreply");
	}
	
}
