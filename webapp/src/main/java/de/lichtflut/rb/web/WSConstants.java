/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

/**
 * <p>
 *  RDF constants for websample.
 * </p>
 *
 * <p>
 * 	Created Sep 2, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface WSConstants {

	String NAMESPACE_URI = "http://lichtflut.de#";
	
	// ---------- Common ----------------------------------
	
	ResourceID HAS_EMAIL = new SimpleResourceID(NAMESPACE_URI, "hasEmail");

	// ---------- Person ----------------------------------

	ResourceID HAS_FORENAME = new SimpleResourceID(NAMESPACE_URI, "hasFirstname");

	ResourceID HAS_SURNAME = new SimpleResourceID(NAMESPACE_URI, "hasLastname");
	
	// ---------- Address ---------------------------------
	
	ResourceID HAS_HOUSNR = new SimpleResourceID(NAMESPACE_URI, "hasHouseNr");

	ResourceID HAS_STREET = new SimpleResourceID(NAMESPACE_URI, "hasStreet");

	// ---------- Country ---------------------------------

	ResourceID HAS_ZIPCODE = new SimpleResourceID(NAMESPACE_URI, "Zipcode");

	ResourceID HAS_CITY = new SimpleResourceID(NAMESPACE_URI, "hasCity");

	ResourceID HAS_COUNTRY = new SimpleResourceID(NAMESPACE_URI, "hasCountry");

	// ---------- Organization ----------------------------

	ResourceID HAS_ORGA_NAME = new SimpleResourceID(NAMESPACE_URI, "hasName");

}
