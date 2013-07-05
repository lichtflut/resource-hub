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
package de.lichtflut.rb.core.schema.parser.rsf;

import java.io.IOException;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;

import de.lichtflut.rb.core.schema.parser.impl.rsf.RSFLexer;
import de.lichtflut.rb.core.schema.parser.impl.rsf.RSFParser;
/**
 * <p>
 * This Test is primarily for monitoring System.err while developing
 * </p>
 * 
 * <p>
 * Created Mar 23, 2012
 * </p>
 * 
 * @author Ravi Knox
 */
public class RSFParserTest {

	@Test
	public void readNamespace() throws RecognitionException{
		RSFParser parser = createParser(getNamespaces() );
		parser.namespace_decl();
	}

	@Test
	public void readPublicConstraintDefinition() throws RecognitionException{
		RSFParser parser = createParser(getPublicConstraintDeclaration());
		parser.public_constraint();
	}

	@Test
	public void  readLabelDecl() throws RecognitionException{
		String label = "label-rule : \"common:hasName <(> common:hasCountry\"";
		RSFParser parser = createParser(label);
		parser.label_decl();
	}

	@Test
	public void readQuickinfo() throws RecognitionException{
		String qInfo = "quick-info {" +
				"common:hasMayor," +
				"common:hasCountry" +
				"}";
		RSFParser parser = createParser(qInfo);
		parser.quick_info();
	}

	@Test
	public void readCardinalDecl() throws RecognitionException{
		String cardinality = "[1..n]";
		RSFParser parser = createParser(cardinality);
		parser.cardinal_decl();
	}

	@Test
	public void readKey() throws RecognitionException{
		String key = "datatype";
		RSFParser parser = createParser(key);
		parser.property_key();
	}

	@Test
	public void readValue() throws RecognitionException{
		String value = "\"assigned to\"";
		RSFParser parser = createParser(value);
		parser.value();
	}

	@Test
	public void readAssigment() throws RecognitionException{
		String assignment = "field-label[de] : \"gehoert zu\"";
		RSFParser parser = createParser(assignment);
		parser.assignment();
	}

	@Test
	public void readSinglePropertyDecl() throws RecognitionException{
		String property = "property \"common:assignedTo\" [1..n] {" +
				"field-label[klingonian] : \"Kaaargh\"" +
				"}";
		RSFParser parser = createParser(property);
		parser.property_decl();
	}

	@Test
	public void readMultiplePropertyDecl() throws RecognitionException{
		String property = "property \"common:assignedTo\" [1..n] {" +
				"field-label[klingonian] : \"Kaaargh\"" +
				"datatype : \"date\"" +
				"resource-constraint : \"http://lichtflut.de/common/Person\"" +
				"literal-constraint : \".*@.*\"" +
				"reference-constraint : \"http://lichtflut.de/constraint/Person\"" +
				"}";

		createParser(property).property_decl();
	}

	@Test
	public void readVisualizationInfo() throws RecognitionException{
		String schema = "visualize { " +
				"floating : \"yes\"" +
				"style : \"width:80px\"" +
				"}";
		RSFParser parser = createParser(schema);
		parser.visualization();
	}

	@Test
	public void readSchemaDecl() throws RecognitionException{
		String schema = "schema for \"commonCity\" { " +
				"label-rule : \"common:hasName common:hasCountry\"" +
				"quick-info {" +
				"common:hasName, " +
				"common:hasCountry, " +
				"common:hasID" +
				"}" +
				"property \"common:assignedTo\" [1..n] {" +
				"field-label[klingonian] : \"Kaaargh\"" +
				"datatype : \"date\"" +
				"}" +
				"}";
		RSFParser parser = createParser(schema);
		parser.schema_decl();
	}

	@Test
	public void readRSF() throws RecognitionException{
		RSFParser parser = createParser(getRSFString() );
		parser.statements();
	}

	/**
	 * @param string The input string
	 * @throws IOException
	 * @throws RecognitionException
	 */
	public RSFParser createParser(final String string) {
		// Create an input character stream from standard in
		CharStream input = null;
		input = new ANTLRStringStream(string);
		// Create an ExprLexer that feeds from that stream
		RSFLexer lexer = new RSFLexer(input);
		// Create a stream of tokens fed by the lexer
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		// Create a reader that feeds off the token stream
		RSFParser parser = new RSFParser(tokens);
		return parser;

	}

	// ------------------------------------------------------

	private String getRSFString(){
		return 	getNamespaces() +
				"" +
				getPublicConstraintDeclaration() +
				"schema for \"commonCity\" { " +
				"label-rule : \"common:hasName <(> common:hasCountry\"" +
				"quick-info{" +
				"common:assignedTo," +
				"common:hasName" +
				"}" +
				"property \"common:assignedTo\" [1..n] {" +
				"field-label[de] : \"Zugeordnet\"" +
				"datatype : \"date\"" +
				"literal-constraint : \".*@.*\"" +
				"}" +
				"}";
	}

	private String getNamespaces() {
		String namespaces = "namespace \"http://rb.lichtflut.de/common#\" prefix \"common\"" +
				"namespace \"http://rb.lichtflut.de/common2#\" prefix \"common2\"";
		return namespaces;
	}

	private String getPublicConstraintDeclaration() {
		String constraint = "constraint definition for \"common:EmailConstraint\" {" +
				"name : \"Email-Constraint\"" +
				"applicable-datatypes : \"string, text\"" +
				"literal-constraint : \".*@.*\"" +
				"}}";
		return constraint;
	}

}
