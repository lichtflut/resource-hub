/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.common.Action;

/**
 * <p>
 *  Model for an {@link RBEntity}.
 * </p>
 *
 * <p>
 * 	Created Nov 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class RBEntityModel implements IModel<RBEntity> {

	private EntityHandle handle;
	
	private RBEntity entity;
	
	private Action<?>[] initializers;
	
	// -----------------------------------------------------
	
	/**
	 * Default constructor.
	 */
	public RBEntityModel() {
	}
	
	/**
	 * @param handle The handle of the entity to load.
	 */
	public RBEntityModel(final EntityHandle handle) {
		this.handle = handle;
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
	@Override
	public void detach() {
	}
	
	// ----------------------------------------------------
	
	/**
	 * Reset the model in order to reload.
	 */
	public void reset() {
		entity = null;
	}
	
	/**
	 * Reset the model in order to reload.
	 */
	public void reset(final EntityHandle handle, Action<?>... initializers) {
		this.handle = handle; 
		this.entity = null;
		this.initializers = initializers;
	}
	
	// -----------------------------------------------------
	
	protected abstract ServiceProvider getServiceProvider();

	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	protected RBEntity load() {
		if (handle.hasId()) {
			final RBEntity loaded = getServiceProvider().getEntityManager().find(handle.getId());
			initialize(loaded);
			return loaded;
		} else if (handle.hasType()){
			final RBEntity loaded = getServiceProvider().getEntityManager().create(handle.getType());
			handle.setId(loaded.getID());
			initialize(loaded);
			return loaded;
		} else {
			return null;
		}
	}
	
	/**
	 * @param loaded
	 */
	private void initialize(RBEntity entity) {
		if (initializers == null) {
			return;
		}
		for (Action<?> action : initializers) {
			action.execute(entity);
		}
	}

}
