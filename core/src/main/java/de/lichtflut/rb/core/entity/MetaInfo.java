/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * <p>
 * 	Meta information about an {@link RBEntity}.
 * </p>
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

	/**
	 * Check if a schema is defined for the entity.
	 * @return true if a schema is defined.
	 */
	boolean isSchemaDefined();
	

	/**
	 * Check if a schema has at least one property declaration.
	 * @return true if a schema is defined.
	 */
	boolean hasPropertyDeclarations();
}
