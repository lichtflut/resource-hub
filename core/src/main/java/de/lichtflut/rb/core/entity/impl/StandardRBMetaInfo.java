/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity.impl;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.MetaInfo;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * Implementation of {@link MetaInfo}.
 *
 * Created: Aug 30, 2011
 *
 * @author Ravi Knox
 */
public class StandardRBMetaInfo implements MetaInfo {

	private ResourceSchema schema;

	/**
	 * Constructor.
	 * @param schema - {@link ResourceSchema} of the IRBEntity
	 */
	public StandardRBMetaInfo(final ResourceSchema schema){
		this.schema = schema;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getSchemaID() {
		return schema.getDescribedType();
	}

}
