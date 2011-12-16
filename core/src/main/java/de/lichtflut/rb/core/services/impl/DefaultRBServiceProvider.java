/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ArastrejuProfile;
import org.arastreju.sge.persistence.ResourceResolver;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.api.DomainOrganizer;
import de.lichtflut.rb.core.api.EntityManager;
import de.lichtflut.rb.core.api.SchemaManager;
import de.lichtflut.rb.core.api.SecurityService;
import de.lichtflut.rb.core.api.TypeManager;
import de.lichtflut.rb.core.api.impl.DomainOrganizerImpl;
import de.lichtflut.rb.core.api.impl.EntityManagerImpl;
import de.lichtflut.rb.core.api.impl.SchemaManagerImpl;
import de.lichtflut.rb.core.api.impl.TypeManagerImpl;
import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * <p>
 * Default implementation of {@link ServiceProvider}.
 * TODO: The rootContext of ArastrejuGate is used, this should be changed
 * </p>
 *
 * Created: Apr 28, 2011
 *
 * @author Nils Bleisch
 */
public class DefaultRBServiceProvider implements ServiceProvider {

    private ArastrejuGate gate;
    
    private SchemaManager schemaManager;
    private EntityManager entityManager;
    private TypeManager typeManager;
    private SecurityService service;
    private DomainOrganizer organizer;

    // --CONSTRUCTOR----------------------------------------

    /**
     * Constructor.
     * @param config The RBConfig.
     */
    public DefaultRBServiceProvider(final RBConfig config) {
        final ArastrejuProfile profile = config.getArastrejuConfiguration();
        gate = Arastreju.getInstance(profile).rootContext();
        schemaManager = new SchemaManagerImpl(this);
        entityManager = new EntityManagerImpl(this);
        typeManager = new TypeManagerImpl(this);
        organizer = new DomainOrganizerImpl(this);
    }

    // -----------------------------------------------------

    /**
     *{@inheritDoc}
     */
    @Override
    public ArastrejuGate getArastejuGate() {
        return gate;
    }

    // -----------------------------------------------------

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
    	return service;
    }
    
    // ----------------------------------------------------
    
    /** 
    * {@inheritDoc}
    */
    @Override
    public ResourceResolver getResourceResolver() {
    	return gate.startConversation();
    }
   
}
