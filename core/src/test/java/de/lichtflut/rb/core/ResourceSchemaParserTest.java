/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.junit.Test;

import de.lichtflut.rb.core.schema.parser.ResourceSchemaLexer;
import de.lichtflut.rb.core.schema.parser.ResourceSchemaParser;
import junit.framework.TestCase;


/**
 * <p>
 *  Some tests to proof and specify the ResourceSchemaParser.
 * </p>
 * 
 *  <p>
 * 	 Created Apr. 14, 2011
 *  </p>
 *
 * @author Nils Bleisch
 * 
 */
public class ResourceSchemaParserTest extends TestCase
{
	
	public void testParser1(){
		CharStream stream = new ANTLRStringStream("property hans ()");
		ResourceSchemaLexer lexer = new ResourceSchemaLexer(stream);
		TokenStream tokens = new CommonTokenStream(lexer);
		ResourceSchemaParser parser = new ResourceSchemaParser(tokens);
		try {
			parser.property();
		} catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
