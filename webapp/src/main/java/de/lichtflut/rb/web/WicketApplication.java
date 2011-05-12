/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;


/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see de.lichtflut.rb.web.Start#main(String[])
 */
public class WicketApplication extends RBApplication {    
    /**
     * Constructor
     */
	public WicketApplication() {
	}
	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	public Class<HomePage> getHomePage() {
		return HomePage.class;
	}

}
