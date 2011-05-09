/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import java.util.Collection;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.web.components.GenericFormPanel;

/**
 * TODO: to comment
 */
public class GenericResourceFormPage extends RBSuperPage {

	private static final long serialVersionUID = 1L;

	public GenericResourceFormPage(){
		this(null);
	}

	@SuppressWarnings("unchecked")
	public GenericResourceFormPage(final PageParameters parameters) {
		ResourceSchema schema=null;
		String identifier = parameters.get("resourceid").toString();
		Collection<ResourceSchema> resourceSchemas =
			(Collection<ResourceSchema>) this.getSession().getAttribute("resourceSchema");
		if(resourceSchemas!=null){
			for (ResourceSchema resourceSchema : resourceSchemas) {
				if(resourceSchema.getResourceID().getQualifiedName().toURI().equals(identifier)){
					schema = resourceSchema;
				}
			}
		}
        this.add(new GenericFormPanel("generic_form", schema));
	}
}
