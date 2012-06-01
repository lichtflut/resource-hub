/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.SNOPS;
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
 * Implementation of {@link Constraint} for resource- and public-literal-references.
 * 
 * </p>
 * Created: May 25, 2012
 * 
 * @author Ravi Knox
 */
public class ReferenceConstraint implements Constraint {

	private static Context ctx = RBSchema.CONTEXT;

	private ResourceNode node;

	// ---------------- Constructor -------------------------
	
	/**
	 * Constructor.
	 */
	public ReferenceConstraint(ResourceNode node) {
		this.node = node;
		// SET public default false
		if(0 == node.getAssociations().size()){
			setIsPublic(true);
//			isLiteral(false);
//			holdsReference(true);
		}
	}
	
	public ReferenceConstraint() {
		this(new SNResource());
	}

	// ------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		if(!isLiteral()){
			return getReference().getQualifiedName().toURI();
		}
		SemanticNode nameNode = SNOPS.singleObject(node, RBSchema.HAS_NAME);
		if(null == nameNode){
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
		if(!isFirstLvlLiteral){
			if(holdsReference()){
				return new ReferenceConstraint(getReference().asResource()).getLiteralConstraint();
			}
		}
		if(isLiteral()){
			return SNOPS.singleObject(node, RBSchema.HAS_CONSTRAINT_VALUE).asValue().getStringValue();
		} else{
			return "";
		}
		
		
//		if(holdsReference() && isLiteral()){
//			Constraint c = resolveReference();
//			if(c != null){
//				return c.getLiteralConstraint();
//			}
//		}
//		return SNOPS.singleObject(node, RBSchema.HAS_CONSTRAINT_VALUE).asValue().getStringValue();
	}

	public void setLiteralConstraint(String pattern){
		SNOPS.assure(node, RBSchema.HAS_CONSTRAINT_VALUE, new SNText(pattern), ctx);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getReference() {
		SemanticNode referenceNode = SNOPS.singleObject(node, RBSchema.HAS_RESOURCE_CONSTRAINT);
		if(null == referenceNode){
			return null;
		}
		return referenceNode.asResource();
	}

	/**
	 * @param reference
	 *            the reference to set
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
		if(isPublicNode == null){
			return false;
		}
		return isPublicNode.asValue().getBooleanValue();
	}

	public void setIsPublic(boolean isPublic) {
		SNOPS.assure(node, RBSchema.IS_PUBLIC_CONSTRAINT, new SNBoolean(isPublic), ctx);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean holdsReference() {
		SemanticNode isReferenceNode = SNOPS.singleObject(node, RBSchema.IS_RESOURCE_CONSTRAINT);
		if(isReferenceNode == null){
			return false;
		}
//		return isReferenceNode.asValue().getBooleanValue();
		return true;
	}
//
//	public void holdsReference(boolean holdsReference) {
//		SNOPS.assure(node, RBSchema.IS_RESOURCE_CONSTRAINT, new SNBoolean(holdsReference), ctx);
//	}
	
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

	public void setDatatypes(List<Datatype> datatypes) {
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
		if(!isFirstLvlLiteral){
			if(holdsReference()){
				return new ReferenceConstraint(getReference().asResource()).isLiteral();
			}
		}
		if(null == getReference()){
			return true;
		}
		return false;
		//return SNOPS.singleObject(node, RBSchema.IS_LITERAL_CONSTRAINT).asValue().getBooleanValue();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceNode asResourceNode() {
		return node;
	}
	
	// ------------------------------------------------------
	
	private Constraint resolveReference() {
		return null;
	}
}
