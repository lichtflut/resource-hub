/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.lichtflut.rb.core.schema.model.RBEntity;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.core.spi.RBServiceProviderFactory;
import de.lichtflut.rb.web.ck.behavior.CKBehavior;
import de.lichtflut.rb.web.ck.components.CKLink;
import de.lichtflut.rb.web.ck.components.CKLinkType;
import de.lichtflut.rb.web.ck.components.SearchBar;
import de.lichtflut.rb.web.components.navigation.NavigationBar;
import de.lichtflut.rb.web.components.navigation.NavigationNodePanel;
import de.lichtflut.rb.web.genericresource.GenericResourceFormPage;
import de.lichtflut.rb.web.resources.SharedResourceProvider;



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

	private static RBServiceProvider provider = null;
	private String title;

	/**
	 * Singleton pattern: There will be only one instance per runtime.
	 * @return {@link RBServiceProvider}
	 */
	public static RBServiceProvider  getRBServiceProvider(){
		if(provider==null) {
			provider= RBServiceProviderFactory.getDefaultServiceProvider();
		}
		return provider;
	}

	// -----------------------------------------------------

	//Constructors
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

	/**
	 * Default constructor.
	 * @param title /
	 */
	public RBSuperPage(final String title){
		super();
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

		mainNavigation.addChild(new NavigationNodePanel("node",
				new CKLink("link", "Resource Schema", RSPage.class, CKLinkType.WEB_PAGE_CLASS),
				Model.of("Navi Label")));

		CKLink link = new CKLink("link", "Sample Resource Page", SampleResourcePage.class, CKLinkType.WEB_PAGE_CLASS);

		NavigationNodePanel menuItem2 = new NavigationNodePanel("node",	link, Model.of("Sample Resource"));

		mainNavigation.addChild(menuItem2);

		CKLink hideMe = new CKLink("link", "Hide me", SampleResourcePage.class, CKLinkType.WEB_PAGE_CLASS);
		NavigationNodePanel menuItem = new NavigationNodePanel("node",
				hideMe,	Model.of("lvl 1 Menu"));

		hideMe.addBehavior(CKLink.ON_LINK_CLICK_BEHAVIOR, new CKBehavior() {

			@SuppressWarnings("rawtypes")
			@Override
			public Object execute(final Object... objects) {
				Component comp = (Link) objects[0];
				comp.setVisible(false);
				return null;
			}
		});

		NavigationNodePanel subMenu = new NavigationNodePanel("node",
				new CKLink("link", "Google 3.1", "http://google.de", CKLinkType.EXTERNAL_LINK),
				Model.of("lvl 2.2 Menu"));

		NavigationNodePanel subMenu2 = new NavigationNodePanel("node",
				new CKLink("link", "Google 3.2", "http://google.de", CKLinkType.EXTERNAL_LINK),
				Model.of("lvl 2.1 Menu"));

		menuItem.addChild(subMenu);
		menuItem.addChild(subMenu2);

		mainNavigation.addChild(menuItem);

		add(mainNavigation);
		mainNavigation.buildComponent();
		mainNavigation.buildComponent();
		mainNavigation.buildComponent();
		mainNavigation.buildComponent();

		this.add(new SearchBar("searchBar") {

			@Override
			public void onSearchSubmit(final RBEntity<Object> instance) {
				PageParameters params = new PageParameters();
				params.add("resourceid", instance.getResourceSchema().getDescribedResourceID().getQualifiedName().toURI());
				params.add("instanceid", instance.getQualifiedName().toURI());
				getRequestCycle().setResponsePage(GenericResourceFormPage.class, params);
			}

			@Override
			public RBServiceProvider getServiceProvider() {
				return getRBServiceProvider();
			}
		});

	}

	// -----------------------------------------------------

	/**
	 * @return the session scoped serviceProvider which is injected via CDI
	 */
	//@Inject SessionBuilder builder;
	//TODO: To fix
	/*public RBServiceProvider getServiceProvider(){
		return builder.getServiceProvider();
	}*/

}
