/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.services.SecurityService;
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
	public SecurityService getAuthenitcationService() {
		logger.info("creating new service provider");
		return new SecurityServiceImpl(new DefaultRBServiceProvider(config));
	}

}
