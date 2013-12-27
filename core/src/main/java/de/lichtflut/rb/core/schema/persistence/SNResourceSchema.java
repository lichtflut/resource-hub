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
package de.lichtflut.rb.core.schema.persistence;

import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.structure.LinkedOrderedNodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Schema for a Class/Resource Type.
 * </p>
 *
 * <p>
 * 	Created Apr 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNResourceSchema extends ResourceView {

	/**
	 * Generated serial number.
	 */
	private static final long serialVersionUID = -2376213727456721748L;

	// -----------------------------------------------------

	/**
	 * Constructor for new Resource Schemas.
	 */
	public SNResourceSchema() {
		this(new SNResource());
	}

	/**
	 * Constructor.
	 * @param resource -
	 */
	public SNResourceSchema(final ResourceNode resource) {
		super(resource);
		SNOPS.associate(this, RDF.TYPE, RBSchema.RESOURCE_SCHEMA);
	}

	// -----------------------------------------------------

	/**
	 * Returns the Class described by this schemas.
	 * @return The class node.
	 */
	public SNClass getDescribedType() {
        return SNClass.from(SNOPS.singleObject(this, RBSchema.DESCRIBES));
	}

	/**
	 * Set the Class described by this schema.
	 * @param type The type node.
	 */
	public void setDescribedType(final ResourceID type) {
		SNOPS.assure(this, RBSchema.DESCRIBES, type);
	}

	/**
	 * @return true if there is a label expression.
	 */
	public boolean hasLabelExpression() {
		return null != SNOPS.singleAssociation(this, RBSchema.HAS_LABEL_EXPRESSION);
	}

	/**
	 * Returns the textual expression for label building.
	 * @return The class node.
	 */
	public SNText getLabelExpression() {
		SemanticNode node = SNOPS.singleObject(this, RBSchema.HAS_LABEL_EXPRESSION);
		if (node != null){
			return node.asValue().asText();
		} else {
			return null;
		}
	}

	/**
	 * Set the textual expression for label building.
	 * @param expression The expression.
	 */
	public void setLabelExpression(final SNText expression) {
		SNOPS.assure(this, RBSchema.HAS_LABEL_EXPRESSION, expression);
	}

	/**
	 * Set one or more {@link ResourceID}s of {@link PropertyDeclaration}s that act as quick-info view.
	 * @param quickInfo The ResourceId
	 */
	public void addQuickInfo(final SNQuickInfo quickInfo){
		SNOPS.associate(this, RBSchema.HAS_QUICK_INFO, quickInfo);
	}

	public List<ResourceID> getQuickInfo(){
		final List<ResourceNode> unsorted = new ArrayList<ResourceNode>();
		for (Statement current : getAssociations(RBSchema.HAS_QUICK_INFO)) {
			unsorted.add(current.getObject().asResource());
		}
		List<ResourceID> result = new ArrayList<ResourceID>(unsorted.size());
		List<ResourceNode> sorted = LinkedOrderedNodes.sortBySuccessors(unsorted);
		for (ResourceNode resourceNode : sorted) {
			result.add(SNOPS.fetchObject(resourceNode, RBSchema.HAS_QUICK_INFO).asResource());
		}
		return result;
	}

	/**
	 * Adds the given PropertyAssertion to this class.
	 * @param decl The declaration to be added.
	 */
	public void addPropertyDeclaration(final SNPropertyDeclaration decl){
		SNOPS.associate(this, RBSchema.HAS_PROPERTY_DECL, decl);
	}

	/**
	 * Collects all PropertyAssertions defined by this Schema.
	 * @return The list of all property assertions.
	 */
	public List<SNPropertyDeclaration> getPropertyDeclarations(){
		final List<ResourceNode> unsorted = new ArrayList<ResourceNode>();
		for (Statement current : getAssociations(RBSchema.HAS_PROPERTY_DECL)) {
			unsorted.add(current.getObject().asResource());
		}
		final List<SNPropertyDeclaration> result = new ArrayList<SNPropertyDeclaration>(unsorted.size());
		final List<ResourceNode> sorted = LinkedOrderedNodes.sortBySuccessors(unsorted);
		for (ResourceNode node : sorted) {
			result.add(new SNPropertyDeclaration(node));
		}
		return result;
	}

	/**
	 * @param decl The declaration to be removed.
	 */
	public void removePropertyDeclaration(final SNPropertyDeclaration decl) {
		Set<? extends Statement> assocs = getAssociations(RBSchema.HAS_PROPERTY_DECL);
		Statement toBeRemoved = null;
		for (Statement current : assocs) {
			if (decl.equals(current.getObject())){
				toBeRemoved = current;
				break;
			}
		}
		removeAssociation(toBeRemoved);
	}

	// -----------------------------------------------------

	public static SNResourceSchema view(final ResourceNode node) {
		if (node instanceof SNResourceSchema) {
			return (SNResourceSchema) node;
		} else {
			return new SNResourceSchema(node);
		}
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ResourceSchema[" + super.toString() + "]");
		sb.append(" for " + getDescribedType() + "\n");
		for(SNPropertyDeclaration pa : getPropertyDeclarations()){
			sb.append("\t" + pa + "\n");
		}
		return sb.toString();
	}

}
