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
import de.lichtflut.rb.core.api.RBEntityManagement;
import de.lichtflut.rb.core.api.ResourceSchemaManagement;
import de.lichtflut.rb.core.api.impl.NewRBEntityManagement;
import de.lichtflut.rb.core.api.impl.RBEntityManagementImpl;
import de.lichtflut.rb.core.api.impl.ResourceSchemaManagementImpl;
import de.lichtflut.rb.core.spi.RBServiceProvider;

/**
 * Reference implementation of {@link RBServiceProvider}.
 * TODO: The rootContext of ArastrejuGate is used, this should be changed
 *
 * Created: Apr 28, 2011
 *
 * @author Nils Bleisch
 */
public class DefaultRBServiceProvider implements RBServiceProvider {

    private final Logger logger = LoggerFactory
            .getLogger(DefaultRBServiceProvider.class);

    private ArastrejuGate gate = null;
    private ResourceSchemaManagement schemaManagement = null;
    private RBEntityManagement typeManagement = null;
    private NewRBEntityManagement newTypeManagement = null;

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
        schemaManagement = new ResourceSchemaManagementImpl(gate);
        typeManagement = new RBEntityManagementImpl(gate);
        newTypeManagement = new NewRBEntityManagement(gate);
    }

    // -----------------------------------------------------

    /**
     *{@inheritDoc}
     */
    @Override
    public ResourceSchemaManagement getResourceSchemaManagement() {
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
    public RBEntityManagement getRBEntityManagement() {
        return typeManagement;
    }

    /**
     *
     * @return -
     */
    public NewRBEntityManagement getNewRBEntityManagement() {
        return newTypeManagement;
    }

}
