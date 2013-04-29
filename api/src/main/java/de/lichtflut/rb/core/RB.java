/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.core;

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

	String COMMON_NAMESPACE_URI = "http://rb.lichtflut.de/common#";

	// ----------------------------------------------------

	ResourceID LOCATION = new SimpleResourceID(COMMON_NAMESPACE_URI, "Location");

	ResourceID LANGUAGE = new SimpleResourceID(COMMON_NAMESPACE_URI, "Language");

	ResourceID CITY = new SimpleResourceID(COMMON_NAMESPACE_URI, "City");

	ResourceID COUNTRY = new SimpleResourceID(COMMON_NAMESPACE_URI, "Country");

	ResourceID PERSON = new SimpleResourceID(COMMON_NAMESPACE_URI, "Person");

	ResourceID ORGANIZATION = new SimpleResourceID(COMMON_NAMESPACE_URI, "Organization");

	ResourceID ORGANIZATIONAL_UNIT = new SimpleResourceID(COMMON_NAMESPACE_URI, "OrganizationalUnit");

	ResourceID PROCESS_ELEMENT = new SimpleResourceID(COMMON_NAMESPACE_URI, "ProcessElement");

	ResourceID EXPERTISE = new SimpleResourceID(COMMON_NAMESPACE_URI, "Expertise");

	ResourceID TREE_NODE = new SimpleResourceID(COMMON_NAMESPACE_URI, "TreeNode");

	// -- COMMON PROPERTIES --------------------------------------

	/**
	 * A node may depend on another.
	 */
	ResourceID DEPENDS_ON = new SimpleResourceID(COMMON_NAMESPACE_URI, "dependsOn");

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
     * Entities may belong to a special scope.
     */
    ResourceID HAS_SCOPE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasScope");

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
	 * The read access (private, protected, public).
	 */
	ResourceID HAS_READ_ACCESS = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasReadAccess");

	/**
	 * The write access (private, protected, public).
	 */
	ResourceID HAS_WRITE_ACCESS = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasWriteAccess");

	/**
	 * General title of something (e.g. a project).
	 */
	ResourceID HAS_TITLE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasTitle");

	/**
	 * A textual description.
	 */
	ResourceID HAS_DESCRIPTION = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasDescription");

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
	ResourceID IS_IN_CITY = new SimpleResourceID(COMMON_NAMESPACE_URI, "isInCity");

	/**
	 * The country.
	 */
	ResourceID IS_IN_COUNTRY = new SimpleResourceID(COMMON_NAMESPACE_URI, "isInCountry");

	/**
	 * The country.
	 */
	ResourceID IS_IN_CONTINENT = new SimpleResourceID(COMMON_NAMESPACE_URI, "isInContinent");

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

	/**
	 * Populations size of a location.
	 */
	ResourceID HAS_POPULATION_SIZE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasPopulationSize");

	/**
	 * ISO alpha 2 code of a country.
	 */
	ResourceID HAS_ISO_ALPHA2_CODE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasIsoAplpha2Code");

	/**
	 * ISO alpha 3 code of a country.
	 */
	ResourceID HAS_ISO_ALPHA3_CODE = new SimpleResourceID(COMMON_NAMESPACE_URI, "hasIsoAplpha3Code");

}
