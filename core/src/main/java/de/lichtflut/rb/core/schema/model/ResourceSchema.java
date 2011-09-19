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
 *  There still exists one reference implementation ResourceSchemaImpl.
 *  It's recommended to use this implementation, cause this is up to date and implementing the whole spec.
 *  If you want to implement your own ResourceSchema class, please be absolutely sure that you're already
 *  knowing the whole facts.
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
	 * The ResourceID  of this schema.
	 * @return This schema's ID.
	 */
	ResourceID getResourceID();

	/**
	 * The ResourceID  of the described resource.
	 *  @return The ID of the described resource.
	 */
	ResourceID getDescribedType();

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
	 * Iterate over its {@link PropertyAssertion} and returns false if just one of them is not resolved.
	 * @return true if all were resolved, false if not
 	 */
	boolean isResolved();

	/**
	 * The label builder can create dynamic labels for an entity belonging to this schema.
	 * @return The label builder for this schema.
	 */
	LabelBuilder getLabelBuilder();

	/**
	 * Please make sure that equals is correct implemented to avoid some merging redundancy conflicts e.g.
	 * @param obj -
	 * @return TODO
	 */
	boolean equals(Object obj);

	/**
	 * Overriding to match conventions while overriding 'equals()' method..
	 * @return int
	 */
	int hashCode();

}
