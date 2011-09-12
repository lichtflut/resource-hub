/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.spi.impl;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ArastrejuProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.api.ISchemaManagement;
import de.lichtflut.rb.core.api.RBEntityManager;
import de.lichtflut.rb.core.api.impl.RBEntityManagerImpl;
import de.lichtflut.rb.core.api.impl.ResourceSchemaManagement;
import de.lichtflut.rb.core.spi.IRBServiceProvider;

/**
 * Reference implementation of {@link IRBServiceProvider}.
 * TODO: The rootContext of ArastrejuGate is used, this should be changed
 *
 * Created: Apr 28, 2011
 *
 * @author Nils Bleisch
 */
public class DefaultRBServiceProvider implements IRBServiceProvider {

    private final Logger logger = LoggerFactory
            .getLogger(DefaultRBServiceProvider.class);

    private ArastrejuGate gate = null;
    private ISchemaManagement schemaManagement = null;
    private RBEntityManager typeManagement = null;

    // --CONSTRUCTOR----------------------------------------

    /**
     * Default constructor.
     */
    public DefaultRBServiceProvider() {
        this(new RBConfig());
    }

    /**
     * Default constructor.
     * @param config -
     */
    public DefaultRBServiceProvider(final RBConfig config) {
        final ArastrejuProfile profile = config.getArastrejuConfiguration();
        logger.info("Initializing Arastreju with profile: " + profile);
        gate = Arastreju.getInstance(profile).rootContext();
<<<<<<< HEAD
        schemaManagement = new ResourceSchemaManagerImpl(this);
=======
        schemaManagement = new ResourceSchemaManagement(gate);
>>>>>>> 7cf153d55197264c0e0c547b580f2980bb05874d
        typeManagement = new RBEntityManagerImpl(this);
    }

    // -----------------------------------------------------

    /**
     *{@inheritDoc}
     */
    @Override
    public ISchemaManagement getResourceSchemaManagement() {
        return schemaManagement;
    }

    // -----------------------------------------------------

    /**
     *{@inheritDoc}
     */
    @Override
    public ArastrejuGate getArastejuGateInstance() {
        return gate;
    }

    // -----------------------------------------------------

    /**
     *{@inheritDoc}
     */
    @Override
    public RBEntityManager getRBEntityManagement() {
        return typeManagement;
    }

}
