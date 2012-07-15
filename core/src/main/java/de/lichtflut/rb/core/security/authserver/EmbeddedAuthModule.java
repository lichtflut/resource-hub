/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security.authserver;

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.context.SimpleContextID;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
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

    private static final String NAMESPACE_URI = "http://rb.lichtflut.de/embedded-auth/";

	private static final String CTX_NAMESPACE_URI = "http://rb.lichtflut.de/embedded-auth/contexts/";
	
	private static final Context IDENT = new SimpleContextID(CTX_NAMESPACE_URI, "IdentityManagement");

    // ----------------------------------------------------

    static final ResourceID USER = new SimpleResourceID(NAMESPACE_URI, "User");
    static final ResourceID DOMAIN = new SimpleResourceID(NAMESPACE_URI, "Domain");

    static final ResourceID IDENTIFIED_BY = new SimpleResourceID(NAMESPACE_URI, "isIdentifiedBy");
    static final ResourceID HAS_CREDENTIAL = new SimpleResourceID(NAMESPACE_URI, "hasCredential");
    static final ResourceID HAS_UNIQUE_NAME = new SimpleResourceID(NAMESPACE_URI, "hasUniqueName");
    static final ResourceID HAS_TITLE = new SimpleResourceID(NAMESPACE_URI, "hasTitle");
    static final ResourceID HAS_DESCRIPTION = new SimpleResourceID(NAMESPACE_URI, "hasDescription");
    static final ResourceID HAS_ROLE = new SimpleResourceID(NAMESPACE_URI, "hasRole");
    static final ResourceID HAS_PERMISSION = new SimpleResourceID(NAMESPACE_URI, "hasPermission");

    static final ResourceID BELONGS_TO_DOMAIN = new SimpleResourceID(NAMESPACE_URI, "belongsToDomain");
    static final ResourceID HAS_ALTERNATE_DOMAIN = new SimpleResourceID(NAMESPACE_URI, "hasAlternateDomain");

    static final ResourceID DEFINES_ROLE = new SimpleResourceID(NAMESPACE_URI, "definesRole");
	
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
		
		final ModelingConversation conversation = gate.startConversation();
		conversation.getConversationContext().setPrimaryContext(EmbeddedAuthModule.IDENT);
		conversation.getConversationContext().setReadContexts(EmbeddedAuthModule.IDENT);
		
		this.domainManager = new EmbeddedAuthDomainManager(conversation, initializer);
		this.userManager = new EmbeddedAuthUserManager(conversation, domainManager);
		this.loginService = new EmbeddedAuthLoginService(conversation);
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
