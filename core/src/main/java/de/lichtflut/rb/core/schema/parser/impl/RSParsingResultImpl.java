/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.parser.RSErrorLevel;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;

/**
 * <p>
 *  This is the reference implementation for {@link RSParsingResult}.
 * </p>
 *
 * <p>
 * 	Created Apr 20, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
public class RSParsingResultImpl implements RSParsingResult{

	/**
	 *
	 */
	private Collection<Error> errorMessages = new LinkedList<Error>();
	/**
	 *
	 */
	private Collection<PropertyDeclaration> propertiesDeclarations = new LinkedHashSet<PropertyDeclaration>();
	/**
	 *
	 */
	private Collection<ResourceSchema> resourceSchemas = new LinkedHashSet<ResourceSchema>();
	/**
	 *
	 */
	//This is the default error level
	private RSErrorLevel level = RSErrorLevel.ALL;

	// -----------------------------------------------------

	/**
	 * Sets property declarations.
	 * @param propertiesDeclarations -
	 */
	public void setPropertyDeclarations(
			final Collection<PropertyDeclaration> propertiesDeclarations) {
		this.propertiesDeclarations = propertiesDeclarations;
	}

	// -----------------------------------------------------

	/**
	 * Adds a proppertydeclaration.
	 * @param property -
	 */
	public void addPropertyDeclaration(final PropertyDeclaration property) {
		this.propertiesDeclarations.add(property);
	}

	// -----------------------------------------------------

	/**
	 * Adds a resource schema.
	 * @param schema -
	 */
	public void addResourceSchema(final ResourceSchema schema) {
		this.resourceSchemas.add(schema);
	}

	// -----------------------------------------------------

	/**
	 * Sets the Errorlevel.
	 * @param lvl - Errorlevel
	 */
	public void setErrorLevel(final RSErrorLevel lvl){
		this.level = lvl;
	}

	// -----------------------------------------------------

	/**
	 * Logs this message with the pre-defined ErrorLevel.
	 * @param errorMessages -
	 */
	public void setErrorMessages(final Collection<String> errorMessages) {
		this.setErrorMessages(errorMessages, this.level);
	}

	// -----------------------------------------------------

	/**
	 * Logs this message with the explicit given ErrorLevel.
	 * @param message -
	 * @param lvl - Errorlevel
	 */
	public void addErrorMessage(final String message, final RSErrorLevel lvl){
		this.errorMessages.add(new Error(lvl, message));
	}

	// -----------------------------------------------------

	/**
	 * Logs this messages with the explicit given ErrorLevel.
	 * @param errorMessages -
	 * @param lvl - Errorlevel
	 */
	public void setErrorMessages(final Collection<String> errorMessages, RSErrorLevel lvl) {
		this.errorMessages = new LinkedList<Error>();
		for (String message : errorMessages) {
			this.errorMessages.add(new Error(lvl,message));
		}
	}

	// -----------------------------------------------------
	/**
	 * Logs this message with the pre-defined ErrorLevel.
	 * @param message -
	 */
	public void addErrorMessage(final String message){
		this.addErrorMessage(message, this.level);
	}

	// -----------------------------------------------------

	/**
	 * Set Resourceschemas.
	 * @param resourceSchemas -
	 */
	public void setResourceSchemas(final Collection<ResourceSchema> resourceSchemas) {
		this.resourceSchemas = resourceSchemas;
	}

	// -----------------------------------------------------

	/**
	 * Returns all Propertydeclarations.
	 * @return Propertydeclarations
	 */
	public Collection<PropertyDeclaration> getPropertyDeclarations() {
		//return an empty collection if an error is occured
		if(isErrorOccured()) return Collections.emptySet();
		return getPropertyDeclarationsIgnoreErrors();
	}

	// -----------------------------------------------------

	public Collection<PropertyDeclaration> getPropertyDeclarationsIgnoreErrors() {
		return this.propertiesDeclarations;
	}

	// -----------------------------------------------------

	public Collection<PropertyDeclaration> getPropertyDeclarationsWithoutResourceAssocIgnoreErrors(){
		Collection<PropertyDeclaration> output = new HashSet<PropertyDeclaration>();
		//Get all propertyDecs assigned to the ResourceSchema
		for (ResourceSchema rSchema : this.resourceSchemas) {
			for (PropertyAssertion assertion : rSchema.getPropertyAssertions()) {
				output.add(assertion.getPropertyDeclaration());
			}
		}//End of outer for

		for (PropertyDeclaration pDec : this.propertiesDeclarations) {
			if(output.contains(pDec)) {output.remove(pDec);}
		}
		return output;
	}

	// -----------------------------------------------------

	public Collection<PropertyDeclaration> getPropertyDeclarationsWithoutResourceAssoc() {
		//return an empty collection if an error is occured
		if(isErrorOccured()){ return Collections.emptySet();}
		return getPropertyDeclarationsWithoutResourceAssocIgnoreErrors();
	}

	// -----------------------------------------------------

	public Collection<ResourceSchema> getResourceSchemasIgnoreErrors() {
		return this.resourceSchemas;
	}

	// -----------------------------------------------------

	public Collection<ResourceSchema> getResourceSchemas() {
		//return an empty collection if an error is occured
		if(isErrorOccured()) {return Collections.emptySet();}
		return getResourceSchemasIgnoreErrors();
	}

	// -----------------------------------------------------

	public boolean isErrorOccured() {
		if(this.errorMessages.size()!=0) {return true;}
		return false;
	}

	// -----------------------------------------------------

	/**
	 * duplicated properties has to be eliminated while merging. Therefore, both Collections are LinkedHashSets
	 * and PropertyDeclaration and ResourceSchema have to override the equals-method.
	 */
	public void merge(RSParsingResult result) {

		if(!result.getErrorMessages().equals("")) {this.errorMessages.addAll(((RSParsingResultImpl) result).errorMessages);}
		this.getPropertyDeclarationsIgnoreErrors().addAll(result.getPropertyDeclarationsIgnoreErrors());
		this.getResourceSchemasIgnoreErrors().addAll(result.getResourceSchemasIgnoreErrors());
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

	public Collection<String> getErrorMessages(RSErrorLevel filter) {
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

	public String getErrorMessagesAsString(RSErrorLevel filter) {
		return this.getErrorMessagesAsString("\n", filter);
	}

	// -----------------------------------------------------

	public String getErrorMessagesAsString(String delim, RSErrorLevel filter) {
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
		  private RSErrorLevel lvl; 
		  private String message; 
		  public Error(RSErrorLevel lvl,String message) { 
		    this.lvl = lvl; 
		    this.message = message; 
		  }
		  public String getMessage() { 
		    return this.message; 
		  }
		  public RSErrorLevel getErrorLevel() { 
		    return this.lvl; 
		  }
		}//End of class Error 
}
