/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.views.SNText;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.entity.impl.RBEntityImpl;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionImpl;
import de.lichtflut.rb.web.WSConstants;

/**
 * <p>
 * This class provides static {@link RBEntityImpl}s for testing purposes.
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

	public static List<RBEntity> createMockEntities() {
		List<RBEntity> list = new ArrayList<RBEntity>();
		final ResourceSchema personSchema = MockResourceSchemaFactory.getInstance().getPersonSchema();
		final ResourceSchema orgSchema = MockResourceSchemaFactory.getInstance().getOrganizationSchema();
		list.addAll(getListOfPersons(personSchema));
		list.addAll(getListOfOrganizations(orgSchema, personSchema));
		list.add(createAddress());
		return list;
	}
	/**
	 * <p>
	 * Creates a {@link RBEntityImpl} with appropriate values.
	 * </p>
	 * <p>
	 * {@link Datatype}: Date, String, Integer, Resource
	 * </p>
	 *
	 * @return Instance of {@link RBEntityImpl}
	 */
	@SuppressWarnings("deprecation")
	public static RBEntityImpl createPerson() {
		return createPerson("Oliver", "Tigges", createAddress("Lustheide", "50", createCity("51427", "Bergisch Gladbach", "Germany")),
				new Date(1975, 8, 15), "otigges@lichtflut.de", null, MockResourceSchemaFactory.getInstance().getPersonSchema());
	}

	/**
	 * Creates a {@link RBEntityImpl} with appropriate values.
	 *
	 * @return Instance of {@link RBEntityImpl}
	 */
	public static RBEntityImpl createStringBasedPerson() {
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
		RBEntityImpl entity = new RBEntityImpl(createOnlyStringSchema());
		for (RBField field : entity.getAllFields()) {
			field.setValues(list.get(counter));
			counter++;
		}
		return entity;
	}

	/**
	 * return a list of {@link RBEntity}.
	 * @param personSchema 
	 * @return -
	 */
	@SuppressWarnings("deprecation")
	public static List<RBEntity> getListOfPersons(ResourceSchema schema){
		List<RBEntity> list = new ArrayList<RBEntity>();
		RBEntity bergischGladbach =  createCity("51427", "Bergisch Gladbach", "Germany");
		RBEntity lustheide50 = createAddress("Lustheide", "50", bergischGladbach);
		list.add(lustheide50);
		list.add(bergischGladbach);
		list.add(createPerson("Oliver", "Tigges", lustheide50, new Date(1975, 8, 15), "otigges@lichtflut.de", null, schema));

		RBEntity lustheide11 = createAddress("Lustheide", "11", bergischGladbach);
		list.add(lustheide11);
		list.add(createPerson("Alexander", "von Aesch", lustheide11, new Date(1972, 2, 12), "avaesch@lichtflut.de", null, schema));
		
		RBEntity bergischGladbach51427 = createCity("51427", "Bergisch Gladbach", "Germany");
		list.add(bergischGladbach51427);
		RBEntity lustheide66 = createAddress("Lustheide", "66", bergischGladbach51427);
		list.add(lustheide66);
		list.add(createPerson("Nils", "Bleisch", lustheide66, new Date(1987, 1, 1), "nbleisch@lichtflut.de", null, schema));
		
		RBEntity leverkusen = createCity("50435", "Leverkusen", "Germany");
		list.add(leverkusen);
		RBEntity bahnhofStr = createAddress("Bahnhofstrasse", "7", leverkusen);
		list.add(bahnhofStr);
		RBEntity benAderhold = createPerson("Ben", "Aderhold", bahnhofStr,	new Date(2011, 8, 15), null, null, schema);
		list.add(benAderhold);
		list.add(createPerson("Erik", "Aderhold", bahnhofStr, new Date(1974, 2, 26), "eaderhold@lichtflut.de", benAderhold, schema));
		
		RBEntity bergischGladbach51469 = createCity("51469", "Bergisch Gladbach", "Germany");
		list.add(bergischGladbach51469);
		RBEntity muelheimerStr = createAddress("Muelheimer Strasse", "216", bergischGladbach51469);
		list.add(muelheimerStr);
		list.add(createPerson("Raphael", "Esterle", muelheimerStr, new Date(1991, 10, 20), "resterle@lichtflut.de", null, schema));
		list.add(createPerson("Ravi", "Knox", muelheimerStr, new Date(1985, 12, 23), "rknox@lichtflut.de", null, schema));
		return list;
	}

	/**
	 * Return an Address.
	 * @return -
	 */
	public static RBEntity createAddress(){
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
		RBEntityImpl entity = new RBEntityImpl(MockResourceSchemaFactory.getInstance().getAddressSchema());
		for (RBField field : entity.getAllFields()) {
			field.setValues(list.get(counter));
			counter++;
		}
		return entity;
	}

	public static RBEntity createCity(){
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
		RBEntityImpl entity = new RBEntityImpl(MockResourceSchemaFactory.getInstance().getCitySchema());
		for (RBField field : entity.getAllFields()) {
			field.setValues(list.get(counter));
			counter++;
		}
		return entity;
	}

	/**
	 * Return an Organization.
	 * @param orgSchema 
	 * @param string2 
	 * @param object 
	 * @param b 
	 * @param rbEntity 
	 * @param rbEntityImpl 
	 * @param string 
	 * @return -
	 */
	public static RBEntity createOrganization(String string, RBEntityImpl rbEntityImpl, RBEntity rbEntity, boolean b, Object object, String string2, ResourceSchema schema){
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<Object> nameList = new ArrayList<Object>();
		List<Object> ceoList = new ArrayList<Object>();
		List<Object> locationList = new ArrayList<Object>();
		List<Object> independentList = new ArrayList<Object>();
		List<Object> memberList = new ArrayList<Object>();
		List<Object> descriptionList = new ArrayList<Object>();
		nameList.add("Lichtflut F & E GmbH");
		independentList.add(true);
		descriptionList.add("Wir betreiben Grundlagenforschung im Bereich der semantischen Informationsverarbeitung, helfen unseren Kunden bei der Erstellung von Meta-Modellen ihrer Unternehmen und entwickeln Produkte für ontologiebasierte Informationsverwaltung.");
		list.add(nameList);
		list.add(ceoList);
		list.add(locationList);
		list.add(independentList);
		list.add(memberList);
		list.add(descriptionList);
		int counter = 0;
		RBEntityImpl entity = new RBEntityImpl(schema);
		for (RBField field : entity.getAllFields()) {
			field.setValues(list.get(counter));
			counter++;
		}
		return entity;
	}

	/**
	 * return a list of {@link RBEntity}.
	 * @return -
	 */
	@SuppressWarnings("deprecation")
	public static List<RBEntity> getListOfOrganizations(ResourceSchema orgSchema, ResourceSchema personSchema){
		List<RBEntity> list = new ArrayList<RBEntity>();
		list.add(createOrganization("Microsoft", 
				createPerson("Bill", "Gates", createAddress(null, null, createCity(null, null, "United Stated of America")), new Date(28, 10, 1955), "bill@microsoft.com", null, personSchema), 
				createAddress(null, null, null), true, null, "Microsoft sells OSs", orgSchema));
		return list;
	}

	// ------------------------------------------------------------

	/**
	 * <p>
	 * Creates a {@link RBEntityImpl} with appropriate values.
	 * </p>
	 * <p>
	 * {@link Datatype}: Date, String, Integer, Resource
	 * </p>
	 * @param date -
	 * @param email -
	 * @param age -
	 * @param resource -
	 * @return Instance of {@link RBEntityImpl}
	 */
	private static RBEntityImpl createPerson(String firstname, String lastname, RBEntity address1,Date date,String email,RBEntity children
			, ResourceSchema schema) {
		SNResource node = new SNResource();
		
		SNOPS.associate(node, WSConstants.HAS_FORENAME, new SNText(firstname));
		SNOPS.associate(node, WSConstants.HAS_SURNAME, new SNText(lastname));
		
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<Object> firstName = new ArrayList<Object>();
		List<Object> lastName = new ArrayList<Object>();
		List<Object> address = new ArrayList<Object>();
		List<Object> emailList = new ArrayList<Object>();
		List<Object> dateOfBirth = new ArrayList<Object>();
		List<Object> childrenList = new ArrayList<Object>();
		firstName.add(firstname);
		lastName.add(lastname);
		dateOfBirth.add(date);
		emailList.add(email);
		if (children != null) {
		} else {
			childrenList.add(null);
		}
		list.add(firstName);
		list.add(lastName);
		list.add(address);
		list.add(dateOfBirth);
		list.add(emailList);
		list.add(childrenList);
		int counter = 0;
		RBEntityImpl entity = new RBEntityImpl(node, schema);
		for (RBField field : entity.getAllFields()) {
			if (counter < list.size()) {
				field.setValues(list.get(counter));
			}
			counter++;
		}
		return entity;
	}

	private static RBEntity createAddress(String street, String houseNr, RBEntity city){
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<Object> streetList = new ArrayList<Object>();
		List<Object> houseNrList = new ArrayList<Object>();
		List<Object> cityList = new ArrayList<Object>();
		streetList.add(street);
		houseNrList.add(houseNr);
		list.add(streetList);
		list.add(houseNrList);
		list.add(cityList);
		int counter = 0;
		RBEntityImpl entity = new RBEntityImpl(MockResourceSchemaFactory.getInstance().getAddressSchema());
		for (RBField field : entity.getAllFields()) {
			field.setValues(list.get(counter));
			counter++;
		}
		return entity;
	}

	private static RBEntity createCity(String zipCode, String city, String country){
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
		RBEntityImpl entity = new RBEntityImpl(MockResourceSchemaFactory.getInstance().getCitySchema());
		for (RBField field : entity.getAllFields()) {
			field.setValues(list.get(counter));
			counter++;
		}
		return entity;
	}
	
	/**
	 * Creates a {@link RBEntityImpl} based on createComplexPerson but without
	 * children.
	 * @param schema -
	 * @return NewRBEntity
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	private static RBEntityImpl createChildlessComplexPerson(final ResourceSchema schema) {
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
		RBEntityImpl entity = new RBEntityImpl(schema);
		for (RBField field : entity.getAllFields()) {
			field.setValues(list.get(counter));
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
		ResourceSchemaImpl schema = new ResourceSchemaImpl();
		TypeDefinitionImpl p2 = new TypeDefinitionImpl();
		TypeDefinitionImpl p3 = new TypeDefinitionImpl();
		TypeDefinitionImpl p4 = new TypeDefinitionImpl();

		p2.setElementaryDataType(Datatype.STRING);
		p3.setElementaryDataType(Datatype.STRING);
		p4.setElementaryDataType(Datatype.STRING);

		p2.addConstraint(ConstraintBuilder.buildConstraint(".*@.*"));

		PropertyDeclarationImpl pa2 = new PropertyDeclarationImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasEmail"), p2);
		PropertyDeclarationImpl pa3 = new PropertyDeclarationImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasAge"), p3);
		PropertyDeclarationImpl pa4 = new PropertyDeclarationImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasChild"), p4);

		pa2.setCardinality(CardinalityBuilder.hasAtLeastOneUpTo(5));
		pa3.setCardinality(CardinalityBuilder.hasExcactlyOne());
		pa4.setCardinality(CardinalityBuilder.hasOptionalOneToMany());

		schema.addPropertyDeclaration(pa2);
		schema.addPropertyDeclaration(pa3);
		schema.addPropertyDeclaration(pa4);

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
