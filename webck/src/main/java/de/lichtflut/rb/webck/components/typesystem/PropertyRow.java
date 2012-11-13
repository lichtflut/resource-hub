/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.infra.Infra;
import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.VisualizationInfo;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintImpl;
import de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.parser.impl.VisualizationBuilder;

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
	public void setPropertyDescriptor(final ResourceID propertyDescriptor) {
		decl.setPropertyDescriptor(propertyDescriptor);
	}

	public String getDefaultLabel() {
		return decl.getFieldLabelDefinition().getDefaultLabel();
	}

	public void setDefaultLabel(final String label) {
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
	public void setDataType(final Datatype newDatatype) {
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
	public void setMin(final int min) {
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
	public void setMax(final int max) {
		Cardinality c = CardinalityBuilder.between(decl.getCardinality().getMinOccurs(), max);
		this.decl.setCardinality(c);
	}

	/**
	 * Sets the cardinality with a String like <code>[1..n]</code>
	 * 
	 * @param string
	 */
	public void setCardinality(final String string) {
		this.decl.setCardinality(CardinalityBuilder.extractFromString(string));
	}

	/**
	 * Get the cardinality as a String like <code>[1..n]</code>
	 */
	public String getCardinality() {
		return CardinalityBuilder.getCardinalityAsString(decl.getCardinality());
	}

	/**
	 * @return the literalConstraint
	 */
	public String getLiteralConstraint() {
		if (hasConstraint() && decl.getConstraint().isLiteral()) {
			return decl.getConstraint().getLiteralConstraint();
		} else {
			return "";
		}
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
		if (hasConstraint() && !decl.getConstraint().isLiteral()) {
			return decl.getConstraint().getTypeConstraint();
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
	public void setResourceConstraint(final ResourceID resourceConstraint) {
		// TODO RESOLVE CONSTRAINT - get ID
		ConstraintImpl constraint = new ConstraintImpl();
		constraint.setTypeConstraint(resourceConstraint);
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

	public void setUnbounded(final boolean unbounded) {
		int min = decl.getCardinality().getMinOccurs();
		if (unbounded) {
			decl.setCardinality(CardinalityBuilder.hasAtLeast(min));
		} else {
			decl.setCardinality(CardinalityBuilder.between(min, Math.max(min, 1)));
		}
	}

	public boolean getEmbedded(){
		return decl.getVisualizationInfo().isEmbedded();
	}

	public void setEmbedded(final boolean embedden){
		new VisualizationBuilder().add((PropertyDeclarationImpl)decl, VisualizationBuilder.EMBEDDED, String.valueOf(embedden));
	}

	public boolean getFloating(){
		return decl.getVisualizationInfo().isFloating();
	}

	public void setFloating(final boolean floating){
		new VisualizationBuilder().add((PropertyDeclarationImpl)decl, VisualizationBuilder.FLOATING, String.valueOf(floating));
	}

	public String getStyle(){
		return decl.getVisualizationInfo().getStyle();
	}

	public void setStyle(final String style){
		new VisualizationBuilder().add((PropertyDeclarationImpl)decl, VisualizationBuilder.STYLE, style);
	}

	public VisualizationInfo getVisualizationInfo(){
		return decl.getVisualizationInfo();
	}

	public void setVisualizationInfo(final VisualizationInfo info){
		decl.setVisualizationInfo(info);
	}
}
