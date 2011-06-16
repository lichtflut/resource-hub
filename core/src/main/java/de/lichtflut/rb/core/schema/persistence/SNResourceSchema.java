/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.model.nodes.views.SNClass;

import de.lichtflut.infra.Infra;
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

	/**
	 * Constructor.
	 * @param resource -
	 */
	public SNResourceSchema(final ResourceNode resource) {
		super(resource);
	}

	/**
	 * Constructor for new Resource Schemas.
	 *  @param ctx -
	 */
	public SNResourceSchema(final Context ctx) {
		Association.create(this, RDF.TYPE, RBSchema.RESOURCE_SCHEMA,ctx);
	}

	// -----------------------------------------------------

	/**
	 * Returns the Class described by this schemas.
	 * @return The class node.
	 */
	public SNClass getDescribedClass() {
		SemanticNode node = getSingleAssociationClient(RBSchema.DESCRIBES);
		if (node != null){
			return node.asResource().asClass();
		} else {
			return null;
		}
	}

	/**
	 * Set the Class described by this schema:.
	 * @param clazz The class node.
	 * @param context The context.
	 */
	public void setDescribedClass(final ResourceNode clazz, final Context context) {
		if (!Infra.equals(getDescribedClass(), clazz)){
			removeAssociations(RBSchema.DESCRIBES);
			removeAssociations(RBSchema.DESCRIBED_BY);
			Association.create(this, RBSchema.DESCRIBES, clazz, context);
			Association.create(clazz, RBSchema.DESCRIBED_BY, this, context);
		}
	}

	/**
	 * Adds the given PropertyAssertion to this class.
	 * @param pa -
	 * @param ctx -
	 */
	public void addPropertyAssertion(final SNPropertyAssertion pa, final Context ctx){
		Association.create(this, RBSchema.HAS_PROPERTY_ASSERT, pa, ctx);
	}

	/**
	 * Collects all PropertyAssertions defined by this Schema.
	 * @return The list of all property assertions.
	 */
	public List<SNPropertyAssertion> getPropertyAssertions(){
		Set<SNPropertyAssertion> result = new HashSet<SNPropertyAssertion>();
		result.addAll(getDeclaredPropertyAssertions());
		List<SNPropertyAssertion> list = new ArrayList<SNPropertyAssertion>(result);
		Collections.sort(list);
		return list;
	}

	/**
	 * Returns only the property declarations declared for this classifier, not the inherited.
	 * @return The list of property declarations.
	 */
	public List<SNPropertyAssertion> getDeclaredPropertyAssertions(){
		List<SNPropertyAssertion> result = new ArrayList<SNPropertyAssertion>();
		Set<Association> assocs = getAssociations(RBSchema.HAS_PROPERTY_ASSERT);
		for (Association current : assocs) {
			result.add(new SNPropertyAssertion(current.getObject().asResource()));
		}
		Collections.sort(result);
		return result;
	}

	/**
	 * @see org.arastreju.api.model.semantic.ResourceClassifier#removePropertyDeclaration.
	 * (org.arastreju.api.model.semantic.SNPropertyDeclaration)
	 * @param decl -
	 */
	public void removePropertyAssertion(final SNPropertyAssertion decl) {
		Set<Association> assocs = getAssociations(RBSchema.HAS_PROPERTY_ASSERT);
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ResourceSchema[" + super.toString() + "]");
		sb.append(" for " + getDescribedClass() + "\n");
		for(SNPropertyAssertion pa : getPropertyAssertions()){
			sb.append("\t" + pa + "\n");
		}
		return sb.toString();
	}
}
