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
import de.lichtflut.rb.core.schema.model.impl.ConstraintFactory;
import de.lichtflut.rb.core.schema.model.impl.PropertyAssertionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
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

	private static final ResourceSchemaImpl addressSchema = new ResourceSchemaImpl(
			"http://lichtflut.de#", "Address");
	private static final ResourceSchemaImpl personSchema = new ResourceSchemaImpl(
			"http://lichtflut.de#", "Person");
	private static final ResourceSchemaImpl citySchema = new ResourceSchemaImpl(
			"http://lichtflut.de#", "City");
	private static final ResourceSchemaImpl organizationSchema = new ResourceSchemaImpl(
			"http://lichtflut.de#", "Organization");
	/**
	 * Constructor.
	 */
	private MockResourceSchemaFactory(){};

	public static List<ResourceSchema> getAllShemas(){
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
	public static ResourceSchema createPersonSchema() {

		if(personSchema.getPropertyAssertions().size()  < 1){
			
		PropertyDeclarationImpl p1 = new PropertyDeclarationImpl();
		p1.setName("http://lichtflut.de#Firstname");
		p1.setElementaryDataType(ElementaryDataType.STRING);
		PropertyAssertionImpl pa1 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasFirstName"),
				p1);
		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		personSchema.addPropertyAssertion(pa1);

		PropertyDeclarationImpl p3 = new PropertyDeclarationImpl();
		p3.setName("http://lichtflut.de#Lastname");
		p3.setElementaryDataType(ElementaryDataType.STRING);
		PropertyAssertionImpl pa3 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasLastname"),
				p3);
		pa3.setCardinality(CardinalityBuilder.hasOptionalOneToMany());
		personSchema.addPropertyAssertion(pa3);

		PropertyDeclarationImpl p8 = new PropertyDeclarationImpl();
		p8.setName("http://lichtflut.de#Address");
		p8.setElementaryDataType(ElementaryDataType.RESOURCE);
		p8.addConstraint(ConstraintFactory.buildConstraint(addressSchema.getDescribedResourceID()));
		PropertyAssertionImpl pa8 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasAddress"), p8);
		pa8.setCardinality(CardinalityBuilder.hasAtLeastOneToMany());
		personSchema.addPropertyAssertion(pa8);

		PropertyDeclarationImpl p4 = new PropertyDeclarationImpl();
		p4.setName("http://lichtflut.de#dateOfBirth");
		p4.setElementaryDataType(ElementaryDataType.DATE);
		PropertyAssertionImpl pa4 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasDateOfBirth"),
				p4);
		pa4.setCardinality(CardinalityBuilder.hasExcactlyOne());
		personSchema.addPropertyAssertion(pa4);
		
		PropertyDeclarationImpl p5 = new PropertyDeclarationImpl();
		p5.setName("http://lichtflut.de#email");
		p5.setElementaryDataType(ElementaryDataType.STRING);
		p5.addConstraint(ConstraintFactory.buildConstraint(".*@.*"));
		PropertyAssertionImpl pa5 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasEmail"), p5);
		pa5.setCardinality(CardinalityBuilder.hasOptionalOneToMany());
		personSchema.addPropertyAssertion(pa5);
		
		PropertyDeclarationImpl p7 = new PropertyDeclarationImpl();
		p7.setName("http://lichtflut.de#child");
		p7.setElementaryDataType(ElementaryDataType.RESOURCE);
		p7.addConstraint(ConstraintFactory.buildConstraint(personSchema.getDescribedResourceID()));	
		PropertyAssertionImpl pa7 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasChildren"), p7);
		pa7.setCardinality(CardinalityBuilder.hasOptionalOneToMany());
		personSchema.addPropertyAssertion(pa7);

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

		if(addressSchema.getPropertyAssertions().size() < 1){
			
		PropertyDeclarationImpl p1 = new PropertyDeclarationImpl();
		p1.setName("http://lichtflut.de#Street");
		p1.setElementaryDataType(ElementaryDataType.STRING);
		PropertyAssertionImpl pa1 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasStreet"), p1);
		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		addressSchema.addPropertyAssertion(pa1);

		PropertyDeclarationImpl p5 = new PropertyDeclarationImpl();
		p5.setName("http://lichtflut.de#HouseNr");
		p5.setElementaryDataType(ElementaryDataType.STRING);
		PropertyAssertionImpl pa5 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasHouseNr"), p5);
		pa5.setCardinality(CardinalityBuilder.hasExcactlyOne());
		addressSchema.addPropertyAssertion(pa5);

		PropertyDeclarationImpl p2 = new PropertyDeclarationImpl();
		p2.setName("http://lichtflut.de#City");
		p2.setElementaryDataType(ElementaryDataType.RESOURCE);
		PropertyAssertionImpl pa2 = new PropertyAssertionImpl(
			new SimpleResourceID("http://lichtflut.de#", "hasCity"), p2);
		pa2.setCardinality(CardinalityBuilder.hasExcactlyOne());
		addressSchema.addPropertyAssertion(pa2);
		p2.addConstraint(ConstraintFactory.buildConstraint(citySchema.getDescribedResourceID()));
		
		addressSchema.setLabelBuilder(StaticLabelBuilders.forAddress());
		}

		return addressSchema;
		
	}

	/**
	 * Creates a {@link ResourceSchema} with String, String {@link ElementaryDataType}s.
	 * @return schema
	 */
	static ResourceSchema createCitySchema(){

		if (citySchema.getPropertyAssertions().size() < 1) {

			PropertyDeclarationImpl p1 = new PropertyDeclarationImpl();
			p1.setName("http://lichtflut.de#Zipcode");
			p1.setElementaryDataType(ElementaryDataType.STRING);
			PropertyAssertionImpl pa1 = new PropertyAssertionImpl(
					new SimpleResourceID("http://lichtflut.de#", "hasZipcode"), p1);
			pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
			citySchema.addPropertyAssertion(pa1);

			PropertyDeclarationImpl p2 = new PropertyDeclarationImpl();
			p2.setName("http://lichtflut.de#City");
			p2.setElementaryDataType(ElementaryDataType.STRING);
			PropertyAssertionImpl pa2 = new PropertyAssertionImpl(
					new SimpleResourceID("http://lichtflut.de#", "hasCity"), p2);
			pa2.setCardinality(CardinalityBuilder.hasExcactlyOne());
			citySchema.addPropertyAssertion(pa2);

			PropertyDeclarationImpl p3 = new PropertyDeclarationImpl();
			p3.setName("http://lichtflut.de#Country");
			p3.setElementaryDataType(ElementaryDataType.STRING);
			PropertyAssertionImpl pa3 = new PropertyAssertionImpl(
					new SimpleResourceID("http://lichtflut.de#", "hasCountry"),
					p3);
			pa3.setCardinality(CardinalityBuilder.hasExcactlyOne());
			citySchema.addPropertyAssertion(pa3);

			citySchema.setLabelBuilder(StaticLabelBuilders.forCity());
		}
		return citySchema;
	}

	/**
	 * Creates a {@link ResourceSchema} with String, String {@link ElementaryDataType}s.
	 * @return schema
	 */
	static ResourceSchema createOrganisationSchema(){

		if(organizationSchema.getPropertyAssertions().size()  < 1 ){

			//TODO Introduce TYPE:URI in RB?
		PropertyDeclarationImpl p1 = new PropertyDeclarationImpl();
		p1.setName("http://lichtflut.de#Name");
		p1.setElementaryDataType(ElementaryDataType.STRING);
		PropertyAssertionImpl pa1 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasName"), p1);
		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		organizationSchema.addPropertyAssertion(pa1);

		PropertyDeclarationImpl p5 = new PropertyDeclarationImpl();
		p5.setName("http://lichtflut.de#CEO");
		p5.setElementaryDataType(ElementaryDataType.RESOURCE);
		p5.addConstraint(ConstraintFactory.buildConstraint(personSchema.getDescribedResourceID()));
		PropertyAssertionImpl pa5 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasCEO"), p5);
		pa5.setCardinality(CardinalityBuilder.hasExcactlyOne());
		organizationSchema.addPropertyAssertion(pa5);

		PropertyDeclarationImpl p6 = new PropertyDeclarationImpl();
		p6.setName("http://lichtflut.de#Location");
		p6.setElementaryDataType(ElementaryDataType.RESOURCE);
		PropertyAssertionImpl pa6 = new PropertyAssertionImpl(
			new SimpleResourceID("http://lichtflut.de#", "hasLocation"), p6);
		pa6.setCardinality(CardinalityBuilder.hasExcactlyOne());
		p6.addConstraint(ConstraintFactory.buildConstraint(addressSchema.getDescribedResourceID()));
		organizationSchema.addPropertyAssertion(pa6);

		PropertyDeclarationImpl p2 = new PropertyDeclarationImpl();
		p2.setName("http://lichtflut.de#Independent");
		p2.setElementaryDataType(ElementaryDataType.BOOLEAN);
		PropertyAssertionImpl pa2 = new PropertyAssertionImpl(
			new SimpleResourceID("http://lichtflut.de#", "hasIndependent"), p2);
		pa2.setCardinality(CardinalityBuilder.hasExcactlyOne());
		organizationSchema.addPropertyAssertion(pa2);
		
		PropertyDeclarationImpl p3 = new PropertyDeclarationImpl();
		p3.setName("http://lichtflut.de#Member");
		p3.setElementaryDataType(ElementaryDataType.RESOURCE);
		PropertyAssertionImpl pa3 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasMember"), p3);
		pa3.setCardinality(CardinalityBuilder.hasAtLeastOneToMany());
		p3.addConstraint(ConstraintFactory.buildConstraint(personSchema.getDescribedResourceID()));
		organizationSchema.addPropertyAssertion(pa3);
		
		PropertyDeclarationImpl p4 = new PropertyDeclarationImpl();
		p4.setName("http://lichtflut.de#Description");
		p4.setElementaryDataType(ElementaryDataType.STRING);
		PropertyAssertionImpl pa4 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasDescription"), p4);
		pa3.setCardinality(CardinalityBuilder.hasOptionalOne());
		organizationSchema.addPropertyAssertion(pa4);

		organizationSchema.setLabelBuilder(StaticLabelBuilders.forOrganization());
		}
		return organizationSchema;
	}
}
