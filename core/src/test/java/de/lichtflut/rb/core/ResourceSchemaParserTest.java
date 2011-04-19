/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import junit.framework.TestCase;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import de.lichtflut.rb.core.schema.model.ResourceSchemaType;
import de.lichtflut.rb.core.schema.parser.impl.RBCaseControlStream;
import de.lichtflut.rb.core.schema.parser.impl.ResourceSchemaLexer;
import de.lichtflut.rb.core.schema.parser.impl.ResourceSchemaParser;
import de.lichtflut.rb.core.schema.parser.impl.ResourceSchemaParser.dsl_return;

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
	
	public void testParser1() throws IOException{
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(getClass().getClassLoader().getResourceAsStream("ResourceSchemaDSL1.dsl")));
		StringBuffer input = new StringBuffer();
		String line;
		while((line = reader.readLine())!=null) input.append(line);
		RBCaseControlStream stream = new RBCaseControlStream(input.toString());
		stream.setCaseSensitive(false);
		ResourceSchemaLexer lexer = new ResourceSchemaLexer(stream);
		TokenStream tokens = new CommonTokenStream(lexer);
		ResourceSchemaParser parser = new ResourceSchemaParser(tokens);
				
		try {
		 dsl_return ret_val = parser.dsl();
		 Set<ResourceSchemaType> types = ret_val.types;
		 for (ResourceSchemaType resourceSchemaType : types) {
			if(resourceSchemaType != null) System.out.println(resourceSchemaType.toString());
		}
		 
		 
		} catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
