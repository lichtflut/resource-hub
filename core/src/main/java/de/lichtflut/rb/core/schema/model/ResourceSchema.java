/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.util.List;

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
	
	/**
	 * Please make sure that equals is correct implemented to avoid some merging redundancy conflicts e.g.
	 */
	boolean equals(Object obj);
}
