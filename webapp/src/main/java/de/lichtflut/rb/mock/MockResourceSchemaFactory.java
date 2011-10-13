/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.web.metamodel.WSConstants;
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

	private static final ResourceSchemaImpl addressSchema = new ResourceSchemaImpl().setDescribedType(
			new SimpleResourceID("http://lichtflut.de#", "Address"));
	private static final ResourceSchemaImpl personSchema = new ResourceSchemaImpl().setDescribedType(
			new SimpleResourceID("http://lichtflut.de#", "Person"));
	private static final ResourceSchemaImpl citySchema = new ResourceSchemaImpl().setDescribedType(
			new SimpleResourceID("http://lichtflut.de#", "City"));
	private static final ResourceSchemaImpl organizationSchema = new ResourceSchemaImpl().setDescribedType(
			new SimpleResourceID("http://lichtflut.de#", "Organization"));
	
	// -----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	private MockResourceSchemaFactory(){};

	static List<ResourceSchema> getAllShemas(){
		List<ResourceSchema> list = new ArrayList<ResourceSchema>();
		list.add(createAddressSchema());
		list.add(createCitySchema());
		list.add(createOrganisationSchema());
		list.add(createPersonSchema());
		return list;
	}
	/**
	 * Creates a {@link ResourceSchema} with String, Integer, Date and Resource
	 * {@link ElementaryDataType}s.
	 *
	 * @return schema
	 */
	static ResourceSchema createPersonSchema() {

		if(personSchema.getPropertyDeclarations().size()  < 1){
			
		TypeDefinitionImpl typeDef1 = new TypeDefinitionImpl();
		typeDef1.setName("http://lichtflut.de#Firstname");
		typeDef1.setElementaryDataType(ElementaryDataType.STRING);
		PropertyDeclarationImpl pa1 = new PropertyDeclarationImpl(WSConstants.HAS_FORENAME, typeDef1);
		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		personSchema.addPropertyDeclaration(pa1);

		TypeDefinitionImpl typeDef2 = new TypeDefinitionImpl();
		typeDef2.setElementaryDataType(ElementaryDataType.STRING);
		PropertyDeclarationImpl pa3 = new PropertyDeclarationImpl(WSConstants.HAS_SURNAME, typeDef2);
		pa3.setCardinality(CardinalityBuilder.hasOptionalOneToMany());
		personSchema.addPropertyDeclaration(pa3);

		TypeDefinitionImpl typeDef3 = new TypeDefinitionImpl();
		typeDef3.setElementaryDataType(ElementaryDataType.RESOURCE);
		typeDef3.addConstraint(ConstraintBuilder.buildConstraint(addressSchema.getDescribedType()));
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
		
		TypeDefinitionImpl typeDef5 = new TypeDefinitionImpl();
		typeDef5.setElementaryDataType(ElementaryDataType.STRING);
		typeDef5.addConstraint(ConstraintBuilder.buildConstraint(".*@.*"));
		PropertyDeclarationImpl pa5 = new PropertyDeclarationImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasEmail"), typeDef5);
		pa5.setCardinality(CardinalityBuilder.hasOptionalOneToMany());
		personSchema.addPropertyDeclaration(pa5);
		
		TypeDefinitionImpl typeDef6 = new TypeDefinitionImpl();
		typeDef6.setElementaryDataType(ElementaryDataType.RESOURCE);
		typeDef6.addConstraint(ConstraintBuilder.buildConstraint(personSchema.getDescribedType()));	
		PropertyDeclarationImpl pa7 = new PropertyDeclarationImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasChildren"), typeDef6);
		pa7.setCardinality(CardinalityBuilder.hasOptionalOneToMany());
		personSchema.addPropertyDeclaration(pa7);

		personSchema.setLabelBuilder(StaticLabelBuilders.forPerson());
		}
		return personSchema;
	}

	/**
	 * Creates a {@link ResourceSchema} with String, Integer
	 * {@link ElementaryDataType}s.
	 *
	 * @return schema
	 */
	static ResourceSchema createAddressSchema(){

		if(addressSchema.getPropertyDeclarations().size() < 1){
			
		TypeDefinitionImpl p1 = new TypeDefinitionImpl();
		p1.setElementaryDataType(ElementaryDataType.STRING);
		PropertyDeclarationImpl pa1 = new PropertyDeclarationImpl(WSConstants.HAS_STREET, p1);
		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		addressSchema.addPropertyDeclaration(pa1);

		TypeDefinitionImpl p5 = new TypeDefinitionImpl();
		p5.setElementaryDataType(ElementaryDataType.STRING);
		PropertyDeclarationImpl pa5 = new PropertyDeclarationImpl(WSConstants.HAS_HOUSNR, p5);
		pa5.setCardinality(CardinalityBuilder.hasExcactlyOne());
		addressSchema.addPropertyDeclaration(pa5);

		TypeDefinitionImpl p2 = new TypeDefinitionImpl();
		p2.setElementaryDataType(ElementaryDataType.RESOURCE);
		PropertyDeclarationImpl pa2 = new PropertyDeclarationImpl(WSConstants.HAS_CITY, p2);
		pa2.setCardinality(CardinalityBuilder.hasExcactlyOne());
		addressSchema.addPropertyDeclaration(pa2);
		p2.addConstraint(ConstraintBuilder.buildConstraint(citySchema.getDescribedType()));
		
		addressSchema.setLabelBuilder(StaticLabelBuilders.forAddress());
		}

		return addressSchema;
		
	}

	/**
	 * Creates a {@link ResourceSchema} with String, String {@link ElementaryDataType}s.
	 * @return schema
	 */
	static ResourceSchema createCitySchema(){

		if (citySchema.getPropertyDeclarations().size() < 1) {

			TypeDefinitionImpl p1 = new TypeDefinitionImpl();
			p1.setElementaryDataType(ElementaryDataType.STRING);
			PropertyDeclarationImpl pa1 = new PropertyDeclarationImpl(WSConstants.HAS_ZIPCODE, p1);
			pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
			citySchema.addPropertyDeclaration(pa1);

			TypeDefinitionImpl p2 = new TypeDefinitionImpl();
			p2.setElementaryDataType(ElementaryDataType.STRING);
			PropertyDeclarationImpl pa2 = new PropertyDeclarationImpl(WSConstants.HAS_CITY, p2);
			pa2.setCardinality(CardinalityBuilder.hasExcactlyOne());
			citySchema.addPropertyDeclaration(pa2);

			TypeDefinitionImpl p3 = new TypeDefinitionImpl();
			p3.setElementaryDataType(ElementaryDataType.STRING);
			PropertyDeclarationImpl pa3 = new PropertyDeclarationImpl(WSConstants.HAS_COUNTRY, p3);
			pa3.setCardinality(CardinalityBuilder.hasExcactlyOne());
			citySchema.addPropertyDeclaration(pa3);

			citySchema.setLabelBuilder(StaticLabelBuilders.forCity());
		}
		return citySchema;
	}

	/**
	 * Creates a {@link ResourceSchema} with String, String {@link ElementaryDataType}s.
	 * @return schema
	 */
	static ResourceSchema createOrganisationSchema(){

		if(organizationSchema.getPropertyDeclarations().size()  < 1 ){

			//TODO Introduce TYPE:URI in RB?
		TypeDefinitionImpl p1 = new TypeDefinitionImpl();
		p1.setName("http://lichtflut.de#Name");
		p1.setElementaryDataType(ElementaryDataType.STRING);
		PropertyDeclarationImpl pa1 = new PropertyDeclarationImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasName"), p1);
		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		organizationSchema.addPropertyDeclaration(pa1);

		TypeDefinitionImpl p5 = new TypeDefinitionImpl();
		p5.setName("http://lichtflut.de#CEO");
		p5.setElementaryDataType(ElementaryDataType.RESOURCE);
		p5.addConstraint(ConstraintBuilder.buildConstraint(personSchema.getDescribedType()));
		PropertyDeclarationImpl pa5 = new PropertyDeclarationImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasCEO"), p5);
		pa5.setCardinality(CardinalityBuilder.hasAtLeastOneToMany());
		organizationSchema.addPropertyDeclaration(pa5);

		TypeDefinitionImpl p6 = new TypeDefinitionImpl();
		p6.setName("http://lichtflut.de#Location");
		p6.setElementaryDataType(ElementaryDataType.RESOURCE);
		PropertyDeclarationImpl pa6 = new PropertyDeclarationImpl(
			new SimpleResourceID("http://lichtflut.de#", "hasLocation"), p6);
		pa6.setCardinality(CardinalityBuilder.hasExcactlyOne());
		p6.addConstraint(ConstraintBuilder.buildConstraint(addressSchema.getDescribedType()));
		organizationSchema.addPropertyDeclaration(pa6);

		TypeDefinitionImpl p2 = new TypeDefinitionImpl();
		p2.setName("http://lichtflut.de#Independent");
		p2.setElementaryDataType(ElementaryDataType.BOOLEAN);
		PropertyDeclarationImpl pa2 = new PropertyDeclarationImpl(
			new SimpleResourceID("http://lichtflut.de#", "hasIndependent"), p2);
		pa2.setCardinality(CardinalityBuilder.hasExcactlyOne());
		organizationSchema.addPropertyDeclaration(pa2);
		
		TypeDefinitionImpl p3 = new TypeDefinitionImpl();
		p3.setName("http://lichtflut.de#Member");
		p3.setElementaryDataType(ElementaryDataType.RESOURCE);
		PropertyDeclarationImpl pa3 = new PropertyDeclarationImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasMember"), p3);
		pa3.setCardinality(CardinalityBuilder.hasAtLeastOneToMany());
		p3.addConstraint(ConstraintBuilder.buildConstraint(personSchema.getDescribedType()));
		organizationSchema.addPropertyDeclaration(pa3);
		
		TypeDefinitionImpl p4 = new TypeDefinitionImpl();
		p4.setName("http://lichtflut.de#Description");
		p4.setElementaryDataType(ElementaryDataType.STRING);
		PropertyDeclarationImpl pa4 = new PropertyDeclarationImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasDescription"), p4);
		pa4.setCardinality(CardinalityBuilder.hasOptionalOne());
		organizationSchema.addPropertyDeclaration(pa4);

		organizationSchema.setLabelBuilder(StaticLabelBuilders.forOrganization());
		}
		return organizationSchema;
	}
}
