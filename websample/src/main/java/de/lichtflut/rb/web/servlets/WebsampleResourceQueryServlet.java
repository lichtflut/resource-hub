/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.servlets;

import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.services.ServiceProvider;
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

	@SpringBean
	private ServiceProvider provider;
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected ServiceProvider getServiceProvider() {
		return provider;
	}

}
