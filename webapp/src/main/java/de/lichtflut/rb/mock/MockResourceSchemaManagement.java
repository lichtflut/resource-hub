/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import java.util.List;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.api.ISchemaManagement;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * Mock-Implementation of {@link ISchemaManagement}.
 *
 * Created: Aug 30, 2011
 *
 * @author Ravi Knox
 */
public class MockResourceSchemaManagement implements ISchemaManagement {

	private static final long serialVersionUID = 1L;

	private List<ResourceSchema> schemas;
	/**
	 * Default Constructor.
	 */
	public MockResourceSchemaManagement() {
		schemas = MockResourceSchemaFactory.getAllShemas();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceSchema getResourceSchemaForResourceType(final ResourceID id) {
		ResourceSchema rs = null;
		for (ResourceSchema schema : schemas) {
			if(schema.getDescribedResourceID() == id){
				return schema;
			}
		}
		return rs;
	}


	@Override
	public List<ResourceSchema> getAllResourceSchemas() {
		return schemas;
	}

}
