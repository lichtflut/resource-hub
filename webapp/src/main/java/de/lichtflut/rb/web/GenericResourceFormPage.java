/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import java.util.Collection;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.lichtflut.rb.core.api.ResourceSchemaManagement;
import de.lichtflut.rb.core.api.ResourceTypeManagement;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance;
import de.lichtflut.rb.web.components.genericresource.GenericResourceFormPanel;

/**
 * TODO: to comment
 * 
 * @author Nils Bleisch
 */
public class GenericResourceFormPage extends RBSuperPage {

	private static final long serialVersionUID = 1L;

	private ResourceSchemaManagement rManagement = getServiceProvider().getResourceSchemaManagement();
	private ResourceTypeManagement rTypeManagement = getServiceProvider().getResourceTypeManagement();
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
        this.add(new GenericResourceFormPanel("generic_form", schema, getServiceProvider(),loadResourceTypeInstanceFromParams(schema, parameters)));
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
	
	// -----------------------------------------------------
	
	/**
	 * Load the ResourceTypeInstance from the give parameters
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private ResourceTypeInstance loadResourceTypeInstanceFromParams(ResourceSchema schema, PageParameters parameters){
		String identifier = parameters.get("instanceid").toString();
		Collection<ResourceTypeInstance> instances = rTypeManagement.loadAllResourceTypeInstancesForSchema(schema);
		for (ResourceTypeInstance<Object> resourceTypeInstance : instances) {
			if(resourceTypeInstance.getQualifiedName().toURI().equals(identifier)){
				return resourceTypeInstance;
			}
		}
		
		return schema.generateTypeInstance();
	}
	
	
}
