/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.model.nodes.views.SNClass;

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
	 * @param ctx The contexts.
	 */
	public SNResourceSchema(final Context... ctx) {
		this(new SNResource(), ctx);
	}
	
	/**
	 * Constructor.
	 * @param resource -
	 */
	public SNResourceSchema(final ResourceNode resource, final Context... ctx) {
		super(resource);
		Association.create(this, RDF.TYPE, RBSchema.RESOURCE_SCHEMA, ctx);
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
	 * Set the Class described by this schema:.
	 * @param type The type node.
	 * @param ctx The context.
	 */
	public void setDescribedType(final ResourceID type, final Context... ctx) {
		SNOPS.replace(this, RBSchema.DESCRIBES, type, ctx);
	}

	/**
	 * Adds the given PropertyAssertion to this class.
	 * @param decl The declaration to be added.
	 * @param ctx The context.
	 */
	public void addPropertyDeclaration(final SNPropertyDeclaration decl, final Context... ctx){
		Association.create(this, RBSchema.HAS_PROPERTY_DECL, decl, ctx);
	}

	/**
	 * Collects all PropertyAssertions defined by this Schema.
	 * @return The list of all property assertions.
	 */
	public List<SNPropertyDeclaration> getPropertyDeclarations(){
		final List<SNPropertyDeclaration> result = new ArrayList<SNPropertyDeclaration>();
		
		SNPropertyDeclaration current = findFirst(getDeclaredPropertyDeclarations());
		while (current != null) {
			result.add(current);
			current = current.getSuccessor();
		}
		return result;
	}

	/**
	 * Returns only the unsorted property declarations declared for this classifier, not the inherited.
	 * @return The list of property declarations.
	 */
	public List<SNPropertyDeclaration> getDeclaredPropertyDeclarations(){
		List<SNPropertyDeclaration> result = new ArrayList<SNPropertyDeclaration>();
		Set<Association> assocs = getAssociations(RBSchema.HAS_PROPERTY_DECL);
		for (Association current : assocs) {
			result.add(new SNPropertyDeclaration(current.getObject().asResource()));
		}
		return result;
	}

	/**
	 * @see org.arastreju.api.model.semantic.ResourceClassifier#removePropertyDeclaration.
	 * (org.arastreju.api.model.semantic.SNPropertyDeclaration)
	 * @param decl -
	 */
	public void removePropertyDeclaration(final SNPropertyDeclaration decl) {
		Set<Association> assocs = getAssociations(RBSchema.HAS_PROPERTY_DECL);
		Association toBeRemoved = null;
		for (Association current : assocs) {
			if (decl.equals(current.getObject())){
				toBeRemoved = current;
				break;
			}
		}
		revoke(toBeRemoved);
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
	
	// -----------------------------------------------------
	
	private SNPropertyDeclaration findFirst(final Collection<SNPropertyDeclaration> decls) {
		if (decls.isEmpty()) {
			return null;
		}
		ensureCorrectOrder(decls);
		final Set<SNPropertyDeclaration> all = new HashSet<SNPropertyDeclaration>(decls);
		final Set<SNPropertyDeclaration> successors = new HashSet<SNPropertyDeclaration>();
		for (SNPropertyDeclaration current : all) {
			final SNPropertyDeclaration successor = current.getSuccessor();
			if (successors != successor) {
				successors.add(successor);
			}
		}
		all.removeAll(successors);
		return all.iterator().next();
	}
	
	private void ensureCorrectOrder(final Collection<SNPropertyDeclaration> decls) {
		final Set<SNPropertyDeclaration> all = new HashSet<SNPropertyDeclaration>(decls);
		final Set<SNPropertyDeclaration> successors = new HashSet<SNPropertyDeclaration>();
		for (SNPropertyDeclaration current : all) {
			final SNPropertyDeclaration successor = current.getSuccessor();
			if (successors.contains(successor)) {
				throw new IllegalStateException("Order of PropertyDeclarations has been corrupted!");
			} else if (null != successor) {
				successors.add(successor);
			}
		}
		all.removeAll(successors);
		if (all.isEmpty()) {
			throw new IllegalStateException("Order of PropertyDeclarations has been corrupted! First element not found.");
		} else if (all.size() > 1) {
			throw new IllegalStateException("Order of PropertyDeclarations has been corrupted! More than one first found.");
		}
	}
}
