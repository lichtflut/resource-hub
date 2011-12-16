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

import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionImpl;
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

		final TypeDefinition emailTypeDef = createEmailTypeDef();
		final ResourceSchema personSchema = createSchema(emailTypeDef);
		
		final OutputElements elements = new OutputElements();
		elements.addSchemas(Collections.singletonList(personSchema));
		
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		exporter.write(out, elements);
		final byte[] bytes = out.toByteArray();
		
		System.out.println(new String(bytes));
		
		final JsonSchemaParser importer = new JsonSchemaParser();
		importer.parse(new ByteArrayInputStream(bytes));
	}
	
	@Test
	public void testTypeDefExport() throws IOException {
		final JsonSchemaWriter exporter = new JsonSchemaWriter();

		final TypeDefinition emailTypeDef = createEmailTypeDef();
		
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		final OutputElements elements = new OutputElements();
		elements.addTypeDefs(Collections.singletonList(emailTypeDef));
		
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
				getClass().getClassLoader().getResourceAsStream("test-schema.json");
		final JsonSchemaParser parser = new JsonSchemaParser();
		final ParsedElements result = parser.parse(in);
		Assert.assertEquals(5, result.getSchemas().size());
	}

	// -----------------------------------------------------
	
	private ResourceSchema createSchema(TypeDefinition emailTypeDef) {
		final ResourceSchemaImpl personSchema = new ResourceSchemaImpl();
		personSchema.setDescribedType(new SimpleResourceID(NAMESPACE_URI, "Person"));
			
		TypeDefinitionImpl typeDef1 = new TypeDefinitionImpl();
		typeDef1.setElementaryDataType(Datatype.STRING);
		PropertyDeclarationImpl pa1 = new PropertyDeclarationImpl(HAS_FORENAME, typeDef1);
		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		personSchema.addPropertyDeclaration(pa1);

		TypeDefinitionImpl typeDef2 = new TypeDefinitionImpl();
		typeDef2.setElementaryDataType(Datatype.STRING);
		PropertyDeclarationImpl pa3 = new PropertyDeclarationImpl(HAS_SURNAME, typeDef2);
		pa3.setCardinality(CardinalityBuilder.hasOptionalOneToMany());
		personSchema.addPropertyDeclaration(pa3);

		TypeDefinitionImpl typeDef3 = new TypeDefinitionImpl();
		typeDef3.setElementaryDataType(Datatype.RESOURCE);
		typeDef3.addConstraint(ConstraintBuilder.buildConstraint(new SimpleResourceID(NAMESPACE_URI, "Adress")));
		PropertyDeclarationImpl pa8 = new PropertyDeclarationImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasAddress"), typeDef3);
		pa8.setCardinality(CardinalityBuilder.hasAtLeastOneToMany());
		personSchema.addPropertyDeclaration(pa8);

		TypeDefinitionImpl typeDef4 = new TypeDefinitionImpl();
		typeDef4.setElementaryDataType(Datatype.DATE);
		PropertyDeclarationImpl pa4 = new PropertyDeclarationImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasDateOfBirth"),
				typeDef4);
		pa4.setCardinality(CardinalityBuilder.hasExcactlyOne());
		personSchema.addPropertyDeclaration(pa4);
		
		PropertyDeclarationImpl pa5 = new PropertyDeclarationImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasEmail"), emailTypeDef);
		pa5.setCardinality(CardinalityBuilder.hasOptionalOneToMany());
		personSchema.addPropertyDeclaration(pa5);
		
		TypeDefinitionImpl typeDef6 = new TypeDefinitionImpl();
		typeDef6.setElementaryDataType(Datatype.RESOURCE);
		typeDef6.addConstraint(ConstraintBuilder.buildConstraint(personSchema.getDescribedType()));	
		PropertyDeclarationImpl pa7 = new PropertyDeclarationImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasChildren"), typeDef6);
		pa7.setCardinality(CardinalityBuilder.hasOptionalOneToMany());
		personSchema.addPropertyDeclaration(pa7);

		return personSchema;
	}
	
	/**
	 * @return
	 */
	private TypeDefinition createEmailTypeDef() {
		final ResourceID id = new SimpleResourceID(NAMESPACE_URI, "EmailAdressTypeDef");
		final TypeDefinitionImpl typeDef = new TypeDefinitionImpl(id, true);
		typeDef.setName("Email-Address");
		typeDef.setElementaryDataType(Datatype.STRING);
		typeDef.addConstraint(ConstraintBuilder.buildConstraint(".*@.*"));
		return typeDef;
	}
	
}
