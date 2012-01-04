/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ArastrejuProfile;
import org.arastreju.sge.persistence.ResourceResolver;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.services.TypeManager;

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
    private SecurityService securityService;
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
        securityService = new SecurityServiceImpl(this);
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
    	return securityService;
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
