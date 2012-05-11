/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import org.arastreju.sge.Arastreju;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.AuthenticationService;
import de.lichtflut.rb.core.security.authserver.EmbeddedAuthModule;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.services.ServiceProviderFactory;

/**
 * <p>
 *  Factory for Service Providers.
 * </p>
 *
 * <p>
 * 	Created Jan 10, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class DefaultServiceProviderFactory implements ServiceProviderFactory {
	
	private final Logger logger = LoggerFactory.getLogger(DefaultServiceProviderFactory.class);
	
	private final RBConfig config;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param config The RB configuration.
	 */
	public DefaultServiceProviderFactory(final RBConfig config) {
		logger.info("initializing service provider factory with config " + config);
		this.config = config;
	}

	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ServiceProvider create() {
		logger.info("creating new service provider");
		return new DefaultRBServiceProvider(config);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public AuthenticationService createAuthenticationService() {
		logger.info("creating new auth service");
		return createAuthModule().getAuthenticationService();
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public AuthModule createAuthModule() {
		logger.info("creating new auth module");
		final Arastreju aras = Arastreju.getInstance(config.getArastrejuConfiguration());
		return new EmbeddedAuthModule(aras.rootContext());
	}

}
