/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.config.selection;

/**
 * <p>
 *  Enumeration of parameter types.
 * </p>
 *
 * <p>
 * 	Created Jan 27, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public enum ParameterType {
	
	// Convenience for field with predicate rdf:type 
	RESOURCE_TYPE,
	
	// a special field.
	SPECIAL_FIELD_VALUE,
	
	// 
	SPECIAL_FIELD_RELATION,
	
	// any field.
	ANY_FIELD_VALUE,
	
	// any field relation.
	ANY_FIELD_RELATION;

}
