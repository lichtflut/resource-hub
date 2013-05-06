/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
import de.lichtflut.rb.core.schema.parser.exception.SchemaParsingException;

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
	public ParsedElements parse(final InputStream in) throws IOException, SchemaParsingException {
		final ParsedElements result = new ParsedElements();

		CharStream input = null;
		input = new ANTLRInputStream(in, "UTF-8");
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

			if (!result.getErrorMessages().isEmpty() || parsed == null) {
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
