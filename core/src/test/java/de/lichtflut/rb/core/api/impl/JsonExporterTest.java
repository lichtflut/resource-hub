/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api.impl;

import java.io.IOException;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionImpl;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Oct 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class JsonExporterTest {
	
	private static final String NAMESPACE_URI = "http://lichtflut.de#";
	
	ResourceID HAS_EMAIL = new SimpleResourceID(NAMESPACE_URI, "hasEmail");

	ResourceID HAS_FORENAME = new SimpleResourceID(NAMESPACE_URI, "hasFirstname");

	ResourceID HAS_SURNAME = new SimpleResourceID(NAMESPACE_URI, "hasLastname");
	
	// -----------------------------------------------------
	
	@Test
	public void testExport() throws IOException {
		final JsonSchemaExporter exporter = new JsonSchemaExporter(null);

		final TypeDefinition emailTypeDef = createEmailTypeDef();
		final ResourceSchema personSchema = createSchema(emailTypeDef);
		
		exporter.write(System.out, personSchema);
	}

	// -----------------------------------------------------
	
	private ResourceSchema createSchema(TypeDefinition emailTypeDef) {
		final ResourceSchemaImpl personSchema = new ResourceSchemaImpl();
		personSchema.setDescribedType(new SimpleResourceID(NAMESPACE_URI, "Person"));
			
		TypeDefinitionImpl typeDef1 = new TypeDefinitionImpl();
		typeDef1.setElementaryDataType(ElementaryDataType.STRING);
		PropertyDeclarationImpl pa1 = new PropertyDeclarationImpl(HAS_FORENAME, typeDef1);
		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		personSchema.addPropertyDeclaration(pa1);

		TypeDefinitionImpl typeDef2 = new TypeDefinitionImpl();
		typeDef2.setElementaryDataType(ElementaryDataType.STRING);
		PropertyDeclarationImpl pa3 = new PropertyDeclarationImpl(HAS_SURNAME, typeDef2);
		pa3.setCardinality(CardinalityBuilder.hasOptionalOneToMany());
		personSchema.addPropertyDeclaration(pa3);

		TypeDefinitionImpl typeDef3 = new TypeDefinitionImpl();
		typeDef3.setElementaryDataType(ElementaryDataType.RESOURCE);
		typeDef3.addConstraint(ConstraintBuilder.buildConstraint(new SimpleResourceID(NAMESPACE_URI, "Adress")));
		PropertyDeclarationImpl pa8 = new PropertyDeclarationImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasAddress"), typeDef3);
		pa8.setCardinality(CardinalityBuilder.hasAtLeastOneToMany());
		personSchema.addPropertyDeclaration(pa8);

		TypeDefinitionImpl typeDef4 = new TypeDefinitionImpl();
		typeDef4.setElementaryDataType(ElementaryDataType.DATE);
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
		typeDef6.setElementaryDataType(ElementaryDataType.RESOURCE);
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
		final TypeDefinitionImpl typeDef = new TypeDefinitionImpl(new SimpleResourceID(), true);
		typeDef.setElementaryDataType(ElementaryDataType.STRING);
		typeDef.addConstraint(ConstraintBuilder.buildConstraint(".*@.*"));
		return typeDef;
	}
	
}
