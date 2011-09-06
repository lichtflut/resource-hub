/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.metamodel;

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

	// ---------- Person ------------------------------------------

	ResourceID HAS_FORENAME = new SimpleResourceID(NAMESPACE_URI, "Firstname");

	ResourceID HAS_SURNAME = new SimpleResourceID(NAMESPACE_URI, "Lastname");

	// ---------- Address -----------------------------------------

	ResourceID HAS_HOUSNR = new SimpleResourceID(NAMESPACE_URI, "HouseNr");

	ResourceID HAS_STREET = new SimpleResourceID(NAMESPACE_URI, "Street");

	ResourceID HAS_CITY_RESOURCE = new SimpleResourceID(NAMESPACE_URI, "City");

	// ---------- Country -----------------------------------------

	ResourceID HAS_CITY = new SimpleResourceID(NAMESPACE_URI, "City");

	ResourceID HAS_COUNTRY = new SimpleResourceID(NAMESPACE_URI, "Country");

	// ---------- Organization ------------------------------------

	ResourceID HAS_ORGA_NAME = new SimpleResourceID(NAMESPACE_URI, "Name");

}
