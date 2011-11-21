/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.servlets;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.web.util.ServiceProviderLocator;
import de.lichtflut.rb.webck.application.ResourceQueryServlet;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Oct 26, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class WebsampleResourceQueryServlet extends ResourceQueryServlet {

	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected ServiceProvider getServiceProvider() {
		return ServiceProviderLocator.get();
	}

}
