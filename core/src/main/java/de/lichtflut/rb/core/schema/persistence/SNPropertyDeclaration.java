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

import java.util.HashSet;
import java.util.Set;

import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.model.nodes.views.SNText;

import de.lichtflut.infra.Infra;
import de.lichtflut.rb.core.schema.RBSchema;


/**
 * <p>
 * Represents the declaration of a Property, which can be assigned to a Class
 * <p>
 * 
 * <p>
 * Consists of a property and constraints:
 * <ul>
 *  <li> identifier (URI)</li>
 *  <li> datatype</li>
 *  <li> constraints</li>
 * </ul>
 * 
 * 	Created: 20.01.2009
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNPropertyDeclaration extends ResourceView {

	/**
	 * Constructor for a new property declaration node.
	 */
	public SNPropertyDeclaration(final Context context) {
		Association.create(this, RDF.TYPE, RBSchema.PROPERTY_DECL,context);
	}
	
	/**
	 * Creates a view for given resource.
	 * @param resource
	 */
	public SNPropertyDeclaration(final ResourceNode resource) {
		super(resource);
	}
	
	// -----------------------------------------------------

	/**
	 * Get the unique identifier.
	 */
	public ResourceID getIdentifier() {
		return this.getResource();
	}
	
	public void setIdentifier(final ResourceID id, final Context context) {
		this.setName(id.getName());
		this.setNamespace(id.getNamespace());
	}
	
	public ElementaryDataType getDatatype() {
		SemanticNode type = getSingleAssociationClient(RBSchema.HAS_DATATYPE);
		if (type != null) {
			String name = type.asValue().asText().getStringValue();
			return ElementaryDataType.valueOf(name);
		} else {
			return null;
		}
	}
	
	public void setDatatype(final ElementaryDataType type, final Context context) {
		if (!Infra.equals(getDatatype(), type)){
			removeAssocs(RBSchema.HAS_DATATYPE);
			Association.create(this, RBSchema.HAS_DATATYPE, new SNText(type.name()), context);
		}
	}
	
	/**
	 * Add a literal constraint to this Property Declaration.
	 * @param contraint The literal constraint, e.g. a RegEX Pattern.
	 * @param context The context.
	 * @return The constraint node.
	 */
	public SNConstraint addLiteralConstraint(final String constraint, final Context context) {
		final SNConstraint constraintNode = new SNConstraint(constraint, context);
		Association.create(this, RBSchema.HAS_LITERAL_CONSTRAINT, constraintNode, context);
		return constraintNode;
	}
	
	/**
	 * Add a type constraint to this Property Declaration.
	 * @param constraint The type constraint.
	 * @param context The context.
	 * @return The constraint node.
	 */
	public SNConstraint addTypeConstraint(final ResourceID constraint, final Context context) {
		final SNConstraint constraintNode = new SNConstraint(constraint, context);
		Association.create(this, RBSchema.HAS_LITERAL_CONSTRAINT, constraintNode, context);
		return constraintNode;
	}
	
	/**
	 * Get all constraints of this Property Declaration.
 	 * @return The set of all constraints.
	 */
	public Set<SNConstraint> getConstraints() {
		final Set<SNConstraint> result = new HashSet<SNConstraint>();
		for (Association assoc: getAssociations()){
			if (RBSchema.HAS_LITERAL_CONSTRAINT.equals(assoc.getPredicate())){
				result.add(new SNConstraint(assoc.getClient().asResource()));
			} else if (RBSchema.HAS_TYPE_CONSTRAINT.equals(assoc.getPredicate())){
				result.add(new SNConstraint(assoc.getClient().asResource()));
			} else if (RBSchema.HAS_CONSTRAINT.equals(assoc.getPredicate())){
				result.add(new SNConstraint(assoc.getClient().asResource()));
			}
		}
		return result;
	}
	
	// -----------------------------------------------------
	
	/* (non-Javadoc)
	 * @see org.arastreju.sge.model.nodes.views.ResourceView#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("PropertyDeclaration[" + super.toString() + "]");
		if (getIdentifier() != null) {
			sb.append(" " + getIdentifier());
		}
		if (getDatatype() != null) {
			sb.append(" " + getDatatype());
		}
		sb.append(" " + getConstraints());
		return sb.toString();
	}
	
	// -----------------------------------------------------
	
	/**
	 * Removes all associations with given predicate.
	 * @param predicate The predicate.
	 */
	protected void removeAssocs(final ResourceID predicate){
		Set<Association> assocs = getAssociations(predicate);
		for (Association assoc : assocs) {
			revoke(assoc);
		}
	}
	
}
