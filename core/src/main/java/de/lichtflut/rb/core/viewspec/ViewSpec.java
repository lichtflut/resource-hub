/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.RB;

/**
 * <p>
 *  Constants for view specifications.
 * </p>
 *
 * <p>
 * 	Created Jan 19, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ViewSpec {
	
	// -- TYPES -------------------------------------------
	
	ResourceID WIDGET = new SimpleResourceID(RB.COMMON_NAMESPACE_URI, "Widget");
	
	ResourceID LAYOUT = new SimpleResourceID(RB.COMMON_NAMESPACE_URI, "Layout");
	
	ResourceID PERSPECTIVE = new SimpleResourceID(RB.COMMON_NAMESPACE_URI,  "Perspective");
	
	ResourceID VIEW_PORT =new SimpleResourceID(RB.COMMON_NAMESPACE_URI,  "ViewPort");
	
	String PERSPECTIVE_URI = RB.COMMON_NAMESPACE_URI + "Perspective";
	
}
