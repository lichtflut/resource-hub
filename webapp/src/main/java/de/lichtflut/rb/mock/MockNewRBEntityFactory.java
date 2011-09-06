/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintFactory;
import de.lichtflut.rb.core.schema.model.impl.NewRBEntity;
import de.lichtflut.rb.core.schema.model.impl.PropertyAssertionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.web.util.StaticLabelBuilders;

/**
 * <p>
 * This class provides static {@link NewRBEntity}s for testing purposes.
 * </p>
 * Created: Aug 16, 2011
 *
 * @author Ravi Knox
 */
// CHECKSTYLE:OFF
public final class MockNewRBEntityFactory {

	private static final int MAX_AGE = 50;
	private static final ResourceSchemaImpl addressSchema = new ResourceSchemaImpl(
			"http://lichtflut.de#", "Address");
	private static final ResourceSchemaImpl personSchema = new ResourceSchemaImpl(
			"http://lichtflut.de#", "Person");
	private static final ResourceSchemaImpl citySchema = new ResourceSchemaImpl(
			"http://lichtflut.de#", "City");
	private static final ResourceSchemaImpl organizationSchema = new ResourceSchemaImpl(
			"http://lichtflut.de#", "Organization");

	/**
	 * Private Constructor.
	 */
	private MockNewRBEntityFactory() {
	};

	// ------------------------------------------------------------


	/**
	 * <p>
	 * Creates a {@link NewRBEntity} with appropriate values.
	 * </p>
	 * <p>
	 * {@link ElementaryDataType}: Date, String, Integer, Resource
	 * </p>
	 *
	 * @return Instance of {@link NewRBEntity}
	 */
	@SuppressWarnings("deprecation")
	public static NewRBEntity createPerson() {
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<Object> firstName = new ArrayList<Object>();
		List<Object> lastName = new ArrayList<Object>();
		List<Object> address = new ArrayList<Object>();
		List<Object> emailList = new ArrayList<Object>();
		List<Object> ageList = new ArrayList<Object>();
		List<Object> dateOfBirth = new ArrayList<Object>();
		List<Object> childrenList = new ArrayList<Object>();
		firstName.add("Ravi");
		lastName.add("Knox");
		address.add(createAddress());
		dateOfBirth.add(new Date(1985, 12, 23));
		emailList.add("max@moritz.de");
		emailList.add("chef@koch.de");
		ageList.add(getRandomNumer(MAX_AGE));
		childrenList.add(createChildlessComplexPerson(createPersonSchema()));
		list.add(firstName);
		list.add(lastName);
		list.add(address);
		list.add(dateOfBirth);
		list.add(emailList);
		list.add(ageList);
		list.add(childrenList);
		int counter = 0;
		NewRBEntity entity = new NewRBEntity(createPersonSchema());
		for (IRBField field : entity.getAllFields()) {
			if (counter < list.size()) {
				field.setFieldValues(list.get(counter));
			}
			counter++;
		}
		return entity;
	}

	/**
	 * Creates a {@link NewRBEntity} with appropriate values.
	 *
	 * @return Instance of {@link NewRBEntity}
	 */
	public static NewRBEntity createStringBasedPerson() {
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<Object> emailList = new ArrayList<Object>();
		List<Object> ageList = new ArrayList<Object>();
		List<Object> childrenList = new ArrayList<Object>();
		emailList.add("max@moritz.de");
		emailList.add("chef@koch.de");
		ageList.add("23");
		childrenList.add("3");
		list.add(emailList);
		list.add(ageList);
		list.add(childrenList);
		int counter = 0;
		NewRBEntity entity = new NewRBEntity(createOnlyStringSchema());
		for (IRBField field : entity.getAllFields()) {
			field.setFieldValues(list.get(counter));
			counter++;
		}
		return entity;
	}

