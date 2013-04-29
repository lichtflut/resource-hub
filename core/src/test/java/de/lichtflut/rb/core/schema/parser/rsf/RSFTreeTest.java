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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Locale;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.Tree;
import org.arastreju.sge.model.SimpleResourceID;
import org.junit.Ignore;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;
import de.lichtflut.rb.core.schema.parser.impl.rsf.RSFLexer;
import de.lichtflut.rb.core.schema.parser.impl.rsf.RSFParser;
import de.lichtflut.rb.core.schema.parser.impl.rsf.RSFTree;

/**
 * <p>
 *  Testcase for RSFTree Im-/Exporter
 * </p>
 *
 * <p>
 * 	Created Mar 26, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class RSFTreeTest {

	private final String namespace = "http://rb.lichtflut.de/common#";

	@Test
	public void testRSFTree() throws RecognitionException {
		RSParsingResult elements = extractElements(getRSFString());
		assertThat(elements.getResourceSchemas().size(), is(2));
		assertThat(elements.getPublicConstraints().size(), is(2));
		ResourceSchema city = elements.getResourceSchemas().get(0);
		assertTrue(new SimpleResourceID(namespace, "City").equals(city.getDescribedType()));
		assertTrue("http://rb.lichtflut.de/common#hasName <,> http://rb.lichtflut.de/common#hasCountry".equals(city.getLabelBuilder().getExpression()));
		assertThat(city.getPropertyDeclarations().size(), is(4));
		assertThat(city.getQuickInfo().size(), is(3));
		PropertyDeclaration pdec = city.getPropertyDeclarations().get(0);
		assertEquals(new SimpleResourceID("http://rb.lichtflut.de/common#hasMayor"), pdec.getPropertyDescriptor());
		assertEquals(1000, pdec.getCardinality().getMinOccurs());
		assertEquals(Integer.MAX_VALUE, pdec.getCardinality().getMaxOccurs());
		assertEquals("Mayor", pdec.getFieldLabelDefinition().getDefaultLabel());
		assertEquals("Buergermeister", pdec.getFieldLabelDefinition().getLabel(Locale.GERMAN));
		assertEquals(Datatype.RESOURCE, pdec.getDatatype());
		assertThat(pdec.getConstraint().isLiteral(), is(false));
	}

	@Test
	@Ignore("Monitor AST while developing...")
	public void testTree() throws RecognitionException{
		CommonTree t = (CommonTree) createTree(getRSFString());
		System.out.println(t.toStringTree());
	}

	// ------------------------------------------------------

	private RSParsingResult extractElements(final String decl) throws RecognitionException {
		// Create an input character stream from standard in
		CharStream input = new ANTLRStringStream(decl);
		// Create an ExprLexer that feeds from that stream
		RSFLexer lexer = new RSFLexer(input);
		// Create a stream of tokens fed by the lexer
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		// Create a parser that feeds off the token stream
		RSFParser parser = new RSFParser(tokens);
		RSFParser.statements_return r = parser.statements();
		CommonTreeNodeStream nodes = new CommonTreeNodeStream(r.getTree());
		nodes.setTokenStream(tokens);
		RSFTree walker = new RSFTree(nodes);
		RSParsingResult elements = walker.statements();
		return elements;
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws RecognitionException
	 */
	public Tree createTree(final String string) throws RecognitionException {
		// Create an input character stream from standard in
		CharStream input = null;
		input = new ANTLRStringStream(string);
		// Create an ExprLexer that feeds from that stream
		RSFLexer lexer = new RSFLexer(input);
		// Create a stream of tokens fed by the lexer
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		// Create a parser that feeds off the token stream
		RSFParser parser = new RSFParser(tokens);
		RSFParser.statements_return r = parser.statements();

		return (CommonTree) r.getTree();
	}

	private String getRSFString(){
		return 	getNamespaces() +
				getPublicConstraintDeclaration() +
				getSchemas();
	}

	private String getNamespaces() {
		String namespaces = "namespace \"http://rb.lichtflut.de/common#\" prefix \"common\"" +
				"namespace \"http://rb.lichtflut.de/common2#\" prefix \"common2\"";
		return namespaces;
	}

	private String getPublicConstraintDeclaration() {
		String constraint = "constraint definition for \"common:EmailConstraint\"{" +
				"name : \"Email-Constraint\"\n" +
				" applicable-datatypes : \"string, text\"\n" +
				" literal-constraint : \".*@.*\"\n" +
				"}" +
				"constraint definition for \"common:URL\" {" +
				"name : \"URL-Constraint-Simple\"" +
				"applicable-datatypes : \"text\"" +
				"literal-constraint : \"http://\"" +
				"}";
		return constraint;
	}

	private String getSchemas(){
		return 	"schema for \"common:City\" { " +
				"label-rule : \"common:hasName <,> common:hasCountry\"" +
				"" +
				"quick-info {" +
				"common:hasMayor," +
				"common:hasPhonebooth," +
				"common:hasClub," +
				"common:invalidProperty" +
				"}" +
				"property \"common:hasMayor\" [1000..xs] {" +
				"field-label : \"Mayor\"" +
				"field-label[de] : \"Buergermeister\"" +
				"field-label[fr] : \"Maire\"" +
				"datatype : \"resource\"" +
				"resource-constraint : \"common:Person\"" +
				"}" +
				"" +
				"property \"common:hasCountry\" [1..1] {" +
				"field-label[en] : \"Name\"" +
				"datatype : \"String\"" +
				"resource-constraint : \"common:Country\"" +
				"}" +
				"property \"common:hasClub\" [1..1] {" +
				"field-label[en] : \"Name\"" +
				"datatype : \"String\"" +
				"resource-constraint : \"common:Country\"" +
				"}" +
				"property \"common:hasPhonebooth\" [1..1] {" +
				"field-label[en] : \"Name\"" +
				"datatype : \"String\"" +
				"resource-constraint : \"common:Country\"" +
				"}" +
				"}" +
				"schema for \"common:Person\" { " +
				"label-rule : \"common:hasName\"" +
				"property \"common:hasName\" [1..n] {" +
				"field-label[en] : \"Name\"" +
				"datatype : \"String\"" +
				"}" +
				"property \"common:hasEmail\" [0..n] {" +
				"field-label : \"E-Mail\"" +
				"datatype : \"string\"" +
				"reference-constraint : \"common:Email-Constraint\"" +
				"}" +
				"}";

	}
}
