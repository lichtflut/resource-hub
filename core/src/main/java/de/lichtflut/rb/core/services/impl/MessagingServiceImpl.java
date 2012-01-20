/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import de.lichtflut.rb.core.services.EmailService;
import de.lichtflut.rb.core.services.MessagingService;
import de.lichtflut.rb.core.services.ServiceProvider;

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
	
	public MessagingServiceImpl(ServiceProvider provider){
		this.emailService = new EmailServiceImpl(provider);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public EmailService getEmailService() {
		return emailService;
	}


}
