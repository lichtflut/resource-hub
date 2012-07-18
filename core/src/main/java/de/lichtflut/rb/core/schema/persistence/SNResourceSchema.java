/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.structure.LinkedOrderedNodes;

import de.lichtflut.rb.core.schema.RBSchema;

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
		SemanticNode node = SNOPS.singleObject(this, RBSchema.DESCRIBES);
		if (node != null){
			return node.asResource().asClass();
		} else {
			return null;
		}
	}

	/**
	 * Set the Class described by this schema.
	 * @param type The type node.
	 * @param ctx The context.
	 */
	public void setDescribedType(final ResourceID type, final Context... ctx) {
		SNOPS.assure(this, RBSchema.DESCRIBES, type, ctx);
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
