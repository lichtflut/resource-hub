/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.io.Serializable;

import org.arastreju.sge.model.ResourceID;


/**
 * <p>
 *  Declaration of a Property - either resource reference or value - of a resource.
 *  A set of PropertyDeclarations build a {@link ResourceSchema}.
 * </p>
 *
 * <p>
 * 	Created Jan 27, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface PropertyDeclaration extends Serializable {

	/**
	 * The property type. Usually this descriptor will be of type rdf:Property. 
	 * In a concrete RDF statement corresponding to this PropertyAssertion this 
	 * will be the predicate.
	 * @return The resource identifier representing the descriptor.
	 */
	ResourceID getPropertyDescriptor();

	/**
	 * Set the property.
	 * @param property The property.
	 */
	void setPropertyDescriptor(ResourceID property); 
	
	// -----------------------------------------------------

	/**
	 * The concrete property of this assertion.
	 * @return The property.
	 */
	@Deprecated
	TypeDefinition getTypeDefinition();
	
	/**
	 * Assign an implicit/private oder an standalone/public TypeDefinition.
	 * @param def The type definition.
	 */
	@Deprecated
	void setTypeDefinition(TypeDefinition def);

	// -----------------------------------------------------

	/**
	 * The cardinality of this property.
	 * @return The cardinality.
	 */
	Cardinality getCardinality();

	/**
	 * Set cardinality of this property.
	 * @param c The cardinality.
	 */
	void setCardinality(Cardinality c);

	// -----------------------------------------------------
	
	/**
	 * Get the definition for the label.
	 * @return The definition.
	 */
	FieldLabelDefinition getFieldLabelDefinition();

	/**
	 * Set the label definition for this declaration.
	 * @param def The definition.
	 */
	void setFieldLabelDefinition(FieldLabelDefinition def);
	
	// -----------------------------------------------------

	/**
	 * The constraints for this property assertion.
	 * @return Set the constraints.
	 */
	Constraint getConstraint();
	
	/**
	 * Set the Constraint for this PropertyDeclaration.
	 */
	void setConstraint(Constraint constraint);
	
	// ------------------------------------------------------
	
	/**
	 * Get the {@link Datatype} for this PropertyDeclaration.
	 */
	void setDatatype(Datatype datatype);
	
	/**
	 * Get the {@link Datatype} for this PropertyDeclaration.
	 */
	Datatype getDatatype();
}
