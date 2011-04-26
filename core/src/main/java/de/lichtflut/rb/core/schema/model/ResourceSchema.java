/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.util.List;

import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Schema for a resource type, e.g. a person, an organization or a project.
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
	 * The resource described by this schema.
	 * @return The ID of the described resource.
	 */
	ResourceID getResourceID();
	
	/**
	 * TODO: ToComment
	 */
	List<PropertyAssertion> getPropertyAssertions();

	/**
	 * TODO: ToComment
	 */
	void setPropertyAssertions(final List<PropertyAssertion> assertions);
	
	/**
	 * TODO: ToComment
	 */
	void addPropertyAssertion(final PropertyAssertion assertion);
	
	/**
	 * TODO: ToComment
	 */
	boolean resolveAssertions();
}
