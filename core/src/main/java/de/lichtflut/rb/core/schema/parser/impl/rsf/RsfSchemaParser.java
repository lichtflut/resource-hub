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
import de.lichtflut.rb.core.schema.parser.ResourceSchemaParser;

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
			result.getSchemas().addAll(walker.statements());
		} catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
