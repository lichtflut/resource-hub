/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl.json;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;


import org.junit.Before;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.parser.OutputElements;
import de.lichtflut.rb.core.schema.parser.ParsedElements;
import de.lichtflut.rb.mock.schema.ConstraintsFactory;
import de.lichtflut.rb.mock.schema.ResourceSchemaFactory;

/**
 * <p>
 * Testclass for {@link JsonSchemaWriter}.
 * </p>
 * Created: May 8, 2012
 *
 * @author Ravi Knox
 */
public class JsonSchemaWriterTest {

	private JsonSchemaWriter exporter;
	private ByteArrayOutputStream out;
	private OutputElements elements;

	@Before
	public void setUp(){
		 exporter = new JsonSchemaWriter();
		 out = new ByteArrayOutputStream();
		 elements = new OutputElements();
	}
	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.parser.impl.json.JsonSchemaWriter#write(java.io.OutputStream, de.lichtflut.rb.core.schema.parser.OutputElements)}.
	 */
	@Test
	public void testWriteOutputStreamOutputElements() {
//		fail("Not yet implemented");
	}

	@Test
	public void testWritePublicConstraints() throws IOException {
		final Constraint emailConstraint = ConstraintsFactory.buildPublicEmailConstraint();
		
		elements.addConstraint(Collections.singletonList(emailConstraint));
		exporter.write(out, elements);
		
		final byte[] bytes = out.toByteArray();
		
		final JsonSchemaParser importer = new JsonSchemaParser();
		ParsedElements parsedElements = importer.parse(new ByteArrayInputStream(bytes));
		
		Constraint parsedConstraint = parsedElements.getConstraints().get(0);
		assertThat(parsedElements.getConstraints().size(), is(1));
		assertThat(parsedConstraint.getID(), equalTo(emailConstraint.getID()));
		assertThat(parsedConstraint.getApplicableDatatypes(), equalTo(emailConstraint.getApplicableDatatypes()));
		assertThat(parsedConstraint.getLiteralConstraint(), equalTo(parsedConstraint.getLiteralConstraint()));
		assertThat(parsedConstraint.getResourceConstraint(), nullValue());
		assertThat(parsedConstraint, equalTo(emailConstraint));
	}

	@Test
	public void testWriteSchemas() throws IOException {
		ResourceSchema schema = ResourceSchemaFactory.buildPersonSchema();
		
		elements.addSchemas(Collections.singletonList(schema));
		exporter.write(out, elements);
		
		final byte[] bytes = out.toByteArray();
		
		final JsonSchemaParser importer = new JsonSchemaParser();
		ParsedElements parsedElements = importer.parse(new ByteArrayInputStream(bytes));
		
		assertThat(parsedElements.getSchemas().size(), is(1));
		
		ResourceSchema parsedSchema = parsedElements.getSchemas().get(0);
		assertThat(parsedSchema.getDescribedType(), equalTo(schema.getDescribedType()));
		assertThat(parsedSchema.getLabelBuilder().getExpression(), equalTo(schema.getLabelBuilder().getExpression()));
		assertThat(parsedSchema.getPropertyDeclarations().size(), is(schema.getPropertyDeclarations().size()));
		assertPropertyDeclaration(parsedSchema.getPropertyDeclarations().get(5), schema.getPropertyDeclarations().get(5));
	}
	/**
	 * @param propertyDeclaration
	 */
	private void assertPropertyDeclaration(PropertyDeclaration propertyDeclaration,PropertyDeclaration original) {
		assertThat(propertyDeclaration.getCardinality().getMinOccurs(), is(original.getCardinality().getMinOccurs()));
		assertThat(propertyDeclaration.getCardinality().getMaxOccurs(), is(original.getCardinality().getMaxOccurs()));
		assertThat(propertyDeclaration.getConstraint().getResourceConstraint(), equalTo(original.getConstraint().getResourceConstraint()));
		assertThat(propertyDeclaration.getFieldLabelDefinition().getDefaultLabel(), equalTo(original.getFieldLabelDefinition().getDefaultLabel()));
		assertThat(propertyDeclaration.getPropertyDescriptor().toURI(), equalTo(original.getPropertyDescriptor().toURI()));
	}
	
}
