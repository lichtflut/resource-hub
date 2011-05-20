/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api;

import java.io.Serializable;
import java.util.Collection;

import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance;

/**
 * <p>
 * This is the RB's ResourceTypeManagement "HighLevel" interface.
 * Whatever you want to manage, this service tries to give you the tools to do that.
 * No additional knowledge-base about infrastructure, technology stack e.g. should be required/necessary.
 * </p>
 * 
 * Try to make this interface as flexible as you can.
 * 
 * Please note, that this is not yet ready.
 * This might be renamed or refactored in sth. bigger
 * 
 * Created: May 18, 2011
 *
 * @author Nils Bleisch
 */
public interface ResourceTypeManagement extends Serializable{
	
	/**
	 * creates or updates a given {@link ResourceTypeInstance}
	 */
	public boolean createOrUpdateRTInstance(ResourceTypeInstance<Object> instance);

	// -----------------------------------------------------
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Collection<ResourceTypeInstance> loadAllResourceTypeInstancesForSchema(ResourceSchema schema);
	
}
