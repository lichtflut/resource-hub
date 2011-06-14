/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

import java.io.IOException;
import java.util.Collection;

import org.antlr.runtime.RecognitionException;
import org.arastreju.sge.model.ElementaryDataType;

import junit.framework.TestCase;
import de.lichtflut.rb.core.api.ResourceSchemaManagement;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;
import de.lichtflut.rb.core.spi.RBServiceProviderFactory;

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
	public final void testParsingAndConstructingModelFromTestSimpleRSFFile1() throws IOException, RecognitionException{		
		ResourceSchemaManagement rManagement = RBServiceProviderFactory.getDefaultServiceProvider().getResourceSchemaManagement();
		
		//Get ResourceSchemaTypes
		RSParsingResult result = rManagement.generateSchemaModelThrough(
				getClass().getClassLoader().getResourceAsStream("ResourceSchemaDSL1.rsf"));
		assertFalse(result.isErrorOccured());		
	}
	
	//---------------------------------------------------------------------------
	
	public final void testParsingAndConstructingModelFromTestOSFFile2() throws IOException, RecognitionException{		
		ResourceSchemaManagement rManagement = RBServiceProviderFactory.getDefaultServiceProvider().getResourceSchemaManagement();
		//Set parsing format to OSF
		rManagement.setFormat(RSFormat.OSF);
		//Get ResourceSchemaTypes
		RSParsingResult result = rManagement.generateAndResolveSchemaModelThrough(
				getClass().getClassLoader().getResourceAsStream("ResourceSchemaDSL2.osf"));
		assertFalse(result.isErrorOccured());	
		boolean pAssertion=false;
		for (ResourceSchema rs : result.getResourceSchemas()) {
			
			//--Search for a given property which must should exists on http://lichtflut.de#testResource2"
			//-----------------------------
			if(rs.getDescribedResourceID().getQualifiedName().toURI().equals("http://lichtflut.de#testResource2")){
				Collection<PropertyAssertion> assertions = rs.getPropertyAssertions();
				for (PropertyAssertion propertyAssertion : assertions) {
					if(propertyAssertion.getPropertyDeclaration().getIdentifier().getQualifiedName().toURI().equals("http://lichtflut.de#Date")){
						assertTrue(propertyAssertion.getPropertyDeclaration().getElementaryDataType() == ElementaryDataType.DATE);
						pAssertion=true;
					}
				}
			}
			//-----------------------------

		}
		//Check if the "http://lichtflut.de#Data"-Properties type of "http://lichtflut.de#testResource2" is Date
		assertTrue(pAssertion);
		assertTrue(result.getPropertyDeclarations().size() == 6);
	}
	
}
