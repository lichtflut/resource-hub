/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import java.util.Locale;

import org.arastreju.sge.security.User;

import de.lichtflut.rb.core.messaging.EmailConfiguration;

/**
 * <p>
 *  This service provides way to transmit stuff via email.
 * </p><p>
 * 	Created Jan 20, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public interface EmailService {

	/**
	 * Sends an email containing a {@link User}s password.
	 * @param user
	 * @param password
	 * @param locale
	 */
	void sendPasswordInformation(User user, String password, EmailConfiguration conf, Locale locale);

	/**
	 * Sends an email to inform a {@link User} of a successful account creation.
	 * @param user
	 * @param locale 
	 */
	void sendRegistrationConfirmation(User user, EmailConfiguration conf, Locale locale);

	/**
	 * Sends an email to inform a {@link User} that his account has been activated.
	 * @param user
	 * @param locale
	 */
	void sendAccountActivatedInformation(User user, EmailConfiguration conf, Locale locale);

}
