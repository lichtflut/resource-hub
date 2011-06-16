/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.application;

import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.protocol.http.WebApplication;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.model.nodes.views.SNTimeSpec;
import de.lichtflut.rb.core.schema.model.RBEntityFactory;
import de.lichtflut.rb.web.conversion.RBEntityConverter;
import de.lichtflut.rb.web.conversion.SNTextConverter;
import de.lichtflut.rb.web.conversion.SNTimeSpecConverter;

/**
 * <p>
 *  Base Application class for all Resource Browser applications.
 * </p>
 *
 * <p>
 * 	Created May 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class AbstractResourceBrowserApplication extends WebApplication {
	
	/* (non-Javadoc)
	 * @see org.apache.wicket.Application#newConverterLocator()
	 */
	@Override
	protected IConverterLocator newConverterLocator() {
		final ConverterLocator locator = new ConverterLocator();
		locator.set(SNText.class, new SNTextConverter());
		locator.set(SNTimeSpec.class, new SNTimeSpecConverter());
		locator.set(RBEntityFactory.class, new RBEntityConverter());
		return locator;
		
	}

}
