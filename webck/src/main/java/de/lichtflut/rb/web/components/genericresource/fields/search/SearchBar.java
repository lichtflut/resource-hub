/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.genericresource.fields.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Response;

import de.lichtflut.rb.core.api.RBEntityManagement;
import de.lichtflut.rb.core.api.RBEntityManagement.SearchContext;
import de.lichtflut.rb.core.schema.model.RBEntity;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.web.components.genericresource.GenericResourceComponent;


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
public abstract class SearchBar extends Panel implements GenericResourceComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  SearchContext sContext = SearchContext.CONJUNCT_MULTIPLE_KEYWORDS;

	
	// --CONSTRUCTORS---------------------------------------
	
	/**
	 * The default constructor.
	 * Any ResourceTypeInstance will be matched against the search-keywords
	 */
	public SearchBar(final String id){
		this(id, null);
	}
	
	// -----------------------------------------------------
	
	/**
	 * <p>
	 * Only the ResourceTypeInstances depending to the given ResourceSchema-collection,
	 * called filter come into the question of being matched against the keywords
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	public SearchBar(final String id, final Collection<ResourceSchema> filter) {
		super(id);
		// TODO Nils: Don't use implementation class HashMap as variable type but interface Map
		final HashMap<Integer, RBEntity<Object>> selectableValues = new HashMap<Integer, RBEntity<Object>>();
		final AutoCompleteTextField autoCompleter =
			new AutoCompleteTextField("searchInput",Model.of(""),new AbstractAutoCompleteRenderer<RBEntity<Object>>(){
				private static final long serialVersionUID = 2950543012596722925L;
			
				protected String getTextValue(final RBEntity<Object> object) {
					return object.toString();
				}

				// -----------------------------------------------------
				
				protected void renderChoice(final RBEntity<Object> object,final Response response, final String criteria) {
					response.write(getTextValue(object));					
				}				
			}){

			private static final long serialVersionUID = -653128720998419185L;
			protected Iterator<RBEntity> getChoices(final String input) {
				Collection<ResourceSchema> rSchemas;
				//if the keywords are not valid, return an iterator of an empty collection
				if(!isKeywordsValidForSearch(input)){
					return new ArrayList<RBEntity>().iterator();
				}
				if(filter==null || filter.isEmpty()){
					rSchemas = getServiceProvider().getResourceSchemaManagement().getAllResourceSchemas();
				}else{
					rSchemas = filter;
				}
				  final RBEntityManagement typeManagement =getServiceProvider().getRBEntityManagement();
				  final Collection<RBEntity> instances = 
					  typeManagement.loadAllResourceTypeInstancesForSchema(rSchemas,input,sContext);
				  for (final RBEntity instance : instances) {
					selectableValues.put(instance.toString().trim().hashCode(),instance);
				  }
			      return instances.iterator();
			  }
			};
			

		final Form<RBEntity<Object>> searchForm = new Form<RBEntity<Object>>("searchForm"){
			private static final long serialVersionUID = -2551391635434282054L;
			protected void onSubmit() {
				Object value =  autoCompleter.getDefaultModelObject();
	        	if(value==null) return;
	        	RBEntity instance = selectableValues.get(value.toString().trim().hashCode());
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
	 * Setting up the SearchContext in self-returning idiom style
	 */
	public SearchBar setSearchContext(SearchContext ctx){
		this.sContext = ctx;
		return this;
	}
	
	
	// -----------------------------------------------------
	
	/**
	 * <p>
	 * This method is called, when the search selection could be successfully matched
	 * to an existing {@link ResourceTypeInstance}
	 * </p>
	 */
	public abstract void onSearchSubmit(RBEntity<Object> instance);	
	
	// -----------------------------------------------------
	
	/**
	 * <p>
	 * Decides if the given keywords are sufficient and valid to process a search with them.
	 * As Default, the keywords-length has to be greater than two, to make sure, that
	 * there is no inefficient search for just 0, 1 or only 2 characters.
	 * </p>
	 */
	protected boolean isKeywordsValidForSearch(String keywords){
		if(keywords.length()>2){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * There is no effect in setting up the ViewMode here in this version
	 */
	public GenericResourceComponent setViewMode(ViewMode mode){
		//Do nothing
		return this;
	}
	
	// -----------------------------------------------------
	
	
}
