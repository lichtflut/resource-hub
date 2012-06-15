/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *  Factory used by Spring to create RB resources.
 * </p>
 *
 * <p>
 * 	Created Jun 15, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBResourceFactory {
	
	private final static Logger logger = LoggerFactory.getLogger(RBResourceFactory.class);
	
	private final ServiceProvider provider;
	
	// ----------------------------------------------------
	
	/**
	 * @param provider
	 */
	public RBResourceFactory(ServiceProvider provider) {
		logger.info("ArastrejuResourceFactory has been created.");
		this.provider = provider;
	}
	
	// ----------------------------------------------------
	
	public EntityManager createEntityManager() {
		logger.info("EntityManager has been requested.");
		return provider.getEntityManager();
	}
	
	public TypeManager createTypeManager() {
		logger.info("TypeManager has been requested.");
		return provider.getTypeManager();
	}
	
	public SecurityService createSecurityService() {
		logger.info("SecurityService has been requested.");
		return provider.getSecurityService();
	}

}
