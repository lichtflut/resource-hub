/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application;

import de.lichtflut.rb.webck.common.RBWebSession;
import org.apache.wicket.Application;
import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.model.nodes.views.SNTimeSpec;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.common.EntityLabelBuilder;
import de.lichtflut.rb.webck.conversion.LabelBuilderConverter;
import de.lichtflut.rb.webck.conversion.QualifiedNameConverter;
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
public abstract class RBApplication extends WebApplication {

    public static RBApplication get() {
        return (RBApplication) Application.get();
    }

    // ----------------------------------------------------

    public abstract Class<? extends Page> getLoginPage();

    // ----------------------------------------------------


    @Override
    protected void init() {
        super.init();
        getRequestCycleListeners().add(new RBRequestCycleListener());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Session newSession(Request request, Response response) {
        return new RBWebSession(request);
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected IConverterLocator newConverterLocator() {
		final ConverterLocator locator = new ConverterLocator();
		locator.set(SNText.class, new SNTextConverter());
		locator.set(SNTimeSpec.class, new SNTimeSpecConverter());
		locator.set(ResourceID.class, new ResourceIDConverter());
		locator.set(QualifiedName.class, new QualifiedNameConverter());
		locator.set(EntityLabelBuilder.class, new LabelBuilderConverter());
		return locator;
	}

}