	/**
	 * return a list of {@link IRBEntity}.
	 * @return -
	 */
	@SuppressWarnings("deprecation")
	public static List<IRBEntity> getListOfPersons(){
		List<IRBEntity> list = new ArrayList<IRBEntity>();
//		list.add(createComplexNewRBEntity(new Date(1956, 03, 12), "nr1@google.com", 42,
//				createChildlessComplexPerson(createPersonSchema())));
//		list.add(createComplexNewRBEntity(new Date(1999, 07, 11), "nr2@google.com", 40, null));
//		list.add(createComplexNewRBEntity(new Date(1989, 01, 01), "nr3@google.com", 27,
//				createChildlessComplexPerson(createPersonSchema())));
		list.add(createOrganization());
		return list;
	}

	/**
	 * Return an Address.
	 * @return -
	 */
	public static IRBEntity createAddress(){
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<Object> streetList = new ArrayList<Object>();
		List<Object> houseNrList = new ArrayList<Object>();
		List<Object> zipCodeList = new ArrayList<Object>();
		List<Object> cityList = new ArrayList<Object>();
		List<Object> countryList = new ArrayList<Object>();
		streetList.add("Lustheide");
		houseNrList.add("85");
		zipCodeList.add(51427);
		cityList.add("Bergisch Gladbach");
		countryList.add("Germany");
		list.add(streetList);
		list.add(houseNrList);
		list.add(zipCodeList);
		list.add(cityList);
		list.add(countryList);
		int counter = 0;
		NewRBEntity entity = new NewRBEntity(createAddressSchema());
		for (IRBField field : entity.getAllFields()) {
			field.setFieldValues(list.get(counter));
			counter++;
		}
		return entity;
	}

	public static IRBEntity createCity(){
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<Object> cityList = new ArrayList<Object>();
		List<Object> countryList = new ArrayList<Object>();
		cityList.add("Bergisch Gladbach");
		countryList.add("Germany");
		list.add(cityList);
		list.add(countryList);
		int counter = 0;
		NewRBEntity entity = new NewRBEntity(createCitySchema());
		for (IRBField field : entity.getAllFields()) {
			field.setFieldValues(list.get(counter));
			counter++;
		}
		return entity;
	}

	/**
	 * Return an Organization.
	 * @return -
	 */
	public static IRBEntity createOrganization(){
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<Object> nameList = new ArrayList<Object>();
		List<Object> ceoList = new ArrayList<Object>();
		List<Object> locationList = new ArrayList<Object>();
		List<Object> independentList = new ArrayList<Object>();
		List<Object> memberList = new ArrayList<Object>();
		List<Object> descriptionList = new ArrayList<Object>();
		nameList.add("Lichtflut F & E GmbH");
		ceoList.add(createPerson());
		locationList.add(createAddress());
		independentList.add(true);
		memberList.add(createPerson());
		descriptionList.add("Wir betreiben Grundlagenforschung im Bereich der semantischen Informationsverarbeitung, helfen unseren Kunden bei der Erstellung von Meta-Modellen ihrer Unternehmen und entwickeln Produkte für ontologiebasierte Informationsverwaltung.");
		list.add(nameList);
		list.add(ceoList);
		list.add(locationList);
		list.add(independentList);
		list.add(memberList);
		list.add(descriptionList);
		int counter = 0;
		System.out.println("MockNewRBEntityFactory.createOrganization() " + organizationSchema.getPropertyAssertions().size());
		NewRBEntity entity = new NewRBEntity(createOrganisationSchema());
		for (IRBField field : entity.getAllFields()) {
			field.setFieldValues(list.get(counter));
			counter++;
		}
		return entity;
	}

	// ------------------------------------------------------------

