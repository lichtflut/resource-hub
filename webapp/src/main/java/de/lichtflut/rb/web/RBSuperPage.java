/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.lichtflut.rb.core.schema.model.RBEntityFactory;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.core.spi.RBServiceProviderFactory;
import de.lichtflut.rb.web.components.genericresource.fields.search.SearchBar;
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
	
	private static RBServiceProvider provider =null;
	private String title;
	
	/**
	 * Singleton pattern: There will be only one instance per runtime
	 * @return {@link RBServiceProvider}
	 */
	public static RBServiceProvider  getRBServiceProvider(){
		if(provider==null) provider= RBServiceProviderFactory.getDefaultServiceProvider();
		return provider;
	}
	
	// -----------------------------------------------------
	
	//Constructors
	/**
	 * Takes PageParamertes as argument
	 */
	public RBSuperPage(String title, PageParameters params){
		super(params);
		this.title = title;
		init();
	}
	
	// -----------------------------------------------------
	
	/**
	 * Default constructor
	 */
	public RBSuperPage(String title){
		super();
		this.title = title;
		init();
	}
		
	// -----------------------------------------------------
	
	/* (non-Javadoc)
	 * @see org.apache.wicket.Component#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.renderJavaScriptReference(new SharedResourceProvider().getJQueryCore());
		response.renderJavaScriptReference(new SharedResourceProvider().getJQueryUI());
	}
	
	// -----------------------------------------------------
	
	@SuppressWarnings({ "serial"})
	public void init(){
		Label titleLabel = new Label("title", title);
		titleLabel.setRenderBodyOnly(true);
		add(titleLabel);
		
		final NavigationBar mainNavigation = new NavigationBar("mainNavigation");
		
		mainNavigation.addChild(new NavigationNodePanel("node", 
				new BookmarkablePageLink<RSPage>("link", RSPage.class),
				Model.of("Resource Schema")));
		
		mainNavigation.addChild(new NavigationNodePanel("node", 
				new BookmarkablePageLink<SampleResourcePage>("link", SampleResourcePage.class),
				Model.of("Sample Resource")));
		
		
		add(mainNavigation);
		
		this.add(new SearchBar("searchBar") {

			public void onSearchSubmit(RBEntityFactory<Object> instance) {
				PageParameters params = new PageParameters();
				params.add("resourceid", instance.getResourceSchema().getDescribedResourceID().getQualifiedName().toURI());
				params.add("instanceid", instance.getQualifiedName().toURI());
				getRequestCycle().setResponsePage(GenericResourceFormPage.class, params);				
			}

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
