/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

import java.io.IOException;
import org.antlr.runtime.RecognitionException;
import junit.framework.TestCase;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;
import de.lichtflut.rb.core.spi.RBServiceProviderFactory;
import de.lichtflut.rb.core.spi.ResourceSchemaManagement;

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
	
	public final void testParsingAndConstructingModelFromTestFile1() throws IOException, RecognitionException{
		ResourceSchemaManagement rManagement = RBServiceProviderFactory.getDefaultServiceProvider().getResourceSchemaManagement();
		
		//Get ResourceSchemaTypes
		RSParsingResult result = rManagement.generateSchemaModelThrough(
				getClass().getClassLoader().getResourceAsStream("ResourceSchemaDSL1.dsl"));
		assertFalse(result.isErrorOccured());		
		//Iterate over collection and print out 'da' model
		for (ResourceSchema resource : result.getResourceSchemas())
				System.out.println("--------------------------\n"+	resource.toString());
		for (PropertyDeclaration property : result.getPropertyDeclarations())
			System.out.println("--------------------------\n"+	property.toString());
	
	}
}
