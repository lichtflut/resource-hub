/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;


/**
 * This is to report and log occurred errors during parsing
 * 
 * Created: Apr 21, 2011
 *
 * @author Nils Bleisch
 */
public interface RSErrorReporter {
	/**
	 * No use to describe what this method should do ;)
	 * @param error
	 */
	public void reportError(String error);	
	
	
	public boolean hasErrorReported();
	
}
