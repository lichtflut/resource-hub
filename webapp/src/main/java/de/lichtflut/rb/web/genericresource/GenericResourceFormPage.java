/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.genericresource;

import java.util.Collection;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.RBSuperPage;
import de.lichtflut.rb.web.components.genericresource.panels.GenericResourceFormPanel;


/**
 * TODO: to comment
 * 
 * @author Nils Bleisch
 */
public class GenericResourceFormPage extends RBSuperPage {

	private static final long serialVersionUID = 1L;

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
        this.add(new GenericResourceFormPanel("generic_form",schema,loadResourceTypeInstanceFromParams(schema, parameters)){
			private static final long serialVersionUID = 117114431624666607L;

			public RBServiceProvider getServiceProvider() {
				return getRBServiceProvider();
			}

        });
	}
	
	
	// -----------------------------------------------------
	
	/**
	 * Load the ResourceSchema for the give parameters
	 */
	private ResourceSchema loadResourceSchemaFromParams(PageParameters parameters){
		ResourceSchema schema=null;
		String identifier = parameters.get("resourceid").toString();
		Collection<ResourceSchema> resourceSchemas = getRBServiceProvider().getResourceSchemaManagement().getAllResourceSchemas();
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
	@SuppressWarnings({ "unchecked"})
	
	private ResourceTypeInstance loadResourceTypeInstanceFromParams(ResourceSchema schema, PageParameters parameters){
		String identifier = parameters.get("instanceid").toString();
		Collection<ResourceTypeInstance> instances = getRBServiceProvider().getResourceTypeManagement().loadAllResourceTypeInstancesForSchema(schema);
		for (ResourceTypeInstance<Object> resourceTypeInstance : instances) {
			if(resourceTypeInstance.getQualifiedName().toURI().equals(identifier)){
				return resourceTypeInstance;
			}
		}
		
		return schema.generateTypeInstance();
	}
	
	// -----------------------------------------------------
	
	
}
