/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.browsing;

import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.entity.VisualizationMode;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Base interface for providing application specific URLs.
 * </p>
 *
 * <p>
 * 	Created Feb 1, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ResourceLinkProvider {

	/**
	 * Get the URL pointing to a resource in a given mode.
	 * @param id The ID of the resource.
	 * @param mode The mode.
	 * @return The URL.
	 */
	String getUrlToResource(ResourceID id, VisualizationMode vis, DisplayMode mode);
	
}