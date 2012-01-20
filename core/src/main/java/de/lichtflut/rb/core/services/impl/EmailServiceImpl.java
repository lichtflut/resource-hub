/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import org.arastreju.sge.security.User;

import de.lichtflut.rb.core.services.EmailService;
import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Jan 20, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class EmailServiceImpl implements EmailService {

	/**
	 * @param provider
	 */
	public EmailServiceImpl(ServiceProvider provider) {
		// TODO Auto-generated constructor stub
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void sendNewPasswordforUser(User user) {
		System.out.println("########## sent new password for user " + user.getEmail() + "#############");
	}

}
