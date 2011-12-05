/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.application;

import org.apache.wicket.Application;
import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.model.nodes.views.SNTimeSpec;

import de.lichtflut.rb.core.entity.RBEntityReference;
import de.lichtflut.rb.webck.conversion.RBEntityReferenceConverter;
import de.lichtflut.rb.webck.conversion.ResourceIDConverter;
import de.lichtflut.rb.webck.conversion.SNTextConverter;
import de.lichtflut.rb.webck.conversion.SNTimeSpecConverter;

/**
 * <p>
 * 	Base Application class for all Resource Browser applications.
 * </p>
 * 
 * <p>
 * 	Created May 12, 2011
 * </p>
 * 
 * @author Oliver Tigges
 */
public abstract class AbstractResourceBrowserApplication extends WebApplication {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected IConverterLocator newConverterLocator() {
		final ConverterLocator locator = new ConverterLocator();
		locator.set(SNText.class, new SNTextConverter());
		locator.set(SNTimeSpec.class, new SNTimeSpecConverter());
		locator.set(ResourceID.class, new ResourceIDConverter());
		locator.set(RBEntityReference.class, new RBEntityReferenceConverter());
		return locator;
	}
	
	@Override
	public Session newSession(Request request, Response response) {
		return new RBWebSession(request);
	}
	
	public static AbstractResourceBrowserApplication get() {
		return (AbstractResourceBrowserApplication) Application.get();
	}
}
