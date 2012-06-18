/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.rsf;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.Tree;
import org.arastreju.sge.model.SimpleResourceID;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
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
	private final String decl = 	"namespace \"" + namespace + "\" prefix \"common\"" +
							"namespace \"http://rb.lichtflut.de/common2#\" prefix \"common2\"" +
							"" +
							"schema for \"common:City\" { " +
									"label-rule : \"common:hasName <,> common:hasCountry\"" +
									"" +
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
										"}" +
							"schema for \"common:Person\" { " +
									"label-rule : \"common:hasName\"" +
										"property \"common:hasName\" [1..n] {" +
											"field-label[en] : \"Name\"" +
											"datatype : \"String\"" +
										"}" +
									"}";
	
	@Test
	public void testRSFTree() throws RecognitionException {
		List<ResourceSchemaImpl> list = extractSchemas(decl);
		assertTrue((2 == list.size() ));
		ResourceSchema city = list.get(0);
		assertTrue(new SimpleResourceID(namespace, "City").equals(city.getDescribedType()));
		assertTrue("http://rb.lichtflut.de/common#hasName <,> http://rb.lichtflut.de/common#hasCountry".equals(city.getLabelBuilder().getExpression()));
		assertTrue(2 == city.getPropertyDeclarations().size());
		PropertyDeclaration pdec = city.getPropertyDeclarations().get(0);
		assertEquals(new SimpleResourceID("http://rb.lichtflut.de/common#hasMayor"), pdec.getPropertyDescriptor());
		assertEquals(1000, pdec.getCardinality().getMinOccurs());
		assertEquals(Integer.MAX_VALUE, pdec.getCardinality().getMaxOccurs());
		assertEquals("Mayor", pdec.getFieldLabelDefinition().getDefaultLabel());
		assertEquals("Buergermeister", pdec.getFieldLabelDefinition().getLabel(Locale.GERMAN));
		assertEquals(Datatype.RESOURCE, pdec.getDatatype());
		assertThat(pdec.getConstraint().isLiteral(), is(false));
	}

	protected List<ResourceSchemaImpl> extractSchemas(String decl) throws RecognitionException {
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
		List<ResourceSchemaImpl> list = walker.statements();
		return list;
	}
	
	@Test
	public void testTree() throws RecognitionException{
		CommonTree t = (CommonTree) createTree(decl);
		System.out.println(t.toStringTree());
	}
	
	/**
	 * @param args
	 * @throws IOException
	 * @throws RecognitionException
	 */
	public Tree createTree(String string) throws RecognitionException {
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
}
