/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.util;

import org.apache.wicket.protocol.http.WebSession;

import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * <p>
 *  Locator for Service Provider in WebSample
 * </p>
 *
 * <p>
 * 	Created Oct 19, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ServiceProviderLocator {
	
	public static ServiceProvider get() {
		final WebSession session = WebSession.get();
		return (ServiceProvider) session.getAttribute("LF:RB:ServiceProvider");
	}

}
