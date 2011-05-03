/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl.osf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedList;

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeNodeStream;

import de.lichtflut.rb.core.schema.model.ResourceSchemaType;
import de.lichtflut.rb.core.schema.parser.RSErrorReporter;
import de.lichtflut.rb.core.schema.parser.RSFormat;
import de.lichtflut.rb.core.schema.parser.RSParsingUnit;
import de.lichtflut.rb.core.schema.parser.exception.RSMissingErrorReporterException;
import de.lichtflut.rb.core.schema.parser.impl.RSCaseControlStream;
import de.lichtflut.rb.core.schema.parser.impl.osf.OSFParser.osl_return;

/**
 * <p>
 *  Parsing-unit of OSF (Oliver Tigges Simple Format).
 *  </p>
 *
 * Created: Apr 03, 2011
 *
 * @author Nils Bleisch
 */
public class OSFParsingUnit extends RSParsingUnit {

    private RSErrorReporter errorReporter= null;
	
	public RSFormat getFormat() {
		return RSFormat.SIMPLE_RSF;
	}
	
	// -----------------------------------------------------

	public Collection<ResourceSchemaType> parse(final String input)
			throws RSMissingErrorReporterException {
		if(errorReporter == null) throw new RSMissingErrorReporterException("RSErrorReporter can not be null");
		return parseOSF(input);
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
	
	
	private Collection<ResourceSchemaType> parseOSF(final String input){
		Collection<ResourceSchemaType> output = new LinkedList<ResourceSchemaType>();
		RSCaseControlStream stream = new RSCaseControlStream(input);
		//Ignore Case, this is really important
		stream.setCaseSensitive(false);
		OSFLexer lexer = new OSFLexer(stream);
		lexer.setErrorReporter(errorReporter);
		TokenStream tokens = new CommonTokenStream(lexer);
		OSFParser parser = new OSFParser(tokens);
		parser.setErrorReporter(errorReporter);
		OSFTree treeParser=null;
		try {
			osl_return result;
			result = parser.osl();
			treeParser = new OSFTree(new CommonTreeNodeStream(result.tree));
			treeParser.setErrorReporter(this.errorReporter);
			output =  treeParser.osl().list;
		} catch (RecognitionException e) {
			errorReporter.reportError(("A RecognitionException has been occurred: " + treeParser.getErrorMessage(e, null)));
		}
		return output;
	}
	
}
