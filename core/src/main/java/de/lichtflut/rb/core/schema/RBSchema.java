/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.naming.QualifiedName;

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
	
	// -- CONTEXT -----------------------------------------

	public static final ResourceID CONTEXT =   new SNResource(new QualifiedName(NAMESPACE_URI, "context"));
	
	
	// -- ROOT-NODE ---------------------------------------
	
	public static final ResourceNode ROOT_NODE = new SNResource(new QualifiedName(NAMESPACE_URI, "SystemRoot"));
	
	// -- TYPES -------------------------------------------
	
	public static final ResourceID RESOURCE_SCHEMA = new SimpleResourceID(NAMESPACE_URI, "ResourceSchema");
	public static final ResourceID PROPERTY_ASSERT = new SimpleResourceID(NAMESPACE_URI, "PropertyAssertion");
	public static final ResourceID PROPERTY_DECL = new SimpleResourceID(NAMESPACE_URI, "PropertyDeclaration");
	public static final ResourceID TYPE_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "TypeConstraint");
	public static final ResourceID LITERAL_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "LiteralConstraint");
	
	// -- PROPERTIES --------------------------------------
	
	public static final ResourceID HAS_SCHEMA = new SimpleResourceID(NAMESPACE_URI, "hasSchema");
	public static final ResourceID DESCRIBES = new SimpleResourceID(NAMESPACE_URI, "describes");
	public static final ResourceID DESCRIBED_BY = new SimpleResourceID(NAMESPACE_URI, "describedBy");
	public static final ResourceID HAS_PROPERTY_ASSERT = new SimpleResourceID(NAMESPACE_URI, "hasPropertyAssertion");
	public static final ResourceID HAS_PROPERTY_DECL = new SimpleResourceID(NAMESPACE_URI, "hasPropertyDeclaration");
	public static final ResourceID HAS_DESCRIPTOR = new SimpleResourceID(NAMESPACE_URI, "hasDescriptor");
	public static final ResourceID HAS_IDENTIFIER = new SimpleResourceID(NAMESPACE_URI, "hasIdentifier");
	public static final ResourceID HAS_DATATYPE = new SimpleResourceID(NAMESPACE_URI, "hasDatatype");
	public static final ResourceID HAS_SCHEMA_REPRESENTATION = new SimpleResourceID(NAMESPACE_URI, "hasSchemaRepresentation");
	public static final ResourceID HAS_RS_FORMAT = new SimpleResourceID(NAMESPACE_URI, "hasRSFormat");
	
	// -- CONSTRAINTS -------------------------------------
	
	public static final ResourceID HAS_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "hasConstraint");
	public static final ResourceID HAS_TYPE_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "hasTypeConstraint");
	public static final ResourceID HAS_LITERAL_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "hasLiteralConstraint");
	public static final ResourceID HAS_CONSTRAINT_VALUE = new SimpleResourceID(NAMESPACE_URI, "hasConstraintValue");
	public static final ResourceID MIN_OCCURS = new SimpleResourceID(NAMESPACE_URI, "minOccurs");
	public static final ResourceID MAX_OCCURS = new SimpleResourceID(NAMESPACE_URI, "maxOccurs");
	
	
}
