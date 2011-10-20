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

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.model.nodes.views.SNText;

import de.lichtflut.infra.Infra;
import de.lichtflut.rb.core.schema.RBSchema;


/**
 * <p>
 * Represents a type definition, which specifies the property type of a Property Declaration.
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
@SuppressWarnings("serial")
public class SNPropertyTypeDefinition extends ResourceView {

	/**
	 * Constructor for a new property declaration node.
	 * @param contexts The contexts for the statements.
	 */
	public SNPropertyTypeDefinition(final Context... contexts) {
		this(new SNResource(), contexts);
	}

	/**
	 * Creates a view for given resource.
	 * @param resource The resource node.
	 * @param contexts The contexts for the statements.
	 */
	public SNPropertyTypeDefinition(final ResourceNode resource, final Context... contexts) {
		super(resource);
		Association.create(this, RDF.TYPE, RBSchema.PROPERTY_TYPE_DEF, contexts);
	}

	// -----------------------------------------------------

	/**
	 * @return The display name.
	 */
	public String getDisplayName() {
		return SNOPS.string(SNOPS.singleObject(this, RBSchema.HAS_NAME));
	}
	
	/**
	 * Set the display name.
	 * @param name The name.
	 * @param ctx The context.
	 */
	public void setDisplayName(final String name, final Context... ctx) {
		SNOPS.replace(this, RBSchema.HAS_NAME, new SNText(name), ctx);
	}
	
	/**
	 * Returns the datatype.
	 * @return {@link ElementaryDataType}
	 */
	public ElementaryDataType getDatatype() {
		SemanticNode type = SNOPS.singleObject(this, RBSchema.HAS_DATATYPE);
		if (type != null) {
			String name = type.asValue().asText().getStringValue();
			return ElementaryDataType.valueOf(name);
		} else {
			return null;
		}
	}

	/**
	 * Sets the datatype.
	 * @param type -
	 * @param context -
	 */
	public void setDatatype(final ElementaryDataType type, final Context context) {
		if (!Infra.equals(getDatatype(), type)){
			removeAssocs(RBSchema.HAS_DATATYPE);
			Association.create(this, RBSchema.HAS_DATATYPE, new SNText(type.name()), context);
		}
	}

	/**
	 * Add a literal constraint to this Property Declaration.
	 * @param constraint The literal constraint, e.g. a RegEX Pattern.
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
		Association.create(this, RBSchema.HAS_TYPE_CONSTRAINT, constraintNode, context);
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
				result.add(new SNConstraint(assoc.getObject().asResource()));
			} else if (RBSchema.HAS_TYPE_CONSTRAINT.equals(assoc.getPredicate())){
				result.add(new SNConstraint(assoc.getObject().asResource()));
			} else if (RBSchema.HAS_CONSTRAINT.equals(assoc.getPredicate())){
				result.add(new SNConstraint(assoc.getObject().asResource()));
			}
		}
		return result;
	}

	/**
	 * Check if this is a public definition.
	 * @return true if it is defined as public.
	 */
	public boolean isPublic() {
		return Aras.TRUE.equals(SNOPS.singleObject(this, RBSchema.IS_PUBLIC_TYPE_DEF));
	}
	
	/**
	 * Make this Property Type Definition public.
	 * @param contexts The contexts of this statement. 
	 */
	public void setPublic(final Context... contexts) {
		SNOPS.replace(this, RBSchema.IS_PUBLIC_TYPE_DEF, Aras.TRUE, contexts);
	}
	
	/**
	 * Make this Property Type Definition public.
	 * @param contexts The contexts of this statement. 
	 */
	public void setPrivate(final Context... contexts) {
		SNOPS.replace(this, RBSchema.IS_PUBLIC_TYPE_DEF, Aras.FALSE, contexts);
	}
	
	
	// -----------------------------------------------------

	/* (non-Javadoc)
	 * @see org.arastreju.sge.model.nodes.views.ResourceView#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("PropertyDeclaration[" + super.toString() + "]");
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
