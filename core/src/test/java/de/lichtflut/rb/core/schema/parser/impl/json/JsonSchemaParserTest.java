/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl.json;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.junit.Before;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
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
	private InputStream in;

	// ------------------------------------------------------
	
	@Before
	public void setUp(){
		in = Thread.currentThread().getContextClassLoader().getResourceAsStream("test-schema-basic.json");
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
		ParsedElements elements = parser.parse(in);
		assertEquals("Number of public-constraints is not as expected", 2, elements.getConstraints().size());
		assertEquals("Number of ResourceSChemas not as expected.", 1, elements.getSchemas().size());
		assertSchema(elements.getSchemas().get(0));
		assertConstraints(elements.getConstraints());
	}

	// ------------------------------------------------------
	
	private void assertConstraints(List<Constraint> list) {
		assertLiteralConstraint(list.get(0));
		assertResourceConstraint(list.get(1));
	}

	private void assertLiteralConstraint(Constraint constraint) {
		String literalConstr = ".*@.*";
		String name = "Email-Constraint";
		
		assertTrue("Constraint is not public.", constraint.isPublic());
		assertThat(constraint.getApplicableDatatypes(), notNullValue());
		assertThat(constraint.getApplicableDatatypes(), not(Collections.EMPTY_LIST));
		assertThat(constraint.getApplicableDatatypes().size(), is(3));
		assertThat(constraint.getName(), equalTo(name));
		assertThat(constraint.getLiteralConstraint(), equalTo(literalConstr));
		assertThat(constraint.getReference(), nullValue());
		assertThat(constraint.isPublic(), is(Boolean.TRUE));
	}
	
	private void assertResourceConstraint(Constraint constraint) {
		ResourceID resourceConstr = new SimpleResourceID("http://test.com/common#Person");
		String name = "Person-Constraint";
		
		assertThat(constraint.isPublic(), is(Boolean.TRUE));
		assertThat(constraint.getReference(), notNullValue());
		assertThat(constraint.getApplicableDatatypes(), notNullValue());
		assertThat(constraint.getName(), equalTo(resourceConstr.getQualifiedName().toURI()));
		assertThat(constraint.getReference(), equalTo(resourceConstr));
		assertThat(constraint.getLiteralConstraint(), equalTo(""));
		assertThat(constraint.isPublic(), is(Boolean.TRUE));
	}

	private void assertSchema(ResourceSchema schema) {
		ResourceID describedType = new SimpleResourceID("http://test.com/common#City");

		String labelExpression = "http://test.com/common#hasName <(> http://test.com/common#isInCountry <)>";
		assertThat(schema.getDescribedType(), equalTo(describedType));
		assertThat(schema.getLabelBuilder().getExpression(), equalTo(labelExpression));
		assertThat(schema.getPropertyDeclarations().size(), is(2));
		assertPropertyDeclaration(schema.getPropertyDeclarations().get(1));
	}

	private void assertPropertyDeclaration(PropertyDeclaration propertyDeclaration) {
		String literalConstr = "[a-z || A-Z]";
		String fieldLabelDef = "Country";
		String propertyDescriptor = "http://test.com/common#isInCountry";
		
		assertThat(propertyDeclaration.getCardinality().getMinOccurs(), is(1));
		assertThat(propertyDeclaration.getCardinality().getMaxOccurs(), is(1));
		assertThat(propertyDeclaration.getConstraint().getLiteralConstraint(), equalTo(literalConstr));
		assertThat(propertyDeclaration.getFieldLabelDefinition().getDefaultLabel(), equalTo(fieldLabelDef));
		assertThat(propertyDeclaration.getPropertyDescriptor().toURI(), equalTo(propertyDescriptor));
	}
}
