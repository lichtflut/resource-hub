/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core;

import org.arastreju.sge.context.Context;
import org.arastreju.sge.context.SimpleContextID;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.Namespace;

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

    Context TYPE_SYSTEM_CTX = new SimpleContextID(Namespace.LOCAL_CONTEXTS, "TypeSystem");

    Context VIEW_SPEC_CTX = new SimpleContextID(Namespace.LOCAL_CONTEXTS, "ViewSpecifications");
	
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
     * A content item represents some content with meta-data (author, title, etc.).
     */
    ResourceID CONTENT_ITEM = new SimpleResourceID(SYS_NAMESPACE_URI, "ContentItem");

    /**
     * A special type of content representing a comment to something.
     */
    ResourceID COMMENT = new SimpleResourceID(SYS_NAMESPACE_URI, "Comment");

    /**
     * A special type of content representing a page (e.g. of a Wiki).
     */
    ResourceID PAGE = new SimpleResourceID(SYS_NAMESPACE_URI, "Page");

    /**
     * A special type of content representing a document.
     */
    ResourceID DOCUMENT = new SimpleResourceID(SYS_NAMESPACE_URI, "Document");
	
	// -- PROPERTIES --------------------------------------
	
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

    // -- CONTENT SPECIFICATION ------------------------------

	/**
	 * Each RBEntity may have a primary image URL/ID.
	 */
	ResourceID HAS_IMAGE = new SimpleResourceID(SYS_NAMESPACE_URI, "hasImage");

    /**
     * The content. E.g. of an attachment.
     */
    ResourceID HAS_CONTENT = new SimpleResourceID(SYS_NAMESPACE_URI, "hasContent");


}