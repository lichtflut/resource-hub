/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.api.TypeManager;
import de.lichtflut.rb.core.entity.RBEntity;

/**
 * <p>
 *  Mock version of type manager.
 * </p>
 *
 * <p>
 * 	Created Sep 21, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class MockTypeManager implements TypeManager, Serializable {

	private List<RBEntity> dataPool;
	
	// -----------------------------------------------------
	
	/**
	 * @param dataPool
	 */
	public MockTypeManager(final List<RBEntity> dataPool) {
		this.dataPool = dataPool;
	}
	
	// -----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SNClass create(final QualifiedName qn) {
		final SNClass type = new SNResource(qn).asClass();
		SNOPS.associate(type, RDF.TYPE, RB.TYPE, RB.TYPE_SYSTEM_CONTEXT);
		return type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(final SNClass type) {
		throw new NotYetImplementedException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SNClass> findAll() {
		List<SNClass> result = new ArrayList<SNClass>();
		for (RBEntity e : dataPool) {
			if(!result.contains(e.getType())){
				result.add(e.getType().asResource().asClass());
			}
		}
		return result;
	}

}
