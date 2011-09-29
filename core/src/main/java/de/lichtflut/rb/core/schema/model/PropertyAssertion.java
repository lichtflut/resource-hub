/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.util.Set;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.naming.QualifiedName;


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
public interface PropertyAssertion extends ResourceSchemaElement {

	/**
	 * The descriptor of this property. Usually the resource identifier representing
	 * the descriptor will be of type rdf:Property. In a concrete RDF statement corresponding
	 * to this PropertyAssertion this will be the predicate.
	 * @return The resource identifier representing the descriptor.
	 */
	ResourceID getPropertyDescriptor();

	// -----------------------------------------------------

	/**
	 * The concrete property of this assertion.
	 * @return The property.
	 */
	TypeDefinition getPropertyDeclaration();

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
	 * Resolved means, if this assertion is already assigned to a known PropertyDeclaration.
	 * @return boolean
	 */
	boolean isResolved();

	// -----------------------------------------------------

	/**
	 * returns the QualifiedName of the PropertyIdentifier.
	 * This is necessary to resolve this PropertyAssertion with a given PropertyDeclaration
	 * If this assertion is still resolved, the PropertyIdentifier is not more needed
	 * If the propertyIdentifier is not a valid URI, it's converted to the default void-namespace URI
	 * @return {@link QualifiedName}
	 */
	QualifiedName getQualifiedPropertyIdentifier();

	// -----------------------------------------------------

	/**
	 * The constraints for this property assertion.
	 * There are to levels where constraints can be defined. Either for the property (First lvl)
	 * or directly for the assertion (Second lvl) . The set of constraints returned by
	 * this method contains them all.
	 * @return Set the constraints.
	 */
	Set<Constraint> getConstraints();

}
