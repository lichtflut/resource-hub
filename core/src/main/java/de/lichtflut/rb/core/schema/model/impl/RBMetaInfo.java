/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.IRBMetaInfo;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * Implementation of {@link IRBMetaInfo}.
 *
 * Created: Aug 30, 2011
 *
 * @author Ravi Knox
 */
public class RBMetaInfo implements IRBMetaInfo {

	private ResourceSchema schema;

	/**
	 * Constructor.
	 * @param schema - {@link ResourceSchema} of the IRBEntity
	 */
	public RBMetaInfo(final ResourceSchema schema){
		this.schema = schema;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getSchemaID() {
		return schema.getDescribedResourceID();
	}

}
