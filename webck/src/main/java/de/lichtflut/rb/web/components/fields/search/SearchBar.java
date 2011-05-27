/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.fields.search;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Response;

import de.lichtflut.rb.core.api.ResourceTypeManagement;
import de.lichtflut.rb.core.api.ResourceTypeManagement.SearchContext;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance;
import de.lichtflut.rb.core.spi.RBServiceProvider;


/**
 * <p>
 *  A common abstract search bar to search for {@link ResourceTypeInstance}.
 *  This component is abstract. The onSearchSubmit method has to be overriden to define what is to do
 *  in case of a valid search result was found.
 *  {@link SearchContext}.CONJUNCT_MULTIPLE_KEYWORDS} is the default context.
 * </p>
 *
 * <p>
 * 	Created May 25, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
public abstract class SearchBar extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  SearchContext sContext = SearchContext.CONJUNCT_MULTIPLE_KEYWORDS;

	
	@SuppressWarnings({ "unchecked", "serial" })
	public SearchBar(final String id, final RBServiceProvider provider) {
		super(id);
		
		final HashMap<Integer, ResourceTypeInstance> selectableValues = new HashMap<Integer, ResourceTypeInstance>();
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
				  ResourceTypeManagement typeManagement =provider.getResourceTypeManagement();
				  Collection<ResourceTypeInstance> instances = 
					  typeManagement.loadAllResourceTypeInstancesForSchema(rSchemas,input,sContext);
				  for (ResourceTypeInstance instance : instances) {
					selectableValues.put(instance.toString().trim().hashCode(),instance);
				  }
			      return instances.iterator();
			  }
			};
			

		final Form<ResourceTypeInstance> searchForm = new Form<ResourceTypeInstance>("searchForm"){
			@Override
			protected void onSubmit() {
				Object value =  autoCompleter.getDefaultModelObject();
	        	if(value==null) return;
	        	ResourceTypeInstance instance = selectableValues.get(value.toString().trim().hashCode());
	        	//Reset selectableValues
	        	selectableValues.clear();
	        	if(instance==null) return;
	        	onSearchSubmit(instance);
			}
		};
		this.add(searchForm);
		searchForm.add(autoCompleter);		
		
	}

	// -----------------------------------------------------
	
	/**
	 * Setting up the SearchContext
	 */
	public void setSearchContext(SearchContext ctx){
		this.sContext = ctx;
	}
	
	
	// -----------------------------------------------------
	
	/**
	 * <p>
	 * This method is called, when the search selection could be successfully matched
	 * to an existing {@link ResourceTypeInstance}
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	public abstract void onSearchSubmit(ResourceTypeInstance instance);	
	
}
