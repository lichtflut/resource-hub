/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableModel;

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
public abstract class RBEntityModel extends AbstractLoadableModel<RBEntity> {
	
	private final Logger logger = LoggerFactory.getLogger(RBEntityModel.class);

	private EntityHandle handle;
	
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
	
	// ----------------------------------------------------
	
	/**
	 * Reset the model in order to reload.
	 */
	public void reset(final EntityHandle handle) {
		reset();
		this.handle = handle; 
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBEntity load() {
		if (handle.hasId()) {
			logger.info("Loading RB Entity: " + handle.getId());
			final RBEntity loaded = getServiceProvider().getEntityManager().find(handle.getId());
			return loaded;
		} else if (handle.hasType()){
			logger.info("Creating new RB Entity");
			final RBEntity loaded = getServiceProvider().getEntityManager().create(handle.getType());
			handle.setId(loaded.getID());
			return loaded;
		} else {
			throw new IllegalStateException("Cannot initialize RB Entity Model.");
		}
	}
	
	// -----------------------------------------------------
	
	protected abstract ServiceProvider getServiceProvider();

}
