/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.services.SchemaImporter;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.impl.AbstractServiceProvider;
import de.lichtflut.rb.core.services.impl.DefaultRBServiceProvider;

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
public class ResourceSchemaParserTest {

	private AbstractServiceProvider serviceProvider;

	// -----------------------------------------------------
	
	@Before
	public void setUp() {
		serviceProvider = new DefaultRBServiceProvider(new RBConfig());
	}
	
	// ----------------------------------------------------
	
	@Test
	public void testJsonImport() throws IOException {
		final InputStream in = 
				getClass().getClassLoader().getResourceAsStream("test-schema.json");
		
		final SchemaManager manager = serviceProvider.getSchemaManager();
		final SchemaImporter importer = manager.getImporter("json");
		importer.read(in);
		
		Collection<ResourceSchema> schemas = manager.findAllResourceSchemas();
		System.out.println(schemas);
		
		Assert.assertEquals(5, manager.findAllResourceSchemas().size());
		
		Assert.assertEquals(1, manager.findPublicTypeDefinitions().size());
	}
	
	@Ignore
	public void testRsfImport() throws IOException {
		final InputStream in = 
				getClass().getClassLoader().getResourceAsStream("test-schema.rsf");
		
		final SchemaManager manager = serviceProvider.getSchemaManager();
		final SchemaImporter importer = manager.getImporter("rsf");
		importer.read(in);
		
		Collection<ResourceSchema> schemas = manager.findAllResourceSchemas();
		System.out.println(schemas);
		
		Assert.assertEquals(5, manager.findAllResourceSchemas().size());
		
		Assert.assertEquals(1, manager.findPublicTypeDefinitions().size());
	}
	

}
