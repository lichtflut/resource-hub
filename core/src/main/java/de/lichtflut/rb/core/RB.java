/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core;

import org.arastreju.sge.context.Context;
import org.arastreju.sge.context.SimpleContextID;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

/**
 *  <p>
 *   A priori known URIs for RB.
 * </p>
 *
 * Created: Aug 25, 2011
 *
 * @author Ravi Knox
 */
public interface RB {

	String CTX_NAMESPACE_URI = "http://rb.lichtflut.de/contexts#";
	
	String COMMON_NAMESPACE_URI = "http://rb.lichtflut.de/common#";
	
	// -- CONTEXTS ----------------------------------------
	
	/**
	 * Represents the schema context. Each schema.based expression has to be applied to this context.
	 */
	Context TYPE_SYSTEM_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "TypeSystem");
	
	Context SCHEMA_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "Schema");
	
	Context DOMAIN_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "Domain");
	
	Context PUBLIC_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "Public");
	
	Context PRIVATE_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "Private");
	
	Context LOCALE_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "Locale");
	
	Context DEFAULT_LOCALE_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "DefaultLocale");
	
	Context LOCALE_ENGLISH_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "English");
	
	Context LOCALE_GERMAN_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "German");
	
	Context LOCALE_FRENCH_CONTEXT = new SimpleContextID(CTX_NAMESPACE_URI, "French");
	
	// ----------------------------------------------------

	ResourceID LOCATION = new SimpleResourceID(COMMON_NAMESPACE_URI, "Location");
	
	ResourceID PERSON = new SimpleResourceID(COMMON_NAMESPACE_URI, "Person");
	
	ResourceID ORGANIZATION = new SimpleResourceID(COMMON_NAMESPACE_URI, "Organization");
	
	ResourceID PROJECT = new SimpleResourceID(COMMON_NAMESPACE_URI, "Project");
	
	ResourceID ADDRESS = new SimpleResourceID(COMMON_NAMESPACE_URI, "Address");
	
	ResourceID NOTE = new SimpleResourceID(COMMON_NAMESPACE_URI, "Note");
	
	ResourceID PROCESS = new SimpleResourceID(COMMON_NAMESPACE_URI, "Process");
	
	ResourceID SKILL = new SimpleResourceID(COMMON_NAMESPACE_URI, "Skill");
	
	ResourceID EXPERTISE = new SimpleResourceID(COMMON_NAMESPACE_URI, "Expertise");
	
	// -- COMMON PROPERTIES --------------------------------------

	/**
	 * Child node in tree structures.
	 */
	ResourceID HAS_CHILD_NODE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasChildNode");
	
	/**
	 * Parent node in tree structures.
	 */
	ResourceID HAS_PARENT_NODE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasParentNode");
	
	/**
	 * Superordinate units.
	 */
	ResourceID HAS_SUPERORDINATE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasSuperordinate");
	
	/**
	 * Subordinate units.
	 */
	ResourceID HAS_SUBORDINATE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasSubordinate");

	/**
	 * General ID of something (e.g. an application).
	 */
	ResourceID HAS_ID = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasID");
	
	/**
	 * General name of something (e.g. an organization).
	 */
	ResourceID HAS_NAME = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasName");
	
	/**
	 * The value.
	 */
	ResourceID HAS_VALUE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasValue");

	/**
	 * General title of something (e.g. a project).
	 */
	ResourceID HAS_TITLE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasTitle");
	
	/**
	 * A textual description.
	 */
	ResourceID HAS_DESCRIPTION = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasDescription");;
	
	/**
	 * General name of something (e.g. an organization).
	 */
	ResourceID HAS_STATUS = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasStatus");
	
	/**
	 * First name of a person.
	 */
	ResourceID HAS_FIRST_NAME = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasFirstname");
	
	/**
	 * Last name of a person.
	 */
	ResourceID HAS_LAST_NAME = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasLastname");
	
	/**
	 * The date of birth.
	 */
	ResourceID HAS_DATE_OF_BIRTH = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasDateOfBirth");
	
	/**
	 * Has email.
	 */
	ResourceID HAS_EMAIL = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasEmail");
	
	/**
	 * Has contact data.
	 */
	ResourceID HAS_CONTACT_DATA = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasContactData");
	
	/**
	 * Has vCard type.
	 */
	ResourceID HAS_VCARD_TYPE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasVCardType");
	
	/**
	 * Is employed by.
	 */
	ResourceID IS_EMPLOYED_BY = new SimpleResourceID(COMMON_NAMESPACE_URI, "isEmployedBy");
	
	/**
	 * Has general manager (of company).
	 */
	ResourceID HAS_GENERAL_MANAGER = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasGeneralManager");
	
	/**
	 * An organizations domicile.
	 */
	ResourceID HAS_DOMICILE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasDomicile");
	
	/**
	 * The address.
	 */
	ResourceID HAS_ADDRESS = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasAddress");
	
	/**
	 * The street.
	 */
	ResourceID HAS_STREET = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasStreet");
	
	/**
	 * The house number.
	 */
	ResourceID HAS_HOUSE_NO = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasHouseNo");
	
	/**
	 * The zipcode.
	 */
	ResourceID HAS_ZIPCODE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasZipcode");
	
	/**
	 * The city.
	 */
	ResourceID HAS_CITY = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasCity");
	
	/**
	 * The country.
	 */
	ResourceID HAS_COUNTRY = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasCountry");
	
	/**
	 * The owner.
	 */
	ResourceID HAS_OWNER = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasOwner");
	
	/**
	 * The lead.
	 */
	ResourceID HAS_LEAD = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasLead");
	
	/**
	 * Is non-profit organization.
	 */
	ResourceID IS_NPO = new SimpleResourceID(COMMON_NAMESPACE_URI, "isNPO");

}
