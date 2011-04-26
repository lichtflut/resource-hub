/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.util.Set;

import org.arastreju.sge.model.ElementaryDataType;

/**
 * <p>
 *  Definition of the property of a {@link PropertyAssertion}.
 * </p>
 * 
 * <p>
 *  Properties may either be literal properties (literals such as strings, numbers, dates)
 *  or resource references:
 *  
 *  <ol>
 *   <li> literal </li>
 *    	<ol> 
 *    	 <li> elementary (string, integer, decimal, date,...)
 *    	 <li> custom (user defined)
 *      </ol>
 *   <li> resource reference</li>
 *  <ol>
 *  
 * </p>
 * 
 * <p>
 * 	Each Property Declaration has a public URI and can be used isolated from Property Assertions.
 * </p>
 *
 * <p>
 * 	Created Jan 27, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface PropertyDeclaration  extends ResourceSchemaType{

	/**
	 * Get the {@link ElementaryDataType}.
	 * @return The elementary property.
	 */
	ElementaryDataType getElementaryDataType();
	
	/**
	 * Set the {@link ElementaryDataType}.
	 * @return void
	 */
	void setElementaryDataType(ElementaryDataType type);	
	
	/**
	 * Get the name of the property.
	 * TODO: Should be Resource ID.
	 * @return The name of the property.
	 */
	String getName();
	
	/**
	 * Set the name or identifier of this property.
	 * @return void
	 * @param the name or identifier you wish to set,
	 */
	void setName(String identifierString);
	
	/**
	 * The context independent constraints for this property.
	 * @return the constraints.
	 */
	Set<Constraint> getConstraints();
	
	/**
	 * The context independent constraints for this property.
	 * @param the constraints you like to set
	 * @return void
	 */
	void setConstraints(Set<Constraint> constraints);
	
	/**
	 * The context independent constraints for this property.
	 * @param the constraint you like to add
	 * @return void
	 */
	void addConstraint(Constraint constraint);
	
		
	// -----------------------------------------------------
	
	boolean isValue();
	
	boolean isElementary();
	
	boolean isCustom(); 
	
	boolean isResourceReference();
	
}
