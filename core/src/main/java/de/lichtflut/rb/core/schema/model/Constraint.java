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
package de.lichtflut.rb.core.schema.model;

import java.io.Serializable;
import java.util.List;

import org.arastreju.sge.model.ResourceID;
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
	 * Returns the display name of a public constraint.
	 */
	String getName();

    /**
     * Returns wether this constraint can be re-used by other {@link PropertyDeclaration}s or not.
     * @return true if Constraint is public, false if not
     */
    boolean isPublic();

    // ----------------------------------------------------

    /**
     * @return true if constraint is literal OR references a literal Constraint,
     * returns false if references a resource
     */
    boolean isLiteral();

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
	ResourceID getTypeConstraint();

    // ----------------------------------------------------
	
	/**
	 * This property can be empty. Do not decide on how to treat a {@link PropertyDeclaration} based on this.
	 * If you want to check for {@link Datatype}.Resource use <code>isLiteral</code>
	 * @return a list of applicable {@link Datatype}s for this {@link Constraint}.
	 */
	List<Datatype> getApplicableDatatypes();
	
}
