/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

import java.util.Collection;

import de.lichtflut.rb.core.schema.model.TypeDefinition;
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
	 * Returns those errors as collection where the filter is matching.
	 * @param filter -
	 * @return those errors as collection where the filter is matching
	 */
	Collection<String> getErrorMessages(RSErrorLevel filter);

	// -----------------------------------------------------

	/**
	 * Returns all errors as collection.
	 * @return all errors as collection
	 */
	Collection<String> getErrorMessages();

	// -----------------------------------------------------

	/**
	 * Returns all the filter matching errors as one whole string concatenated
	 *         with "\n".
	 *         @param filter -
	 * @return all the filter matching errors as one whole string concatenated
	 *         with "\n"
	 */
	String getErrorMessagesAsString(RSErrorLevel filter);

	// -----------------------------------------------------

	/**
	 * Returns all errors as one whole string concatenated with "\n".
	 * @return all errors as one whole string concatenated with "\n"
	 */
	String getErrorMessagesAsString();

	// -----------------------------------------------------

	/**
	 * Returns all the filter matching errors as one whole string concatenated
	 *         with the given delimeter.
	 * @param delim -
	 * @param filter -
	 * @return all the filter matching errors as one whole string concatenated
	 *         with the given delimeter
	 */
	String getErrorMessagesAsString(String delim, RSErrorLevel filter);

	// -----------------------------------------------------

	/**
	 * Returns all errors as one whole string concatenated with the given
	 *         delimeter.
	 * @param delim -
	 * @return all errors as one whole string concatenated with the given
	 *         delimeter
	 */
	String getErrorMessagesAsString(String delim);

	// -----------------------------------------------------

	/**
	 * Returns a Collection of property declarations.
	 * @return is empty when at least on error has been occurred
	 */
	Collection<TypeDefinition> getPropertyDeclarations();

	// -----------------------------------------------------

	/**
	 * Returns Collection of ResourceSchemas.
	 * @return is empty when at least on error has been occurred
	 */
	Collection<ResourceSchema> getResourceSchemas();

	// -----------------------------------------------------

	/**
	 *
	 * @return the ResourceSchema even if errors have been occured
	 */
	Collection<ResourceSchema> getResourceSchemasIgnoreErrors();

	// -----------------------------------------------------

	/**
	 *
	 * @return the PropertyDeclaration even if errors have been occured
	 */
	Collection<TypeDefinition> getPropertyDeclarationsIgnoreErrors();

	// -----------------------------------------------------

	/**
	 *
	 * @return the PropertyDeclaration which are not assigned to a
	 *         ResourceSchema, even if errors have been occured
	 */
	Collection<TypeDefinition> getPropertyDeclarationsWithoutResourceAssocIgnoreErrors();

	// -----------------------------------------------------

	/**
	 * all the PropertyDeclarations which are not assigned to a ResourceSchema.
	 *
	 * @return is empty when at least on error has been occurred
	 */
	Collection<TypeDefinition> getPropertyDeclarationsWithoutResourceAssoc();

	// -----------------------------------------------------

	/**
	 * Determines if at lest one error is occurred or not.
	 * @return boolean true if error occured, false if not
	 */
	boolean isErrorOccured();

	// -----------------------------------------------------

	/**
	 * Merge another ParsingResult with this ParsingResult. Duplicated model
	 * entries will be eliminated. This member can be understand as "union" A
	 * Testcase still exists
	 * @param result -
	 */
	void merge(RSParsingResult result);

	/**
	 * Set the ErrorLevel which is used as default when no other ErrorLevel is explicitly given.
	 * @param lvl -
	 */
	void setErrorLevel(RSErrorLevel lvl);
}
