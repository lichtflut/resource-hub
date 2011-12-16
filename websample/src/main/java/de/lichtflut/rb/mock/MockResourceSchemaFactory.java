/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import java.util.HashSet;
import java.util.Set;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionImpl;
import de.lichtflut.rb.web.WSConstants;
import de.lichtflut.rb.web.util.StaticLabelBuilders;

/**
 * <p>
 * This class provides static {@link ResourceSchema}s for testing purposes.
 * </p>
 *
 * Created: Aug 16, 2011
 *
 * @author Ravi Knox
 *
 */
//CHECKSTYLE:OFF
public final class MockResourceSchemaFactory {
	
	public static final ResourceID ADRESS = new SimpleResourceID("http://lichtflut.de#", "Address");
	
	public static final ResourceID PERSON = new SimpleResourceID("http://lichtflut.de#", "Person");
	
	public static final ResourceID CITY = new SimpleResourceID("http://lichtflut.de#", "City");
	
	public static final ResourceID ORGANIZATION = new SimpleResourceID("http://lichtflut.de#", "Organization");
	
	public static final ResourceID EMAIL_TYPE_DEF_ID = new SimpleResourceID("http://lichtflut.de/public-type-defs#", "EmailTypeDef");
	
	private static final MockResourceSchemaFactory INSTANCE = new MockResourceSchemaFactory();

	private final ResourceSchema addressSchema;
	
	private final ResourceSchema personSchema;
	
	private final ResourceSchema citySchema;
	
	private final ResourceSchema organizationSchema;
	
	private final TypeDefinition emailTypeDef;
	
