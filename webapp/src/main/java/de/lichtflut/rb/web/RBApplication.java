/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import org.apache.wicket.request.mapper.ICompoundRequestMapper;
import org.apache.wicket.request.mapper.MountedMapper;

import de.lichtflut.rb.web.application.AbstractResourceBrowserApplication;
import de.lichtflut.rb.web.genericresource.GenericResourceFormPage;

/**
 * Application object for your web application. 
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see de.lichtflut.rb.web.Start#main(String[])
 */
public class RBApplication extends AbstractResourceBrowserApplication {
	
    /**
     * Constructor
     */
	public RBApplication() {
	}
	
	
    protected void init() {
        super.init();
        ICompoundRequestMapper mCompound = getRootRequestMapperAsCompound();
        mCompound.add(new MountedMapper("/RSSchema", RSPage.class));
        mCompound.add(new MountedMapper("/Resource", GenericResourceFormPage.class));
        mCompound.add(new MountedMapper("/SampleResourcePage", SampleResourcePage.class));
    }

	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	public Class<RSPage> getHomePage() {
		return RSPage.class;
	}

}
