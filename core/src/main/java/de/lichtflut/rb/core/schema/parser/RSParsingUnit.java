/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

import java.io.InputStream;
import java.util.Collection;

import de.lichtflut.rb.core.schema.model.ResourceSchemaType;


/**
 * A RSParsingUnit consists of a lexer and a parser with a given format.
 * 
 * Created: Apr 28, 2011
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
	
	enum RSFormat{
	
		SIMPLE_RDF("simple-rdf");
		
		private String name = "undefined";
		private RSFormat(String name){
			this.name= name;
		}
		
		public String toString(){
			return name;
		}
	}
	
	
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
