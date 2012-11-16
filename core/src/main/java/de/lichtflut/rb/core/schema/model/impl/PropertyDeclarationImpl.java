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
import de.lichtflut.rb.core.schema.model.VisualizationInfo;

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

	private VisualizationInfo visualizationInfo;

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
	 * @param propertyDescriptor The descriptor.
	 * @param dataType The data type.
	 */
	public PropertyDeclarationImpl(final ResourceID propertyDescriptor, final Datatype dataType) {
		this(propertyDescriptor, dataType, null);
	}

	/**
	 * Constructor.
	 * Besides the given values, the PropertyDeclaration's default values for Cardinality will be <code>[n..n]</code>
	 * and the PropertyDescriptor will be used as a default label.
	 * @param propertyDescriptor The descriptor.
	 * @param dataType The datatype.
	 * @param constraint The constraint.
	 */
	public PropertyDeclarationImpl(final ResourceID propertyDescriptor, final Datatype dataType, final Constraint constraint) {
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
	public void setConstraint(final Constraint constraint) {
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
	public void setDatatype(final Datatype datatype) {
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
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFieldLabelDefinition(final FieldLabelDefinition def) {
		this.labelDefinition = def;
	}

	// ----------------------------------------------------

	@Override
	public VisualizationInfo getVisualizationInfo() {
		return visualizationInfo;
	}

	@Override
	public void setVisualizationInfo(final VisualizationInfo visualizationInfo) {
		this.visualizationInfo = visualizationInfo;
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
		if (null != visualizationInfo) {
			sb.append(", ").append(visualizationInfo);
		}
		if(null!=constraint){
			sb.append(" "+constraint.toString());
		}
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardinality == null) ? 0 : cardinality.hashCode());
		result = prime * result + ((constraint == null) ? 0 : constraint.hashCode());
		result = prime * result + ((datatype == null) ? 0 : datatype.hashCode());
		result = prime * result + ((labelDefinition == null) ? 0 : labelDefinition.hashCode());
		result = prime * result + ((propertyDescriptor == null) ? 0 : propertyDescriptor.hashCode());
		result = prime * result + ((visualizationInfo == null) ? 0 : visualizationInfo.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PropertyDeclarationImpl)) {
			return false;
		}
		PropertyDeclarationImpl other = (PropertyDeclarationImpl) obj;
		if (cardinality == null) {
			if (other.cardinality != null) {
				return false;
			}
		} else if (!cardinality.equals(other.cardinality)) {
			return false;
		}
		if (constraint == null) {
			if (other.constraint != null) {
				return false;
			}
		} else if (!constraint.equals(other.constraint)) {
			return false;
		}
		if (datatype != other.datatype) {
			return false;
		}
		if (labelDefinition == null) {
			if (other.labelDefinition != null) {
				return false;
			}
		} else if (!labelDefinition.equals(other.labelDefinition)) {
			return false;
		}
		if (propertyDescriptor == null) {
			if (other.propertyDescriptor != null) {
				return false;
			}
		} else if (!propertyDescriptor.equals(other.propertyDescriptor)) {
			return false;
		}
		if (visualizationInfo == null) {
			if (other.visualizationInfo != null) {
				return false;
			}
		} else if (!visualizationInfo.equals(other.visualizationInfo)) {
			return false;
		}
		return true;
	}

}
