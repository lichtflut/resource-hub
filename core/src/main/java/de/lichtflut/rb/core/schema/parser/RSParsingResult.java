/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

import java.util.List;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * <p>
 * Wraps the parsing result in ResourceSchema- and Constraint-sets,
 * TODO: additional to the error messages which have been occurred.
 * </p>
 *
 * <p>
 * Created Apr 20, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
public interface RSParsingResult {

	// ------------------------------------------------------

	/**
	 * @return a Collection of {@link Constraint}s. 
	 */
	List<Constraint> getPublicConstraints();

	// -----------------------------------------------------

	/**
	 * @return Collection of ResourceSchemas.
	 */
	List<ResourceSchema> getResourceSchemas();

	// -----------------------------------------------------

	/**
	 * Determines if at lest one error is occurred or not.
	 * @return boolean true if error occured, false if not
	 */
	boolean isErrorOccured();

	// -----------------------------------------------------

}
