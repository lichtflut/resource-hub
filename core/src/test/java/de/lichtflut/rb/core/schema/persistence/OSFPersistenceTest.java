/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.persistence;

import java.util.Collection;

import junit.framework.TestCase;

import org.junit.Test;

import de.lichtflut.rb.core.api.ResourceSchemaManagement;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.parser.RSFormat;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.core.spi.RBServiceProviderFactory;

/**
 * <p>
 *  Test case for storing and reading of Resource Schema Models to/from Arastreju.
 * </p>
 *
 * <p>
 * 	Created Apr 21, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class OSFPersistenceTest extends TestCase{
	

	@Test
	public void testStoringAndParsingOSF() {
		
		RBServiceProvider provider = RBServiceProviderFactory.getDefaultServiceProvider();
		ResourceSchemaManagement rManagement = provider.getResourceSchemaManagement();
		//Set parsing format to OSF
		rManagement.setFormat(RSFormat.OSF);
		//Get ResourceSchemaTypes
		RSParsingResult result = rManagement.generateAndResolveSchemaModelThrough(
				getClass().getClassLoader().getResourceAsStream("ResourceSchemaDSL3.osf"));
		assertFalse(result.isErrorOccured());		
		
		//spec
		int cnt_resources = 1;
		int cnt_property_decs= 7;
		
		//Verify the pasring result
		assertEquals(result.getPropertyDeclarations().size(), cnt_property_decs);
		assertEquals(result.getResourceSchemas().size(), cnt_resources);
		
		//Store the schema
		assertEquals(rManagement.getAllResourceSchemas().size(),0);
		
		rManagement.storeOrOverrideResourceSchema(result.getResourceSchemas());
		rManagement.storeOrOverridePropertyDeclaration(result.getPropertyDeclarations());
		
		//Verify the store's content
		assertEquals(rManagement.getAllResourceSchemas().size(),cnt_resources);
		//assertEquals(rManagement.getAllPropertyDeclarations().size(),cnt_property_decs);
		
		Collection<PropertyDeclaration> decs = rManagement.getAllPropertyDeclarations();
		for (PropertyDeclaration dec : decs) {
			System.out.println(dec.toString());
			
		}
		
		
		
		
	}
	
	// -----------------------------------------------------
	

	
}
