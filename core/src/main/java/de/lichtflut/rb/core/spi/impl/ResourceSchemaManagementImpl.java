/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.spi.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceSchemaType;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;
import de.lichtflut.rb.core.schema.parser.impl.RBCaseControlStream;
import de.lichtflut.rb.core.schema.parser.impl.RSParsingResultImpl;
import de.lichtflut.rb.core.schema.parser.impl.ResourceSchemaLexer;
import de.lichtflut.rb.core.schema.parser.impl.ResourceSchemaParser;
import de.lichtflut.rb.core.schema.parser.impl.ResourceSchemaParser.dsl_return;
import de.lichtflut.rb.core.spi.ResourceSchemaManagement;

/**
 * [TODO Insert description here.]
 * 
 * Created: Apr 19, 2011
 *
 * @author [SPECIFY USER: Window-> Preferences] 
 */
public class ResourceSchemaManagementImpl implements ResourceSchemaManagement {

	public ResourceSchemaManagementImpl(){
		
	}
	
	public RSParsingResult generateSchemaModelThrough(InputStream is){
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder bufferedInput = new StringBuilder();
		String line;
		try {
			while((line = reader.readLine())!=null) bufferedInput.append(line).append("\n");
		} catch (IOException e) {
			RSParsingResultImpl result = new RSParsingResultImpl();
			result.addErrorMessage("The following I/O-Error has been occured: " + e.getMessage());
			return result;
		}
		return generateSchemaModelThrough(bufferedInput.toString());
	}

	public RSParsingResult generateSchemaModelThrough(File file){
		try {
			return generateSchemaModelThrough(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			RSParsingResultImpl result = new RSParsingResultImpl();
			result.addErrorMessage("File " + file.getAbsolutePath() + " was not found");
			return result;
		}
	}

	public RSParsingResult generateSchemaModelThrough(String s) {
		RSParsingResultImpl result = new RSParsingResultImpl();
		try {
			Set<ResourceSchemaType> resultTypes = parseDSL(s);
			result.merge(convertToParsingResult(resultTypes));
		} catch (RecognitionException e) {
			result.addErrorMessage("The following Parsing-Error(s) has been occured: " + e.getMessage());
		}
		return result;
	}
	
	public RSParsingResult generateAndResolveSchemaModelThrough(InputStream is) {
		RSParsingResultImpl result = new RSParsingResultImpl();
		result.merge(generateSchemaModelThrough(is));
		
		return result;
	}

	public RSParsingResult generateAndResolveSchemaModelThrough(File f) {
		RSParsingResultImpl result = new RSParsingResultImpl();
		result.merge(generateSchemaModelThrough(f));
		
		return result;
	}

	public RSParsingResult generateAndResolveSchemaModelThrough(String s) {
		RSParsingResultImpl result = new RSParsingResultImpl();
		result.merge(generateSchemaModelThrough(s));
		
		return result;
	}
	
	
	private RSParsingResult convertToParsingResult(Set<ResourceSchemaType> types){
		RSParsingResultImpl result = new RSParsingResultImpl();
		for (ResourceSchemaType type : types) {
			if(type instanceof ResourceSchema) result.addResourceSchema((ResourceSchema) type);
			if(type instanceof PropertyDeclaration) result.addPropertyDeclaration((PropertyDeclaration) type);
		}
		return result;
	}
	
	private Set<ResourceSchemaType> parseDSL(final String input) throws RecognitionException{
		RBCaseControlStream stream = new RBCaseControlStream(input);
		//Ignore Case, this is really important
		stream.setCaseSensitive(false);
		ResourceSchemaLexer lexer = new ResourceSchemaLexer(stream);
		TokenStream tokens = new CommonTokenStream(lexer);
		ResourceSchemaParser parser = new ResourceSchemaParser(tokens);
		dsl_return result = parser.dsl();
		
		return result.types;
	}

}
