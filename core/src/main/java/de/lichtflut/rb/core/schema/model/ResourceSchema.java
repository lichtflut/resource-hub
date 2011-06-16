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
	 * The ResourceID  of it's own schema.
	 * @return The ID of the resource.
	 */
	ResourceID getResourceID();

	/**
	 * The ResourceID  of the described resource.
	 *  @return The ID of the descrived resource.
	 */
	ResourceID getDescribedResourceID();

	/**
	 * Get all the schema's {@link PropertyAssertion} as {@link Collection}.
	 * @return all schema {@link PropertyAssertion} as {@link Collection}
	 */
	Collection<PropertyAssertion> getPropertyAssertions();

	/**
	 * Set the {@link Collection} of {@link PropertyAssertion} to the ResourceSchema.
	 * @param assertions -
	 */
	void setPropertyAssertions(final Collection<PropertyAssertion> assertions);

	/**
	 * Add a additional {@link PropertyAssertion} to the ResourceSchema.
	 * @param assertion -
	 */
	void addPropertyAssertion(final PropertyAssertion assertion);

	/**
	 * iterate over its {@link PropertyAssertion} and returns false if just one of them is not resolved.
	 * @return true if all were resolved, false if not
 	 */
	boolean isResolved();

	/**
	 * Please make sure that equals is correct implemented to avoid some merging redundancy conflicts e.g.
	 * @param obj -
	 * @return TODO
	 */
	boolean equals(Object obj);

	/**
	 * generates an entity of the schema's described resource-type.
	 * @return Entity  of the schema's described resource-type
	 */
	RBEntity<Object> generateRBEntity();

	/**
	 * Overriding to match conventions while ioverriding 'equals()' method..
	 * @return int
	 */
	int hashCode();

}
