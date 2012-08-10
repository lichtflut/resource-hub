/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */

package de.lichtflut.rb.core.schema.model.impl;

import java.io.Serializable;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.FieldLabelDefinition;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;

/**
 * <p>
 *	Implementation of {@link PropertyDeclaration}.
 * </p>
 *
 * <p>
 *  This instance is initialized with the following set of default values:
 * 	<ol>
 * 	 <li>Cardinality = (min=0, max=infinity)</li>
 * 	 <li>Constraints = an empty set</li>
 *  </ol>
 * </p>
 * <p>
 * 	Created Mar 16, 2011
 * </p>
 *
 * @author Raphael Esterle
 */
public class PropertyDeclarationImpl implements PropertyDeclaration, Serializable {

	private ResourceID propertyDescriptor;
	
	private Datatype datatype;
	
	private FieldLabelDefinition labelDefinition;
	
	private Cardinality cardinality = CardinalityBuilder.hasOptionalOneToMany();
	
	private Constraint constraint;
	
	// -----------------------------------------------------
	
	/**
	 * Default Constructor.
	 */
	public PropertyDeclarationImpl() {}
	
	/**
	 * Constructor.
	 * Besides the given values, the PropertyDeclaration's default values for Cardinality will be <code>[n..n]</code>
	 * and the PropertyDescriptor will be used as a default label. No Constraints will be set.
	 * @param propertyDescriptor
	 * @param dataType
	 */
	public PropertyDeclarationImpl(ResourceID propertyDescriptor, Datatype dataType) {
		this(propertyDescriptor, dataType, null);
	}
	
	/**
	 * Constructor.
	 * Besides the given values, the PropertyDeclaration's default values for Cardinality will be <code>[n..n]</code>
	 * and the PropertyDescriptor will be used as a default label.
	 * @param propertyDescriptor
	 * @param dataType
	 * @param constraint
	 */
	public PropertyDeclarationImpl(ResourceID propertyDescriptor, Datatype dataType, Constraint constraint) {
		this.propertyDescriptor = propertyDescriptor;
		this.datatype = dataType;
		this.constraint = constraint;
		this.cardinality = CardinalityBuilder.hasOptionalOneToMany();
		this.labelDefinition = new FieldLabelDefinitionImpl(propertyDescriptor);
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getPropertyDescriptor() {
		return propertyDescriptor;
	}
	
	/**
	 * Sets the property descriptor.
	 * @param propertyDescriptor -
	 */
	@Override
	public void setPropertyDescriptor(final ResourceID propertyDescriptor){
		this.propertyDescriptor = propertyDescriptor;
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cardinality getCardinality() {
		return cardinality;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCardinality(final Cardinality c) {
		this.cardinality = c;
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Constraint getConstraint() {
		return constraint;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasConstraint() {
		boolean hasConstraint = false;
		if(getConstraint() != null){
			hasConstraint = true;
		}
		return hasConstraint;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDatatype(Datatype datatype) {
		this.datatype = datatype;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Datatype getDatatype() {
		return datatype;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FieldLabelDefinition getFieldLabelDefinition() {
		return labelDefinition;
	};
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFieldLabelDefinition(FieldLabelDefinition def) {
		this.labelDefinition = def;
	}
	
	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("PropertyDeclaration for " + ((propertyDescriptor!=null)
					? propertyDescriptor.getQualifiedName().toURI() : ""));
		sb.append(" " + cardinality.toString());
		sb.append(", " + datatype);
		if(null!=constraint){
			sb.append(" "+constraint.toString());
		}
		return sb.toString();
	}

}
