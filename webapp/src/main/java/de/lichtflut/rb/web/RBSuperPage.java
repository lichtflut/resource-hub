/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.inject.Inject;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.IAutoCompleteRenderer;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.JavascriptResourceReference;

import de.lichtflut.rb.builders.SessionBuilder;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.core.spi.RBServiceProviderFactory;
import de.lichtflut.rb.web.components.navigation.NavigationBar;
import de.lichtflut.rb.web.components.navigation.NavigationNodePanel;
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
public abstract class RBSuperPage extends WebPage {
	
	private static RBServiceProvider provider =null;
	private String title;
	@SuppressWarnings("unchecked")
	private final HashMap<String, ResourceTypeInstance> selectableValues = new HashMap<String, ResourceTypeInstance>();
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
	
	/* (non-Javadoc)
	 * @see org.apache.wicket.Component#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.renderJavascriptReference(new SharedResourceProvider().getJQueryCore());
		response.renderJavascriptReference(new SharedResourceProvider().getJQueryUI());
	}
	
	// -----------------------------------------------------
	
	@SuppressWarnings({ "serial", "unchecked" })
	public void init(){
		final RBSuperPage pageRef = this;
		add(new Label("title", title));
		
		final NavigationBar mainNavigation = new NavigationBar("mainNavigation");
		
		mainNavigation.addChild(new NavigationNodePanel("node", 
				new BookmarkablePageLink<RSPage>("link", RSPage.class),
				Model.of("Resource Schema")));
		
		mainNavigation.addChild(new NavigationNodePanel("node", 
				new BookmarkablePageLink<SampleResourcePage>("link", SampleResourcePage.class),
				Model.of("Sample Resource")));
		
		add(mainNavigation);
		
		final AutoCompleteTextField autoCompleter =
			new AutoCompleteTextField("searchInput",Model.of(""),new AbstractAutoCompleteRenderer<ResourceTypeInstance>(){

				@Override
				protected String getTextValue(ResourceTypeInstance object) {
					return object.toString();
				}

				@Override
				protected void renderChoice(ResourceTypeInstance object,Response response, final String criteria) {
					response.write(getTextValue(object));					
				}				
			}){

			protected Iterator getChoices(String input) {
				
				  Collection<ResourceSchema> rSchemas = provider.getResourceSchemaManagement().getAllResourceSchemas();
				  Collection<ResourceTypeInstance> instances = provider.getResourceTypeManagement().loadAllResourceTypeInstancesForSchema(rSchemas,input);
				  for (ResourceTypeInstance instance : instances) {
					selectableValues.put(instance.toString().trim(),instance);
				  }
			      return instances.iterator();
			  }
			};
			

		final Form<ResourceTypeInstance> searchForm = new Form<ResourceTypeInstance>("searchForm"){
			@Override
			protected void onSubmit() {
				Object value =  autoCompleter.getDefaultModelObject();
	        	if(value==null) return;
	        	ResourceTypeInstance instance = selectableValues.get(value.toString().trim());
	        	if(instance==null) return;
	    		PageParameters params = new PageParameters();
				params.add("resourceid", instance.getResourceSchema().getDescribedResourceID().getQualifiedName().toURI());
				params.add("instanceid", instance.getQualifiedName().toURI());
				pageRef.getRequestCycle().setResponsePage(GenericResourceFormPage.class, params);
			}
		};
		this.add(searchForm);
		searchForm.add(autoCompleter);		
		
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
