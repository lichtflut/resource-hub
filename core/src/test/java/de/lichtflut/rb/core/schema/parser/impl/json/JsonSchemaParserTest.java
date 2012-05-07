/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl.json;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import de.lichtflut.rb.core.schema.parser.ParsedElements;

/**
 * <p>
 * Testclass for {@link JsonSchemaParser}.
 * </p>
 * Created: May 7, 2012
 * 
 * @author Ravi Knox
 */
public class JsonSchemaParserTest {

	private JsonSchemaParser parser;

	@Before
	public void setUp(){
		parser = new JsonSchemaParser();
	}
	
	/**
	 * Test method for
	 * {@link de.lichtflut.rb.core.schema.parser.impl.json.JsonSchemaParser#JsonSchemaParser()}
	 * .
	 */
	@Test
	public void testJsonSchemaParser() {
		assertNotNull("Can not instantiate JSONSchemaParser.", parser);
	}

	/**
	 * Test method for
	 * {@link de.lichtflut.rb.core.schema.parser.impl.json.JsonSchemaParser#parse(java.io.InputStream)}
	 * .
	 * @throws IOException 
	 */
	@Test
	public void testParse() throws IOException {
		final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("test-schema-2.json");
		ParsedElements elements = parser.parse(in);
		System.out.println(elements.getSchemas().get(0));
//		assertTrue("Number of publicconstraints is not as expected", 1 == elements.getConstraints().size());
	}

	/**
	 * Test method for
	 * {@link de.lichtflut.rb.core.schema.parser.impl.json.JsonSchemaParser#toResourceID(java.lang.String)}
	 * .
	 */
	@Test
	public void testToResourceID() {
	}

}
