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
	
	ResourceID getResourceID();
	
	List<PropertyAssertion> getPropertyDeclarations();

}
