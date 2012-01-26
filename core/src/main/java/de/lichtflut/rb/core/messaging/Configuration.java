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

	public static final String CONFIG_EXT_RESOURCES_DIR = "com.innosquared.external-resources-dir";
	
	public static final String CONFIG_FULL_QUALIFIED_PAGE_PREFIX = "com.innosquared.full-qualified-application-prefix";
	
	public static final String SMTP_SERVER = "de.glasnost.smtp.server";
	
	public static final String SMTP_USER = "de.glasnost.smtp.user";
	
	public static final String SMTP_PASSWORD = "de.glasnost.smtp.password";
	
	public static final String CONFIG_INTERNAL_MESSAGE_RECEIVER = "com.innosquared.messaging.internal-message-receiver";
	
	public static final String CONFIG_REFERRAL_MESSAGE_OBSERVER = "com.innosquared.messaging.referral-message-observer";
	
	public static final String CONFIG_EXT_URL_I2_WEB_SITE = "com.innosquared.external-urls.web-site";
	
	public static final String CONFIG_EXT_URL_TAC = "com.innosquared.external-urls.tac";
	
	public static final String CONFIG_EXT_URL_FAQ = "com.innosquared.external-urls.faq";
	
	public static final String CONFIG_EXT_URL_PRIVACY = "com.innosquared.external-urls.privacy";
	
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
