/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.genericresource;

import java.util.Collection;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import de.lichtflut.rb.core.schema.model.RBEntity;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.RBSuperPage;
import de.lichtflut.rb.web.ck.components.GenericResourceFormPanel;


/**
 * TODO: to comment.
 *
 * @author Nils Bleisch
 */
public class GenericResourceFormPage extends RBSuperPage {

	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor, setting PageParameters to null.
	 */
	public GenericResourceFormPage(){
		this(null);
	}

	// -----------------------------------------------------
	/**
	 * @param parameters /
	 */
	public GenericResourceFormPage(final PageParameters parameters) {
		super("Resource " + parameters.get("resourceid"), parameters);
		ResourceSchema schema = loadResourceSchemaFromParams(parameters);
		//Add generic_form_panel to page
        this.add(new GenericResourceFormPanel("generic_form",schema,loadRBEntitiesFromParams(schema, parameters)){
			private static final long serialVersionUID = 117114431624666607L;

			public RBServiceProvider getServiceProvider() {
				return getRBServiceProvider();
			}

        });
	}

	// -----------------------------------------------------

	/**
	 * Load the ResourceSchema for the give parameters.
	 * @param parameters /
	 * @return /
	 */
	private ResourceSchema loadResourceSchemaFromParams(final PageParameters parameters){
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
	 * Load the ResourceTypeInstance from the give parameters.
	 * @param schema /
	 * @param parameters /
	 * @return /
	 */

	@SuppressWarnings("rawtypes")
	private RBEntity loadRBEntitiesFromParams(final ResourceSchema schema, final PageParameters parameters){
		String identifier = parameters.get("instanceid").toString();
		Collection<RBEntity> instances =
			getRBServiceProvider().getRBEntityManagement().loadAllEntitiesForSchema(schema);
		for (RBEntity<Object> resourceTypeInstance : instances) {
			if(resourceTypeInstance.getQualifiedName().toURI().equals(identifier)){
				return resourceTypeInstance;
			}
		}

		return schema.generateRBEntity();
	}

	// -----------------------------------------------------

}
