/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.util.Collection;
import java.util.Set;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Definition of the type of a property.
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
 *  </ol>
 * </p>
 *
 * <p>
 * 	Each TypeDefinition has an unique ID and can be used isolated from Property Declarations.
 * </p>
 *
 * <p>
 * 	Created Jan 27, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface TypeDefinition  extends ResourceSchemaElement{

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
	
	// -----------------------------------------------------

	/**
	 * Get the identifier of the property.
	 * @return The identifier of the property.
	 */
	ResourceID getID();

	/**
	 * An unqualified readable name for this type definition.
	 * Useful if it is a public TypeDefinition.
	 * @return The display name.
	 */
	String getName();
	
	// -----------------------------------------------------

	/**
	 * Check if this is a public/global or an private/implicit type definition.
	 * return true if this is a public type.
	 */
	boolean isPublicTypeDef();
	
	/**
	 * @return boolean true if defined datatype is elementary. 
	 */
	boolean isLiteralValue();

	/**
	 * Is true when property declaration contains resource references and no independent values.
	 * @return true when property declaration contains independent, false if references resources
	 */
	boolean isResourceReference();
	
	// -----------------------------------------------------
	
	/**
	 * Get the {@link ElementaryDataType}.
	 * @return The elementary property.
	 */
	ElementaryDataType getElementaryDataType();

	/**
	 * Set the {@link ElementaryDataType}.
	 * @param type -
	 */
	void setElementaryDataType(ElementaryDataType type);

	// -----------------------------------------------------

	/**
	 * The context independent constraints for this property.
	 * @return the constraints.
	 */
	Set<Constraint> getConstraints();

	/**
	 * The context independent constraints for this property.
	 * @param constraints the constraints you like to set
	 */
	void setConstraints(Collection<Constraint> constraints);

	/**
	 * The context independent constraints for this property.
	 * @param constraint the constraint you like to add
	 */
	void addConstraint(Constraint constraint);

	// -----------------------------------------------------
	
	/**
	 * @return the qualified name
	 */
	@Deprecated
	String getIdentifierString();


	/**
	 * Set the name or identifier of this property.
	 * Tries to generate an URI from the given String, if not, the default Namespace will be used
	 * @param identifierString the name or identifier you wish to set,
	 */
	@Deprecated
	void setIdentifier(String identifierString);
	
	//------------------------------------------------------

	/**
	 * Please make sure that equals is correct implemented to avoid some merging redundancy conflicts e.g.
	 * @param obj -
	 * @return true if equals, false if not
	 */
	boolean equals(Object obj);

	
	/**
	 * Overriding only to match conventions while overriding 'equals()'.
	 * @return int
	 */
	int hashCode();
}
