/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import java.util.Collection;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.lichtflut.rb.core.api.ResourceSchemaManagement;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.models.ResourceSchemaModel;
import de.lichtflut.rb.web.components.genericresource.GenericResourceFormPanel;

/**
 * TODO: to comment
 * 
 * @author Nils Bleisch
 */
public class GenericResourceFormPage extends RBSuperPage {

	private static final long serialVersionUID = 1L;

	private ResourceSchemaManagement rManagement = getServiceProvider().getResourceSchemaManagement();
	/**
	 * Default Constructor, setting PageParameters to null
	 */
	public GenericResourceFormPage(){
		this(null);
	}

	// -----------------------------------------------------
	
	public GenericResourceFormPage(final PageParameters parameters) {
		super("Resource " + parameters.get("resourceid"), parameters);
		ResourceSchema schema = loadResourceSchemaFromParams(parameters);
		//Add generic_form_panel to page
		ResourceSchemaModel model = ResourceSchemaModel.getInstance();
        this.add(new GenericResourceFormPanel("generic_form", schema));
	}
	
	
	// -----------------------------------------------------
	
	/**
	 * Load the ResourceSchema for the give parameters
	 */
	private ResourceSchema loadResourceSchemaFromParams(PageParameters parameters){
		ResourceSchema schema=null;
		String identifier = parameters.get("resourceid").toString();
		Collection<ResourceSchema> resourceSchemas = rManagement.getAllResourceSchemas();
		if(resourceSchemas!=null){
			for (ResourceSchema resourceSchema : resourceSchemas) {
				if(resourceSchema.getDescribedResourceID().getQualifiedName().toURI().equals(identifier)){
					schema = resourceSchema;
				}
			}
		}
		return schema;
	}
	
	
}
