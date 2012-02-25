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
public interface Languages {
	
	String LANGUAGE_NAMESPACE_URI = "http://l2r.info/languages#";
	
	// ----------------------------------------------------
	
	ResourceID ENGLISH = new SimpleResourceID(LANGUAGE_NAMESPACE_URI, "Europe");
	
	ResourceID GERMAN = new SimpleResourceID(LANGUAGE_NAMESPACE_URI, "German");
	
	ResourceID FRENCH = new SimpleResourceID(LANGUAGE_NAMESPACE_URI, "French");
	
}
