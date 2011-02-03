/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema;

import java.util.Set;

import org.arastreju.sge.model.ElementaryDataType;

/**
 * <p>
 *  Definition of the datatype of a {@link PropertyDeclaration}.
 * </p>
 * 
 * <p>
 *  Datatypes may either be literal datatypes (literals such as strings, numbers, dates)
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
public interface Datatype {

	/**
	 * Get the {@link ElementaryDataType}.
	 * @return The elementary datatype.
	 */
	ElementaryDataType getElementaryDataType();
	
	/**
	 * Get the name of the datatype.
	 * @return The name of the datatype.
	 */
	String getName();
	
	/**
	 * The context independent constraints for this datatype.
	 * @return the constraints.
	 */
	Set<Constraint> getConstraints();
	
	// -----------------------------------------------------
	
	boolean isValue();
	
	boolean isElementary();
	
	boolean isCustom(); 
	
	boolean isResourceReference();
	
}
