/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import org.apache.wicket.protocol.http.WebApplication;

/**
 * Application object for your web application. 
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see de.lichtflut.rb.web.Start#main(String[])
 */
public class RBApplication extends WebApplication {
	
    /**
     * Constructor
     */
	public RBApplication() {
	}
	
	
    protected void init() {
        super.init();
        mountPage("/RSSchema", RSPage.class);
        mountPage("/Resource", GenericResourceFormPage.class);
    }

	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	public Class<HomePage> getHomePage() {
		return HomePage.class;
	}

}
