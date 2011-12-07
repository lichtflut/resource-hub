/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import java.util.List;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * <p>
 *  Model of a list of entities.
 * </p>
 *
 * <p>
 * 	Created Nov 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class RBEntityListModel extends AbstractLoadableModel<List<RBEntity>> {

	private ResourceID type;
	
	// -----------------------------------------------------
	
	/**
	 * @param id The id of the entity to load.
	 */
	public RBEntityListModel(final ResourceID type) {
		this.type = type;
	}

	// -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	public List<RBEntity> load() {
		return getServiceProvider().getEntityManager().findByType(type);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void detach() {
	}
	
	// -----------------------------------------------------
	
	protected abstract ServiceProvider getServiceProvider();

}
