/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import org.arastreju.sge.spi.GateContext;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.services.ServiceContext;
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
public class DefaultRBServiceProvider extends AbstractServiceProvider implements ServiceProvider {

    // --CONSTRUCTOR----------------------------------------

    /**
     * Constructor.
     * @param config The RBConfig.
     */
    public DefaultRBServiceProvider(final RBConfig config) {
    	super(new ServiceContext(config, GateContext.MASTER_DOMAIN));
    }

}
