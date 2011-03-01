/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * <p>
 *  Common implementation of {@link ResourceSchema}
 * </p>
 *
 * <p>
 * 	Created Mar 01, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
public class ResourceSchemaImpl implements ResourceSchema{

	//Instance members
	private ResourceID resource;
	//LinkedList is chosen because it's more flexible compared to an index-driven list
	private final List<PropertyDeclaration> propertyList = new LinkedList<PropertyDeclaration>();
	
	// -----------------------------------------------------
	
	//Constructor stuff
	
	//Default constructor
	public ResourceSchemaImpl(){
		//Generates a SimpleResourceID instance with an random UUID for namespace and identifier each
		this.resource = new SimpleResourceID(
				UUID.randomUUID().toString(),
				UUID.randomUUID().toString());
	}
	
	// -----------------------------------------------------
	
	//Constructor takes as argument an identifier which will help to define the Resource Identifier
	public ResourceSchemaImpl(final String identifier) throws IllegalArgumentException{
		/*TODO check if identifier is an valid uri, if not generate a valid one, if this is not possible, throw an exception???
		 * Or generate it anyway?
		 */
	}
	
	public List<PropertyDeclaration> getPropertyDeclarations() {
		return this.propertyList;
	}

	// -----------------------------------------------------
	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.schema.model.Constraint#isLiteralConstraint()
	 */
	public ResourceID getResourceID() {
		return this.resource;
	}

	// -----------------------------------------------------
	
	/**
	 * @return Returns the toString()-representation of ResourceID followed by a list of toString()-PropertyDeclaration separated in '\n'
	 */
	@Override
	public String toString(){
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(getResourceID().toString() + "\n");
		for (PropertyDeclaration property : getPropertyDeclarations()) {
			sBuffer.append(property.toString() + "\n");
		}
		return sBuffer.toString();
	}
	
	// -----------------------------------------------------
	
}//End of class ResourceSchemaImpl
