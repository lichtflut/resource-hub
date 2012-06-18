/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

/**
 * <p>
 *  Factory for {@link ServiceProvider}
 * </p>
 *
 * <p>
 * 	Created Jun 15, 2012
 * </p>
 *
 * @author Nils Bleisch
 */
public interface ServiceProviderFactory {

	public ServiceProvider createServiceProvider(ServiceContext context);
	
}
