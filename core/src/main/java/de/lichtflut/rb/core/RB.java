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

	String SYS_NAMESPACE_URI = "http://rb.lichtflut.de/system#";
	
	String CTX_NAMESPACE_URI = "http://rb.lichtflut.de/contexts#";
	
	// -- CONTEXTS ----------------------------------------
	
	/**
	 * Represents the schema context. Each schema.based expression has to be applied to this context.
	 */
	Context TYPE_SYSTEM_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "TypeSystem");
	
	Context SCHEMA_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "Schema");
	
	Context DOMAIN_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "Domain");
	
	Context PUBLIC_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "Public");
	
	Context PRIVATE_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "Private");
	
	Context LOCALE_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "Locale");
	
	Context DEFAULT_LOCALE_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "DefaultLocale");
	
	Context LOCALE_ENGLISH_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "English");
	
	Context LOCALE_GERMAN_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "German");
	
	Context LOCALE_FRENCH_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "French");
	
	// ----------------------------------------------------

	/**
	 * Sub class of rdf:Class/owl:Class. Classes that are resource browser types.
	 * can be marked as such by being of rdf:type rb:Type.
	 */
	ResourceID TYPE = new SimpleResourceID(SYS_NAMESPACE_URI, "Type");
	
	/**
	 * Each RBEntity may have a primary image URL/ID.
	 */
	ResourceID HAS_IMAGE = new SimpleResourceID(SYS_NAMESPACE_URI, "hasImage");

	/**
	 * Each RBEntity may have a short literal description.
	 */
	ResourceID HAS_SHORT_DESC = new SimpleResourceID(SYS_NAMESPACE_URI, "hasShortDescription");
	
	/**
	 * Each RBEntity may have a primary image URL/ID.
	 */
	ResourceID HAS_FIELD_LABEL = new SimpleResourceID(SYS_NAMESPACE_URI, "hasFieldLabel");


}
