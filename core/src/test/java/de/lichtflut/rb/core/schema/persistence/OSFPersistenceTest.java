/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.persistence;


import junit.framework.Assert;

import org.arastreju.sge.model.nodes.ResourceNode;
import org.junit.Test;

import de.lichtflut.rb.core.api.ResourceSchemaManagement;
import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.parser.RSFormat;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;
import de.lichtflut.rb.core.spi.INewRBServiceProvider;
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
public class OSFPersistenceTest {

	/**
	 *
	 */
	@Test
	public void testStoringAndParsingOSF() {

		INewRBServiceProvider provider = RBServiceProviderFactory.getDefaultServiceProvider();
		ResourceSchemaManagement rManagement = provider.getResourceSchemaManagement();
		//Set parsing format to OSF
		rManagement.setFormat(RSFormat.OSF);
		//Get ResourceSchemaTypes
		RSParsingResult result = rManagement.generateAndResolveSchemaModelThrough(
				getClass().getClassLoader().getResourceAsStream("ResourceSchemaDSL3.osf"));
		Assert.assertFalse(result.isErrorOccured());

		//spec
		int cnt_resources = 1;
		int cnt_property_decs= 7;

		//Verify the pasring result
		Assert.assertEquals(result.getPropertyDeclarations().size(), cnt_property_decs);
		Assert.assertEquals(result.getResourceSchemas().size(), cnt_resources);

		//Store the schema
		Assert.assertEquals(rManagement.getAllResourceSchemas().size(),0);
		rManagement.storeOrOverrideResourceSchema(result.getResourceSchemas());

		@SuppressWarnings("unused")
		ResourceNode ctxResource = provider.getArastejuGateInstance().startConversation().
		findResource(RBSchema.CONTEXT.getQualifiedName());

		rManagement.storeOrOverridePropertyDeclaration(result.getPropertyDeclarations());

		ctxResource = provider.getArastejuGateInstance().startConversation().
		findResource(RBSchema.CONTEXT.getQualifiedName());

		rManagement.storeOrOverrideResourceSchema(result.getResourceSchemas());
		rManagement.storeOrOverridePropertyDeclaration(result.getPropertyDeclarations());

		//TODO Store of PropertyDeclaration builds some duplicates

		//Verify the store's content
		Assert.assertEquals(rManagement.getAllResourceSchemas().size(),cnt_resources);
		Assert.assertEquals(rManagement.getAllPropertyDeclarations().size(),(cnt_property_decs));

		//Just trigger an exception
		rManagement.getAllPropertyDeclarations();

	}

	// -----------------------------------------------------



}
