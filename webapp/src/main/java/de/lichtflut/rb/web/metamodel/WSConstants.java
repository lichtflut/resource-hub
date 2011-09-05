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

	ResourceID HAS_FORENAME = new SimpleResourceID(NAMESPACE_URI, "Vorname");

	ResourceID HAS_SURNAME = new SimpleResourceID(NAMESPACE_URI, "Nachname");

}
