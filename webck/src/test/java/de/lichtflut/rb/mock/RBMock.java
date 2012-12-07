/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import org.arastreju.sge.context.Context;
import org.arastreju.sge.context.SimpleContextID;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

/**
 * <p>
 * This class provides several constants for testing purposes.
 * </p>
 * Created: May 7, 2012
 *
 * @author Ravi Knox
 */
public class RBMock {


	public static final String CTX_NAMESPACE_URI = "http://rb.test.lichtflut.de/contexts#";

	public static final String COMMON_NAMESPACE_URI = "http://rb.test.lichtflut.de/common#";

	// -- CONTEXTS ----------------------------------------

	/**
	 * Represents the schema context. Each schema.based expression has to be applied to this context.
	 */
	public static final Context TYPE_SYSTEM_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "TypeSystem");

	public static final Context SCHEMA_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "Schema");

	public static final Context DOMAIN_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "Domain");

	public static final Context PUBLIC_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "Public");

	public static final Context PRIVATE_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "Private");

	// ----------------------------------------------------

	public static final ResourceID LOCATION = new SimpleResourceID(COMMON_NAMESPACE_URI, "Location");

	public static final ResourceID LANGUAGE = new SimpleResourceID(COMMON_NAMESPACE_URI, "Language");

	public static final ResourceID CITY = new SimpleResourceID(COMMON_NAMESPACE_URI, "City");

	public static final ResourceID COUNTRY = new SimpleResourceID(COMMON_NAMESPACE_URI, "Country");

	public static final ResourceID CONTINENT = new SimpleResourceID(COMMON_NAMESPACE_URI, "Continent");

	public static final ResourceID PERSON = new SimpleResourceID(COMMON_NAMESPACE_URI, "Person");

	public static final ResourceID ORGANIZATION = new SimpleResourceID(COMMON_NAMESPACE_URI, "Organization");

	public static final ResourceID ORGANIZATIONAL_UNIT = new SimpleResourceID(COMMON_NAMESPACE_URI, "OrganizationalUnit");

	public static final ResourceID PROJECT = new SimpleResourceID(COMMON_NAMESPACE_URI, "Project");

	public static final ResourceID ADDRESS = new SimpleResourceID(COMMON_NAMESPACE_URI, "Address");

	public static final ResourceID NOTE = new SimpleResourceID(COMMON_NAMESPACE_URI, "Note");

	public static final ResourceID PROCESS = new SimpleResourceID(COMMON_NAMESPACE_URI, "Process");

	public static final ResourceID PROCESS_ELEMENT = new SimpleResourceID(COMMON_NAMESPACE_URI, "ProcessElement");

	public static final ResourceID SKILL = new SimpleResourceID(COMMON_NAMESPACE_URI, "Skill");

	public static final ResourceID EXPERTISE = new SimpleResourceID(COMMON_NAMESPACE_URI, "Expertise");

	// -- COMMON PROPERTIES --------------------------------------

	/**
	 * Child node in tree structures.
	 */
	public static final ResourceID DEPENDS_ON = new SimpleResourceID(COMMON_NAMESPACE_URI, "dependsOn");

	/**
	 * Child node in tree structures.
	 */
	public static final ResourceID HAS_CHILD_NODE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasChildNode");

	/**
	 * Parent node in tree structures.
	 */
	public static final ResourceID HAS_PARENT_NODE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasParentNode");

	/**
	 * Superordinate units.
	 */
	public static final ResourceID HAS_SUPERORDINATE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasSuperordinate");

	/**
	 * Subordinate units.
	 */
	public static final ResourceID HAS_SUBORDINATE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasSubordinate");

	/**
	 * General ID of something (e.g. an application).
	 */
	public static final ResourceID HAS_ID = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasID");

	/**
	 * General name of something (e.g. an organization).
	 */
	public static final ResourceID HAS_NAME = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasName");

	/**
	 * The value.
	 */
	public static final ResourceID HAS_VALUE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasValue");

	/**
	 * General title of something (e.g. a project).
	 */
	public static final ResourceID HAS_TITLE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasTitle");

	/**
	 * A textual description.
	 */
	public static final ResourceID HAS_DESCRIPTION = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasDescription");;

	/**
	 * General name of something (e.g. an organization).
	 */
	public static final ResourceID HAS_STATUS = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasStatus");

	/**
	 * First name of a person.
	 */
	public static final ResourceID HAS_FIRST_NAME = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasFirstname");

	/**
	 * Last name of a person.
	 */
	public static final ResourceID HAS_LAST_NAME = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasLastname");

	/**
	 * The date of birth.
	 */
	public static final ResourceID HAS_DATE_OF_BIRTH = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasDateOfBirth");

	/**
	 * Has email.
	 */
	public static final ResourceID HAS_EMAIL = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasEmail");

	/**
	 * Has contact data.
	 */
	public static final ResourceID HAS_CONTACT_DATA = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasContactData");

	/**
	 * Has vCard type.
	 */
	public static final ResourceID HAS_VCARD_TYPE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasVCardType");

	/**
	 * Is employed by.
	 */
	public static final ResourceID IS_EMPLOYED_BY = new SimpleResourceID(COMMON_NAMESPACE_URI, "isEmployedBy");

	/**
	 * Has general manager (of company).
	 */
	public static final ResourceID HAS_GENERAL_MANAGER = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasGeneralManager");

	/**
	 * An organizations domicile.
	 */
	public static final ResourceID HAS_DOMICILE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasDomicile");

	/**
	 * The address.
	 */
	public static final ResourceID HAS_ADDRESS = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasAddress");

	/**
	 * The street.
	 */
	public static final ResourceID HAS_STREET = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasStreet");

	/**
	 * The house number.
	 */
	public static final ResourceID HAS_HOUSE_NO = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasHouseNo");

	/**
	 * The zipcode.
	 */
	public static final ResourceID HAS_ZIPCODE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasZipcode");

	/**
	 * The entities' avatar
	 */
	public static final ResourceID HAS_AVATAR = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasAvatar");

	/**
	 * The city.
	 */
	public static final ResourceID IS_IN_CITY = new SimpleResourceID(COMMON_NAMESPACE_URI, "isInCity");

	/**
	 * The country.
	 */
	public static final ResourceID IS_IN_COUNTRY = new SimpleResourceID(COMMON_NAMESPACE_URI, "isInCountry");

	/**
	 * The country.
	 */
	public static final ResourceID IS_IN_CONTINENT = new SimpleResourceID(COMMON_NAMESPACE_URI, "isInContinent");

	/**
	 * The owner.
	 */
	public static final ResourceID HAS_OWNER = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasOwner");

	/**
	 * The lead.
	 */
	public static final ResourceID HAS_LEAD = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasLead");

	/**
	 * Is non-profit organization.
	 */
	public static final ResourceID IS_NPO = new SimpleResourceID(COMMON_NAMESPACE_URI, "isNPO");

	/**
	 * Populations size of a location.
	 */
	public static final ResourceID HAS_POPULATION_SIZE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasPopulationSize");

	/**
	 * ISO alpha 2 code of a country.
	 */
	public static final ResourceID HAS_ISO_ALPHA2_CODE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasIsoAplpha2Code");

	/**
	 * ISO alpha 3 code of a country.
	 */
	public static final ResourceID HAS_ISO_ALPHA3_CODE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasIsoAplpha3Code");

}