	/**
	 * <p>
	 * Creates a {@link NewRBEntity} with appropriate values.
	 * </p>
	 * <p>
	 * {@link ElementaryDataType}: Date, String, Integer, Resource
	 * </p>
	 * @param date -
	 * @param email -
	 * @param age -
	 * @param resource -
	 * @return Instance of {@link NewRBEntity}
	 */
	@SuppressWarnings("deprecation")
	private static NewRBEntity createComplexNewRBEntity(final Date date,final String email,final int age, final IRBEntity resource) {
		ResourceSchema schema = createPersonSchema();
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<Object> firstName = new ArrayList<Object>();
		List<Object> lastName = new ArrayList<Object>();
		List<Object> emailList = new ArrayList<Object>();
		List<Object> ageList = new ArrayList<Object>();
		List<Object> dateOfBirth = new ArrayList<Object>();
		List<Object> childrenList = new ArrayList<Object>();
		firstName.add("Ravi");
		lastName.add("Knox");
		dateOfBirth.add(new Date(1985, 12, 23));
		emailList.add("max@moritz.de");
		emailList.add("chef@koch.de");
		ageList.add(getRandomNumer(MAX_AGE));
		childrenList.add(createChildlessComplexPerson(schema));
		list.add(firstName);
		list.add(lastName);
		list.add(dateOfBirth);
		list.add(emailList);
		list.add(ageList);
		list.add(childrenList);
		int counter = 0;
		NewRBEntity entity = new NewRBEntity(schema);
		for (IRBField field : entity.getAllFields()) {
			field.setFieldValues(list.get(counter));
			counter++;
		}
		return entity;
	}

	/**
	 * Creates a {@link NewRBEntity} based on createComplexPerson but without
	 * children.
	 * @param schema -
	 * @return NewRBEntity
	 */
	@SuppressWarnings("deprecation")
	private static NewRBEntity createChildlessComplexPerson(
			final ResourceSchema schema) {
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<Object> firstName = new ArrayList<Object>();
		List<Object> lastName = new ArrayList<Object>();
		List<Object> address = new ArrayList<Object>();
		List<Object> emailList = new ArrayList<Object>();
		List<Object> ageList = new ArrayList<Object>();
		List<Object> dateOfBirth = new ArrayList<Object>();
		List<Object> childrenList = new ArrayList<Object>();
		firstName.add("Kind1");
		lastName.add("Knox");
		address.add(createAddress());
		dateOfBirth.add(new Date(1985, 12, 23));
		emailList.add("max@moritz.de");
		emailList.add("chef@koch.de");
		ageList.add(getRandomNumer(MAX_AGE));
		list.add(firstName);
		list.add(lastName);
		list.add(address);
		list.add(dateOfBirth);
		list.add(emailList);
		list.add(ageList);
		list.add(childrenList);
		int counter = 0;
		NewRBEntity entity = new NewRBEntity(schema);
		for (IRBField field : entity.getAllFields()) {
			field.setFieldValues(list.get(counter));
			counter++;
		}
		return entity;
	}

	/**
	 * <p>
	 * Returns a {@link ResourceSchema} based on Strings only.
	 * </p>
	 *
	 * @return schema -
	 */
	private static ResourceSchema createOnlyStringSchema() {
		ResourceSchemaImpl schema = new ResourceSchemaImpl(
				"http://lichtflut.de#", "Stringschema");
		PropertyDeclarationImpl p2 = new PropertyDeclarationImpl();
		PropertyDeclarationImpl p3 = new PropertyDeclarationImpl();
		PropertyDeclarationImpl p4 = new PropertyDeclarationImpl();
		p2.setName("http://lichtflut.de#email");
		p3.setName("http://lichtflut.de#age");
		p4.setName("http://lichtflut.de#children");

		p2.setElementaryDataType(ElementaryDataType.STRING);
		p3.setElementaryDataType(ElementaryDataType.STRING);
		p4.setElementaryDataType(ElementaryDataType.STRING);

		p2.addConstraint(ConstraintFactory.buildConstraint(".*@.*"));

		PropertyAssertionImpl pa2 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasEmail"), p2);
		PropertyAssertionImpl pa3 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasAge"), p3);
		PropertyAssertionImpl pa4 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasChild"), p4);

		pa2.setCardinality(CardinalityBuilder.hasAtLeastOneUpTo(5));
		pa3.setCardinality(CardinalityBuilder.hasExcactlyOne());
		pa4.setCardinality(CardinalityBuilder.hasOptionalOneToMany());

		schema.addPropertyAssertion(pa2);
		schema.addPropertyAssertion(pa3);
		schema.addPropertyAssertion(pa4);

		return schema;
	}

