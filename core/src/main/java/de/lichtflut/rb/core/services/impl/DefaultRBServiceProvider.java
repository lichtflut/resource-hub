/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ArastrejuProfile;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.api.EntityManager;
import de.lichtflut.rb.core.api.SchemaManager;
import de.lichtflut.rb.core.api.TypeManager;
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
   
}
