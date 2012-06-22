/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.schema.parser.RSErrorReporter;
/**
 * Reference-Implementation of {@link RSErrorReporter}.
 *
 * Created: Apr 21, 2011
 *
 * @author Nils Bleisch
 */
public class RSParsingResultErrorReporter implements RSErrorReporter{

	private final Logger logger = LoggerFactory.getLogger(RSParsingResultErrorReporter.class);
	
	private final RSParsingResultImpl result;

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
	@Override
	public void reportError(final String error) {
		logger.info("ErrorReporter has added the following error: " + error);
//		TODO: introduce error-functionality
//		result.addErrorMessage(error, RSErrorLevel.GRAMMAR);
	}

	/**
	 * Returns if an error is occured.
	 * @return true if error is occured, false if not
	 */
	@Override
	public boolean hasErrorReported() {
		return result.isErrorOccured();
	}

}
