/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

/**
 * <p>
 *  The cardinality of a PropertyDeclaration.
 *  In contrast to the concept of 'cardinality' in software modeling or design
 *  the cardinality of a PropertyDeclaration is rather a recommendation than obligatory.
 * </p>
 * 
 * <p>
 * 	The methods are may seem slightly redundant. If <code>isSingle()</code> is true, this 
 *  could also be expressed by max occurs = 1. But this cardinality representation can be
 *  used in a fuzzy mode (isSingle()/isUnbound()) and in an exact mode. Additionally 
 *  <code>isUnbound()</code> must be true if max occurs will be grater than Integer.MAX_VALUE;  
 * </p>
 *
 * <p>
 * 	Created Feb 3, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface Cardinality {
	
	boolean isUnbound();
	
	boolean isSingle();

	int getMinOccurs();
	
	int getMaxOccurs();
	
}