	/**
	 * Creates a {@link ResourceSchema} with String, Integer, Date and Resource
	 * {@link ElementaryDataType}s.
	 *
	 * @return schema
	 */
	private static ResourceSchema createPersonSchema() {

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
		p8.setElementaryDataType(ElementaryDataType.STRING);
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

		PropertyDeclarationImpl p6 = new PropertyDeclarationImpl();
		p6.setElementaryDataType(ElementaryDataType.INTEGER);
		p6.setName("http://lichtflut.de#age");
		PropertyAssertionImpl pa6 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasAge"), p6);
		pa6.setCardinality(CardinalityBuilder.hasExcactlyOne());
		personSchema.addPropertyAssertion(pa6);
		
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
	private static ResourceSchema createAddressSchema(){

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
		p2.setName("http://lichtflut.de#ZipCode");
		p2.setElementaryDataType(ElementaryDataType.INTEGER);
		PropertyAssertionImpl pa2 = new PropertyAssertionImpl(
			new SimpleResourceID("http://lichtflut.de#", "hasZipCode"), p2);
		pa2.setCardinality(CardinalityBuilder.hasExcactlyOne());
		addressSchema.addPropertyAssertion(pa2);
		
		PropertyDeclarationImpl p3 = new PropertyDeclarationImpl();
		p3.setName("http://lichtflut.de#City");
		p3.setElementaryDataType(ElementaryDataType.STRING);
		PropertyAssertionImpl pa3 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasCity"), p3);
		pa3.setCardinality(CardinalityBuilder.hasExcactlyOne());
		addressSchema.addPropertyAssertion(pa3);
		
		PropertyDeclarationImpl p4 = new PropertyDeclarationImpl();
		p4.setName("http://lichtflut.de#Country");
		p4.setElementaryDataType(ElementaryDataType.STRING);
		PropertyAssertionImpl pa4 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasCountry"), p4);
		addressSchema.addPropertyAssertion(pa4);

		addressSchema.setLabelBuilder(StaticLabelBuilders.forAddress());
		}

		return addressSchema;
		
	}

	/**
	 * Creates a {@link ResourceSchema} with String, String {@link ElementaryDataType}s.
	 * @return schema
	 */
	private static ResourceSchema createCitySchema(){

		if(citySchema.getPropertyAssertions().size()  < 1 ){
			
		PropertyDeclarationImpl p1 = new PropertyDeclarationImpl();
		p1.setName("http://lichtflut.de#City");
		p1.setElementaryDataType(ElementaryDataType.STRING);
		PropertyAssertionImpl pa1 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasCity"), p1);
		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		citySchema.addPropertyAssertion(pa1);

		PropertyDeclarationImpl p2 = new PropertyDeclarationImpl();
		p2.setName("http://lichtflut.de#Country");
		p2.setElementaryDataType(ElementaryDataType.STRING);
		PropertyAssertionImpl pa2 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasCountry"), p2);
		pa2.setCardinality(CardinalityBuilder.hasExcactlyOne());
		citySchema.addPropertyAssertion(pa2);

		citySchema.setLabelBuilder(StaticLabelBuilders.forCity());
		}
		return citySchema;
	}

	/**
	 * Creates a {@link ResourceSchema} with String, String {@link ElementaryDataType}s.
	 * @return schema
	 */
	private static ResourceSchema createOrganisationSchema(){

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

	/**
	 * Returns a random number.
	 * @param max
	 *            - max. number
	 * @return Random Number
	 */
	private static int getRandomNumer(final int max) {
		return (int) (Math.random() * max);
	}
}
