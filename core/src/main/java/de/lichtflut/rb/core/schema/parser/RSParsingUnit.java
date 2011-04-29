/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

import java.io.InputStream;
import java.util.Collection;

import de.lichtflut.rb.core.schema.model.ResourceSchemaType;
import de.lichtflut.rb.core.schema.parser.impl.simplersf.RSFormat;


/**
 * <p>
 * 	A RSParsingUnit consists of a lexer and a parser with a given format.
 * </p>
 * 
 * <p>
 * Created: Apr 28, 2011
 * </p>
 * 
 * <pre>
 * TODO: Some aspects to check
 * - Shouldn't this be an abstract class (to enforce RSErrorReporter set in Constructor)
 * - Why is RSMissingErrorReporterException inner class?
 * </pre>
 *
 * @author Nils Bleisch
 */
public interface RSParsingUnit {

	//Define a special exception 
	@SuppressWarnings("serial")
	class RSMissingErrorReporterException extends Exception{
		public RSMissingErrorReporterException(String string) {
			super(string);
		}}
		
	// -----------------------------------------------------
	
	/** 
	 * @return {@link Collection} of {@link ResourceSchemaTypes} which has been tokenized and parsed
	 */
	Collection<ResourceSchemaType> parse(final String input) throws RSMissingErrorReporterException;
	
	// -----------------------------------------------------
	
	/** 
	 * @return {@link Collection} of {@link ResourceSchemaTypes} which has been tokenized and parsed
	 */
	Collection<ResourceSchemaType> parse(final InputStream input) throws RSMissingErrorReporterException;
	
	// -----------------------------------------------------
	
	
	
	/**
	 * Set the RSErrorReporter to report errors while lexing
	 * @param errorReporter
	 */
    public void setErrorReporter(final RSErrorReporter errorReporter);
	
	// -----------------------------------------------------
	
	public RSFormat getFormat();
	
	// -----------------------------------------------------

	
}
