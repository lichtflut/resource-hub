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
public interface PropertyDeclaration {

	/**
	 * The semantic of this property. Usually the resource identifier representing
	 * the semantic will be of type rdf:Property. In a concrete RDF statement corresponding
	 * to this PropertyDeclaration this will be the predicate. 
	 * @return The resource identifier representing the semantic.
	 */
	ResourceID getSemantic();
	
	/**
	 * The datatype of the property.
	 * @return The datatype.
	 */
	Datatype getDatatype();
	
	/**
	 * The cardinality of this property,
	 * @return The cardinality.
	 */
	Cardinality getCardinality();
	
	/**
	 * The constraints for this property declaration.
	 * There are to levels where constraints can be defined. Either for the datatype
	 * or only for the property declaration. The set of constraints returned by
	 * this method contains them all.
	 * @return the constraints.
	 */
	Set<Constraint> getConstraints();
	
}
