/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import java.util.Locale;

import de.lichtflut.rb.core.eh.RBException;
import de.lichtflut.rb.core.messaging.EmailConfiguration;
import de.lichtflut.rb.core.security.RBUser;

/**
 * <p>
 *  This service provides way to transmit messages via email.
 * </p><p>
 * 	Created Jan 20, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public interface EmailService {

	/**
	 * Sends an email containing a {@link RBUser}s password.
	 * @param user
	 * @param password
	 * @param locale
	 * @throws RBException 
	 */
	void sendPasswordInformation(RBUser user, String password, EmailConfiguration conf, Locale locale) throws RBException;

	/**
	 * Sends an email to inform a {@link RBUser} of a successful account creation.
	 * @param user
	 * @param locale 
	 * @throws RBException 
	 */
	void sendRegistrationConfirmation(RBUser user, EmailConfiguration conf, Locale locale) throws RBException;

	/**
	 * Sends an email to inform a {@link RBUser} that his account has been activated.
	 * @param user
	 * @param locale
	 * @throws RBException 
	 */
	void sendAccountActivatedInformation(RBUser user, EmailConfiguration conf, Locale locale) throws RBException;

}
