/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * [TODO Insert description here.
 *
 * Created: Aug 8, 2011
 *
 * @author Ravi Knox
 */
public interface MetaInfo {

	/**
	 * Returns the {@link ResourceID} of this {@link ResourceSchema}.
	 * @return {@link ResourceID} of this {@link ResourceSchema}
	 */
	ResourceID getSchemaID();
}
