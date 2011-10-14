/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.lichtflut.rb.web.components.LoginPanelPage;
import de.lichtflut.rb.web.entities.EntityDetailPage;
import de.lichtflut.rb.web.entities.EntityOverviewPage;
import de.lichtflut.rb.web.types.TypeSystemPage;
import de.lichtflut.rb.webck.components.CKLink;
import de.lichtflut.rb.webck.components.CKLinkType;
import de.lichtflut.rb.webck.components.navigation.NavigationBar;
import de.lichtflut.rb.webck.components.navigation.NavigationNode;
import de.lichtflut.rb.webck.components.navigation.NavigationNodePanel;
import de.lichtflut.rb.webck.resources.SharedResourceProvider;

/**
 * <p>
 * TODO: To comment
 *  Root/super-Page,
 *  including important/essential services and configuration-options via CDI.
 *  Each further page should be a subclass of this.
 * </p>
 *
 * <p>
 * 	Created May 09, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
@SuppressWarnings("serial")
public abstract class RBSuperPage extends WebPage {


	private String title;

	// -----------------------------------------------------

	/**
	 * Default constructor.
	 * @param title /
	 */
	public RBSuperPage(final String title){
		super();
		this.title = title;
		init();
	}
	
	/**
	 * Takes PageParamertes as argument.
	 * @param title /
	 * @param params /
	 */
	public RBSuperPage(final String title, final PageParameters params){
		super(params);
		this.title = title;
		init();
	}

	// -----------------------------------------------------

	/* (non-Javadoc)
	 * @see org.apache.wicket.Component#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);
		response.renderJavaScriptReference(new SharedResourceProvider().getJQueryCore());
		response.renderJavaScriptReference(new SharedResourceProvider().getJQueryUI());
	}

	// -----------------------------------------------------


	/**
	 *
	 */
	public void init(){
		Label titleLabel = new Label("title", title);
		titleLabel.setRenderBodyOnly(true);
		add(titleLabel);

		final NavigationBar mainNavigation = new NavigationBar("mainNavigation");
		mainNavigation.addChild(new NavigationNodePanel(
				new CKLink("link", "Home", RSPage.class, null, CKLinkType.BOOKMARKABLE_WEB_PAGE_CLASS)));

		// Mock Entity Pages
		NavigationNode mockPages = new NavigationNodePanel(new CKLink("link", "Entities", CKLinkType.CUSTOM_BEHAVIOR));
		CKLink mockEmployee = new CKLink("link", "Sample Entities", EntityDetailPage.class, CKLinkType.WEB_PAGE_CLASS);
		mockPages.addChild(new NavigationNodePanel(mockEmployee));
		CKLink mockView = new CKLink("link", "TableView", EntityOverviewPage.class, CKLinkType.WEB_PAGE_CLASS);
		mockPages.addChild(new NavigationNodePanel(mockView));
		mainNavigation.addChild(mockPages);
		
		// Type System
		final NavigationNode typeSystem = new NavigationNodePanel(
				new CKLink("link", "Type-System", TypeSystemPage.class, CKLinkType.WEB_PAGE_CLASS));
		mainNavigation.addChild(typeSystem);

		
		// Security-Stuff Link
		NavigationNode securityStuff = new NavigationNodePanel(new CKLink("link", "Security-Related", CKLinkType.CUSTOM_BEHAVIOR));
		CKLink login = new CKLink("link", "Login & Registration Modul", LoginPanelPage.class, CKLinkType.WEB_PAGE_CLASS);
		securityStuff.addChild(new NavigationNodePanel(login));
		mainNavigation.addChild(securityStuff);

		add(mainNavigation);

		add(createSideBar("sidebarLeft"));
		
	}
	
	protected Component createSideBar(final String id) {
		return new WebMarkupContainer(id);
	}

}
