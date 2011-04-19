/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.util.Set;

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
public interface PropertyAssertion extends ResourceSchemaType {

	/**
	 * The descriptor of this property. Usually the resource identifier representing
	 * the descriptor will be of type rdf:Property. In a concrete RDF statement corresponding
	 * to this PropertyAssertion this will be the predicate. 
	 * @return The resource identifier representing the descriptor.
	 */
	ResourceID getPropertyDescriptor();
	
	/**
	 * The concrete property of this assertion.
	 * @return The property.
	 */
	PropertyDeclaration getProperty();
	
	/**
	 * The cardinality of this property,
	 * @return The cardinality.
	 */
	Cardinality getCardinality();
	
	/**
	 * Set cardinality of this property,
	 * @param The cardinality.
	 */
	void setCardinality(Cardinality c);
	
	/**
	 * TODO: ToComment
	 */
	void setPropertyIdentifier(String identifier);
	
	/**
	 * TODO: ToComment
	 */
	String getPropertyIdentifier();
	
	
	/**
	 * TODO: ToComment
	 */
	boolean resolveAssertion();
	
	/**
	 * TODO: ToComment
	 */
	boolean isResolved();
	
	
	/**
	 * The constraints for this property assertion.
	 * There are to levels where constraints can be defined. Either for the property (First lvl)
	 * or directly for the assertion (Second lvl) . The set of constraints returned by
	 * this method contains them all.
	 * @return the constraints.
	 */
	Set<Constraint> getConstraints();
	
}
