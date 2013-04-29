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
