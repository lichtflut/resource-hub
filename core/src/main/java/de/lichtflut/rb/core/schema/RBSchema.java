/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

/**
 * <p>
 *   A priori known URIs for RB Schema.
 * </p>
 *
 * <p>
 * 	Created Apr 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface RBSchema {

	public static final String NAMESPACE_URI = "http://lichtflut.de/rbschema#";
	
	// -- TYPES -------------------------------------------
	
	public static final ResourceID ACTIVITY_CLASS = new SimpleResourceID(NAMESPACE_URI, "ResourceSchema");
	public static final ResourceID PROPERTY_ASSERT = new SimpleResourceID(NAMESPACE_URI, "PropertyAssertion");
	public static final ResourceID PROPERTY_DECL = new SimpleResourceID(NAMESPACE_URI, "PropertyDeclaration");
	
	// -- PROPERTIES --------------------------------------
	
	public static final ResourceID HAS_SCHEMA = new SimpleResourceID(NAMESPACE_URI, "hasSchema");
	public static final ResourceID HAS_PROPERTY_ASSERT = new SimpleResourceID(NAMESPACE_URI, "hasPropertyAssertion");
	public static final ResourceID HAS_PROPERTY_DECL = new SimpleResourceID(NAMESPACE_URI, "hasPropertyDeclaration");
	public static final ResourceID HAS_DESCRIPTOR = new SimpleResourceID(NAMESPACE_URI, "hasDescriptor");
	public static final ResourceID HAS_IDENTIFIER = new SimpleResourceID(NAMESPACE_URI, "hasIdentifier");
	public static final ResourceID HAS_DATATYPE = new SimpleResourceID(NAMESPACE_URI, "hasDatatype");
	
	// -- CONSTRAINTS -------------------------------------
	
	public static final ResourceID HAS_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "hasConstraint");
	public static final ResourceID HAS_TYPE_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "hasTypeConstraint");
	public static final ResourceID HAS_LITERAL_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "hasLiteralConstraint");
	
	public static final ResourceID MIN_OCCURS = new SimpleResourceID(NAMESPACE_URI, "minOccurs");
	public static final ResourceID MAX_OCCURS = new SimpleResourceID(NAMESPACE_URI, "maxOccurs");
	
	
}