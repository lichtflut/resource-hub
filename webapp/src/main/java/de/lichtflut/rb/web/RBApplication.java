/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;


import de.lichtflut.rb.web.application.AbstractResourceBrowserApplication;
import de.lichtflut.rb.web.mockPages.EmployeePage;
import de.lichtflut.rb.web.mockPages.EmployeesPage;
import de.lichtflut.rb.web.mockPages.RepeaterPage;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 *
 * @see de.lichtflut.rb.web.Start#main(String[])
 */
public class RBApplication extends AbstractResourceBrowserApplication {

    /**
     * Constructor.
     */
	public RBApplication() {
	}

	/**
	 *
	 */
    protected void init() {
        super.init();
        mountPage("/RSSchema", RSPage.class);
        mountPage("/SampleResourcePage", SampleResourcePage.class);
        mountPage("/r2", RepeaterPage.class);
        mountPage("/view", EmployeesPage.class);
        mountPage("/detail", EmployeePage.class);
    }


	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 * @return /
	 */
	public Class<RSPage> getHomePage() {
		return RSPage.class;
	}

}
