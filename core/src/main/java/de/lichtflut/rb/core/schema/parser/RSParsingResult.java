/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

import java.util.Collection;

import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * <p>
 *  Wraps the parsing result in ResourceSchema- and PropertyDeclaration-sets, additional
 *  to the error messages which have been occurred.
 * </p>
 *
 * <p>
 * 	Created Apr 20, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
public interface RSParsingResult {

	
	/**
	 * Bit-masking enum shit
	 * SYSTEM.add(GRAMMAR.add(...).....).add(.....)
	 */
	enum ErrorLevel{
		
		// ----------------------------
		
		SYSTEM(0),INTERPRETER(1),GRAMMAR(2), ALL(new ErrorLevel[]{SYSTEM, INTERPRETER, GRAMMAR});
		private int value = 0;
		private ErrorLevel(int level){
			this.value = 1<<level;
		}
		
		// ----------------------------
		
		private ErrorLevel(ErrorLevel[] lvl){
			for (ErrorLevel errorLevel : lvl) {
				this.add(errorLevel);
			}
		}
		
		// ----------------------------
		
		public boolean contains(ErrorLevel lvl){
			
			if((lvl.value & this.value)!= 0) return true;
			return false;
		}
		
		// ----------------------------
		
		/**
		 * Self returning idiom 4tw
		 */
		public ErrorLevel add(ErrorLevel lvl){
			if(!contains(lvl)) this.value += lvl.value;
			return this;
		}
	}
	
	/**
	 * 
	 * @return those errors as collection where the filter is matching
	 */
	public Collection<String> getErrorMessages(ErrorLevel filter);
	
	// -----------------------------------------------------
	
	/**
	 * 
	 * @return all errors as collection
	 */
	public Collection<String> getErrorMessages();
	
	// -----------------------------------------------------
	
	
	/**
	 * @return all the filter matching errors as one whole string concatenated with "\n"
	 */
	public String getErrorMessagesAsString(ErrorLevel filter);
	
	// -----------------------------------------------------
	
	/**
	 * @return all errors as one whole string concatenated with "\n"
	 */
	public String getErrorMessagesAsString();
	
	// -----------------------------------------------------
	
	
	/**
	 * @return all the filter matching errors as one whole string concatenated with the given delimeter
	 */
	public String getErrorMessagesAsString(String delim, ErrorLevel filter);
	
	// -----------------------------------------------------
	
	
	/**
	 * @return all errors as one whole string concatenated with the given delimeter
	 */
	public String getErrorMessagesAsString(String delim);
	
	
	// -----------------------------------------------------
	
	/**
	 * @return null when at least on error has been occurred
	 */
	public Collection<PropertyDeclaration> getPropertyDeclarations();
	
	// -----------------------------------------------------
	

	/**
	 * @return null when at least on error has been occurred
	 */
	public Collection<ResourceSchema> getResourceSchemas();
	
	// -----------------------------------------------------
	
	/**
	 * @return null when at least on error has been occurred
	 */
	public Collection<PropertyDeclaration> getPropertyDeclarationsWithoutResourceAssoc();
	
	// -----------------------------------------------------
	
	/**
	 * Determines if at lest one error is occurred or not
	 */
	public boolean isErrorOccured();
	
	// -----------------------------------------------------
	
	/**
	 * Merge another ParsingResult with this ParsingResult.
	 * Duplicated model entries will be eliminated.
	 * This member can be understand as "union"
	 * TODO: Write a test to verify this
	 */
	public void merge(RSParsingResult result);
	
}