/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components.typesystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

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
	
	private ResourceID propertyDescriptor;
	
	private ElementaryDataType dataType;
	
	private ResourceID resourceConstraint;
	
	private int min;
	
	private int max;
	
	private boolean unbounded;
	
	private List<Constraint> literalConstraints;
	
	private boolean hasPublicPropertyDeclaration;
	
	private boolean isResourceReference;
	
	// -----------------------------------------------------
	
	public static List<PropertyRow> toRowList(ResourceSchema schema) {
		final List<PropertyRow> list = new ArrayList<PropertyRow>();
		for (PropertyAssertion pa : schema.getPropertyAssertions()) {
			list.add(new PropertyRow(pa));
		}
		return list;
	}
	
	// -----------------------------------------------------
	
	/**
	 * Default constructor. 
	 */
	public PropertyRow() {
	}
	
	/**
	 * Constructor. 
	 */
	public PropertyRow(PropertyAssertion assertion) {
		this.propertyDescriptor = assertion.getPropertyDescriptor();
		this.dataType = assertion.getPropertyDeclaration().getElementaryDataType();
		this.min = assertion.getCardinality().getMinOccurs();
		this.max = assertion.getCardinality().getMaxOccurs();
		this.unbounded = assertion.getCardinality().isUnbound();
	}
	
	// -----------------------------------------------------

	/**
	 * @return the propertyDescriptor
	 */
	public ResourceID getPropertyDescriptor() {
		return propertyDescriptor;
	}

	/**
	 * @param propertyDescriptor the propertyDescriptor to set
	 */
	public void setPropertyDescriptor(ResourceID propertyDescriptor) {
		this.propertyDescriptor = propertyDescriptor;
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
	public void setLiteralConstraints(List<Constraint> literalConstraints) {
		this.literalConstraints = literalConstraints;
	}

	/**
	 * @return the hasPublicPropertyDeclaration
	 */
	public boolean isHasPublicPropertyDeclaration() {
		return hasPublicPropertyDeclaration;
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
	
}
