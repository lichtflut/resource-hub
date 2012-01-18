/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl.rsf;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import de.lichtflut.rb.core.schema.parser.ParsedElements;
import de.lichtflut.rb.core.schema.parser.RSErrorReporter;
import de.lichtflut.rb.core.schema.parser.ResourceSchemaParser;
import de.lichtflut.rb.core.schema.parser.impl.RSParsingResultErrorReporter;
import de.lichtflut.rb.core.schema.parser.impl.RSParsingResultImpl;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Jan 17, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class RsfSchemaParser implements ResourceSchemaParser{

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ParsedElements parse(InputStream in) throws IOException {
		final ParsedElements result = new ParsedElements();
		
		final RSErrorReporter errorReporter = new RSParsingResultErrorReporter(new RSParsingResultImpl());
		
		final ANTLRInputStream antlrInput = new ANTLRInputStream(in);
		final RSFLexer lexer = new RSFLexer(antlrInput);
		lexer.setErrorReporter(errorReporter);
		final CommonTokenStream tokens = new CommonTokenStream(lexer);
		final RSFParser parser = new RSFParser(tokens);
		parser.setErrorReporter(errorReporter);
		try {
			parser.declarations();
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		return result;
	}

}
