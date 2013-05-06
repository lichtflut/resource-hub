/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

	String NAMESPACE_URI = "http://rb.lichtflut.de/schema#";

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
	ResourceID PUBLIC_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "PublicConstraint");

	// -- Quick-Info --------------------------------------

	ResourceID HAS_QUICK_INFO = new SimpleResourceID(NAMESPACE_URI, "hasQuickInfo");

	// -- PROPERTIES --------------------------------------

	/**
	 * A ResourceSchema describes a ResourceType.
	 */
	ResourceID DESCRIBES = new SimpleResourceID(NAMESPACE_URI, "describes");

    /**
     * A ResourceType can have a ResourceSchema.
     */
    ResourceID HAS_SCHEMA = new SimpleResourceID(NAMESPACE_URI, "hasSchema");

	/**
	 * A Schema has one or more PropertyDeclarations.
	 */
	ResourceID HAS_PROPERTY_DECL = new SimpleResourceID(NAMESPACE_URI, "hasPropertyDeclaration");

	/**
	 * A PropertyAssertion can be have several Property-Descriptors to define the labels or sth. like that-
	 * It's like a predicate
	 */
	ResourceID HAS_DESCRIPTOR = new SimpleResourceID(NAMESPACE_URI, "hasDescriptor");

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
	ResourceID IS_PUBLIC_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "isPublicConstraint");

	/**
	 * Text expression for building a label for an entity.
	 */
	ResourceID HAS_LABEL_EXPRESSION = new SimpleResourceID(NAMESPACE_URI, "hasLabelExpression");

	// -- CONSTRAINTS -------------------------------------

	ResourceID HAS_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "hasConstraint");
	ResourceID HAS_TYPE_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "hasTypeConstraint");
    ResourceID HAS_LITERAL_CONSTRAINT = new SimpleResourceID(NAMESPACE_URI, "hasLiteralConstraint");
	ResourceID MIN_OCCURS = new SimpleResourceID(NAMESPACE_URI, "minOccurs");
	ResourceID MAX_OCCURS = new SimpleResourceID(NAMESPACE_URI, "maxOccurs");

    // -- VISUALIZATION -----------------------------------

    ResourceID HAS_VISUALIZATION_INFO = new SimpleResourceID(NAMESPACE_URI, "hasVisualizationInfo");

    ResourceID IS_EMBEDDED = new SimpleResourceID(NAMESPACE_URI, "isEmbedded");

    ResourceID IS_FLOATING = new SimpleResourceID(NAMESPACE_URI, "isFloating");

    ResourceID HAS_STYLE = new SimpleResourceID(NAMESPACE_URI, "hasStyle");

}
