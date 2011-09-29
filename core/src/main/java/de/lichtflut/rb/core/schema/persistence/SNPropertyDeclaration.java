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
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.model.nodes.views.SNScalar;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.infra.Infra;
import de.lichtflut.rb.core.schema.RBSchema;


/**
 * <p>
 * Represents the assertion of a Property Declaration to a Class.
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
public class SNPropertyDeclaration extends ResourceView implements Comparable<SNPropertyDeclaration>{

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
	 * @param singleObject
	 */
	private static SNPropertyDeclaration view(final ResourceNode node) {
		if (node == null){
			return null;
		} else if (node instanceof SNPropertyDeclaration) {
			return (SNPropertyDeclaration) node;
		} else {
			return new SNPropertyDeclaration(node);
		}
	}

	//-----------------------------------------------------

	/**
	 * Get the TypeDefinition.
	 * @return The TypeDefinition.
	 */
	public SNPropertyTypeDefinition getTypeDefinition() {
		SemanticNode node = SNOPS.singleObject(this, RBSchema.HAS_PROPERTY_TYPE_DEF);
		if (node != null){
			return new SNPropertyTypeDefinition(node.asResource());
		} else {
			return null;
		}
	}

	/**
	 * Set the TypeDefinition.
	 * @param typeDef The TypeDefinition
	 * @param context The context.
	 */
	public void setTypeDefinition(final SNPropertyTypeDefinition typeDef, final Context context) {
		if (!Infra.equals(getTypeDefinition(), typeDef)){
			removeAssocs(RBSchema.HAS_PROPERTY_TYPE_DEF);
			Association.create(this, RBSchema.HAS_PROPERTY_TYPE_DEF, typeDef, context);
		}
	}

	/**
	 * Returns the property declared by this property declaration.
	 * @return The property.
	 */
	public ResourceID getPropertyDescriptor() {
		SemanticNode node = SNOPS.singleObject(this, RBSchema.HAS_DESCRIPTOR);
		if (node != null){
			return new SimpleResourceID(new QualifiedName(node.asValue().getStringValue()));
		} else {
			return null;
		}
	}

	/**
	 * Set the property declared by this property declaration.
	 * @param property The property.
	 * @param context The context.
	 */
	public void setDescriptor(final ResourceID property, final Context context) {
		if (!Infra.equals(getPropertyDescriptor(), property)){
			SNValue pDescriptor = new SNValue(ElementaryDataType.URI,property.getQualifiedName().toURI());
			removeAssocs(RBSchema.HAS_DESCRIPTOR);
			Association.create(this, RBSchema.HAS_DESCRIPTOR, pDescriptor, context);
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
	 * @param context -
	 */
	public void setMinOccurs(final SNScalar minOccurs, final Context context) {
		if (!Infra.equals(getMinOccurs(), minOccurs)){
			removeAssocs(RBSchema.MIN_OCCURS);
			Association.create(this, RBSchema.MIN_OCCURS, minOccurs, context);
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
	 * @param minOccurs -
	 * @param context -
	 */
	public void setMaxOccurs(final SNScalar minOccurs, final Context context) {
		if (!Infra.equals(getMaxOccurs(), minOccurs)){
			removeAssocs(RBSchema.MAX_OCCURS);
			Association.create(this, RBSchema.MAX_OCCURS, minOccurs, context);
		}
	}
	
	/**
	 * Get the successor of this PropertyDeclaration in the ordered list.
	 * @return The successor or null.
	 */
	public SNPropertyDeclaration getSuccessor() {
		final SemanticNode successor = SNOPS.singleObject(this, Aras.IS_PREDECESSOR_OF);
		return view(successor.asResource());
	}

	/**
	 * Set the successor of this PropertyDeclaration in the ordered list.
	 * @param successor The successor node.
	 * @param contexts The contexts.
	 * @return The successor or null.
	 */
	public void setSuccessor(final SNPropertyDeclaration successor, final Context... contexts) {
		SNOPS.replace(this, Aras.IS_PREDECESSOR_OF, successor, contexts);
	}
	
	//-----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(final SNPropertyDeclaration other) {
		if (this.getPropertyDescriptor() == null){
			return 1;
		} else if (other.getPropertyDescriptor() == null){
			return -1;
		} else {
			return Infra.compare(this.getPropertyDescriptor().getQualifiedName(), other.getPropertyDescriptor().getQualifiedName());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer("PropertyAssertion[" + super.toString() + "] ");
		if (getPropertyDescriptor() != null){
			sb.append(getPropertyDescriptor().getQualifiedName().toURI());
		}
		sb.append(" " + getMinOccurs() + ".." + getMaxOccurs());
		sb.append("\n\t\t" + getTypeDefinition());
		return sb.toString();
	}

	//-----------------------------------------------------

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
