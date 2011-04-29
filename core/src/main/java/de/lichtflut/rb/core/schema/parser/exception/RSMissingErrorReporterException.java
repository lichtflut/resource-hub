/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.exception;

/**
 * <p>
 * 	Exception which is fired if an ErrorReporter is not set when it's required
 * </p>
 * 
 * Created: Apr 29, 2011
 *
 * @author Nils Bleisch
 */
@SuppressWarnings("serial")
public class RSMissingErrorReporterException extends Exception {

	public RSMissingErrorReporterException(String string) {
		super(string);
	}
	
}
