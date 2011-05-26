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
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance;
import de.lichtflut.rb.core.spi.RBServiceProvider;

/**
 * <p>
 *  A common search bar.
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
				
			      String searchInput = generateSearchInput(input);
				
				  Collection<ResourceSchema> rSchemas = provider.getResourceSchemaManagement().getAllResourceSchemas();
				  Collection<ResourceTypeInstance> instances = provider.getResourceTypeManagement().loadAllResourceTypeInstancesForSchema(rSchemas,searchInput);
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
	
	public String generateSearchInput(String input) {
		String[] keywords = input.split(" ");
		StringBuilder convertedInput = new StringBuilder();
		for (int i = 0; i < keywords.length; i++) {
			if(keywords[i].length()>0){
				convertedInput.append(keywords[i]+"*" + ((i < (keywords.length-1)) ? "AND" : "") + " ");
			}else{
				convertedInput.append(keywords[i]);
			}
		}
		return convertedInput.toString();
	}

	/**
	 * <p>
	 * This method is called, when the search selection could be successfully matched
	 * to an existing {@link ResourceTypeInstance}
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	public abstract void onSearchSubmit(ResourceTypeInstance instance);	
	
}
