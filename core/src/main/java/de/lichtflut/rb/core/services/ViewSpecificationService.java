/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.WidgetSpec;

/**
 * <p>
 *  Service interface for view specifications and widgets.
 * </p>
 *
 * <p>
 * 	Created Jan 30, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ViewSpecificationService {
	
	/**
	 * Find a perspective specification by it's unique ID.
	 * @param id The ID.
	 * @return The perspective specification or null if not found.
	 */
	Perspective findPerspective(ResourceID id);
	
	// ----------------------------------------------------
	
	/**
	 * Find a widget specification by it's unique ID.
	 * @param id The ID.
	 * @return The widget specification or null if not found.
	 */
	WidgetSpec findWidgetSpec(ResourceID id);
	
	/**
	 * Store the widget specification. Update or create.
	 * @param widgetSpec The specification to store.
	 */
	void store(WidgetSpec widgetSpec);
	
}
