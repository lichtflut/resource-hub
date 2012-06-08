/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security.authserver;

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.context.SimpleContextID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.AuthenticationService;
import de.lichtflut.rb.core.security.DomainInitializer;
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
	
	public static final String CTX_NAMESPACE_URI = "http://rb.lichtflut.de/embedded-auth/contexts";
	
	public static final String ROOT_USER = "root";
	
	public static final Context IDENT = new SimpleContextID(CTX_NAMESPACE_URI, "IdentityManagement");
	
	// ----------------------------------------------------

	private final Logger logger = LoggerFactory.getLogger(EmbeddedAuthModule.class);
	
	private final EmbeddedAuthDomainManager domainManager;
	
	private final EmbeddedAuthUserManager userManager;
	
	private final EmbeddedAuthLoginService loginService;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param gate The Arastreju Gate.
	 */
	public EmbeddedAuthModule(ArastrejuGate gate) {
		this(gate, null);
	}
	
	/**
	 * Constructor.
	 * @param gate The Arastreju Gate.
	 */
	public EmbeddedAuthModule(ArastrejuGate gate, DomainInitializer initializer) {
		if (initializer != null) {
			logger.info("Creating new Embedded Auth Module with initializer " + initializer.getClass().getSimpleName());
		} else {
			logger.info("Creating new Embedded Auth Module without initializer.");
		}
		this.domainManager = new EmbeddedAuthDomainManager(gate, initializer);
		this.userManager = new EmbeddedAuthUserManager(gate, domainManager);
		this.loginService = new EmbeddedAuthLoginService(gate.startConversation());
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public AuthenticationService getAuthenticationService() {
		return loginService;
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
