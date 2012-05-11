/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.spi.GateContext;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.authserver.EmbeddedAuthModule;
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
public class DefaultRBServiceProvider extends AbstractServiceProvider {

    // --CONSTRUCTOR----------------------------------------

	 /**
     * Constructor.
     * @param config The RBConfig.
     */
    public DefaultRBServiceProvider(RBConfig config) {
    	super(new ServiceContext(config, GateContext.MASTER_DOMAIN), createDefaultAuthModule(config));
    }
	
	/**
     * Constructor.
     * @param config The RBConfig.
     * @param authModule The AuthModule.
     */
    public DefaultRBServiceProvider(RBConfig config, AuthModule authModule) {
    	super(new ServiceContext(config, GateContext.MASTER_DOMAIN), authModule);
    }
    
    // ----------------------------------------------------
    
	private static AuthModule createDefaultAuthModule(RBConfig config) {
		final Arastreju aras = Arastreju.getInstance(config.getArastrejuConfiguration());
		return new EmbeddedAuthModule(aras.rootContext());
	}

}
