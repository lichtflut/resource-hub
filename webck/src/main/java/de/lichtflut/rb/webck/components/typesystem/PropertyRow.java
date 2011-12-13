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

import org.arastreju.sge.model.ResourceID;

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
	
	private Datatype dataType;
	
	private ResourceID resourceConstraint;
	
	private int min;
	
	private int max;
	
	private boolean unbounded;
	
	private List<String> literalConstraints;
	
	private boolean isResourceReference;
	
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
			typeDef.setElementaryDataType(row.dataType);
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
		if (row.isResourceReference) {
			def.addConstraint(ConstraintBuilder.buildConstraint(row.resourceConstraint));
		} else {
			for (String constraint : row.literalConstraints) {
				def.addConstraint(ConstraintBuilder.buildConstraint(constraint));
			}
		}
		def.setElementaryDataType(row.dataType);
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
		
		setTypeDefinition(decl.getTypeDefinition());
	}
	
	/**
	 * Constructor for a TypeDefintion.
	 * @param def The type definition.
	 */
	public PropertyRow(final TypeDefinition def) {
		setTypeDefinition(def);
	}
	
	/**
	 * Constructs a default,empty row.
	 */
	public PropertyRow() {
		this.min = 0;
		this.max = 1;
		this.unbounded = true;
		this.dataType = Datatype.STRING;
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
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the dataType
	 */
	public Datatype getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(Datatype dataType) {
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
	public List<String> getLiteralConstraints() {
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
		this.displayName = def.getName();
		this.isResourceReference = def.isResourceReference();
		if (def.isResourceReference()) {
			this.resourceConstraint = ResourceTypeDefinition.view(def).getResourceTypeConstraint();
			this.literalConstraints = Collections.emptyList();
		} else {
			this.literalConstraints = new ArrayList<String>();
			for (Constraint constraint : def.getConstraints()) {
				literalConstraints.add(constraint.getLiteralConstraint());
			}
		}
	}
	
	// -----------------------------------------------------
	
	protected Set<Constraint> buildConstraints() {
		final Set<Constraint> constraints = new HashSet<Constraint>();
		if (isResourceReference && resourceConstraint != null) {
			constraints.add(ConstraintBuilder.buildConstraint(resourceConstraint));
		} else if (!isResourceReference && !literalConstraints.isEmpty()){
			for (String constraint : literalConstraints) {
				constraints.add(ConstraintBuilder.buildConstraint(constraint));
			}
		}
		return constraints;
	}

}
