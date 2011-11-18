/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core;

import org.arastreju.sge.context.Context;
import org.arastreju.sge.context.SimpleContextID;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

/**
 *  <p>
 *   A priori known URIs for RB.
 * </p>
 *
 * Created: Aug 25, 2011
 *
 * @author Ravi Knox
 */
public interface RB {

	String NAMESPACE_URI = "http://lichtflut.de/rb#";
	
	/**
	 * Represents the schema context. Each schema.based expression has to be applied to this context.
	 */
	Context TYPE_SYSTEM_CONTEXT = new SimpleContextID(NAMESPACE_URI, "TypeSystemContext");

	/**
	 * Sub class of rdf:Class/owl:Class. Classes that are resource browser types.
	 * can be marked as such by being of rdf:type rb:Type.
	 */
	ResourceID TYPE = new SimpleResourceID(NAMESPACE_URI, "Type");
	
	/**
	 * Each RBEntity may have a primary image URL/ID.
	 */
	ResourceID HAS_IMAGE = new SimpleResourceID(NAMESPACE_URI, "hasImage");

	/**
	 * Each RBEntity may have a short literal description.
	 */
	ResourceID HAS_SHORT_DESC = new SimpleResourceID(NAMESPACE_URI, "hasShortDescription");
	
	/**
	 * Each RBEntity may have a primary image URL/ID.
	 */
	ResourceID HAS_FIELD_LABEL = new SimpleResourceID(NAMESPACE_URI, "hasFieldLabel");


}
