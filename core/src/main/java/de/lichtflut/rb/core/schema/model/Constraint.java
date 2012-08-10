/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.io.Serializable;
import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;

/**
 * <p>
 *  Constraint for a property. In general there are two types of constraints:
 *  <ol>
 *   <li>Pattern constraints for literal value properties</li>
 *   <li>Resource-constraints for resource references</li>
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
     * Each constraint has a unique qualified name.
     * @return The qualified name of this constraint.
     */
    QualifiedName getQualifiedName();

	/**
	 * Returns the name of a public constraint.
	 */
	String getName();

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
	ResourceID getReference();
	
	/**
	 * Returns wether this constraint can be re-used by other {@link PropertyDeclaration}s or not.
	 * @return true if Constraint is public, false if not
	 */
	boolean isPublic();
	
	/**
	 * Returns whether this constraint references a public constraint.
	 * @return true if yes, false if it hold a literal pattern
	 */
	boolean holdsReference();
	
	/**
	 * This property can be empty. Do not decide on how to treat a {@link PropertyDeclaration} based on this.
	 * If you want to check for {@link Datatype}.Resource use <code>isLiteral</code>
	 * @return a list of applicable {@link Datatype}s for this {@link Constraint}.
	 */
	List<Datatype> getApplicableDatatypes();
	
	/**
	 * @return true if constraint is literal OR references a literal Constraint, 
	 * returns false if references a resource
	 */
	boolean isLiteral();
	
	/**
	 * Returns the constraint as a ResourceNode.
	 * @return
     * @deprecated This constraint may not contain it's representing node.
	 */
    @Deprecated
	ResourceNode asResourceNode();

}
