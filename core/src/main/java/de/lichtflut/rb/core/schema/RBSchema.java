/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema;

import org.arastreju.sge.context.Context;
import org.arastreju.sge.context.SimpleContextID;
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

	/**
	 * Represents the schema context. Each schema.based expression has to be applied to this context.
	 */
	Context CONTEXT = new SimpleContextID(NAMESPACE_URI, "SchemaContext");


	// -- ROOT-NODE ---------------------------------------

	/**
	 * Represents the "root-node" of the schema graph.
	 */
	ResourceNode ROOT_NODE = new SNResource(new QualifiedName(NAMESPACE_URI, "SystemRoot"));

	// -- TYPES -------------------------------------------

	/**
	 * Each ResourceSchema has to be a type of this.
	 */
	ResourceID RESOURCE_SCHEMA = new SimpleResourceID(NAMESPACE_URI, "ResourceSchema");
	
	/**
	 * Each PropertyDeclaration has to be a type of this.
	 */
	ResourceID PROPERTY_DECL = new SimpleResourceID(NAMESPACE_URI, "PropertyDeclaration");
	
	/**
	 * Each PropertyDeclaration has to be a type of this.
	 */
	ResourceID PROPERTY_TYPE_DEF = new SimpleResourceID(NAMESPACE_URI, "PropertyTypeDefinition");
	
	/**
	 * Each TypeConstraint has to be a type of this.
	 */
	ResourceID TYPE_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "TypeConstraint");
	
	/**
	 * Each LiteralConstraint has to be a type of this.
	 */
	ResourceID LITERAL_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "LiteralConstraint");

	// -- PROPERTIES --------------------------------------

	/**
	 * Still unused.
	 */
	ResourceID HAS_SCHEMA = new SimpleResourceID(NAMESPACE_URI, "hasSchema");
	
	/**
	 * A ResourceSchema describes a ResourceType.
	 */
	ResourceID DESCRIBES = new SimpleResourceID(NAMESPACE_URI, "describes");
	
	/**
	 * A ResourceType is described by a ResourceSchema.
	 */
	ResourceID DESCRIBED_BY = new SimpleResourceID(NAMESPACE_URI, "describedBy");
	
	/**
	 * A PropertyAssertion must have a PropertyDeclaration.
	 */
	ResourceID HAS_PROPERTY_DECL = new SimpleResourceID(NAMESPACE_URI, "hasPropertyDeclaration");
	
	/**
	 * A PropertyAssertion must have a PropertyDeclaration.
	 */
	ResourceID HAS_PROPERTY_TYPE_DEF = new SimpleResourceID(NAMESPACE_URI, "hasPropertyTypeDefinition");
	
	/**
	 * A PropertyAssertion can be have several Property-Descriptors to define the labels or sth. like that-
	 * It's like a predicate
	 */
	ResourceID HAS_DESCRIPTOR = new SimpleResourceID(NAMESPACE_URI, "hasDescriptor");
	
	/**
	 * Still unused.
	 */
	ResourceID HAS_IDENTIFIER = new SimpleResourceID(NAMESPACE_URI, "hasIdentifier");
	
	/**
	 * A PropertyDeclaration can have an ElementaryDatatype if it's not a Resource-Reference.
	 */
	ResourceID HAS_DATATYPE = new SimpleResourceID(NAMESPACE_URI, "hasDatatype");
	
	/**
	 * A Type Definition can have a display name.
	 */
	ResourceID HAS_NAME = new SimpleResourceID(NAMESPACE_URI, "hasName");
	
	/**
	 * Boolean expression if a Property Type Definition is public or private.
	 */
	ResourceID IS_PUBLIC_TYPE_DEF = new SimpleResourceID(NAMESPACE_URI, "isPublicTypeDefinition");
	
	
	/**
	 * A SystemRoot can have Schema-Representations.
	 */
	ResourceID HAS_SCHEMA_REPRESENTATION = new SimpleResourceID(NAMESPACE_URI, "hasSchemaRepresentation");
	
	/**
	 * A Schema-Representation must have a RS-Format.
	 */
	ResourceID HAS_RS_FORMAT = new SimpleResourceID(NAMESPACE_URI, "hasRSFormat");

	// -- CONSTRAINTS -------------------------------------

	ResourceID HAS_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "hasConstraint");
	ResourceID HAS_TYPE_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "hasTypeConstraint");
	ResourceID HAS_LITERAL_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "hasLiteralConstraint");
	ResourceID HAS_CONSTRAINT_VALUE = new SimpleResourceID(NAMESPACE_URI, "hasConstraintValue");
	ResourceID MIN_OCCURS = new SimpleResourceID(NAMESPACE_URI, "minOccurs");
	ResourceID MAX_OCCURS = new SimpleResourceID(NAMESPACE_URI, "maxOccurs");

}
