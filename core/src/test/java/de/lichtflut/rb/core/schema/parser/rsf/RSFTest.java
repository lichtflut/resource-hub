/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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
public class RSFTest {

	private String decl = 	"namespace \"http://rb.lichtflut.de/common#\" prefix \"common\"" +
							"namespace \"http://rb.lichtflut.de/common2#\" prefix \"common2\"" +
							"" +
							"schema for \"commonCity\" { " +
									"label-rule : \"common:hasName common:hasCountry\"" +
										"property \"common:assignedTo\" [1..n] {" +
										"field-label[klingonian] : \"Kaaargh\"" +
										"type-definition : \"date\"" +
										"}" +
									"}";

	@Test
	public void readNamespace() throws RecognitionException{
		String namespaces = "namespace \"http://rb.lichtflut.de/common#\" prefix \"common\"";
		RSFParser parser = createParser(namespaces );
		parser.namespace_decl();
	}
	
	@Test
	public void  readLabelDecl() throws RecognitionException{
		String label = "label-rule : \"common:hasName common:hasCountry\"";
		RSFParser parser = createParser(label);
		parser.label_decl();
	}
	
	@Test
	public void readCardinalDecl() throws RecognitionException{
		String cardinality = "[1..n]";
		RSFParser parser = createParser(cardinality);
		parser.cardinal_decl();
	}
	
	@Test
	public void readKey() throws RecognitionException{
		String key = "type-definition";
		RSFParser parser = createParser(key);
		parser.key();
	}
	
	@Test
	public void readValue() throws RecognitionException{
		String value = "\"assigned to\"";
		RSFParser parser = createParser(value);
		parser.value();
	}
	
	@Test
	public void readAssigment() throws RecognitionException{
		String assigment = "field-label[de] : \"gehoert zu\"";
		RSFParser parser = createParser(assigment);
		parser.assigment();
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
	public void readMultipleePropertyDecl() throws RecognitionException{
		String property = "property \"common:assignedTo\" [1..n] {" +
				"field-label[klingonian] : \"Kaaargh\"" +
				"type-definition : \"date\"" +
				"}";
		RSFParser parser = createParser(property);
		parser.property_decl();
	}
	
	@Test
	public void readSchemaDecl() throws RecognitionException{
		String schema = "schema for \"commonCity\" { " +
				"label-rule : \"common:hasName common:hasCountry\"" +
					"property \"common:assignedTo\" [1..n] {" +
					"field-label[klingonian] : \"Kaaargh\"" +
					"type-definition : \"date\"" +
					"}" +
				"}";
		RSFParser parser = createParser(schema);
		parser.schema_decl();
		
	}
	@Test
	public void readRSF() throws RecognitionException{
		RSFParser parser = createParser(decl );
		parser.statements();
	}
	/**
	 * @param args
	 * @throws IOException
	 * @throws RecognitionException
	 */
	public RSFParser createParser(String string) {
		// Create an input character stream from standard in
		CharStream input = null;
		input = new ANTLRStringStream(string);
		// Create an ExprLexer that feeds from that stream
		RSFLexer lexer = new RSFLexer(input);
		// Create a stream of tokens fed by the lexer
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		// Create a parser that feeds off the token stream
		RSFParser parser = new RSFParser(tokens);
		return parser;

	}

}