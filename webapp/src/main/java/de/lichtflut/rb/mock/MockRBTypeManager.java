/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.model.nodes.views.SNClass;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.api.TypeManager;
import de.lichtflut.rb.core.entity.RBEntity;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Sep 21, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class MockRBTypeManager implements TypeManager {

	private List<RBEntity> dataPool;
	
	// -----------------------------------------------------
	
	/**
	 * @param dataPool
	 */
	public MockRBTypeManager(final List<RBEntity> dataPool) {
		this.dataPool = dataPool;
	}
	
	// -----------------------------------------------------
	
	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.api.TypeManager#create(java.lang.String, java.lang.String)
	 */
	@Override
	public SNClass create(String namespace, String name) {
		throw new NotYetImplementedException();
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.api.TypeManager#remove(org.arastreju.sge.model.nodes.views.SNClass)
	 */
	@Override
	public void remove(SNClass type) {
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
