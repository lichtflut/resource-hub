/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.application;

import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Application scope provider for links.
 * </p>
 *
 * <p>
 * 	Created Nov 18, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface LinkProvider {
	
	/**
	 * Create a link to navigate to a resource.
	 * @param rid The resource to navigate.
	 * @return The URL for the link
	 */
	CharSequence browseToResource(ResourceID rid);

}
