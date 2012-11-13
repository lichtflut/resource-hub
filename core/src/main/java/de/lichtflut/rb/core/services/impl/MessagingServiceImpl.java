/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import de.lichtflut.rb.core.config.EmailConfiguration;
import de.lichtflut.rb.core.config.RBConfig;
import de.lichtflut.rb.core.services.EmailService;
import de.lichtflut.rb.core.services.MessagingService;

/**
 * <p>
 *  Implementation of {@link MessagingService}
 * </p><p>
 * 	Created Jan 20, 2012
 * </p>
 * @author Ravi Knox
 */
public class MessagingServiceImpl implements MessagingService {

	private EmailService emailService;
	
	// ---------------- Constructor -------------------------
	
	public MessagingServiceImpl(RBConfig config){
		this.emailService = new EmailServiceImpl(config.getEmailConfiguration());
	}
	
	// ------------------------------------------------------
	
	@Override
	public EmailService getEmailService() {
		return emailService;
	}

}
