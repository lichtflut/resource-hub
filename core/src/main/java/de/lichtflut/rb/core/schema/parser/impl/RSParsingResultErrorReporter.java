/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl;

import de.lichtflut.rb.core.schema.parser.RSErrorReporter;
import de.lichtflut.rb.core.schema.parser.RSParsingResult.ErrorLevel;
/**
 * Reference-Implementation of {@link RSErrorReporter}
 * 
 * Created: Apr 21, 2011
 *
 * @author Nils Bleisch
 */
public class RSParsingResultErrorReporter implements RSErrorReporter{

	private RSParsingResultImpl result;
	
	// -----------------------------------------------------

	public RSParsingResultErrorReporter(RSParsingResultImpl result){
		this.result = result;
	}
	
	// -----------------------------------------------------
	
	public void reportError(String error) {
		result.addErrorMessage(error, ErrorLevel.GRAMMAR);
	}

}
