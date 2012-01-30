/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.websample.base;

import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.application.ResourceQueryServlet;

/**
 * <p>
 *  WebSample specific servlet.
 * </p>
 *
 * <p>
 * 	Created Jan 30, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class WebsampleResourceQueryServlet extends ResourceQueryServlet {
	
	private ServiceProvider provider;
	
	// ----------------------------------------------------

	protected ServiceProvider getServiceProvider() throws ServletException {
		if (provider == null) {
			final WebApplicationContext wac = 
					WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
			provider = wac.getBean(ServiceProvider.class);
		}
		return provider;
	}
	
}
