/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

/**
 * <p>
 *  This service provides different kinds of ways to communicate (email, facebook, twitter).
 * </p>
 *
 * <p>
 * 	Created Jan 20, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public interface MessagingService {

	/**
	 * @return the email service.
	 */
	EmailService getEmailService();
}
