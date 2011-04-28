/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.util.Set;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;

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
 *  
 *  ===ATTENTION===
 *  There still exists one reference implementation {@link PropertyDeclarationImpl}.
 *  It's recommended to use this implementation  because this is already implementing the whole spec right.
 *  If you want to realize your own ResourceSchema-class, please be absolutely sure that you already know what you do.
 *  
 *
 * <p>
 * 	Created Jan 27, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface PropertyDeclaration  extends ResourceSchemaType{

	/**
	 * TODO: Please continue this lists.
	 */
	ElementaryDataType[] ELEMENTATY_DATA_TYPES = new ElementaryDataType[]{
			ElementaryDataType.BOOLEAN,
			ElementaryDataType.INTEGER,
			ElementaryDataType.DECIMAL,
			ElementaryDataType.STRING,
			ElementaryDataType.DATE,
	};
	
	ElementaryDataType[] NON_ELEMENTATY_DATA_TYPES = new ElementaryDataType[]{
			ElementaryDataType.RESOURCE,
			ElementaryDataType.UNDEFINED,
			ElementaryDataType.URI
	};
	
	
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
	 * Get the identifier of the property.
	 * If the identifier is no valid uri, it will be converted into the default uri with void namespace
	 * @return The identifier of the property.
	 */
	ResourceID getIdentifier();
	
	/**
	 * @return the unqualified name
	 */
	String getName();
	
	/**
	 * Set the name or identifier of this property.
	 * Tries to generate an URI from the given String, if not, the default Namespace will be used
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
	
	/**
	 * Please make sure that equals is correct implemented to avoid some merging redundancy conflicts e.g.
	 */
	boolean equals(Object obj);
	
	
	//------------------------------------------------------
	
	boolean isValue();
	
	//------------------------------------------------------
	
	boolean isElementary();
	
	//------------------------------------------------------
	
	boolean isCustom(); 
	
	//------------------------------------------------------
	
	boolean isResourceReference();
	
}
