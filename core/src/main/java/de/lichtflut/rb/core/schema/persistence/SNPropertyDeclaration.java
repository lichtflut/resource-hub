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

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.model.nodes.views.SNScalar;
import org.arastreju.sge.model.nodes.views.SNText;

import de.lichtflut.infra.Infra;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.FieldLabelDefinition;
import de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl;
import de.lichtflut.rb.core.schema.model.impl.ConstraintImpl;


/**
 * <p>
 * Represents the Property Declaration to a Class.
 * <p>
 *
 * <p>
 * Consists of a property and constraints:
 * <ul>
 *  <li> descriptor</li>
 *  <li> cardinality (minOccurs, maxOccurs)</li>
 *  <li> Many-In-Time-Flag (nyi)</li>
 * </ul>
 *
 * 	Created: 20.01.2009
 *
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNPropertyDeclaration extends ResourceView {

	/**
	 * Constructor for a new property declaration node.
	 */
	public SNPropertyDeclaration() {
	}

	/**
	 * Creates a view for given resource.
	 * @param resource -
	 */
	public SNPropertyDeclaration(final ResourceNode resource) {
		super(resource);
	}
	
	// -----------------------------------------------------
	
	/**
	 * Get the TypeDefinition.
	 * @return The TypeDefinition.
	 */
	public Constraint getConstraint() {
		Constraint constraint = null;
		SemanticNode constraintNode = SNOPS.singleObject(this, RBSchema.HAS_CONSTRAINT);
		if(null != constraintNode){
			constraint = new ConstraintImpl(constraintNode.asResource());
		}
		return constraint;
	}
	
	/**
	 * @return true if the SNPropertyDeclaration has a {@link Constraint}, false if not.
	 */
	public boolean hasConstraint(){
		boolean hasConstraint = false;
		if(getConstraint() != null){
			hasConstraint = true;
		}
		return hasConstraint;
	}

	/**
	 * Returns the property declared by this property declaration.
	 * @return The property.
	 */
	public ResourceID getPropertyDescriptor() {
		final SemanticNode node = SNOPS.singleObject(this, RBSchema.HAS_DESCRIPTOR);
		if (node != null) {
			return node.asResource();
		} else {
			return null;
		}
	}
	
	/**
	 * Set the property declared by this property declaration.
	 * @param property The property.
	 */
	public void setPropertyDescriptor(final ResourceID property) {
		if (!Infra.equals(getPropertyDescriptor(), property)){
			SNOPS.assure(this, RBSchema.HAS_DESCRIPTOR, property);
		}
	}

	/**
	 * Returns the {@link Datatype} declared by this property declaration.
	 * @return The property.
	 */
	public Datatype getDatatype() {
		final SemanticNode node = SNOPS.singleObject(this, RBSchema.HAS_DATATYPE);
		if (node != null) {
			return Datatype.valueOf(node.asValue().getStringValue());
		} else {
			throw new IllegalArgumentException("No Datatype found");
		}
	}

	/**
	 * Sets the {@link Datatype}
	 * @param datatype
	 */
	public void setDatatype(final Datatype datatype){
		if(datatype != null){
			SNOPS.assure(this, RBSchema.HAS_DATATYPE, datatype.name());
		}
	}
	
	/**
	 * Returns the min. occurrences.
	 * @return {@link SNScalar}
	 */
	public SNScalar getMinOccurs(){
		SemanticNode minOccurs = SNOPS.singleObject(this, RBSchema.MIN_OCCURS);
		if (minOccurs != null) {
			return minOccurs.asValue().asScalar();
		} else {
			return null;
		}
	}

	/**
	 * Sets the min. occurrences
	 * @param minOccurs -
	 */
	public void setMinOccurs(final SNScalar minOccurs) {
		if (!Infra.equals(getMinOccurs(), minOccurs)){
			SNOPS.assure(this, RBSchema.MIN_OCCURS, minOccurs);
		}
	}

	/**
	 * Returns the max. occurrences.
	 * @return {@link SNScalar}
	 */
	public SNScalar getMaxOccurs(){
		SemanticNode maxOccurs = SNOPS.singleObject(this, RBSchema.MAX_OCCURS);
		if (maxOccurs != null) {
			return maxOccurs.asValue().asScalar();
		} else {
			return null;
		}
	}

	/**
	 * Sets the max. occurrences
	 * @param maxOccurs -
	 */
	public void setMaxOccurs(final SNScalar maxOccurs) {
		if (!Infra.equals(getMaxOccurs(), maxOccurs)){
			SNOPS.assure(this, RBSchema.MAX_OCCURS, maxOccurs);
		}
	}
	
	/**
	 * Sets the constraint.
	 * @param constraint
	 */
	public void setConstraint(final Constraint constraint){
		SNOPS.assure(this, RBSchema.HAS_CONSTRAINT, constraint.asResourceNode());
	}

	/**
	 * Set the FieldLabel.
	 * @param def The field label definition
	 */
	public void setFieldLabelDefinition(final FieldLabelDefinition def){
		SNOPS.associate(this, RBSystem.HAS_FIELD_LABEL, new SNText(def.getDefaultLabel()));
	}
	
	/**
	 * Get the FieldLabel.
	 */
	public FieldLabelDefinition getFieldLabelDefinition(){
		final String defaultName = getPropertyDescriptor().getQualifiedName().getSimpleName();
		final FieldLabelDefinition def = new FieldLabelDefinitionImpl(defaultName);
		final Set<? extends Statement> assocs = getAssociations(RBSystem.HAS_FIELD_LABEL);
		for (Statement current : assocs) {
			// TODO: Evaluate context to locale
			def.setDefaultLabel(current.getObject().asValue().getStringValue());
		}
		return def;
	}
	
	// -- ORDER -------------------------------------------
	
	/**
	 * Set the successor of this PropertyDeclaration in the ordered list.
	 * @param successor The successor node.
	 * @param contexts The contexts.
	 * @return The successor or null.
	 */
	public void setSuccessor(final SNPropertyDeclaration successor, final Context... contexts) {
		SNOPS.assure(this, Aras.IS_PREDECESSOR_OF, successor, contexts);
	}
	
	// ------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer("PropertyDecl[" + super.toString() + "] ");
		if (getPropertyDescriptor() != null){
			sb.append(getPropertyDescriptor().toURI());
		}
		sb.append(" " + getMinOccurs() + ".." + getMaxOccurs());
		sb.append("\n\t\t" + getConstraint());
		return sb.toString();
	}

}
