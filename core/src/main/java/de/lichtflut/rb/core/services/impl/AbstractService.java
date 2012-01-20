/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ArastrejuProfile;
import org.arastreju.sge.spi.GateContext;

import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * <p>
 *  Abstract base for services.
 * </p>
 *
 * <p>
 * 	Created Jan 19, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class AbstractService {
	
	private final ServiceProvider provider;

	// ----------------------------------------------------
	
	/**
	 * @param provider
	 */
	public AbstractService(ServiceProvider provider) {
		this.provider = provider;
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return the provider
	 */
	protected ServiceProvider getProvider() {
		return provider;
	}
	
	// ----------------------------------------------------
	
	protected ArastrejuGate gate() {
		return provider.getArastejuGate();
	}
	
	protected ArastrejuGate masterGate() {
		return gate(GateContext.MASTER_DOMAIN);
	}
	
	protected ArastrejuGate gate(String domain) {
		final ArastrejuProfile profile = provider.getContext().getConfig().getArastrejuConfiguration();
		return Arastreju.getInstance(profile).rootContext(domain);
	}

}
