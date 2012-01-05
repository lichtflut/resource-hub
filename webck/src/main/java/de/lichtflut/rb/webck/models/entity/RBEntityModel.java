/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.application.RBWebSession;
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

	// -----------------------------------------------------
	
	/**
	 * Default constructor.
	 */
	public RBEntityModel() {
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBEntity load() {
		final EntityHandle handle = RBWebSession.get().getHistory().getCurrentStep().getHandle();
		if (handle.hasId()) {
			logger.debug("Loading RB Entity: " + handle.getId());
			final RBEntity loaded = getServiceProvider().getEntityManager().find(handle.getId());
			return loaded;
		} else if (handle.hasType()){
			logger.debug("Creating new RB Entity");
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
