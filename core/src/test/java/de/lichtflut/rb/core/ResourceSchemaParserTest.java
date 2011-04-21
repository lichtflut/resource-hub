/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core;

import java.io.IOException;
import java.util.Set;

import org.antlr.runtime.RecognitionException;

import junit.framework.TestCase;
import de.lichtflut.rb.core.schema.model.ResourceSchemaType;
import de.lichtflut.rb.core.spi.ResourceSchemaManagement;
import de.lichtflut.rb.core.spi.impl.ResourceSchemaManagementImpl;

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
	
	public void testParsingAndConstructingModelFromTestFile1() throws IOException, RecognitionException{
		ResourceSchemaManagement rManagement = new ResourceSchemaManagementImpl();
		//Get ResourceSchemaTypes
		Set<ResourceSchemaType> types = rManagement.generateSchemaModelThrough(
				getClass().getClassLoader().getResourceAsStream("ResourceSchemaDSL1.dsl"));
		//Iterate over collection and print out 'da' model
		for (ResourceSchemaType resourceSchemaType : types) {
			if(resourceSchemaType != null){ 
				System.out.println("--------------------------\n"+	resourceSchemaType.toString());
			}
		}//end of for
	}
}
