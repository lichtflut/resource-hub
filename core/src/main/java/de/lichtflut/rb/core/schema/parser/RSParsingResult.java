/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

import java.util.Collection;

import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * <p>
 * Wraps the parsing result in ResourceSchema- and PropertyDeclaration-sets,
 * additional to the error messages which have been occurred.
 * </p>
 * 
 * <p>
 * Created Apr 20, 2011
 * </p>
 * 
 * @author Nils Bleisch
 */
public interface RSParsingResult {


	/**
	 * 
	 * @return those errors as collection where the filter is matching
	 */
	public Collection<String> getErrorMessages(RSErrorLevel filter);

	// -----------------------------------------------------

	/**
	 * 
	 * @return all errors as collection
	 */
	public Collection<String> getErrorMessages();

	// -----------------------------------------------------

	/**
	 * @return all the filter matching errors as one whole string concatenated
	 *         with "\n"
	 */
	public String getErrorMessagesAsString(RSErrorLevel filter);

	// -----------------------------------------------------

	/**
	 * @return all errors as one whole string concatenated with "\n"
	 */
	public String getErrorMessagesAsString();

	// -----------------------------------------------------

	/**
	 * @return all the filter matching errors as one whole string concatenated
	 *         with the given delimeter
	 */
	public String getErrorMessagesAsString(String delim, RSErrorLevel filter);

	// -----------------------------------------------------

	/**
	 * @return all errors as one whole string concatenated with the given
	 *         delimeter
	 */
	public String getErrorMessagesAsString(String delim);

	// -----------------------------------------------------

	/**
	 * @return is empty when at least on error has been occurred
	 */
	public Collection<PropertyDeclaration> getPropertyDeclarations();

	// -----------------------------------------------------

	/**
	 * @return is empty when at least on error has been occurred
	 */
	public Collection<ResourceSchema> getResourceSchemas();

	// -----------------------------------------------------

	/**
	 * 
	 * @return the ResourceSchema even if errors have been occured
	 */
	public Collection<ResourceSchema> getResourceSchemasIgnoreErrors();

	// -----------------------------------------------------

	/**
	 * 
	 * @return the PropertyDeclaration even if errors have been occured
	 */
	public Collection<PropertyDeclaration> getPropertyDeclarationsIgnoreErrors();

	// -----------------------------------------------------

	/**
	 * 
	 * @return the PropertyDeclaration which are not assigned to a
	 *         ResourceSchema, even if errors have been occured
	 */
	public Collection<PropertyDeclaration> getPropertyDeclarationsWithoutResourceAssocIgnoreErrors();

	// -----------------------------------------------------

	/**
	 * all the PropertyDeclarations which are not assigned to a ResourceSchema
	 * 
	 * @return is empty when at least on error has been occurred
	 */
	public Collection<PropertyDeclaration> getPropertyDeclarationsWithoutResourceAssoc();

	// -----------------------------------------------------

	/**
	 * Determines if at lest one error is occurred or not
	 */
	public boolean isErrorOccured();

	// -----------------------------------------------------

	/**
	 * Merge another ParsingResult with this ParsingResult. Duplicated model
	 * entries will be eliminated. This member can be understand as "union" A
	 * Testcase still exists
	 */
	public void merge(RSParsingResult result);
	
	/**
	 * Set the ErrorLevel which is used as default when no other ErrorLevel is explicitly given
	 * @param lvl
	 */
	public void setErrorLevel(RSErrorLevel lvl);

}