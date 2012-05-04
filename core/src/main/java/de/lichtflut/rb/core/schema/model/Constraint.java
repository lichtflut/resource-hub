/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.io.Serializable;
import java.util.List;

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
 *  <p>
 *  Type constraint for resource references have to be interpreted as follows:
 *  <pre>
 *  	if constraint type is X and the resource applied id Y
 *  	the constraint is fulfilled
 *  	if (X rdf:type Y) is true
 *   </pre>
 * </p>
 *
 *
 * <p>
 * Please note, that cardinality (min occurs, max occurs) are not constraints.
 * </p>
 *
 * <p>
 * 	Created Feb 3, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface Constraint extends Serializable {

	/**
	 * @return the ID of a Constraint.
	 */
	ResourceID getID();
	
	/**
	 * Returns the name of a public constraint.
	 */
	String getName();
	
	/**
	 * Check if this constraint is a type-of constraint for resources.
	 * @return true if this constraint is for resources.
	 */
	boolean isResourceReference();

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

	/**
	 * Returns wether this constraint can be re-used by other {@link PropertyDeclaration}s or not.
	 * @return true if Constraint is public, false if not
	 */
	boolean isPublicConstraint();
	
	/**
	 * @return a list of applicable {@link Datatype}s for this {@link Constraint}.
	 */
	List<Datatype> getApplicableDatatypes();
}
