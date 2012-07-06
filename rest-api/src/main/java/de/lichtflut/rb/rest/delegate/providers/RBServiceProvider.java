/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.delegate.providers;

import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.SecurityConfiguration;
import de.lichtflut.rb.core.services.ArastrejuResourceFactory;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.core.services.impl.SchemaManagerImpl;
import de.lichtflut.rb.core.services.impl.SecurityServiceImpl;
import de.lichtflut.rb.core.services.impl.TypeManagerImpl;
import org.arastreju.sge.ModelingConversation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *  Abstract base for Service Providers.
 * </p>
 *
 * <p>
 * 	Created Jan 10, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBServiceProvider implements ServiceProvider {
	
	private final Logger logger = LoggerFactory.getLogger(RBServiceProvider.class);

	// ----------------------------------------------------

    private ArastrejuResourceFactory arastrejuResourceFactory;

	private ServiceContext ctx;

    private AuthModule authModule;

    private SecurityConfiguration securityConfiguration;

    // ----------------------------------------------------

	/**
	 * Constructor.
	 */
	public RBServiceProvider(ServiceContext ctx, ArastrejuResourceFactory factory,
                             AuthModule authModule, SecurityConfiguration securityConfiguration) {
		this.ctx = ctx;
        this.arastrejuResourceFactory = factory;
        this.authModule = authModule;
        this.securityConfiguration = securityConfiguration;
	}

    protected RBServiceProvider() { }

	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ModelingConversation getConversation() {
        return arastrejuResourceFactory.getConversation();
	}
	
	// ----------------------------------------------------
	
	/**
	 *{@inheritDoc}
	 */
	public ServiceContext getContext() {
		return ctx;
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public SchemaManager getSchemaManager() {
	    return new SchemaManagerImpl(getConversation());
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public SecurityService getSecurityService() {
        final SecurityServiceImpl service = new SecurityServiceImpl(getContext(), getConversation(), authModule);
        service.setSecurityConfiguration(securityConfiguration);
        return service;
	}

    @Override
    public TypeManager getTypeManager() {
        return new TypeManagerImpl(getConversation(), getSchemaManager());
    }

    // ----------------------------------------------------


    public void setArastrejuResourceFactory(ArastrejuResourceFactory arastrejuResourceFactory) {
        this.arastrejuResourceFactory = arastrejuResourceFactory;
    }

    public void setServiceContext(ServiceContext ctx) {
        this.ctx = ctx;
    }

    public void setAuthModule(AuthModule authModule) {
        this.authModule = authModule;
    }

    public void setSecurityConfiguration(SecurityConfiguration securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
    }
}