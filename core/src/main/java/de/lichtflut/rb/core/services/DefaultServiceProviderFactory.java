/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.services.impl.DefaultRBServiceProvider;

/**
 * <p>
 *  Default Factory for {@link DefaultServiceProvider}
 * </p>
 *
 * <p>
 * 	Created Jun 15, 2012
 * </p>
 *
 * @author Nils Bleisch
 */
public final class DefaultServiceProviderFactory implements ServiceProviderFactory {

	
	private AuthModule authModule;
	private RBConfig config;
	
	public DefaultServiceProviderFactory(AuthModule authModule, RBConfig config){
		this.authModule = authModule;
		this.config = config;
	}
	
	/**
	 * 
	 * @return {@link ServiceProvider}
	 */
	@Override
	public ServiceProvider createServiceProvider(ServiceContext context){
		ServiceProvider provider = new DefaultRBServiceProvider(config, authModule);
		provider.getContext().setDomain(context.getDomain());
		provider.getContext().setUser(context.getUser());
		return provider;
	}
	
}
