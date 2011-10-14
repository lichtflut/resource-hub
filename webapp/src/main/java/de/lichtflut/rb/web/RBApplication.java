/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;


import de.lichtflut.rb.web.entities.EntityDetailPage;
import de.lichtflut.rb.web.entities.EntityOverviewPage;
import de.lichtflut.rb.web.mockPages.RepeaterPage;
import de.lichtflut.rb.web.types.TypeSystemPage;
import de.lichtflut.rb.webck.application.AbstractResourceBrowserApplication;

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
        mountPage("/entities", EntityOverviewPage.class);
        mountPage("/entity-detail", EntityDetailPage.class);
        mountPage("/typesystem", TypeSystemPage.class);

        getMarkupSettings().setStripWicketTags(true);
    }


	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 * @return /
	 */
	public Class<RSPage> getHomePage() {
		return RSPage.class;
	}

}
