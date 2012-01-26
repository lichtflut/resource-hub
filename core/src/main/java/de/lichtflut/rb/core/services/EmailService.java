/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import org.arastreju.sge.security.User;

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
	 * This method sends an email containing his new password.
	 * @param user
	 */
	void sendPasswordInformation(User user, String Password);
}
