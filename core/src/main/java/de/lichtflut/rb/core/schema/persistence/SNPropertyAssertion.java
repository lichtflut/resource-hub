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

import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.model.nodes.views.SNScalar;

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
public class SNPropertyAssertion extends ResourceView implements Comparable<SNPropertyAssertion>{

	/**
	 * Constructor for a new property declaration node.
	 */
	public SNPropertyAssertion() {
	}
	
	/**
	 * Creates a view for given resource.
	 * @param resource
	 */
	public SNPropertyAssertion(final ResourceNode resource) {
		super(resource);
	}
	
	//-----------------------------------------------------

	/**
	 * Returns the Property Declaration.
	 * @return The property decl.
	 */
	public SNPropertyDeclaration getPropertyDeclaration() {
		SemanticNode node = getSingleAssociationClient(RBSchema.HAS_PROPERTY_DECL);
		if (node != null){
			return new SNPropertyDeclaration(node.asResource());
		} else {
			return null;
		}
	}
	
	/**
	 * Set the Property Declaratio.
	 * @param propertyDecl The property decl.
	 * @param context The context.
	 */
	public void setPropertyDeclaration(final SNPropertyDeclaration propertyDecl, final Context context) {
		if (!Infra.equals(getDescriptor(), propertyDecl)){
			removeAssocs(RBSchema.HAS_PROPERTY_DECL);
			Association.create(this, RBSchema.HAS_PROPERTY_DECL, propertyDecl, context);
		}
	}
	
	/**
	 * Returns the property declared by this property declaration.
	 * @return The property.
	 */
	public ResourceID getDescriptor() {
		SemanticNode node = getSingleAssociationClient(RBSchema.HAS_DESCRIPTOR);
		if (node != null){
			return node.asResource();
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
		if (!Infra.equals(getDescriptor(), property)){
			removeAssocs(RBSchema.HAS_DESCRIPTOR);
			Association.create(this, RBSchema.HAS_DESCRIPTOR, property, context);
		}
	}
	
	public SNScalar getMinOccurs(){
		SemanticNode minOccurs = getSingleAssociationClient(RBSchema.MIN_OCCURS);
		if (minOccurs != null) {
			return minOccurs.asValue().asScalar();
		} else {
			return null;
		}
	}
	
	public void setMinOccurs(final SNScalar minOccurs, final Context context) {
		if (!Infra.equals(getMinOccurs(), minOccurs)){
			removeAssocs(RBSchema.MIN_OCCURS);
			Association.create(this, RBSchema.MIN_OCCURS, minOccurs, context);
		}
	}
	
	public SNScalar getMaxOccurs(){
		SemanticNode maxOccurs = getSingleAssociationClient(RBSchema.MAX_OCCURS);
		if (maxOccurs != null) {
			return maxOccurs.asValue().asScalar();
		} else {
			return null;
		}
	}
	
	public void setMaxOccurs(final SNScalar minOccurs, final Context context) {
		if (!Infra.equals(getMaxOccurs(), minOccurs)){
			removeAssocs(RBSchema.MAX_OCCURS);
			Association.create(this, RBSchema.MAX_OCCURS, minOccurs, context);
		}
	}
	
	//-----------------------------------------------------
	
	public int compareTo(final SNPropertyAssertion other) {
		if (this.getDescriptor() == null){
			return 1;
		} else if (other.getDescriptor() == null){
			return -1;
		} else {
			return Infra.compare(this.getDescriptor().getQualifiedName(), other.getDescriptor().getQualifiedName());
		}
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer("PropertyAssertion[" + super.toString() + "] ");
		if (getDescriptor() != null){
			sb.append(getDescriptor().getQualifiedName().toURI());
		}
		sb.append(" " + getMinOccurs() + ".." + getMaxOccurs());
		sb.append("\n\t\t" + getPropertyDeclaration());
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
