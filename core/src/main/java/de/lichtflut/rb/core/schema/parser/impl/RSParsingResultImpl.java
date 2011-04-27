/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;

/**
 * <p>
 *  This is the reference implementation of {@link RSParsingResult}
 * </p>
 *
 * <p>
 * 	Created Apr 20, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
public class RSParsingResultImpl implements RSParsingResult{

	private Collection<Error> errorMessages = new LinkedList<Error>();
	private Collection<PropertyDeclaration> propertiesDeclarations = new LinkedHashSet<PropertyDeclaration>();
	private Collection<ResourceSchema> resourceSchemas = new LinkedHashSet<ResourceSchema>();
	//This is the default error level
	private ErrorLevel level = ErrorLevel.ALL;

	// -----------------------------------------------------
	
	public void setPropertyDeclarations(
			Collection<PropertyDeclaration> propertiesDeclarations) {
		this.propertiesDeclarations = propertiesDeclarations;
	}

	// -----------------------------------------------------
	
	public void addPropertyDeclaration(PropertyDeclaration property) {
		this.propertiesDeclarations.add(property);
	}

	// -----------------------------------------------------
	
	public void addResourceSchema(ResourceSchema schema) {
		this.resourceSchemas.add(schema);
	}

	// -----------------------------------------------------
	

	public void setErrorLevel(ErrorLevel lvl){
		this.level = lvl;
	}
	
	// -----------------------------------------------------
	
	/**
	 * Logs this message with the pre-defined ErrorLevel 
	 */
	public void setErrorMessages(Collection<String> errorMessages) {
		this.setErrorMessages(errorMessages, this.level);
	}

	// -----------------------------------------------------
	
	/**
	 * Logs this message with the explicit given ErrorLevel
	 */
	public void addErrorMessage(String message, ErrorLevel lvl){
		this.errorMessages.add(new Error(lvl, message));
	}
	
	// -----------------------------------------------------
	
	/**
	 * Logs this messages with the explicit given ErrorLevel
	 */
	public void setErrorMessages(Collection<String> errorMessages, ErrorLevel lvl) {
		this.errorMessages = new LinkedList<Error>();
		for (String message : errorMessages) {
			this.errorMessages.add(new Error(lvl,message));
		}
	}

	// -----------------------------------------------------
	/**
	 * Logs this message with the pre-defined ErrorLevel 
	 */
	public void addErrorMessage(String message){
		this.addErrorMessage(message, this.level);
	}
	
	// -----------------------------------------------------
	
	public void setResourceSchemas(Collection<ResourceSchema> resourceSchemas) {
		this.resourceSchemas = resourceSchemas;
	}

	// -----------------------------------------------------

	public Collection<PropertyDeclaration> getPropertyDeclarations() {
		return this.propertiesDeclarations;
	}

	// -----------------------------------------------------

	
	public Collection<PropertyDeclaration> getPropertyDeclarationsWithoutResourceAssoc() {
		Collection<PropertyDeclaration> output = new HashSet<PropertyDeclaration>();
		//Get all propertyDecs assigned to the ResourceSchema
		for (ResourceSchema rSchema : this.resourceSchemas) {
			for (PropertyAssertion assertion : rSchema.getPropertyAssertions()) {
				output.add(assertion.getPropertyDeclaration());
			}
		}//End of outer for
		
		for (PropertyDeclaration pDec : this.propertiesDeclarations) {
			if(output.contains(pDec)) output.remove(pDec);
		}
		return output;
	}

	// -----------------------------------------------------
	
	public Collection<ResourceSchema> getResourceSchemas() {
		return this.resourceSchemas;
	}

	// -----------------------------------------------------
	
	public boolean isErrorOccured() {
		if(this.errorMessages.size()!=0) return true;
		return false;
	}

	// -----------------------------------------------------
	
	/**
	 * duplicated properties has to be eliminated while merging. Therfore, both Collections are LinkedHashSets
	 * and PropertyDeclaration and ResourceSchema have to override the equals.method.
	 */
	public void merge(RSParsingResult result) {
		if(!result.getErrorMessages().equals("")) this.errorMessages.addAll(((RSParsingResultImpl) result).errorMessages);
		this.getPropertyDeclarations().addAll(result.getPropertyDeclarations());
		this.getResourceSchemas().addAll(result.getResourceSchemas());
	}

	// -----------------------------------------------------
	
	public String getErrorMessagesAsString() {
		return getErrorMessagesAsString(this.level);
	}
	
	// -----------------------------------------------------
	
	public Collection<String> getErrorMessages(){
		return getErrorMessages(this.level);
	}

	// -----------------------------------------------------
	
	public Collection<String> getErrorMessages(ErrorLevel filter) {
		Collection<String> output = new LinkedList<String>();
		for (Error error : this.errorMessages) {
			//Check for filter
			if(filter.contains(error.getErrorLevel())){
				output.add(error.message);
			}
		}
		return output;
	}

	// -----------------------------------------------------
	
	public String getErrorMessagesAsString(ErrorLevel filter) {
		return this.getErrorMessagesAsString("\n", this.level);
	}

	// -----------------------------------------------------
	
	public String getErrorMessagesAsString(String delim, ErrorLevel filter) {
		Collection<String> messages = getErrorMessages(filter);
		StringBuilder sBuilder = new StringBuilder();
		for (String error : messages) {
			sBuilder.append(error).append(delim);
		}
		return sBuilder.toString();
	}

	public String getErrorMessagesAsString(String delim) {
		return this.getErrorMessagesAsString(delim, this.level);
	}
	
	/**
	 * Awesome tuple class, acts as tuple and is just needed here. 
	 */
	class Error { 
		  private ErrorLevel lvl; 
		  private String message; 
		  public Error(ErrorLevel lvl,String message) { 
		    this.lvl = lvl; 
		    this.message = message; 
		  } 
		  public String getMessage() { 
		    return this.message; 
		  } 
		  public ErrorLevel getErrorLevel() { 
		    return this.lvl; 
		  } 
		}//End of class Error 
	
}
