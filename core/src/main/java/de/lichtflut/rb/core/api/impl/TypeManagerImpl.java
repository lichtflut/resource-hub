/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.query.QueryManager;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.api.TypeManager;
import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * <p>
 *  Implementation of {@link TypeManager}.
 * </p>
 *
 * <p>
 * 	Created Sep 19, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class TypeManagerImpl implements TypeManager {
	
	private final ServiceProvider provider;
	
	// -----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param provider The service provider.
	 */
	public TypeManagerImpl(final ServiceProvider provider) {
		this.provider = provider;
	}
	
	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SNClass> findAll() {
		final List<SNClass> result = new ArrayList<SNClass>();
		final List<ResourceNode> nodes = query().findByType(RB.TYPE);
		for (ResourceNode current : nodes) {
			result.add(current.asClass());
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SNClass create(final QualifiedName qn) {
		final SNClass type = new SNResource(qn).asClass();
		SNOPS.associate(type, RDF.TYPE, RB.TYPE, RB.TYPE_SYSTEM_CONTEXT);
		newMC().attach(type);
		return type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(final SNClass type) {
		newMC().remove(type, true);
	}
	
	// -----------------------------------------------------
	
	private ModelingConversation newMC() {
		return provider.getArastejuGate().startConversation();
	}
	
	private QueryManager query() {
		return provider.getArastejuGate().createQueryManager();
	}

}
