/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl.rsf;

import java.io.IOException;
import java.io.InputStream;

import de.lichtflut.rb.core.schema.parser.exception.SchemaParsingException;
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

	@Override
	public ParsedElements parse(InputStream in) throws IOException, SchemaParsingException {
		final ParsedElements result = new ParsedElements();

		CharStream input = null;
		input = new ANTLRInputStream(in);
		RSFLexer lexer = new RSFLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		RSFParser parser = new RSFParser(tokens);
		try {
			RSFParser.statements_return statements = parser.statements();
			CommonTreeNodeStream nodes = new CommonTreeNodeStream(statements.getTree());
			nodes.setTokenStream(tokens);
			RSFTree walker = new RSFTree(nodes);
            walker.setErrorReporter(new RsfErrorReporterImpl(result));
			RSParsingResult parsed = walker.statements();

            if (!result.getErrorMessages().isEmpty()) {
                throw new SchemaParsingException("Error(s) while parsing schema: "
                        + result.getErrorMessages().toString());
            }

			result.getSchemas().addAll(parsed.getResourceSchemas());
			result.getConstraints().addAll(parsed.getPublicConstraints());

		} catch (RecognitionException e) {
			throw new SchemaParsingException(e);
		}
		return result;
	}

}
