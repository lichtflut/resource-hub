/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNBoolean;
import org.arastreju.sge.model.nodes.views.SNText;

import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;

/**
 * <p>
 * Implementation of {@link Constraint}.
 * </p>
 * <p>
 * All but public constraints shuold be constructed by using the appropriate <code>buildXXX</code>
 * -methods to ensure integrity
 * </p>
 * <p>
 * Public-constraints must set the following properties:
 * <ul>
 * <li>
 * Name</li>
 * <li>
 * literal constraint</li>
 * <li>
 * isPublic</li>
 * <li>
 * applicable Datatypes</li>
 * </ul>
 * </p>
 * 
 * 
 * </p> Created: May 25, 2012
 * 
 * @author Ravi Knox
 */
public class ConstraintImpl implements Constraint {

	private static Context ctx = RBSchema.CONTEXT;

	private final ResourceNode node;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 */
	public ConstraintImpl(ResourceNode node) {
		this.node = node;
	}

	public ConstraintImpl() {
		this(new SNResource());
	}

	// ------------------------------------------------------

	public void buildReferenceConstraint(ResourceID reference, boolean isLiteralReference) {
		this.isPublic(false);
		this.setReference(reference);
	}

	public void buildLiteralConstraint(String pattern) {
		this.isPublic(false);
		this.setLiteralConstraint(pattern);
	}

	// ------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		if (!isLiteral()) {
			return getReference().getQualifiedName().toURI();
		}
		SemanticNode nameNode = SNOPS.singleObject(node, RBSchema.HAS_NAME);
		if (null == nameNode) {
			return getLiteralConstraint();
		}
		return nameNode.asValue().getStringValue();
	}

	public void setName(String name) {
		SNOPS.assure(node, RBSchema.HAS_NAME, new SNText(name), ctx);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLiteralConstraint() {
		boolean isFirstLvlLiteral = (node.getAssociations(RBSchema.HAS_CONSTRAINT_VALUE).size() > 0);
		if (!isFirstLvlLiteral) {
			if (holdsReference()) {
				return new ConstraintImpl(getReference().asResource()).getLiteralConstraint();
			}
			if (!isLiteral()) {
				return getReference().toURI();
			}
		}
		if (isLiteral()) {
			SemanticNode constraint = SNOPS.singleObject(node, RBSchema.HAS_CONSTRAINT_VALUE);
			if (constraint == null) {
				return "";
			}
			return SNOPS.singleObject(node, RBSchema.HAS_CONSTRAINT_VALUE).asValue().getStringValue();
		} else {
			return "";
		}

	}

	public void setLiteralConstraint(String pattern) {
		if ((pattern != null) && !pattern.isEmpty()) {
			SNOPS.assure(node, RBSchema.HAS_CONSTRAINT_VALUE, new SNText(pattern), ctx);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getReference() {
		SemanticNode referenceNode = SNOPS.singleObject(node, RBSchema.HAS_RESOURCE_CONSTRAINT);
		if (null == referenceNode) {
			return null;
		}
		return referenceNode.asResource();
	}

	/**
	 * @param reference the reference to set
	 */
	public void setReference(ResourceID reference) {
		SNOPS.assure(node, RBSchema.HAS_RESOURCE_CONSTRAINT, reference, ctx);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPublic() {
		SemanticNode isPublicNode = SNOPS.singleObject(node, RBSchema.IS_PUBLIC_CONSTRAINT);
		if (isPublicNode == null) {
			return false;
		}
		return isPublicNode.asValue().getBooleanValue();
	}

	public void isPublic(boolean isPublic) {
		SNOPS.assure(node, RBSchema.IS_PUBLIC_CONSTRAINT, new SNBoolean(isPublic), ctx);
		if (isPublic) {
			SNOPS.assure(node, RDF.TYPE, RBSchema.PUBLIC_CONSTRAINT, ctx);
		} else {
			SNOPS.remove(node, RDF.TYPE);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean holdsReference() {
		SemanticNode isReferenceNode = SNOPS.singleObject(node, RBSchema.HAS_RESOURCE_CONSTRAINT);
		if (isReferenceNode == null) {
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Datatype> getApplicableDatatypes() {
		List<Datatype> datatypes = new ArrayList<Datatype>();
		for (SemanticNode snType : SNOPS.objects(node, RBSchema.HAS_DATATYPE)) {
			datatypes.add(Datatype.valueOf(snType.asValue().getStringValue()));
		}
		return datatypes;
	}

	public void setApplicableDatatypes(List<Datatype> datatypes) {
		for (SemanticNode snType : SNOPS.objects(node, RBSchema.HAS_DATATYPE)) {
			SNOPS.remove(this.asResourceNode(), RBSchema.HAS_DATATYPE, snType);
		}
		for (Datatype datatype : datatypes) {
			SNOPS.associate(node, RBSchema.HAS_DATATYPE, new SNText(datatype.name()), ctx);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isLiteral() {
		boolean isFirstLvlLiteral = (node.getAssociations(RBSchema.HAS_CONSTRAINT_VALUE).size() > 0);
		if (!isFirstLvlLiteral) {
			if (!holdsReference()) {
				return new ConstraintImpl(getReference().asResource()).isLiteral();
			}else{
				return false;
			}
		}
		if (null == getReference()) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceNode asResourceNode() {
		return node;
	}

	// ------------------------------------------------------
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Constraint id: " + node.getQualifiedName());
		sb.append("name: " + getName());
		sb.append("is public: " + isPublic());
		sb.append("is Reference: " + holdsReference());
		return sb.toString();
	}
}
