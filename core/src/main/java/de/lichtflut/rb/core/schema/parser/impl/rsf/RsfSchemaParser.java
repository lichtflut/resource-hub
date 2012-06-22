/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl.rsf;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;

import de.lichtflut.rb.core.schema.parser.ParsedElements;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;
import de.lichtflut.rb.core.schema.parser.ResourceSchemaParser;

/**
 * <p>
 *  Schema Parser for RSF files.
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

		CharStream input = null;
		input = new ANTLRInputStream(in);
		RSFLexer lexer = new RSFLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		RSFParser parser = new RSFParser(tokens);
		try {
			RSFParser.statements_return r = parser.statements();

			CommonTreeNodeStream nodes = new CommonTreeNodeStream(r.getTree());
			nodes.setTokenStream(tokens);
			RSFTree walker = new RSFTree(nodes);
//			walker.setErrorReporter(new RSParsingResultErrorReporter(new RSParsingResultImpl()));
			RSParsingResult parsed = walker.statements();
			result.getSchemas().addAll(parsed.getResourceSchemas());
			result.getConstraints().addAll(parsed.getPublicConstraints());
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

}
