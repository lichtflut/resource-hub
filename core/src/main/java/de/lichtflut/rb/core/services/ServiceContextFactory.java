package de.lichtflut.rb.core.services;

import de.lichtflut.rb.core.config.RBConfig;

/**
 * <p>
 *  Factory for service contexts.
 * </p>
 *
 * <p>
 *  Created 01.10.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class ServiceContextFactory {

    private RBConfig rbConfig;

    // ----------------------------------------------------

    public ServiceContextFactory(RBConfig rbConfig) {
        this.rbConfig = rbConfig;
    }

    // ----------------------------------------------------

    public ServiceContext createServiceContext() {
        return new ServiceContext(rbConfig);
    }
}
