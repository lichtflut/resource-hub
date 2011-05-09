/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import java.util.Collection;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.lichtflut.rb.core.api.ResourceSchemaManagement;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.web.components.GenericFormPanel;

/**
 * TODO: to comment
 * 
 * @author Nils Bleisch
 */
public class GenericResourceFormPage extends RBSuperPage {

	private static final long serialVersionUID = 1L;

	private ResourceSchemaManagement rManagement = this.getServiceProvider().getResourceSchemaManagement();
	
	public GenericResourceFormPage(){
		this(null);
	}

	public GenericResourceFormPage(final PageParameters parameters) {
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
        this.add(new GenericFormPanel("generic_form", schema));
	}
}
