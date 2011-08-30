/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Response;

import de.lichtflut.rb.core.api.INewRBEntityManagement;
import de.lichtflut.rb.core.api.RBEntityManagement.SearchContext;
import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.schema.model.ResourceSchema;



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
public abstract class SearchBar extends CKComponent{

// TODO: integrate NewRBService
	private static final String FILTER = "filter";
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private  SearchContext sContext = SearchContext.CONJUNCT_MULTIPLE_KEYWORDS;


	// --CONSTRUCTORS---------------------------------------

	/**
	 * The default constructor.
	 * Any ResourceTypeInstance will be matched against the search-keywords
	 * @param id /
	 */
	public SearchBar(final String id){
		this(id, null);
	}

	// -----------------------------------------------------

	/**
	 * <p>
	 * Only the ResourceTypeInstances depending to the given ResourceSchema-collection,
	 * called filter come into the question of being matched against the keywords.
	 * </p>
	 * @param id /
	 * @param filter /
	 */
	public SearchBar(final String id, final Collection<ResourceSchema> filter) {
		super(id);
		if(filter!=null){
			this.getModel().addValue(FILTER, new ArrayList<ResourceSchema>(filter));
		}
		buildComponent();
	}

	// -----------------------------------------------------

	/**
	 * Setting up the SearchContext in self-returning idiom style.
	 * @param ctx /
	 * @return this
	 */
	public SearchBar setSearchContext(final SearchContext ctx){
		this.sContext = ctx;
		return this;
	}


	// -----------------------------------------------------

	/**
	 * <p>
	 * This method is called, when the search selection could be successfully matched.
	 * to an existing {@link ResourceTypeInstance}
	 * @param instance
	 * </p>
	 */
	public abstract void onSearchSubmit(IRBEntity instance);

	// -----------------------------------------------------

	/**
	 * <p>
	 * Decides if the given keywords are sufficient and valid to process a search with them.
	 * As Default, the keywords-length has to be greater than two, to make sure, that
	 * there is no inefficient search for just 0, 1 or only 2 characters.
	 * </p>
	 * @param keywords /
	 * @return boolean
	 */
	protected boolean isKeywordsValidForSearch(final String keywords){
		if(keywords.length()>2){
			return true;
		}

		return false;

	}

	// -----------------------------------------------------

	/**
	 * There is no effect in setting up the ViewMode here in this version.
	 * @param mode /
	 * @return mode /
	 */
	public CKComponent setViewMode(final ViewMode mode){
		throw new UnsupportedOperationException();
		//return this;
	}

	// -----------------------------------------------------

	@SuppressWarnings("unchecked")
	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		final Collection<ResourceSchema> filter = (Collection<ResourceSchema>) model.getValue(FILTER);
		final Map<Integer, IRBEntity> selectableValues = new HashMap<Integer, IRBEntity>();
		@SuppressWarnings("rawtypes")
		final AutoCompleteTextField autoCompleter =
			new AutoCompleteTextField("searchInput",Model.of(""),new AbstractAutoCompleteRenderer<IRBEntity>(){
				private static final long serialVersionUID = 2950543012596722925L;

				protected String getTextValue(final IRBEntity object) {
					return object.toString();
				}

				// -----------------------------------------------------

				protected void renderChoice(final IRBEntity object,final Response response, final String criteria) {
					response.write(getTextValue(object));
				}
			}){

			private static final long serialVersionUID = -653128720998419185L;
			@SuppressWarnings("null")
			protected Iterator<IRBEntity> getChoices(final String input) {
				@SuppressWarnings("unused")
				Collection<ResourceSchema> rSchemas;
				//if the keywords are not valid, return an iterator of an empty collection
				if(!isKeywordsValidForSearch(input)){
					return new ArrayList<IRBEntity>().iterator();
				}
				if(filter==null || filter.isEmpty()){
					rSchemas = getServiceProvider().getResourceSchemaManagement().getAllResourceSchemas();
				}else{
					rSchemas = filter;
				}
				  @SuppressWarnings("unused")
				final INewRBEntityManagement typeManagement =getServiceProvider().getRBEntityManagement();
				  final Collection<IRBEntity> instances = null;
//				  TODO: FIX!!!
//					  typeManagement.loadAllEntitiesForSchema(rSchemas,input,sContext);
				  for (final IRBEntity instance : instances) {
					selectableValues.put(instance.toString().trim().hashCode(),instance);
				  }
			      return instances.iterator();
			  }
			};


		final Form<IRBEntity> searchForm = new Form<IRBEntity>("searchForm"){
			private static final long serialVersionUID = -2551391635434282054L;
			protected void onSubmit() {
				Object value =  autoCompleter.getDefaultModelObject();
	        	if(value==null) {
					return;
				}
	        	IRBEntity instance = selectableValues.get(value.toString().trim().hashCode());
	        	//Reset selectableValues
	        	selectableValues.clear();
	        	if(instance==null) {
					return;
				}
	        	onSearchSubmit(instance);
			}
		};
		this.add(searchForm);
		searchForm.add(autoCompleter);
	}

	// -----------------------------------------------------


}
