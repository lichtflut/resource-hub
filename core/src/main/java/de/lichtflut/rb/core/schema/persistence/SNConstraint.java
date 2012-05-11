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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.model.nodes.views.SNBoolean;
import org.arastreju.sge.model.nodes.views.SNText;

import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.Datatype;


/**
 * <p>
 * Represents the declaration of a Constraint for a Property Declaration.
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
	public SNConstraint(final String literalConstraint, List<Datatype> datatypes, Context ctx) {
		SNOPS.associate(this, RDF.TYPE, RBSchema.PROPERTY_CONSTRAINT, ctx);
		SNOPS.associate(this, RBSchema.HAS_CONSTRAINT_VALUE, new SNText(literalConstraint), ctx);
		SNOPS.assure(this, RBSchema.IS_PUBLIC_CONSTRAINT, new SNBoolean(false), ctx);
		SNOPS.assure(this, RBSchema.RESOURCE_CONSTRAINT, new SNBoolean(false), ctx);
		setApplicableDatatypes(datatypes, ctx);
	}
	
	/**
	 * Creates a new type constraint.
	 * @param typeConstraint The type constraint.
	 * @param ctx The context.
	 */
	public SNConstraint(final ResourceID typeConstraint, final Context ctx) {
		SNOPS.associate(this, RDF.TYPE, RBSchema.PROPERTY_CONSTRAINT, ctx);
		SNOPS.associate(this, RBSchema.HAS_CONSTRAINT_VALUE, typeConstraint, ctx);
		SNOPS.assure(this, RBSchema.IS_PUBLIC_CONSTRAINT, new SNBoolean(false), ctx);
		SNOPS.assure(this, RBSchema.RESOURCE_CONSTRAINT, new SNBoolean(true), ctx);
		setApplicableDatatypes(Collections.singletonList(Datatype.RESOURCE), ctx);
	}

	// -----------------------------------------------------
	
	/**
	 * Returns whether constraint is type constraint.
	 * @return boolean
	 */
	public boolean isResourceConstraint() {
		boolean node = SNOPS.singleObject(this, RBSchema.RESOURCE_CONSTRAINT).asValue().getBooleanValue();
		return node;
	}

	/**
	 * Returns whether constraint is public.
	 * @return true if constraint is public, false if not
	 */
	public boolean isPublic(){
		return SNOPS.singleObject(this, RBSchema.IS_PUBLIC_CONSTRAINT).asValue().getBooleanValue();
	}
	
	/**
	 * @return the name of this constraint.
	 */
	public String getName(){
		SemanticNode name = SNOPS.singleObject(this, RBSchema.HAS_NAME);
		if(name == null){
			return null;
		}
		else{
			return name.asValue().getStringValue();
		}
	}
	
	/**
	 * @return the id of this constraint
	 */
	public ResourceID getID(){
		return (ResourceID) SNOPS.singleObject(this, RBSchema.HAS_IDENTIFIER);
	}
	
	/**
	 * Value of constraint.
	 * @return {@link SemanticNode}
	 */
	public SemanticNode getConstraintValue() {
		return SNOPS.singleObject(this, RBSchema.HAS_CONSTRAINT_VALUE);
	}
	
	/**
	 * @return a {@link List} of applicable {@link Datatype}s
	 */
	public List<Datatype> getApplicableDatatypes(){
		List<Datatype> datatypes = new ArrayList<Datatype>();
		for (SemanticNode snType : SNOPS.objects(this, RBSchema.HAS_DATATYPE)) {
			datatypes.add(Datatype.valueOf(snType.asValue().getStringValue()));
		}
		return datatypes;
	}

	/**
	 * Sets the ID.
	 * @param publicID
	 */
	public void setID(ResourceID publicID, Context ctx) {
		if(publicID != null){
			SNOPS.assure(this, RBSchema.HAS_IDENTIFIER, publicID, ctx);
		}
	}

	/**
	 * Sets the name.
	 * @param name
	 * @param context
	 */
	public void setName(String name, Context ctx) {
		if(name != null){
			SNOPS.assure(this, RBSchema.HAS_NAME, new SNText(name), ctx);
		}
	}

	/**
	 * Sets whether this Constraint is public or not.
	 * @param b
	 * @param context
	 */
	public void setPublic(boolean isPublic, Context ctx) {
		SNOPS.assure(this, RBSchema.IS_PUBLIC_CONSTRAINT, new SNBoolean(isPublic), ctx);
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
		final Set<SemanticNode> types = SNOPS.objects(this, RDF.TYPE);
		for (SemanticNode current : types) {
			if (current.isResourceNode() &&	current.asResource().asClass().isSpecializationOf(type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Sets the applicable {@link Datatype}s.
	 * @param datatypes
	 * @param ctx
	 */
	private void setApplicableDatatypes(List<Datatype> datatypes, Context ctx){
		if(datatypes == null){
			throw new IllegalArgumentException("No applicable Datatype found for the constraint: " + getQualifiedName());
		}
		for (Datatype datatype : datatypes) {
			SNOPS.associate(this, RBSchema.HAS_DATATYPE, new SNText(datatype.name()), ctx);
		}
	}
	
}
