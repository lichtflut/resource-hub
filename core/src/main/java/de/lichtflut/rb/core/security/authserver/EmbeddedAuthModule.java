/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security.authserver;

import org.arastreju.sge.ArastrejuGate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.AuthenticationService;
import de.lichtflut.rb.core.security.DomainManager;
import de.lichtflut.rb.core.security.UserManager;

/**
 * <p>
 *  Authentication module using Arastreju as store.
 * </p>
 *
 * <p>
 * 	Created May 11, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class EmbeddedAuthModule implements AuthModule {

	private final Logger logger = LoggerFactory.getLogger(EmbeddedAuthUserManager.class);
	
	private final DomainManager domainManager;
	
	private final EmbeddedAuthUserManager userManager;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param gate The Arastreju Gate.
	 */
	public EmbeddedAuthModule(ArastrejuGate gate) {
		logger.info("Creating new Embedded Auth Module.");
		this.userManager = new EmbeddedAuthUserManager(gate, null);
		this.domainManager = new EmbeddedAuthDomainManager(gate);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public AuthenticationService getAuthenticationService() {
		return userManager;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public UserManager getUserManagement() {
		return userManager;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public DomainManager getDomainManager() {
		return domainManager;
	}

}
