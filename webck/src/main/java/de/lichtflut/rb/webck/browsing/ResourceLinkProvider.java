/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.browsing;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.editor.VisualizationMode;

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