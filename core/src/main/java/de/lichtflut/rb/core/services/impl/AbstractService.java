/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.Query;

import de.lichtflut.rb.core.security.RBUser;
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
	 * Constructor.
	 * @param provider The provider.
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
		final RBUser user = getProvider().getContext().getUser();
		if (user == null) {
			return null;
		} else {
			return mc().findResource(user.getQualifiedName());
		}
	}
	
	// ----------------------------------------------------
	
	protected Query query() {
		return gate().createQueryManager().buildQuery();
	}
	
	protected ModelingConversation mc() {
		if (conversation == null) {
			conversation = gate().startConversation();
		}
		return conversation;
	}
	
	// ----------------------------------------------------
	
	protected ArastrejuGate gate() {
		return provider.getArastejuGate();
	}
	
}
