/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.resources;

import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * <p>
 *  Provider for shared resources as.
 *  <ul>
 *  	<li>JavaScript</li>
 *  	<li>CSS</li>
 *  </ul>
 * </p>
 *
 * <p>
 * 	Created May 25, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SharedResourceProvider {

	/**
	 * @return The resource reference to JQuery Core.
	 */
	public ResourceReference getJQueryCore() {

		return new PackageResourceReference(SharedResourceProvider.class, "jquery-1.6.1.min.js");
	}

	/**
	 * @return The resource reference to JQuery UI (full).
	 */
	public ResourceReference getJQueryUI() {
		return new PackageResourceReference(SharedResourceProvider.class, "jquery-ui-1.8.13.custom.min.js");
	}

}
