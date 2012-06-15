/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.util.List;

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
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
			return mc().resolve(new SimpleResourceID(user.getQualifiedName()));
		}
	}
	
	protected List<ResourceNode> findResourcesByType(ResourceID type) {
		final Query query = mc().createQuery();
		query.addField(RDF.TYPE, type);
		return query.getResult().toList(2000);
	}
	
	// ----------------------------------------------------
	
	protected ModelingConversation mc() {
		return provider.getConversation();
	}
	
	// ----------------------------------------------------
	
	protected ArastrejuGate gate() {
		return provider.getArastejuGate();
	}
	
}
