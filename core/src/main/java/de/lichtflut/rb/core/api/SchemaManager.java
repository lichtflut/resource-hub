/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api;

import java.util.Collection;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.parser.RSFormat;

/**
 * <p>
 * This is the RB's ResourceSchemaManagement "HighLevel" interface.
 * Whatever you want to manage, this service tries to give you the tools to do that.
 * No additional knowledge-base about infrastructure, technology stack e.g. should be required/necessary.
 * </p>
 *
 * Created: Sep 9, 2011
 *
 * @author Ravi Knox
 */
public interface SchemaManager {

	/**
	 * Returns a {@link ResourceSchema} for a given ResourceType.
	 * @param type The Resource Type
	 * @return the Resource Schema for the given Type.
	 */
	ResourceSchema findByType(ResourceID type);

	// -----------------------------------------------------

	/**
	 * @return all the system-known ResourceSchemas
	 */
	Collection<ResourceSchema> findAllResourceSchemas();

	// -----------------------------------------------------

	/**
	 * @return all the system-known PropertyDeclarations
	 */
	Collection<TypeDefinition> findAllTypeDefinitions();

	// -----------------------------------------------------

	/**
	 * Stores or overrides the given ResourceSchema with the given one.
	 * @param schema - the {@link ResourceSchema}
	 */
	void store(ResourceSchema schema);

	// -----------------------------------------------------

	/**
	 * @param format -
	 * @return -
	 */
	SchemaImporter getImporter(RSFormat format);

}
