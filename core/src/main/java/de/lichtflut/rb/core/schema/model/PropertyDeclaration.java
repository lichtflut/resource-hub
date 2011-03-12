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
 * 	Created Jan 27, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface PropertyDeclaration {

	/**
	 * Get the {@link ElementaryDataType}.
	 * @return The elementary property.
	 */
	ElementaryDataType getElementaryDataType();
	
	/**
	 * Get the name of the property.
	 * @return The name of the property.
	 */
	String getName();
	
	/**
	 * The context independent constraints for this property.
	 * @return the constraints.
	 */
	Set<Constraint> getConstraints();
	
	// -----------------------------------------------------
	
	boolean isValue();
	
	boolean isElementary();
	
	boolean isCustom(); 
	
	boolean isResourceReference();
	
}
