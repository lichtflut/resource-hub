/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.SNResource;

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
	private List<PropertyDeclaration> list = new LinkedList<PropertyDeclaration>();
	
	// -----------------------------------------------------
	
	//Constructor stuff
	
	//Default constructor
	public ResourceSchemaImpl(){
		//TODO Generate resource with UUID
		//resource = new SNResource(UUID.randomUUID().toString() ,UUID.randomUUID().toString() );
	}
	
	// -----------------------------------------------------
	
	//constructor takes as argument an identifier which will help to define the Resource Identifier
	public ResourceSchemaImpl(final String identifier) throws IllegalArgumentException{
		//TODO check if identifier is an valid uri, if not generate a valid one, if this is not possible, throw an exception
		
	}
	
	public List<PropertyDeclaration> getPropertyDeclarations() {
		return list;
	}

	// -----------------------------------------------------
	
	public ResourceID getResourceID() {
		return this.resource;
	}

	// -----------------------------------------------------
	
	@Override
	public String toString(){
		StringBuffer s_buffer = new StringBuffer();
		s_buffer.append(getResourceID().toString() + "\n");
		for (PropertyDeclaration property : getPropertyDeclarations()) {
			s_buffer.append(property.toString() + "\n");
		}
		return s_buffer.toString();
	}
	
	// -----------------------------------------------------
	
}
