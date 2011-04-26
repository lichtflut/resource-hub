/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

import java.util.Collection;

import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * 
 * [TODO Insert description here.]
 * 
 * Created: Apr 21, 2011
 *
 * @author [SPECIFY USER: Window-> Preferences]
 */
public interface RSParsingResult {

	public Collection<String> getErrorMessages();
	
	public String getErrorMessagesAsString();
	
	public Collection<PropertyDeclaration> getPropertyDeclarations();
	
	public Collection<ResourceSchema> getResourceSchemas();
	
	public Collection<PropertyDeclaration> getPropertyDeclarationsWithoutResourceAssoc();
	
	public boolean isErrorOccured();
	
	public void merge(RSParsingResult result);
	
}