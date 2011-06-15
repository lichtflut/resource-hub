/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;


/**
 * This is to report and log occurred errors during parsing.
 *
 * Created: Apr 21, 2011
 *
 * @author Nils Bleisch
 */
public interface RSErrorReporter {
	/**
	 * No use to describe what this method should do ;) .
	 * @param error to report
	 */
	void reportError(String error);

	/**
	 * Checks whether error has been reported.
	 * @return true if error has been reported, false if not
	 */
	boolean hasErrorReported();
}
