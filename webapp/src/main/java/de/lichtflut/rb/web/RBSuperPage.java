/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.lichtflut.rb.builders.SessionBuilder;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.core.spi.RBServiceProviderFactory;
import de.lichtflut.rb.web.components.fields.search.SearchBar;
import de.lichtflut.rb.web.components.navigation.NavigationBar;
import de.lichtflut.rb.web.components.navigation.NavigationNodePanel;



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
public abstract class RBSuperPage extends WebPage {
	
	private static RBServiceProvider provider =null;
	private String title;
	
	/**
	 * Singleton pattern: There will be only one instance per runtime
	 * @return {@link RBServiceProvider}
	 */
	public static RBServiceProvider  getServiceProvider(){
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
	
	@SuppressWarnings("serial")
	public void init(){
		add(new Label("title", title));
		
		final NavigationBar mainNavigation = new NavigationBar("mainNavigation");
		
		mainNavigation.addChild(new NavigationNodePanel("node", 
				new BookmarkablePageLink<RSPage>("link", RSPage.class),
				Model.of("Resource Schema")));
		
		mainNavigation.addChild(new NavigationNodePanel("node", 
				new BookmarkablePageLink<SampleResourcePage>("link", SampleResourcePage.class),
				Model.of("Sample Resource")));
		
		add(mainNavigation);
		
//		this.add(new SearchBar("searchBar", getServiceProvider()) {
//			@SuppressWarnings("unchecked")
//			@Override
//			public void onSearchSubmit(ResourceTypeInstance instance) {
//				PageParameters params = new PageParameters();
//				params.add("resourceid", instance.getResourceSchema().getDescribedResourceID().getQualifiedName().toURI());
//				params.add("instanceid", instance.getQualifiedName().toURI());
//				getRequestCycle().setResponsePage(GenericResourceFormPage.class, params);				
//			}
//		});
		
	}
	
	// -----------------------------------------------------
	
	/**
	 * @return the session scoped serviceProvider which is injected via CDI
	 */
	@Inject SessionBuilder builder;
	//TODO: To fix
	/*public RBServiceProvider getServiceProvider(){
		return builder.getServiceProvider();
	}*/
	
	
	
	
}
