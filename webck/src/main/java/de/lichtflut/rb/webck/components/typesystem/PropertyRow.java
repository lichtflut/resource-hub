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
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.FieldLabelDefinition;
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
	
	private ResourceID propertyType;
	
	private TypeDefinition typeDefinition;
	
	private String displayName;
	
	private FieldLabelDefinition fieldLabel;
	
	private Datatype datatype;
	
	private ResourceID resourceConstraint;
	
	private List<String> literalConstraints;
	
	private int min;
	
	private int max;
	
	private boolean unbounded;
	
	// -----------------------------------------------------
	
	/**
	 * Create rows corresponding to Property Declarations of given Resource Schema.
	 * @param schema The Resource Schema.
	 * @return The row list.
	 */
	public static List<PropertyRow> toRowList(final ResourceSchema schema) {
		final List<PropertyRow> list = new ArrayList<PropertyRow>();
		for (PropertyDeclaration pa : schema.getPropertyDeclarations()) {
			list.add(new PropertyRow(pa));
		}
		return list;
	}
	
	public static PropertyDeclaration toPropertyDeclaration(final PropertyRow row) {
		final PropertyDeclaration decl = new PropertyDeclarationImpl();
		decl.setPropertyDescriptor(row.propertyType);
		decl.setFieldLabelDefinition(row.fieldLabel);
		if (row.isUnbounded()) {
			decl.setCardinality(CardinalityBuilder.hasAtLeast(row.min));	
		} else {
			decl.setCardinality(CardinalityBuilder.between(row.min, row.max));
		}
		if (row.isTypeDefinitionPublic()) {
			decl.setTypeDefinition(row.typeDefinition);
		} else {
			final TypeDefinition typeDef = new TypeDefinitionImpl();
			typeDef.setElementaryDataType(row.datatype);
			typeDef.setConstraints(row.buildConstraints());
			decl.setTypeDefinition(typeDef);
		}
		return decl;
	}
	
	/**
	 * Converts the given row to a new TypeDefinition.
	 * @param row The row object to be converted.
	 */
	public static TypeDefinition toTypeDefinition(final PropertyRow row) {
		final TypeDefinitionImpl def = new TypeDefinitionImpl(row.typeDefinition.getID(), row.isTypeDefinitionPublic());
		def.setName(row.displayName);
		if (row.isResourceReference()) {
			def.addConstraint(ConstraintBuilder.buildConstraint(row.resourceConstraint));
		} else {
			for (String constraint : row.literalConstraints) {
				if (constraint != null && !constraint.trim().isEmpty()) {
					def.addConstraint(ConstraintBuilder.buildConstraint(constraint));
				}
			}
		}
		def.setElementaryDataType(row.datatype);
		return def;
	}
	
	// -----------------------------------------------------
	
	/**
	 * Constructor for a {@link PropertyDeclaration}
	 * @param decl The declaration.
	 */
	public PropertyRow(final PropertyDeclaration decl) {
		this.propertyType = decl.getPropertyDescriptor();
		this.min = decl.getCardinality().getMinOccurs();
		this.max = decl.getCardinality().getMaxOccurs();
		this.unbounded = decl.getCardinality().isUnbound();
		this.fieldLabel = decl.getFieldLabelDefinition();
		
		initializeFrom(decl.getTypeDefinition());
	}
	
	/**
	 * Constructor for a TypeDefintion.
	 * @param def The type definition.
	 */
	public PropertyRow(final TypeDefinition def) {
		initializeFrom(def);
	}
	
	/**
	 * Constructs a default,empty row.
	 */
	public PropertyRow() {
		this.min = 0;
		this.max = 1;
		this.unbounded = true;
		this.datatype = Datatype.STRING;
		this.literalConstraints = new ArrayList<String>();
		this.fieldLabel = new FieldLabelDefinitionImpl();
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
	
	public String getDefaultLabel() {
		return fieldLabel.getDefaultLabel();
	}
	
	public void setDefaultLabel(String label) {
		this.fieldLabel.setDefaultLabel(label);
	}
	
	/**
	 * @return the display name for public type definitions.
	 */
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * @param displayName the display name for public type definitions.
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the dataType
	 */
	public Datatype getDataType() {
		return datatype;
	}

	/**
	 * @param newDatatype the new data type to set
	 */
	public void setDataType(Datatype newDatatype) {
		if (!Infra.equals(this.datatype, newDatatype)) {
			initializeFrom(newDatatype);
		}
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
	 * Sets the cardinality with a String like <code>[1..n]</code>
	 * @param string
	 */
	public void setCardinality(String string){
		min = CardinalityBuilder.extractFromString(string).getMinOccurs();
		max = CardinalityBuilder.extractFromString(string).getMaxOccurs();
	}

	/**
	 * Get the cardinality as a String like <code>[1..n]</code>
	 * @param string
	 */
	public String getCardinality(){
		String s = "[";
		if(min == 0){
			s+= "n..";
		}else{
			s+= String.valueOf(min) + "..";
		}
		if(max == Integer.MAX_VALUE){
			s+="n..";
		}else{
			s+=String.valueOf(max);
		}
		s += "]";
		System.out.println(s);
		return s;
	}
	/**
	 * @return the literalConstraints
	 */
	public List<String> getLiteralConstraints() {
		if (literalConstraints.isEmpty()) {
			literalConstraints.add("");
		}
		return literalConstraints;
	}

	/**
	 * @param literalConstraints the literalConstraints to set
	 */
	public void setLiteralConstraints(final List<String> literalConstraints) {
		this.literalConstraints = literalConstraints;
	}

	/**
	 * @return true if the Type Definition is public, false if it is private.
	 */
	public boolean isTypeDefinitionPublic() {
		return typeDefinition != null && typeDefinition.isPublicTypeDef();
	}

	/**
	 * @return the isResourceReference
	 */
	public boolean isResourceReference() {
		return Datatype.RESOURCE.equals(datatype);
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
	
	protected void initializeFrom(final TypeDefinition def) {
		this.typeDefinition = def;
		this.datatype = def.getElementaryDataType();
		this.displayName = def.getName();
		this.literalConstraints = new ArrayList<String>();
		if (def.isResourceReference()) {
			this.resourceConstraint = ResourceTypeDefinition.view(def).getResourceTypeConstraint();
		} else {
			for (Constraint constraint : def.getConstraints()) {
				literalConstraints.add(constraint.getLiteralConstraint());
			}
		}
	}
	
	protected void initializeFrom(final Datatype datatype) {
		this.datatype = datatype;
		this.resourceConstraint = null;
		this.literalConstraints = new ArrayList<String>();
	}
	
	// -----------------------------------------------------
	
	protected Set<Constraint> buildConstraints() {
		final Set<Constraint> constraints = new HashSet<Constraint>();
		if (isResourceReference() && resourceConstraint != null) {
			constraints.add(ConstraintBuilder.buildConstraint(resourceConstraint));
		} else if (!isResourceReference() && !literalConstraints.isEmpty()){
			for (String constraint : literalConstraints) {
				constraints.add(ConstraintBuilder.buildConstraint(constraint));
			}
		}
		return constraints;
	}

}
