/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.entity;

import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableModel;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class RBEntityModel extends AbstractLoadableModel<RBEntity> {
	
	private final Logger logger = LoggerFactory.getLogger(RBEntityModel.class);
	
	@SpringBean
	private EntityManager entityManager;
	
	// -----------------------------------------------------
	
	/**
	 * Default constructor.
	 */
	public RBEntityModel() {
		Injector.get().inject(this);
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
			final RBEntity loaded = entityManager.find(handle.getId());
			return loaded;
		} else if (handle.hasType()){
			logger.debug("Creating new RB Entity");
			final RBEntity loaded = entityManager.create(handle.getType());
			handle.setId(loaded.getID());
			return loaded;
		} else {
			throw new IllegalStateException("Cannot initialize RB Entity Model.");
		}
	}
	
}
