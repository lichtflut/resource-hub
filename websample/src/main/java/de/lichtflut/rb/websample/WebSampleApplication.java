/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.websample;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.webck.application.AbstractResourceBrowserApplication;
import de.lichtflut.rb.websample.components.ComponentsCatalogPage;
import de.lichtflut.rb.websample.entities.EntityDetailPage;
import de.lichtflut.rb.websample.entities.EntityOverviewPage;
import de.lichtflut.rb.websample.types.TypeSystemPage;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see de.lichtflut.rb.websample.Start#main(String[])
 */
public class WebSampleApplication extends AbstractResourceBrowserApplication {

	public static MetaDataKey<RBConfig> RBCONFIG_KEY = new MetaDataKey<RBConfig>() { };

	// -----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public WebSampleApplication() {
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
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		initWebsampleApp();
	}
	
	protected void initWebsampleApp() {
		mountPage("/dashboard", DashboardPage.class);
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
