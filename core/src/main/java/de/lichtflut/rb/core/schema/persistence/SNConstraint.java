/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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
package de.lichtflut.rb.core.schema.persistence;

import java.util.Set;

import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.model.nodes.views.SNText;

import de.lichtflut.rb.core.schema.RBSchema;


/**
 * <p>
 * Represents the declaration of a Constraint for a Property Declaration or Assertion.
 * <p>
 *
 * <p>
 * There are two types of Constraints:
 * <ul>
 *  <li>type constraints (for resource references)</li>
 *  <li>literal constraints (for values)</li>
 * </ul>
 *
 * 	Created: 26.04.2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class SNConstraint extends ResourceView {

	/**
	 * Constructor for a new constraint node.
	 */
	public SNConstraint() {
	}

	/**
	 * Creates a view for given resource.
	 * @param resource -
	 */
	public SNConstraint(final ResourceNode resource) {
		super(resource);
	}

	/**
	 * Creates a new literal constraint.
	 * @param literalConstraint The literal constraint.
	 * @param ctx The context.
	 */
	public SNConstraint(final String literalConstraint, final Context ctx) {
		Association.create(this, RDF.TYPE, RBSchema.LITERAL_CONSTRAINT, ctx);
		Association.create(this, RBSchema.HAS_CONSTRAINT_VALUE, new SNText(literalConstraint), ctx);
	}

	/**
	 * Creates a new type constraint.
	 * @param typeConstraint The type constraint.
	 * @param ctx The context.
	 */
	public SNConstraint(final ResourceID typeConstraint, final Context ctx) {
		Association.create(this, RDF.TYPE, RBSchema.TYPE_CONSTRAINT, ctx);
		Association.create(this, RBSchema.HAS_CONSTRAINT_VALUE, typeConstraint, ctx);
	}

	// -----------------------------------------------------

	/**
	 * Returns whether constraint is literal constraint.
	 * @return boolean
	 */
	public boolean isLiteralConstraint() {
		return isOfType(RBSchema.LITERAL_CONSTRAINT);
	}
	/**
	 * Returns whether constraint is type constraint.
	 * @return boolean
	 */
	public boolean isTypeConstraint() {
		return isOfType(RBSchema.TYPE_CONSTRAINT);
	}

	/**
	 * Value of constraint.
	 * @return {@link SemanticNode}
	 */
	public SemanticNode getConstraintValue() {
		return getSingleAssociationClient(RBSchema.HAS_CONSTRAINT_VALUE);
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Constraint[" + super.toString() + "]");
		return sb.toString();
	}

	// -----------------------------------------------------

	/**
	 * Checks if constraint is of a given type.
	 * @param type -
	 * @return boolean true if constraint is of given type, false if not
	 */
	private boolean isOfType(final ResourceID type) {
		final Set<SemanticNode> types = getAssociationClients(RDF.TYPE);
		for (SemanticNode current : types) {
			if (current.isResourceNode() &&	current.asResource().asClass().isSpecializationOf(type)) {
				return true;
			}
		}
		return false;
	}

}
