/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * <p>
 *  Model of a list of Resource Nodes.
 * </p>
 *
 * <p>
 * 	Created Nov 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class ResourceListModel extends AbstractLoadableDetachableModel<List<ResourceNode>> {

	private ResourceID type;
	
	// -----------------------------------------------------
	
	/**
	 * @param id The id of the entity to load.
	 */
	public ResourceListModel(final ResourceID type) {
		this.type = type;
	}

	// -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	public List<ResourceNode> load() {
		return getServiceProvider().getArastejuGate().createQueryManager().findByType(type);
	}
	
	// -----------------------------------------------------
	
	protected abstract ServiceProvider getServiceProvider();

}
