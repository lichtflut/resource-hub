/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.SNResource;

import de.lichtflut.infra.Infra;
import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ConstraintImpl;

/**
 * <p>
 * This value object represents a flattened {@link PropertyDeclaration} of a Resource Schema. It is
 * meant for being edited in a table or similar component.
 * </p>
 * 
 * <p>
 * Created Sep 23, 2011
 * </p>
 * 
 * @author Oliver Tigges
 */
public class PropertyRow implements Serializable {

	private final PropertyDeclaration decl;

	// -----------------------------------------------------

	/**
	 * Create rows corresponding to Property Declarations of given Resource Schema.
	 * 
	 * @param schema The Resource Schema.
	 * @return The row list.
	 */
	public static List<PropertyRow> toRowList(final ResourceSchema schema) {
		final List<PropertyRow> list = new ArrayList<PropertyRow>();
		for (PropertyDeclaration pd : schema.getPropertyDeclarations()) {
			list.add(new PropertyRow(pd));
		}
		return list;
	}

	/**
	 * Converts the given row to a new Constraint.
	 * 
	 * @param row The row object to be converted.
	 */
	public static Constraint toPublicConstraint(final PropertyRow row) {
		throw new NotYetImplementedException();
	}

	// -----------------------------------------------------

	/**
	 * Constructor for a {@link PropertyDeclaration}
	 * 
	 * @param decl The declaration.
	 */
	public PropertyRow(final PropertyDeclaration decl) {
		this.decl = decl;
	}

	public PropertyRow(final Constraint constraint) {
		this.decl = new PropertyDeclarationImpl();
		this.decl.setConstraint(constraint);
	}

	/**
	 * Constructs a default,empty row.
	 */
	public PropertyRow() {
		this.decl = new PropertyDeclarationImpl();
		this.decl.setDatatype(Datatype.STRING);
		this.decl.setFieldLabelDefinition(new FieldLabelDefinitionImpl());
	}

	// -----------------------------------------------------

	public PropertyDeclaration asPropertyDeclaration() {
		return decl;
	}

	/**
	 * @return the propertyDescriptor
	 */
	public ResourceID getPropertyDescriptor() {
		return decl.getPropertyDescriptor();
	}

	/**
	 * @param propertyDescriptor the propertyDescriptor to set
	 */
	public void setPropertyDescriptor(ResourceID propertyDescriptor) {
		decl.setPropertyDescriptor(propertyDescriptor);
	}

	public String getDefaultLabel() {
		return decl.getFieldLabelDefinition().getDefaultLabel();
	}

	public void setDefaultLabel(String label) {
		this.decl.getFieldLabelDefinition().setDefaultLabel(label);
	}

	/**
	 * @return the dataType
	 */
	public Datatype getDataType() {
		return decl.getDatatype();
	}

	/**
	 * @param newDatatype the new data type to set
	 */
	public void setDataType(Datatype newDatatype) {
		if (!Infra.equals(this.decl.getDatatype(), newDatatype)) {
			decl.setDatatype(newDatatype);
		}
	}

	/**
	 * @return the min
	 */
	public int getMin() {
		return decl.getCardinality().getMinOccurs();
	}

	/**
	 * @param min the min to set
	 */
	public void setMin(int min) {
		Cardinality c = CardinalityBuilder.between(min, decl.getCardinality().getMaxOccurs());
		this.decl.setCardinality(c);
	}

	/**
	 * @return the max
	 */
	public int getMax() {
		return decl.getCardinality().getMaxOccurs();
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(int max) {
		Cardinality c = CardinalityBuilder.between(decl.getCardinality().getMinOccurs(), max);
		this.decl.setCardinality(c);
	}

	/**
	 * Sets the cardinality with a String like <code>[1..n]</code>
	 * 
	 * @param string
	 */
	public void setCardinality(String string) {
		this.decl.setCardinality(CardinalityBuilder.extractFromString(string));
	}

	/**
	 * Get the cardinality as a String like <code>[1..n]</code>
	 * 
	 * @param string
	 */
	public String getCardinality() {
		int min = decl.getCardinality().getMinOccurs();
		int max = decl.getCardinality().getMaxOccurs();
		String s = "[";
		if (min == 0) {
			s += "n..";
		} else {
			s += String.valueOf(min) + "..";
		}
		if (max == Integer.MAX_VALUE) {
			s += "n";
		} else {
			s += String.valueOf(max);
		}
		s += "]";
		return s;
	}

	/**
	 * @return the literalConstraint
	 */
	public String getLiteralConstraint() {
		if (decl.getConstraint() == null || !hasConstraint()) {
			return "";
		}
		if (!decl.getConstraint().holdsReference()) {
			if (decl.getConstraint().isLiteral()) {
				return decl.getConstraint().getLiteralConstraint();
			} else {
				return decl.getConstraint().getReference().getQualifiedName().getSimpleName();
			}
		}
		return null;
	}

	/**
	 * @param literalConstraint
	 */
	public void setLiteralConstraint(final String literalConstraint) {
		if(literalConstraint != null && !literalConstraint.isEmpty()){
			ConstraintImpl constraint = new ConstraintImpl();
			constraint.buildLiteralConstraint(literalConstraint);
			decl.setConstraint(constraint);
		}
	}

	public void clearConstraint() {
		decl.setConstraint(null);
	}

	/**
	 * @return the resourceConstraint
	 */
	public ResourceID getResourceConstraint() {
		if (!hasConstraint()) {
			return null;
		}
		if (decl.getConstraint().holdsReference()) {
			return decl.getConstraint().getReference();
		}
		return null;
	}

	public boolean isConstraintPublic() {
		if(decl.getConstraint() == null){
			return false;
		}
		return decl.getConstraint().isPublic();
	}

	/**
	 * @param resourceConstraint the resourceConstraint to set
	 */
	public void setResourceConstraint(ResourceID resourceConstraint) {
		// TODO RESOLVE CONSTRAINT - get ID
		ConstraintImpl constraint = new ConstraintImpl(new SNResource());
		constraint.setReference(resourceConstraint);
		decl.setConstraint(constraint);
	}

	/**
	 * @return true if the Type Definition is public, false if it is private.
	 */
	public boolean hasPublicConstraint() {
		return decl.getConstraint().isPublic();
	}

	public boolean hasConstraint() {
		return decl.hasConstraint();
	}

	/**
	 * @return the isResourceReference
	 */
	public boolean isResourceReference() {
		return Datatype.RESOURCE.equals(decl.getDatatype());
	}

	/**
	 * @return the unbounded
	 */
	public boolean isUnbounded() {
		return decl.getCardinality().isUnbound();
	}

	public void setUnbounded(boolean unbounded) {
		int min = decl.getCardinality().getMinOccurs();
		if (unbounded) {
			decl.setCardinality(CardinalityBuilder.hasAtLeast(min));
		} else {
			decl.setCardinality(CardinalityBuilder.between(min, Math.max(min, 1)));
		}
	}
}
