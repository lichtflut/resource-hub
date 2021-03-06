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
package de.lichtflut.rb.core;

import org.arastreju.sge.context.Context;
import org.arastreju.sge.context.ContextID;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.QualifiedName;

/**
 * <p>
 *  Constants for resources in RB System namespace.
 * </p>
 *
 * <p>
 * 	Created Jan 26, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface RBSystem {

	String SYS_NAMESPACE_URI = "http://rb.lichtflut.de/system#";

    String PERSPECTIVES_NAMESPACE_URI = QualifiedName.LOCAL + "perspectives:";

    String WIDGETS_NAMESPACE_URI = QualifiedName.LOCAL + "widgets:";

    String PERCEPTIONS_NAMESPACE_URI = QualifiedName.LOCAL + "perceptions:";

	Context TYPE_SYSTEM_CTX = ContextID.localContext("typesystem");

	Context VIEW_SPEC_CTX = ContextID.localContext("viewspec");

    Context META_CTX = ContextID.localContext("meta");

    Context DOMAIN_CTX = ContextID.forContext(Context.DOMAIN_CONTEXT);

	// -- TYPES -------------------------------------------

	/**
	 * Sub class of rdf:Class/owl:Class. Classes that are resource browser types.
	 * can be marked as such by being of rdf:type rb:Type.
	 */
	ResourceID TYPE = new SimpleResourceID(SYS_NAMESPACE_URI, "Type");

	/**
	 * rb:Entities are instances of rb:Type.
	 */
	ResourceID ENTITY = new SimpleResourceID(SYS_NAMESPACE_URI, "Entity");

    /**
     * A perception represents a contexts.
     */
    ResourceID PERCEPTION = new SimpleResourceID(SYS_NAMESPACE_URI, "Perception");

    /**
     * A category describing a perception.
     */
    ResourceID PERCEPTION_CATEGORY = new SimpleResourceID(SYS_NAMESPACE_URI, "PerceptionCategory");

    /**
     * A item of a perception.
     */
    ResourceID PERCEPTION_ITEM = new SimpleResourceID(SYS_NAMESPACE_URI, "PerceptionItem");

	/**
	 * An information may be visible or writable only by owner.
	 */
	ResourceID PRIVATE_ACCESS = new SimpleResourceID(SYS_NAMESPACE_URI, "PrivateAccess");

	/**
	 * An information may be visible or writable only some users.
	 */
	ResourceID PROTECTED_ACCESS = new SimpleResourceID(SYS_NAMESPACE_URI, "ProtectedAccess");

	/**
	 * An information may be visible or writable by all users.
	 */
	ResourceID PUBLIC_ACCESS = new SimpleResourceID(SYS_NAMESPACE_URI, "PublicAccess");

	// -- PROPERTIES --------------------------------------

	/**
	 * As each entity may have more than one rdf:type, the type specifying the schema can be marked with this
	 * predicate.
	 */
	ResourceID HAS_SCHEMA_IDENTIFYING_TYPE = new SimpleResourceID(SYS_NAMESPACE_URI, "hasSchemaIdentifyingType");

    /**
     * Labels and field labels can have a locale (or system:default).
     */
    ResourceID HAS_LOCALE = new SimpleResourceID(SYS_NAMESPACE_URI, "hasLocale");

	/**
	 * For each type there may be defined a prototype resource with the default properties.
	 */
	ResourceID HAS_PROTOTYPE = new SimpleResourceID(SYS_NAMESPACE_URI, "hasPrototype");

    /**
     * A perception can be based on another perception, or an item on another item.
     */
    ResourceID BASED_ON = new SimpleResourceID(SYS_NAMESPACE_URI, "basedOn");

	/**
	 * Users must provide an email address for identification.
	 */
	ResourceID HAS_EMAIL = new SimpleResourceID(SYS_NAMESPACE_URI, "hasEmail");

	/**
	 * Users may have a unique user name additionally to their email address.
	 */
	ResourceID HAS_USERNAME = new SimpleResourceID(SYS_NAMESPACE_URI, "hasUsername");

	/**
	 * The last login of a user.
	 */
	ResourceID HAS_LAST_LOGIN = new SimpleResourceID(SYS_NAMESPACE_URI, "hasLastLogin");
	/**
	 * Each User may be represented by an RBEntity.
	 */
	ResourceID IS_RESPRESENTED_BY = new SimpleResourceID(SYS_NAMESPACE_URI, "isRepresentedBy");
	/**
	 * Is attached to an entity.
	 */
	ResourceID IS_ATTACHED_TO = new SimpleResourceID(SYS_NAMESPACE_URI, "isAttachedTo");

	/**
	 * Each RBEntity may have a primary image URL/ID.
	 */
	ResourceID HAS_FIELD_LABEL = new SimpleResourceID(SYS_NAMESPACE_URI, "hasFieldLabel");

	/**
	 * Boolean value indicating of an organization is the domain's owning one.
	 */
	ResourceID IS_DOMAIN_ORGANIZATION = new SimpleResourceID(SYS_NAMESPACE_URI, "isDomainOrganization");

	/**
	 * Indicates default value.
	 */
	ResourceID DEFAULT = new SimpleResourceID(SYS_NAMESPACE_URI, "default");

	// -- CONTENT SPECIFICATION ------------------------------

	/**
	 * Each RBEntity may have a primary image URL/ID.
	 */
	ResourceID HAS_IMAGE = new SimpleResourceID(SYS_NAMESPACE_URI, "hasImage");

	/**
	 * The content. E.g. of an attachment.
	 */
	ResourceID HAS_CONTENT = new SimpleResourceID(SYS_NAMESPACE_URI, "hasContent");

    // -- PERCEPTIONS -------------------------------------

    /**
     * Each perception item belongs to a perception.
     */
    ResourceID BELONGS_TO_PERCEPTION = new SimpleResourceID(SYS_NAMESPACE_URI, "belongsToPerception");

    /**
     * A perception contains one or more 'root' items.
     */
    ResourceID CONTAINS_TREE_ROOT_ITEM = new SimpleResourceID(SYS_NAMESPACE_URI, "containsTreeRootItem");

    /**
     * A perception contains items.
     */
    ResourceID CONTAINS_PERCEPTION_ITEM = new SimpleResourceID(SYS_NAMESPACE_URI, "containsPerceptionItem");

    /**
     * Describes the color of an object.
     */
    ResourceID HAS_COLOR = new SimpleResourceID(SYS_NAMESPACE_URI, "hasColor");

}