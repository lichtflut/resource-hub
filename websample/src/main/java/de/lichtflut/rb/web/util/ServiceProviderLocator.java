/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.util;

import org.apache.wicket.Application;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.request.cycle.RequestCycle;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.services.impl.DefaultRBServiceProvider;
import de.lichtflut.rb.web.WebsampleApplication;

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
	
	private static MetaDataKey<ServiceProvider> REQUEST_KEY = new MetaDataKey<ServiceProvider>() {};

	public static ServiceProvider get() {
		final RBConfig rbConfig = Application.get().getMetaData(WebsampleApplication.RBCONFIG_KEY);
		ServiceProvider sp = findInRequestCycle();
		if (sp == null) {
			sp = newServiceProdider(rbConfig);
		}
		return sp;
	}

	private static ServiceProvider newServiceProdider(final RBConfig rbConfig) {
		final ServiceProvider sp = new DefaultRBServiceProvider(rbConfig);
//		final ServiceProvider sp = new MockServiceProvider();
		final RequestCycle cycle = RequestCycle.get();
		if (cycle != null) {
			cycle.setMetaData(REQUEST_KEY, sp);
		}
		return sp;
	}
	
	private static ServiceProvider findInRequestCycle() {
		final RequestCycle cycle = RequestCycle.get();
		if (cycle != null) {
			return cycle.getMetaData(REQUEST_KEY);
		} else {
			return null;
		}
	}
	
}
