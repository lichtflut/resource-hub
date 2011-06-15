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

	String NAMESPACE_URI = "http://lichtflut.de/rbschema#";
	
	// -- CONTEXT -----------------------------------------

	ResourceID CONTEXT = new SimpleResourceID(NAMESPACE_URI, "context");
	
	
	
	// -- ROOT-NODE ---------------------------------------
	
	ResourceNode ROOT_NODE = new SNResource(new QualifiedName(NAMESPACE_URI, "SystemRoot"));
	
	// -- TYPES -------------------------------------------
	
	ResourceID RESOURCE_SCHEMA = new SimpleResourceID(NAMESPACE_URI, "ResourceSchema");
	ResourceID PROPERTY_ASSERT = new SimpleResourceID(NAMESPACE_URI, "PropertyAssertion");
	ResourceID PROPERTY_DECL = new SimpleResourceID(NAMESPACE_URI, "PropertyDeclaration");
	ResourceID TYPE_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "TypeConstraint");
	ResourceID LITERAL_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "LiteralConstraint");
	
	// -- PROPERTIES --------------------------------------
	
	ResourceID HAS_SCHEMA = new SimpleResourceID(NAMESPACE_URI, "hasSchema");
	ResourceID DESCRIBES = new SimpleResourceID(NAMESPACE_URI, "describes");
	ResourceID DESCRIBED_BY = new SimpleResourceID(NAMESPACE_URI, "describedBy");
	ResourceID HAS_PROPERTY_ASSERT = new SimpleResourceID(NAMESPACE_URI, "hasPropertyAssertion");
	ResourceID HAS_PROPERTY_DECL = new SimpleResourceID(NAMESPACE_URI, "hasPropertyDeclaration");
	ResourceID HAS_DESCRIPTOR = new SimpleResourceID(NAMESPACE_URI, "hasDescriptor");
	ResourceID HAS_IDENTIFIER = new SimpleResourceID(NAMESPACE_URI, "hasIdentifier");
	ResourceID HAS_DATATYPE = new SimpleResourceID(NAMESPACE_URI, "hasDatatype");
	ResourceID HAS_SCHEMA_REPRESENTATION = new SimpleResourceID(NAMESPACE_URI, "hasSchemaRepresentation");
	ResourceID HAS_RS_FORMAT = new SimpleResourceID(NAMESPACE_URI, "hasRSFormat");
	
	// -- CONSTRAINTS -------------------------------------
	
	ResourceID HAS_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "hasConstraint");
	ResourceID HAS_TYPE_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "hasTypeConstraint");
	ResourceID HAS_LITERAL_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "hasLiteralConstraint");
	ResourceID HAS_CONSTRAINT_VALUE = new SimpleResourceID(NAMESPACE_URI, "hasConstraintValue");
	ResourceID MIN_OCCURS = new SimpleResourceID(NAMESPACE_URI, "minOccurs");
	ResourceID MAX_OCCURS = new SimpleResourceID(NAMESPACE_URI, "maxOccurs");
	
	
}
