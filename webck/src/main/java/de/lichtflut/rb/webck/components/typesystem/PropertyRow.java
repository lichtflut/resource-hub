/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.infra.Infra;
import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceTypeDefinition;
import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl;
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
	
	private final PropertyDeclaration decl;
	
	// -----------------------------------------------------
	
	/**
	 * Create rows corresponding to Property Declarations of given Resource Schema.
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
	 * Converts the given row to a new TypeDefinition.
	 * @param row The row object to be converted.
	 */
	public static TypeDefinition toTypeDefinition(final PropertyRow row) {
		return row.asPropertyDeclaration().getTypeDefinition();
	}
	
	// -----------------------------------------------------
	
	/**
	 * Constructor for a {@link PropertyDeclaration}
	 * @param decl The declaration.
	 */
	public PropertyRow(final PropertyDeclaration decl) {
		this.decl = decl;
	}
	
	/**
	 * Constructor for a TypeDefintion.
	 * @param def The type definition.
	 */
	public PropertyRow(final TypeDefinition def) {
		this.decl = new PropertyDeclarationImpl();
		decl.setTypeDefinition(def);
	}
	
	/**
	 * Constructs a default,empty row.
	 */
	public PropertyRow() {
		this.decl = new PropertyDeclarationImpl();
		decl.setCardinality(CardinalityBuilder.hasOptionalOneToMany());
		TypeDefinition def = new TypeDefinitionImpl();
		def.setElementaryDataType(Datatype.STRING);
		this.decl.setTypeDefinition(def);
		this.decl.setFieldLabelDefinition(new FieldLabelDefinitionImpl());
	}
	
	// -----------------------------------------------------

	public PropertyDeclaration asPropertyDeclaration(){
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
	 * @return the display name for public type definitions.
	 */
	public String getDisplayName() {
		return decl.getTypeDefinition().getName();
	}

	/**
	 * @return the dataType
	 */
	public Datatype getDataType() {
		return decl.getTypeDefinition().getElementaryDataType();
	}

	/**
	 * @param newDatatype the new data type to set
	 */
	public void setDataType(Datatype newDatatype) {
		if (!Infra.equals(this.decl.getTypeDefinition().getElementaryDataType(), newDatatype)) {
			decl.getTypeDefinition().setElementaryDataType(newDatatype);
		}
	}

	/**
	 * @return the resourceConstraint
	 */
	public ResourceID getResourceConstraint() {
		if (decl.getTypeDefinition().isResourceReference()) {
			return ResourceTypeDefinition.view(decl.getTypeDefinition()).getResourceTypeConstraint();
		}
		return null;
	}

	/**
	 * @param resourceConstraint the resourceConstraint to set
	 */
	public void setResourceConstraint(ResourceID resourceConstraint) {
		final Set<Constraint> constraints = new HashSet<Constraint>();
		constraints.add(ConstraintBuilder.buildConstraint(resourceConstraint));
		decl.getTypeDefinition().setConstraints(constraints);
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
	 * @param string
	 */
	public void setCardinality(String string){
		this.decl.setCardinality(CardinalityBuilder.extractFromString(string));
	}

	/**
	 * Get the cardinality as a String like <code>[1..n]</code>
	 * @param string
	 */
	public String getCardinality(){
		int min = decl.getCardinality().getMinOccurs();
		int max = decl.getCardinality().getMaxOccurs();
		String s = "[";
		if(min == 0){
			s+= "n..";
		}else{
			s+= String.valueOf(min) + "..";
		}
		if(max == Integer.MAX_VALUE){
			s+="n";
		}else{
			s+=String.valueOf(max);
		}
		s += "]";
		return s;
	}
	/**
	 * @return the literalConstraints
	 */
	public List<String> getLiteralConstraints() {
		List<String> constraints = new ArrayList<String>();
		if(!decl.getTypeDefinition().isResourceReference()){
			for (Constraint c : decl.getTypeDefinition().getConstraints()) {
				constraints.add(c.getLiteralConstraint());
			}
		}
		return constraints;
	}

	/**
	 * @param literalConstraints the literalConstraints to set
	 */
	public void setLiteralConstraints(final List<String> literalConstraints) {
		final Set<Constraint> constraints = new HashSet<Constraint>();
		for (String constraint : getLiteralConstraints()) {
			constraints.add(ConstraintBuilder.buildConstraint(constraint));
		}
		decl.getTypeDefinition().setConstraints(constraints);
	}

	/**
	 * @return true if the Type Definition is public, false if it is private.
	 */
	public boolean isTypeDefinitionPublic() {
		return decl.getTypeDefinition() != null && decl.getTypeDefinition().isPublicTypeDef();
	}

	/**
	 * @return the isResourceReference
	 */
	public boolean isResourceReference() {
		return Datatype.RESOURCE.equals(decl.getTypeDefinition().getElementaryDataType());
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
	
	public TypeDefinition getTypeDefinition(){
		return decl.getTypeDefinition();
	}

}
