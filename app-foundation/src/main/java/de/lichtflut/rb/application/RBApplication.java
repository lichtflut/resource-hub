/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application;

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

import de.lichtflut.rb.application.base.LoginPage;
import de.lichtflut.rb.application.base.LogoutPage;
import de.lichtflut.rb.application.custom.BrowseAndSearchPage;
import de.lichtflut.rb.application.custom.PerspectivePage;
import de.lichtflut.rb.application.custom.UserProfilePage;
import de.lichtflut.rb.application.custom.WelcomePage;
import de.lichtflut.rb.application.graphvis.FlowChartInfoVisPage;
import de.lichtflut.rb.application.graphvis.HierarchyInfoVisPage;
import de.lichtflut.rb.application.graphvis.PeripheryViewPage;
import de.lichtflut.rb.application.resourceview.EntityDetailPage;
import de.lichtflut.rb.core.common.EntityLabelBuilder;
import de.lichtflut.rb.webck.common.RBWebSession;
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

    @Override
    public Class<? extends Page> getHomePage(){
    	return WelcomePage.class;
    }
    
    public Class<? extends Page> getLoginPage(){
    	return LoginPage.class;
    }
    
    public Class<? extends Page> getLogoutPage(){
    	return LogoutPage.class;
    }
    
    public Class<? extends Page> getEntityDetailPage(){
    	return EntityDetailPage.class;
    }
    
    public Class<? extends Page> getPerspectivePage(){
    	return PerspectivePage.class;
    }
    
    public Class<? extends Page> getPeripheryVizPage(){
    	return PeripheryViewPage.class;
    }
    
    public Class<? extends Page> getHierarchyVizPage(){
    	return HierarchyInfoVisPage.class;
    }
    
    public Class<? extends Page> getFlowChartVizPage(){
    	return FlowChartInfoVisPage.class;
    }
    
    public Class<? extends Page> getBrowseAndSearchPage(){
    	return BrowseAndSearchPage.class;
    }
    
    public Class<?extends Page> getUserProfilePage(){
    	return UserProfilePage.class;
    }

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
