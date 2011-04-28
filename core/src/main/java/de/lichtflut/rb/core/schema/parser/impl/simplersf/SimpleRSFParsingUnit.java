/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl.simplersf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Set;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import de.lichtflut.rb.core.schema.model.ResourceSchemaType;
import de.lichtflut.rb.core.schema.parser.RSErrorReporter;
import de.lichtflut.rb.core.schema.parser.RSParsingUnit;
import de.lichtflut.rb.core.schema.parser.impl.RSCaseControlStream;
import de.lichtflut.rb.core.schema.parser.impl.simplersf.ResourceSchemaParser.dsl_return;

/**
 * Parsing-unit of SimpleRSF
 * 
 * Created: Apr 28, 2011
 *
 * @author Nils Bleisch
 */
public class SimpleRSFParsingUnit implements RSParsingUnit{

	private RSErrorReporter errorReporter= null;
	
	public RSFormat getFormat() {
		return RSFormat.SIMPLE_RSF;
	}
	
	// -----------------------------------------------------

	public Collection<ResourceSchemaType> parse(final String input)
			throws RSMissingErrorReporterException {
		if(errorReporter == null) throw new RSMissingErrorReporterException("RSErrorReporter can not be null");
		return parseRSF(input);
	}

	// -----------------------------------------------------
	
	public Collection<ResourceSchemaType> parse(final InputStream input)
			throws RSMissingErrorReporterException {
		if(errorReporter == null) throw new RSMissingErrorReporterException("RSErrorReporter can not be null");
		try {
			//Build a String from InputStream
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuilder bufferedInput = new StringBuilder();
			String line;
			while((line = reader.readLine())!=null) bufferedInput.append(line).append("\n");
			return parse(bufferedInput.toString());
		} catch (IOException e) {
			errorReporter.reportError("The following I/O-Error has been occured: " + e.getMessage());
		}
		return null;
	}

	// -----------------------------------------------------
	
	public void setErrorReporter(final RSErrorReporter errorReporter) {
		this.errorReporter = errorReporter;
	}

	// -----------------------------------------------------
	
	
	private Set<ResourceSchemaType> parseRSF(final String input){
		RSCaseControlStream stream = new RSCaseControlStream(input);
		//Ignore Case, this is really important
		stream.setCaseSensitive(false);
		ResourceSchemaLexer lexer = new ResourceSchemaLexer(stream);
		TokenStream tokens = new CommonTokenStream(lexer);
		ResourceSchemaParser parser = new ResourceSchemaParser(tokens);
		parser.setErrorReporter(errorReporter);
		try {
			dsl_return result;
			result = parser.dsl();
			return result.types;
		} catch (RecognitionException e) {
			errorReporter.reportError(("A RecognitionException has been occurred: " + e.getMessage()));
		}
		return null;
	}
	
}
