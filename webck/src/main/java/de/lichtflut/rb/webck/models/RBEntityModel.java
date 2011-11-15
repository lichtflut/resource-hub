/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Nov 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class RBEntityModel implements IModel<RBEntity> {

	private ResourceID type;
	
	private ResourceID id;
	
	private RBEntity entity;
	
	// -----------------------------------------------------
	
	/**
	 * @param id The id of the entity to load.
	 */
	public RBEntityModel(final ResourceID id, final ResourceID type) {
		this.id = id;
		this.type = type;
	}

	// -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBEntity getObject() {
		if (entity == null) {
			entity = load();
		}
		return entity;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setObject(final RBEntity object) {
		this.entity = object;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	protected RBEntity load() {
		final RBEntity loaded;
		if (id == null) {
			loaded = getServiceProvider().getEntityManager().create(type);
			this.id = loaded.getID();
		} else {
			loaded = getServiceProvider().getEntityManager().find(id);
		}
		return loaded;
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
