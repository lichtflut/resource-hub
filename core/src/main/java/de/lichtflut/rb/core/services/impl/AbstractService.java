/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.Query;
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
	
	private ModelingConversation conversation;
	
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
	
	protected ResourceNode currentUser() {
		final ResourceNode user = getProvider().getContext().getUser();
		if (user == null) {
			return null;
		} else {
			return getProvider().getResourceResolver().resolve(user); 
		}
	}
	
	// ----------------------------------------------------
	
	protected Query query() {
		return provider.getArastejuGate().createQueryManager().buildQuery();
	}
	
	protected ModelingConversation mc() {
		if (conversation == null) {
			conversation = provider.getArastejuGate().startConversation();
		}
		return conversation;
	}
	
	// ----------------------------------------------------
	
	protected ArastrejuGate gate() {
		return provider.getArastejuGate();
	}
	
	protected ArastrejuGate masterGate() {
		return gate(GateContext.MASTER_DOMAIN);
	}
	
	protected ArastrejuGate gate(String domain) {
		return provider.getArastejuGate(domain);
	}
	
}
