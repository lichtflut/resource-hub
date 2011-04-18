/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core;

import java.util.List;
import java.util.Set;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;

import de.lichtflut.rb.core.schema.model.ResourceSchemaType;
import de.lichtflut.rb.core.schema.parser.impl.ResourceSchemaLexer;
import de.lichtflut.rb.core.schema.parser.impl.ResourceSchemaParser;
import de.lichtflut.rb.core.schema.parser.impl.ResourceSchemaParser.dsl_return;


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
		String txt = //"resource PETER_PAN( " +
					//					" has 1 and hasMax 3 emails" +
					//		            " has 1 name" +
					//		            " \n)" +
					 "property EMAIL(" +
					 				" type is TEXT\n" +
					 				")" +
		 "property NAME(" +
			" regex \" PAUL \"\n" +
			")";
		
		
		
		CharStream stream = new ANTLRStringStream(txt);
		ResourceSchemaLexer lexer = new ResourceSchemaLexer(stream);
		TokenStream tokens = new CommonTokenStream(lexer);
		ResourceSchemaParser parser = new ResourceSchemaParser(tokens);
				
		try {
		 dsl_return ret_val = parser.dsl();
		 
		 Set<ResourceSchemaType> types = ret_val.types;
		 for (ResourceSchemaType resourceSchemaType : types) {
			System.out.println(resourceSchemaType.toString());
		}
		 
		 
		} catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
