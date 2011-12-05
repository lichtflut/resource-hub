/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import org.apache.wicket.MetaDataKey;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.web.components.ComponentsCatalogPage;
import de.lichtflut.rb.web.entities.EntityDetailPage;
import de.lichtflut.rb.web.entities.EntityOverviewPage;
import de.lichtflut.rb.web.types.TypeSystemPage;
import de.lichtflut.rb.webck.application.AbstractResourceBrowserApplication;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see de.lichtflut.rb.web.Start#main(String[])
 */
public class WebsampleApplication extends AbstractResourceBrowserApplication {

	public static MetaDataKey<RBConfig> RBCONFIG_KEY = new MetaDataKey<RBConfig>() { };

	// -----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public WebsampleApplication() {
	}

	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<EntityOverviewPage> getHomePage() {
		return EntityOverviewPage.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void init() {
		super.init();
		mountPage("/RSSchema", RSPage.class);
		mountPage("/entities", EntityOverviewPage.class);
		mountPage("/entity-detail", EntityDetailPage.class);
		mountPage("/typesystem", TypeSystemPage.class);
		mountPage("/catalogue", ComponentsCatalogPage.class);

		getMarkupSettings().setStripWicketTags(true);
		
		setMetaData(RBCONFIG_KEY, new RBConfig("websample"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onDestroy() {
		getMetaData(RBCONFIG_KEY).getArastrejuConfiguration().close();
	}

}
