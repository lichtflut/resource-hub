/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.util.List;

import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Schema for a Resource Type, e.g. a person, an organization or a project.
 * </p>
 *
 * <p>
 * 	Created Jan 27, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ResourceSchema extends ResourceSchemaElement {

	/**
	 * The ResourceID  of the Type described by this schema.
	 * @return The ID of the described Resource Type.
	 */
	ResourceID getDescribedType();
	
	// -----------------------------------------------------

	/**
	 * Get all the schema's {@link PropertyDeclaration} as list.
	 * @return all schema {@link PropertyDeclaration} as list.
	 */
	List<PropertyDeclaration> getPropertyDeclarations();

	/**
	 * Add a additional {@link PropertyDeclaration} to the ResourceSchema.
	 * @param assertion -
	 */
	void addPropertyDeclaration(final PropertyDeclaration assertion);
	
	// -----------------------------------------------------

	/**
	 * The label builder can create dynamic labels for an entity belonging to this schema.
	 * @return The label builder for this schema.
	 */
	LabelBuilder getLabelBuilder();

}
