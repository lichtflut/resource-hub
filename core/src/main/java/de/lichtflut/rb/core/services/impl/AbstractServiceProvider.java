/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import org.arastreju.sge.persistence.ResourceResolver;

import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.MessagingService;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.core.services.ViewSpecificationService;

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
public abstract class AbstractServiceProvider implements ServiceProvider{

	private final ServiceContext ctx;
	
	private SchemaManager schemaManager;
	private EntityManager entityManager;
	private TypeManager typeManager;
	private SecurityService securityService;
	private MessagingService messagingService;
	private ViewSpecificationService viewSpecService;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 */
	public AbstractServiceProvider(ServiceContext ctx) {
		this.ctx = ctx;
		schemaManager = new SchemaManagerImpl(this);
		entityManager = new EntityManagerImpl(this);
		typeManager = new TypeManagerImpl(this);
		messagingService = new MessagingServiceImpl(this);
		securityService = newSecurityService();
		viewSpecService = new ViewSpecificationServiceImpl(this);
	}

	// ----------------------------------------------------
	
	/**
	 *{@inheritDoc}
	 */
	public ServiceContext getContext() {
		return ctx;
	};
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public EntityManager getEntityManager() {
 	    return entityManager;
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public SchemaManager getSchemaManager() {
	    return schemaManager;
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public TypeManager getTypeManager() {
		return typeManager;
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public abstract DomainOrganizer getDomainOrganizer();

	/** 
	* {@inheritDoc}
	*/
	@Override
	public SecurityService getSecurityService() {
		return securityService;
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public ResourceResolver getResourceResolver() {
		return getArastejuGate().startConversation();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public MessagingService getMessagingService(){
		return messagingService;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ViewSpecificationService getViewSpecificationService() {
		return viewSpecService;
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return The security service.
	 */
	protected SecurityService newSecurityService() {
		return new SecurityServiceImpl(this);
	}

}