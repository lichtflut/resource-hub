/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl;

import java.util.ArrayList;
import java.util.Collection;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;

/**
 * [TODO Insert description here.]
 * 
 * Created: Apr 21, 2011
 *
 * @author [SPECIFY USER: Window-> Preferences] 
 */
public class RSParsingResultImpl implements RSParsingResult{

	private ArrayList<String> errorMessages = new ArrayList<String>();
	private Collection<PropertyDeclaration> propertiesDeclarations;
	private Collection<ResourceSchema> resourceSchemas;
	

		
	public String getErrorMessages() {
		StringBuilder sBuilder = new StringBuilder();
		for (String message : errorMessages) {
			sBuilder.append(message).append("\n");
		}
		return sBuilder.toString();
	}
	



	public void setPropertyDeclarations(
			Collection<PropertyDeclaration> propertiesDeclarations) {
		this.propertiesDeclarations = propertiesDeclarations;
	}

	public void addPropertyDeclaration(PropertyDeclaration property) {
		this.propertiesDeclarations.add(property);
	}

	public void addResourceSchema(ResourceSchema schema) {
		this.resourceSchemas.add(schema);
	}

	public void setErrorMessages(Collection<String> errorMessages) {
		this.errorMessages = new ArrayList<String>(errorMessages);
	}


	public void addErrorMessage(String message){
		this.errorMessages.add(message);
	}
	
	public void setResourceSchemas(Collection<ResourceSchema> resourceSchemas) {
		this.resourceSchemas = resourceSchemas;
	}


	public Collection<PropertyDeclaration> getPropertyDeclarations() {
		return this.propertiesDeclarations;
	}

	public Collection<PropertyDeclaration> getPropertyDeclarationsWithoutResourceAssoc() {
		throw new NotImplementedException();
	}

	public Collection<ResourceSchema> getResourceSchemas() {
		return this.resourceSchemas;
	}
	
}
