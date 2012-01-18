/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.schema.parser.RSErrorReporter;
import de.lichtflut.rb.core.schema.parser.RSErrorLevel;
/**
 * Reference-Implementation of {@link RSErrorReporter}.
 *
 * Created: Apr 21, 2011
 *
 * @author Nils Bleisch
 */
public class RSParsingResultErrorReporter implements RSErrorReporter{

	private final Logger logger = LoggerFactory.getLogger(RSParsingResultErrorReporter.class);
	
	private RSParsingResultImpl result;

	// -----------------------------------------------------

	/**
	 * TODO .
	 * @param result -
	 */
	public RSParsingResultErrorReporter(final RSParsingResultImpl result){
		this.result = result;
	}

	// -----------------------------------------------------

	/**
	 * Adds Error to Result.
	 * @param error that has occured
	 */
	public void reportError(final String error) {
		logger.info("ErrorReporter has added the following error: " + error);
		result.addErrorMessage(error, RSErrorLevel.GRAMMAR);
	}

	/**
	 * Returns if an error is occured.
	 * @return true if error is occured, false if not
	 */
	public boolean hasErrorReported() {
		return result.isErrorOccured();
	}

}
