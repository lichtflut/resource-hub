/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.entities;

import java.util.Collection;

import org.apache.wicket.Component;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.arastreju.sge.model.nodes.views.SNClass;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.web.RBBasePage;
import de.lichtflut.rb.web.mockPages.FeaturedTablePage;
import de.lichtflut.rb.web.util.ServiceProviderLocator;
import de.lichtflut.rb.webck.components.CKLink;
import de.lichtflut.rb.webck.components.CKLinkType;
import de.lichtflut.rb.webck.components.navigation.NavigationBar;
import de.lichtflut.rb.webck.components.navigation.NavigationNode;
import de.lichtflut.rb.webck.components.navigation.NavigationNodePanel;

/**
 * <p>
 *  Base page for pages with entities.
 * </p>
 *
 * <p>
 * 	Created Sep 21, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class EntitySamplesBasePage extends RBBasePage {
	
	/**
	 * Singleton pattern: There will be only one instance per runtime.
	 * @return {@link ServiceProvider}
	 */
	public ServiceProvider getServiceProvider(){
		return ServiceProviderLocator.get();
	}
	
	// -----------------------------------------------------

	/**
	 * @param title
	 */
	public EntitySamplesBasePage(String title) {
		super(title);
	}

	/**
	 * @param title
	 * @param params
	 */
	public EntitySamplesBasePage(String title, PageParameters params) {
		super(title, params);
	}

	// -----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Component createSideBar(String id) {
		final NavigationBar menuLeft = new NavigationBar(id);

		final NavigationNode showByTypes = new NavigationNodePanel(new CKLink("link", "Show By Type", CKLinkType.CUSTOM_BEHAVIOR));
		final Collection<SNClass> types = getServiceProvider().getTypeManager().findAll();
		for (SNClass type : types) {
			PageParameters param = new PageParameters();
			param.add("type", type);
			CKLink link = new CKLink("link", type.getQualifiedName().getSimpleName(),
					EntityOverviewPage.class, param, CKLinkType.BOOKMARKABLE_WEB_PAGE_CLASS);
			showByTypes.addChild(new NavigationNodePanel(link));
		}
		menuLeft.addChild(showByTypes);
		CKLink link = new CKLink("link", "Full Features Page", FeaturedTablePage.class, CKLinkType.WEB_PAGE_CLASS);
		menuLeft.addChild(new NavigationNodePanel(link));
		return menuLeft;
	}
	
}
