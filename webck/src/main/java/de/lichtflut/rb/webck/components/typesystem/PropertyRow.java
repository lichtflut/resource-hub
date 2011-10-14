/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceTypeDefinition;
import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionImpl;

/**
 * <p>
 *  This value object represents a flattened Property Assertion (with Declaration) of a Resource Schema.
 *  It is meant for being edited in a table or similar component. 
 * </p>
 *
 * <p>
 * 	Created Sep 23, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class PropertyRow implements Serializable {
	
	private ResourceID propertyType;
	
	private TypeDefinition typeDefinition;
	
	private ElementaryDataType dataType;
	
	private ResourceID resourceConstraint;
	
	private int min;
	
	private int max;
	
	private boolean unbounded;
	
	private List<Constraint> literalConstraints;
	
	private boolean isResourceReference;
	
	// -----------------------------------------------------
	
	public static List<PropertyRow> toRowList(final ResourceSchema schema) {
		if (schema == null || schema.getPropertyDeclarations().isEmpty()) {
			return Collections.emptyList();
		}
		final List<PropertyRow> list = new ArrayList<PropertyRow>();
		for (PropertyDeclaration pa : schema.getPropertyDeclarations()) {
			list.add(new PropertyRow(pa));
		}
		return list;
	}
	
	public static PropertyDeclaration toPropertyDeclaration(final PropertyRow row) {
		final PropertyDeclaration decl = new PropertyDeclarationImpl();
		if (row.isUnbounded()) {
			decl.setCardinality(CardinalityBuilder.hasAtLeast(row.min));	
		} else {
			decl.setCardinality(CardinalityBuilder.between(row.min, row.max));
		}
		decl.setPropertyType(row.propertyType);
		if (row.isTypeDefinitionPublic()) {
			decl.setTypeDefinition(row.typeDefinition);
		} else {
			final TypeDefinition typeDef = new TypeDefinitionImpl();
			typeDef.setElementaryDataType(row.dataType);
			typeDef.setConstraints(row.buildConstraints());
			decl.setTypeDefinition(typeDef);
		}
		return decl;
	}
	
	// -----------------------------------------------------
	
	/**
	 * Constructor. 
	 */
	public PropertyRow(final PropertyDeclaration decl) {
		this.propertyType = decl.getPropertyType();
		this.min = decl.getCardinality().getMinOccurs();
		this.max = decl.getCardinality().getMaxOccurs();
		this.unbounded = decl.getCardinality().isUnbound();
		
		setTypeDefinition(decl.getTypeDefinition());
	}
	
	// -----------------------------------------------------

	/**
	 * @return the propertyDescriptor
	 */
	public ResourceID getPropertyDescriptor() {
		return propertyType;
	}

	/**
	 * @param propertyDescriptor the propertyDescriptor to set
	 */
	public void setPropertyDescriptor(ResourceID propertyDescriptor) {
		this.propertyType = propertyDescriptor;
	}

	/**
	 * @return the dataType
	 */
	public ElementaryDataType getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(ElementaryDataType dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the resourceConstraint
	 */
	public ResourceID getResourceConstraint() {
		return resourceConstraint;
	}

	/**
	 * @param resourceConstraint the resourceConstraint to set
	 */
	public void setResourceConstraint(ResourceID resourceConstraint) {
		this.resourceConstraint = resourceConstraint;
	}

	/**
	 * @return the min
	 */
	public int getMin() {
		return min;
	}

	/**
	 * @param min the min to set
	 */
	public void setMin(int min) {
		this.min = min;
	}

	/**
	 * @return the max
	 */
	public int getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(int max) {
		this.max = max;
	}

	/**
	 * @return the literalConstraints
	 */
	public List<Constraint> getLiteralConstraints() {
		return literalConstraints;
	}

	/**
	 * @param literalConstraints the literalConstraints to set
	 */
	public void setLiteralConstraints(final List<Constraint> literalConstraints) {
		this.literalConstraints = literalConstraints;
	}

	/**
	 * @return true if the Type Definition is public, false if it is private.
	 */
	public boolean isTypeDefinitionPublic() {
		return typeDefinition.isPublicTypeDef();
	}

	/**
	 * @return the isResourceReference
	 */
	public boolean isResourceReference() {
		return isResourceReference;
	}
	
	/**
	 * @return the unbounded
	 */
	public boolean isUnbounded() {
		return unbounded;
	}
	
	/**
	 * @param unbounded the unbounded to set
	 */
	public void setUnbounded(boolean unbounded) {
		this.unbounded = unbounded;
	}
	
	// -----------------------------------------------------
	
	public void setTypeDefinition(final TypeDefinition def) {
		this.typeDefinition = def;
		this.dataType = def.getElementaryDataType();
		this.isResourceReference = def.isResourceReference();
		if (def.isResourceReference()) {
			this.resourceConstraint = ResourceTypeDefinition.view(def).getResourceTypeConstraint();
			this.literalConstraints = Collections.emptyList();
		} else {
			this.literalConstraints = new ArrayList<Constraint>(def.getConstraints());
		}
	}
	
	// -----------------------------------------------------
	
	protected Set<Constraint> buildConstraints() {
		final Set<Constraint> constraints = new HashSet<Constraint>();
		if (isResourceReference && resourceConstraint != null) {
			constraints.add(ConstraintBuilder.buildConstraint(resourceConstraint));
		} else if (!isResourceReference && !literalConstraints.isEmpty()){
			constraints.addAll(literalConstraints);
		}
		return constraints;
	}
	
}
