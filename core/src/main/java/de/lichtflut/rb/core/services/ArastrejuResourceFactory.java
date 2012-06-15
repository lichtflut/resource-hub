/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import org.arastreju.sge.ModelingConversation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *  Factory used by Spring to create Arastreju resources.
 * </p>
 *
 * <p>
 * 	Created Jun 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ArastrejuResourceFactory {
	
	private final static Logger logger = LoggerFactory.getLogger(ArastrejuResourceFactory.class);
	
	private final ServiceProvider provider;
	
	// ----------------------------------------------------
	
	/**
	 * @param provider
	 */
	public ArastrejuResourceFactory(ServiceProvider provider) {
		logger.info("ArastrejuResourceFactory has been created.");
		this.provider = provider;
	}
	
	// ----------------------------------------------------
	
	public ModelingConversation getConversation() {
		logger.info("Conversation has been requested.");
		return provider.getConversation();
	}
	
	// ----------------------------------------------------
	
	

}
