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

	/**
	 * Private Constructor.
	 */
	private MockNewRBEntityFactory() {
	};

	// ------------------------------------------------------------

	public static List<IRBEntity> getAllEntities(){
		List<IRBEntity> list = new ArrayList<IRBEntity>();
		list.addAll(getListOfPersons());
		list.addAll(getListOfOrganizations());
		list.add(createAddress());
		return list;
	}
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
		List<Object> dateOfBirth = new ArrayList<Object>();
		List<Object> childrenList = new ArrayList<Object>();
		firstName.add("Ravi");
		lastName.add("Knox");
		address.add(createAddress());
		dateOfBirth.add(new Date(1985, 12, 23));
		emailList.add("max@moritz.de");
		emailList.add("chef@koch.de");
		childrenList.add(createChildlessComplexPerson(MockResourceSchemaFactory.createPersonSchema()));
		list.add(firstName);
		list.add(lastName);
		list.add(address);
		list.add(dateOfBirth);
		list.add(emailList);
		list.add(childrenList);
		int counter = 0;
		NewRBEntity entity = new NewRBEntity(MockResourceSchemaFactory.createPersonSchema());
		for (IRBField field : entity.getAllFields()) {
			if (counter < list.size()) {
				field.setFieldValues(list.get(counter));
			}
			counter++;
		}
		return createPerson("Oliver", "Tigges", createAddress("Lustheide", "50", createCity("51427", "Bergisch Gladbach", "Germany")),
				new Date(1975, 8, 15), "otigges@lichtflut.de", null);
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
		IRBEntity bergischGladbach =  createCity("51427", "Bergisch Gladbach", "Germany");
		IRBEntity lustheide50 = createAddress("Lustheide", "50", bergischGladbach);
		list.add(lustheide50);
		list.add(bergischGladbach);
		list.add(createPerson("Oliver", "Tigges", lustheide50, new Date(1975, 8, 15), "otigges@lichtflut.de", null));

		IRBEntity lustheide11 = createAddress("Lustheide", "11", bergischGladbach);
		list.add(lustheide11);
		list.add(createPerson("Alexander", "von Aesch", lustheide11, new Date(1972, 2, 12), "avaesch@lichtflut.de", null));
		
		IRBEntity bergischGladbach51427 = createCity("51427", "Bergisch Gladbach", "Germany");
		list.add(bergischGladbach51427);
		IRBEntity lustheide66 = createAddress("Lustheide", "66", bergischGladbach51427);
		list.add(lustheide66);
		list.add(createPerson("Nils", "Bleisch", lustheide66, new Date(1987, 1, 1), "nbleisch@lichtflut.de", null));
		
		IRBEntity leverkusen = createCity("50435", "Leverkusen", "Germany");
		list.add(leverkusen);
		IRBEntity bahnhofStr = createAddress("Bahnhofstrasse", "7", leverkusen);
		list.add(bahnhofStr);
		IRBEntity benAderhold = createPerson("Ben", "Aderhold", bahnhofStr,	new Date(2011, 8, 15), null, null);
		list.add(benAderhold);
		list.add(createPerson("Erik", "Aderhold", bahnhofStr, new Date(1974, 2, 26), "eaderhold@lichtflut.de", benAderhold));
		
		IRBEntity bergischGladbach51469 = createCity("51469", "Bergisch Gladbach", "Germany");
		list.add(bergischGladbach51469);
		IRBEntity muelheimerStr = createAddress("Mülheimer Straße", "216", bergischGladbach51469);
		list.add(muelheimerStr);
		list.add(createPerson("Raphael", "Esterle", muelheimerStr, new Date(1991, 10, 20), "resterle@lichtflut.de", null));
		list.add(createPerson("Ravi", "Knox", muelheimerStr, new Date(1985, 12, 23), "rknox@lichtflut.de", null));
		list.add(createPerson(null,null, createAddress(null, null, createCity(null, null, null)),
				null, null, null));
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
		List<Object> cityList = new ArrayList<Object>();
		streetList.add("Lustheide");
		houseNrList.add("85");
		cityList.add(createCity());
		list.add(streetList);
		list.add(houseNrList);
		list.add(cityList);
		int counter = 0;
		NewRBEntity entity = new NewRBEntity(MockResourceSchemaFactory.createAddressSchema());
		for (IRBField field : entity.getAllFields()) {
			field.setFieldValues(list.get(counter));
			counter++;
		}
		return entity;
	}

	public static IRBEntity createCity(){
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<Object> zipCodelist = new ArrayList<Object>();
		List<Object> cityList = new ArrayList<Object>();
		List<Object> countryList = new ArrayList<Object>();
		zipCodelist.add("51427");
		cityList.add("Bergisch Gladbach");
		countryList.add("Germany");
		list.add(zipCodelist);
		list.add(cityList);
		list.add(countryList);
		int counter = 0;
		NewRBEntity entity = new NewRBEntity(MockResourceSchemaFactory.createCitySchema());
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
		NewRBEntity entity = new NewRBEntity(MockResourceSchemaFactory.createOrganisationSchema());
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
	public static IRBEntity createOrganization(String name, IRBEntity ceo, IRBEntity address, boolean independent,
			List<IRBEntity> member, String description){
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<Object> nameList = new ArrayList<Object>();
		List<Object> ceoList = new ArrayList<Object>();
		List<Object> locationList = new ArrayList<Object>();
		List<Object> independentList = new ArrayList<Object>();
		List<Object> memberList = new ArrayList<Object>();
		List<Object> descriptionList = new ArrayList<Object>();
		nameList.add(name);
		ceoList.add(ceo);
		locationList.add(address);
		independentList.add(independent);
		memberList.addAll(memberList);
		descriptionList.add(description);
		list.add(nameList);
		list.add(ceoList);
		list.add(locationList);
		list.add(independentList);
		list.add(memberList);
		list.add(descriptionList);
		int counter = 0;
		NewRBEntity entity = new NewRBEntity(MockResourceSchemaFactory.createOrganisationSchema());
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
	public static List<IRBEntity> getListOfOrganizations(){
		List<IRBEntity> list = new ArrayList<IRBEntity>();
		list.add(createOrganization("Microsoft", createPerson("Bill", "Gates", createAddress(null, null, createCity(null, null, "United Stated of America")), new Date(28, 10, 1955), "bill@microsoft.com", null), createAddress(null, null, null), true, null, "Microsoft sells OSs"));
		list.add(createOrganization());
		return list;
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
	private static NewRBEntity createPerson(String firstname, String lastname, IRBEntity address1,Date date,String email,IRBEntity children) {
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<Object> firstName = new ArrayList<Object>();
		List<Object> lastName = new ArrayList<Object>();
		List<Object> address = new ArrayList<Object>();
		List<Object> emailList = new ArrayList<Object>();
		List<Object> dateOfBirth = new ArrayList<Object>();
		List<Object> childrenList = new ArrayList<Object>();
		firstName.add(firstname);
		lastName.add(lastname);
		address.add(address1);
		dateOfBirth.add(date);
		emailList.add(email);
		childrenList.add(children);
		list.add(firstName);
		list.add(lastName);
		list.add(address);
		list.add(dateOfBirth);
		list.add(emailList);
		list.add(childrenList);
		int counter = 0;
		NewRBEntity entity = new NewRBEntity(MockResourceSchemaFactory.createPersonSchema());
		for (IRBField field : entity.getAllFields()) {
			if (counter < list.size()) {
				field.setFieldValues(list.get(counter));
			}
			counter++;
		}
		return entity;
	}

	private static IRBEntity createAddress(String street, String houseNr, IRBEntity city){
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<Object> streetList = new ArrayList<Object>();
		List<Object> houseNrList = new ArrayList<Object>();
		List<Object> cityList = new ArrayList<Object>();
		streetList.add(street);
		houseNrList.add(houseNr);
		cityList.add(city);
		list.add(streetList);
		list.add(houseNrList);
		list.add(cityList);
		int counter = 0;
		NewRBEntity entity = new NewRBEntity(MockResourceSchemaFactory.createAddressSchema());
		for (IRBField field : entity.getAllFields()) {
			field.setFieldValues(list.get(counter));
			counter++;
		}
		return entity;
	}

	private static IRBEntity createCity(String zipCode, String city, String country){
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<Object> cityList = new ArrayList<Object>();
		List<Object> zipCodeList = new ArrayList<Object>();
		List<Object> countryList = new ArrayList<Object>();
		zipCodeList.add(zipCode);
		cityList.add(city);
		countryList.add(country);
		list.add(zipCodeList);
		list.add(cityList);
		list.add(countryList);
		int counter = 0;
		NewRBEntity entity = new NewRBEntity(MockResourceSchemaFactory.createCitySchema());
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
	 * Returns a random number.
	 * @param max
	 *            - max. number
	 * @return Random Number
	 */
	private static int getRandomNumer(final int max) {
		return (int) (Math.random() * max);
	}
}
