/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl.rsf;

import java.io.IOException;
import java.io.InputStream;

import de.lichtflut.rb.core.schema.parser.ParsedElements;
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
//		final ParsedElements result = new ParsedElements();
//
//		CharStream input = null;
//		input = new ANTLRInputStream(in);
//		RSFLexer lexer = new RSFLexer(input);
//		CommonTokenStream tokens = new CommonTokenStream(lexer);
//		RSFParser parser = new RSFParser(tokens);
//		try {
//			RSFParser.statements_return r = parser.statements();
//
//			CommonTreeNodeStream nodes = new CommonTreeNodeStream(r.getTree());
//			nodes.setTokenStream(tokens);
//			RSFTree walker = new RSFTree(nodes);
//			walker.setErrorReporter(new RSParsingResultErrorReporter(new RSParsingResultImpl()));
//			result.getSchemas().addAll(walker.statements());
//		} catch (RecognitionException e) {
//			throw new RuntimeException(e);
//		}
		return null;
	}

}
