/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.apriori;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

/**
 * <p>
 *  A priori known names for geographical locations.
 * </p>
 *
 * <p>
 * 	Created Feb 17, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface Geonames {
	
	String GEO_NAMESPACE_URI = "http://l2r.info/geonames#";
	
	// ----------------------------------------------------
	
	ResourceID EUROPE = new SimpleResourceID(GEO_NAMESPACE_URI, "Europe");
	
	ResourceID NORTH_AMERICA = new SimpleResourceID(GEO_NAMESPACE_URI, "NorthAmerica");
	
	ResourceID SOUTH_AMERICA = new SimpleResourceID(GEO_NAMESPACE_URI, "SouthAmerica");
	
	ResourceID AFRICA = new SimpleResourceID(GEO_NAMESPACE_URI, "Africa");
	
	ResourceID ASIA = new SimpleResourceID(GEO_NAMESPACE_URI, "Asia");
	
	ResourceID OCEANIA = new SimpleResourceID(GEO_NAMESPACE_URI, "Oceania");
	
	ResourceID ANTARCTICA = new SimpleResourceID(GEO_NAMESPACE_URI, "Antarctica");
	
}
