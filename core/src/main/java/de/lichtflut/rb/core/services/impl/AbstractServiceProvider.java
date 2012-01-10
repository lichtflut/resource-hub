/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import org.arastreju.sge.persistence.ResourceResolver;

import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.services.TypeManager;

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

	private SchemaManager schemaManager;
	private EntityManager entityManager;
	private TypeManager typeManager;
	private SecurityService securityService;
	private DomainOrganizer organizer;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 */
	public AbstractServiceProvider() {
		schemaManager = new SchemaManagerImpl(this);
		entityManager = new EntityManagerImpl(this);
		typeManager = new TypeManagerImpl(this);
		organizer = new DomainOrganizerImpl(this);
		securityService = new SecurityServiceImpl(this);
	}

	// ----------------------------------------------------
	
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
	public DomainOrganizer getDomainOrganizer() {
		return organizer;
	}

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

}