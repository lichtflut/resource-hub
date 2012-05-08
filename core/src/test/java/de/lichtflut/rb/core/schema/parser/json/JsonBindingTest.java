/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.json;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import junit.framework.Assert;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.junit.Test;

import de.lichtflut.rb.core.schema.ConstraintsFactory;
import de.lichtflut.rb.core.schema.ResourceSchemaFactory;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.parser.OutputElements;
import de.lichtflut.rb.core.schema.parser.ParsedElements;
import de.lichtflut.rb.core.schema.parser.impl.json.JsonSchemaParser;
import de.lichtflut.rb.core.schema.parser.impl.json.JsonSchemaWriter;

/**
 * <p>
 *  Test Cases for JSon Ex/Importer.
 * </p>
 *
 * <p>
 * 	Created Oct 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class JsonBindingTest {
	
	private static final String NAMESPACE_URI = "http://lichtflut.de#";
	
	ResourceID HAS_EMAIL = new SimpleResourceID(NAMESPACE_URI, "hasEmail");

	ResourceID HAS_FORENAME = new SimpleResourceID(NAMESPACE_URI, "hasFirstname");

	ResourceID HAS_SURNAME = new SimpleResourceID(NAMESPACE_URI, "hasLastname");
	
	// -----------------------------------------------------
	
	@Test
	public void testSchemaExport() throws IOException {
		final JsonSchemaWriter exporter = new JsonSchemaWriter();

		final ResourceSchema personSchema = ResourceSchemaFactory.buildPersonSchema();
		
		final OutputElements elements = new OutputElements();
		elements.addSchemas(Collections.singletonList(personSchema));
		
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		exporter.write(out, elements);
		final byte[] bytes = out.toByteArray();
		
		final JsonSchemaParser importer = new JsonSchemaParser();
		importer.parse(new ByteArrayInputStream(bytes));
	}
	
	@Test
	public void testConstraintsExport() throws IOException {
		final JsonSchemaWriter exporter = new JsonSchemaWriter();

		
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		final OutputElements elements = new OutputElements();
		elements.addConstraint(Collections.singletonList(ConstraintsFactory.buildPublicEmailConstraint()));
		
		exporter.write(out, elements);
		exporter.write(out, elements);
		exporter.write(out, elements);
		
		final byte[] bytes = out.toByteArray();
		
		final JsonSchemaParser importer = new JsonSchemaParser();
		importer.parse(new ByteArrayInputStream(bytes));
	}
	
	@Test
	public void testSchemaImport() throws IOException {
		final InputStream in = 
				Thread.currentThread().getContextClassLoader().getResourceAsStream("test-schema-2.json");
		final JsonSchemaParser parser = new JsonSchemaParser();
		final ParsedElements result = parser.parse(in);
		Assert.assertEquals(5, result.getSchemas().size());
	}

}
