/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema;

import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Constraint for a property. In general there are two types of constraints:
 *  <ol>
 *   <li>Pattern constraints for literal value properties</li>
 *   <li>Type-Of constraints for resource references</li>
 *  </ol>
 * </p>
 *  
 * <p>
 *  Cardinality (min occurs, max occurs) are not constraints. 
 * </p>
 *
 * <p>
 * 	Created Feb 3, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface Constraint {
	
	/**
	 * Check if this constraint is for literal values.
	 * @return true if this constraint is for literal values.
	 */
	boolean isLiteralConstraint();
	
	/**
	 * Check if this constraint is a type-of constraint for resources.
	 * @return true if this constraint is for resources.
	 */
	boolean isResourceTypeConstraint();
	
	/**
	 * Get the literal constraint, a regular expression pattern. If this constraint is a resource type constraint
	 * (isLiteralConstraint() returns false) null will be returned;
	 * @return The literal constraint or null.
	 */
	String getLiteralConstraint();
	
	/**
	 * Get the resource type constraint, i.e. the type of which the resource must be. 
	 * If this constraint is a literal constraint
	 * (isResourceTypeConstraint() returns false) null will be returned;
	 * @return The resource type or null.
	 */
	ResourceID getResourceTypeConstraint();
	
}