	// -----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	private MockResourceSchemaFactory(){
		this.emailTypeDef = createEmailTypeDef();
		this.personSchema = createPersonSchema();
		this.addressSchema = createAddressSchema();
		this.citySchema = createCitySchema();
		this.organizationSchema = createOrganisationSchema();
	};
	
	// -----------------------------------------------------

	/**
	 * @return the instance
	 */
	public static MockResourceSchemaFactory getInstance() {
		return INSTANCE;
	}
	
	// -----------------------------------------------------
	
	/**
	 * @return the addressschema
	 */
	public ResourceSchema getAddressSchema() {
		return addressSchema;
	}
	
	/**
	 * @return the cityschema
	 */
	public ResourceSchema getCitySchema() {
		return citySchema;
	}
	
	/**
	 * @return the organizationschema
	 */
	public ResourceSchema getOrganizationSchema() {
		return organizationSchema;
	}
	
	/**
	 * @return the personschema
	 */
	public ResourceSchema getPersonSchema() {
		return personSchema;
	}
	
	// -----------------------------------------------------

	public Set<ResourceSchema> getAllShemas(){
		final Set<ResourceSchema> result = new HashSet<ResourceSchema>();
		result.add(addressSchema);
		result.add(citySchema);
		result.add(organizationSchema);
		result.add(personSchema);
		return result;
	}
	
	public Set<TypeDefinition> getPublicTypeDefs() {
		final Set<TypeDefinition> result = new HashSet<TypeDefinition>();
		result.add(emailTypeDef);
		return result;
	}
	
	// -----------------------------------------------------
	
	/**
	 * Creates a {@link ResourceSchema} with String, Integer, Date and Resource
	 * {@link Datatype}s.
	 *
	 * @return schema
	 */
	private ResourceSchema createPersonSchema() {
		final ResourceSchemaImpl personSchema = new ResourceSchemaImpl();
		personSchema.setDescribedType(PERSON);
			
		TypeDefinitionImpl typeDef1 = new TypeDefinitionImpl();
		typeDef1.setElementaryDataType(Datatype.STRING);
		PropertyDeclarationImpl pa1 = new PropertyDeclarationImpl(WSConstants.HAS_FORENAME, typeDef1);
		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		personSchema.addPropertyDeclaration(pa1);

		TypeDefinitionImpl typeDef2 = new TypeDefinitionImpl();
		typeDef2.setElementaryDataType(Datatype.STRING);
		PropertyDeclarationImpl pa3 = new PropertyDeclarationImpl(WSConstants.HAS_SURNAME, typeDef2);
		pa3.setCardinality(CardinalityBuilder.hasOptionalOneToMany());
		personSchema.addPropertyDeclaration(pa3);

		TypeDefinitionImpl typeDef3 = new TypeDefinitionImpl();
		typeDef3.setElementaryDataType(Datatype.RESOURCE);
		typeDef3.addConstraint(ConstraintBuilder.buildConstraint(ADRESS));
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

		personSchema.setLabelBuilder(StaticLabelBuilders.forPerson());
		return personSchema;
	}

	/**
	 * Creates a {@link ResourceSchema} with String, Integer
	 * {@link Datatype}s.
	 *
	 * @return schema
	 */
	private ResourceSchema createAddressSchema(){
		final ResourceSchemaImpl addressSchema = new ResourceSchemaImpl();
		addressSchema.setDescribedType(ADRESS);
			
		TypeDefinitionImpl p1 = new TypeDefinitionImpl();
		p1.setElementaryDataType(Datatype.STRING);
		PropertyDeclarationImpl pa1 = new PropertyDeclarationImpl(WSConstants.HAS_STREET, p1);
		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		addressSchema.addPropertyDeclaration(pa1);

		TypeDefinitionImpl p5 = new TypeDefinitionImpl();
		p5.setElementaryDataType(Datatype.STRING);
		PropertyDeclarationImpl pa5 = new PropertyDeclarationImpl(WSConstants.HAS_HOUSNR, p5);
		pa5.setCardinality(CardinalityBuilder.hasExcactlyOne());
		addressSchema.addPropertyDeclaration(pa5);

		TypeDefinitionImpl p2 = new TypeDefinitionImpl();
		p2.setElementaryDataType(Datatype.RESOURCE);
		PropertyDeclarationImpl pa2 = new PropertyDeclarationImpl(WSConstants.HAS_CITY, p2);
		pa2.setCardinality(CardinalityBuilder.hasExcactlyOne());
		addressSchema.addPropertyDeclaration(pa2);
		p2.addConstraint(ConstraintBuilder.buildConstraint(CITY));
		
		addressSchema.setLabelBuilder(StaticLabelBuilders.forAddress());
		return addressSchema;
	}

	/**
	 * Creates a {@link ResourceSchema} with String, String {@link Datatype}s.
	 * @return schema
	 */
	private ResourceSchema createCitySchema(){
		final ResourceSchemaImpl citySchema = new ResourceSchemaImpl();
		citySchema.setDescribedType(CITY);
		
		TypeDefinitionImpl p1 = new TypeDefinitionImpl();
		p1.setElementaryDataType(Datatype.STRING);
		PropertyDeclarationImpl pa1 = new PropertyDeclarationImpl(WSConstants.HAS_ZIPCODE, p1);
		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		citySchema.addPropertyDeclaration(pa1);

		TypeDefinitionImpl p2 = new TypeDefinitionImpl();
		p2.setElementaryDataType(Datatype.STRING);
		PropertyDeclarationImpl pa2 = new PropertyDeclarationImpl(WSConstants.HAS_CITY, p2);
		pa2.setCardinality(CardinalityBuilder.hasExcactlyOne());
		citySchema.addPropertyDeclaration(pa2);

		TypeDefinitionImpl p3 = new TypeDefinitionImpl();
		p3.setElementaryDataType(Datatype.STRING);
		PropertyDeclarationImpl pa3 = new PropertyDeclarationImpl(WSConstants.HAS_COUNTRY, p3);
		pa3.setCardinality(CardinalityBuilder.hasExcactlyOne());
		citySchema.addPropertyDeclaration(pa3);

		citySchema.setLabelBuilder(StaticLabelBuilders.forCity());
		return citySchema;
	}

	/**
	 * Creates a {@link ResourceSchema} with String, String {@link Datatype}s.
	 * @return schema
	 */
	private ResourceSchema createOrganisationSchema() {
		final ResourceSchemaImpl organizationSchema = new ResourceSchemaImpl();
		organizationSchema.setDescribedType(ORGANIZATION);

		//TODO Introduce TYPE:URI in RB?
		TypeDefinitionImpl p1 = new TypeDefinitionImpl();
		p1.setName("http://lichtflut.de#Name");
		p1.setElementaryDataType(Datatype.STRING);
		PropertyDeclarationImpl pa1 = new PropertyDeclarationImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasName"), p1);
		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		organizationSchema.addPropertyDeclaration(pa1);

		TypeDefinitionImpl p5 = new TypeDefinitionImpl();
		p5.setName("http://lichtflut.de#CEO");
		p5.setElementaryDataType(Datatype.RESOURCE);
		p5.addConstraint(ConstraintBuilder.buildConstraint(PERSON));
		PropertyDeclarationImpl pa5 = new PropertyDeclarationImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasCEO"), p5);
		pa5.setCardinality(CardinalityBuilder.hasAtLeastOneToMany());
		organizationSchema.addPropertyDeclaration(pa5);

		TypeDefinitionImpl p6 = new TypeDefinitionImpl();
		p6.setName("http://lichtflut.de#Location");
		p6.setElementaryDataType(Datatype.RESOURCE);
		PropertyDeclarationImpl pa6 = new PropertyDeclarationImpl(
			new SimpleResourceID("http://lichtflut.de#", "hasLocation"), p6);
		pa6.setCardinality(CardinalityBuilder.hasExcactlyOne());
		p6.addConstraint(ConstraintBuilder.buildConstraint(ADRESS));
		organizationSchema.addPropertyDeclaration(pa6);

		TypeDefinitionImpl p2 = new TypeDefinitionImpl();
		p2.setName("http://lichtflut.de#Independent");
		p2.setElementaryDataType(Datatype.BOOLEAN);
		PropertyDeclarationImpl pa2 = new PropertyDeclarationImpl(
			new SimpleResourceID("http://lichtflut.de#", "hasIndependent"), p2);
		pa2.setCardinality(CardinalityBuilder.hasExcactlyOne());
		organizationSchema.addPropertyDeclaration(pa2);
		
		TypeDefinitionImpl p3 = new TypeDefinitionImpl();
		p3.setName("http://lichtflut.de#Member");
		p3.setElementaryDataType(Datatype.RESOURCE);
		PropertyDeclarationImpl pa3 = new PropertyDeclarationImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasMember"), p3);
		pa3.setCardinality(CardinalityBuilder.hasAtLeastOneToMany());
		p3.addConstraint(ConstraintBuilder.buildConstraint(PERSON));
		organizationSchema.addPropertyDeclaration(pa3);
		
		TypeDefinitionImpl p4 = new TypeDefinitionImpl();
		p4.setName("http://lichtflut.de#Description");
		p4.setElementaryDataType(Datatype.STRING);
		PropertyDeclarationImpl pa4 = new PropertyDeclarationImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasDescription"), p4);
		pa4.setCardinality(CardinalityBuilder.hasOptionalOne());
		organizationSchema.addPropertyDeclaration(pa4);

		organizationSchema.setLabelBuilder(StaticLabelBuilders.forOrganization());
		return organizationSchema;
	}
	
	/**
	 * @return
	 */
	private TypeDefinition createEmailTypeDef() {
		final TypeDefinitionImpl typeDef = new TypeDefinitionImpl(EMAIL_TYPE_DEF_ID, true);
		typeDef.setElementaryDataType(Datatype.STRING);
		typeDef.addConstraint(ConstraintBuilder.buildConstraint(".*@.*"));
		return typeDef;
	}
	
}
