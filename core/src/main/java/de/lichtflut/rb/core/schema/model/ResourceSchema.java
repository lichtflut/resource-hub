/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.util.Collection;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Schema for a resource type, e.g. a person, an organization or a project.
 *  
 *  ===ATTENTION===
 *  There still exists one reference implementation {@link ResourceSchemaImpl}.
 *  It's recommended to use this implementation, cause this is up to date and implementing the whole spec.
 *  If you want to realize your own ResourceSchema-class, please be absolutely sure that you're already knowing the whole facts 
 * </p>
 *
 * <p>
 * 	Created Jan 27, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ResourceSchema extends ResourceSchemaType{
	
	/**
	 * The ResourceID  of it's own schema
	 * @return The ID of the resource.
	 */
	ResourceID getResourceID();
	
	/**
	 * The ResourceID  of the described resource 
	 *  @return The ID of the descrived resource.
	 */
	ResourceID getDescribedResourceID();
	
	
	/**
	 * Get all the schema's {@link PropertyAssertion} as {@link Collection}
	 */
	Collection<PropertyAssertion> getPropertyAssertions();

	/**
	 * Set the {@link Collection} of {@link PropertyAssertion} to the ResourceSchema
	 */
	void setPropertyAssertions(final Collection<PropertyAssertion> assertions);
	
	/**
	 * Add a additional {@link PropertyAssertion} to the ResourceSchema
	 */
	void addPropertyAssertion(final PropertyAssertion assertion);
	
	/**
	 * iterate over its {@link PropertyAssertion} and returns false if just one of them is not resolved
 	 */
	boolean isResolved();
	
	/**
	 * Please make sure that equals is correct implemented to avoid some merging redundancy conflicts e.g.
	 */
	boolean equals(Object obj);
	
	
}
