/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api;

import java.util.Collection;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
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
	 * @param id - the {@link ResourceID} of the ResourceType
	 * @return the ResourceSchema for the given {@link ResourceID}
	 */
	ResourceSchema getResourceSchemaForResourceType(ResourceID id);

	// -----------------------------------------------------

	/**
	 * @return all the system-known ResourceSchemas
	 */
	Collection<ResourceSchema> getAllResourceSchemas();

	// -----------------------------------------------------

	/**
	 * @return all the system-known PropertyDeclarations
	 */
	Collection<PropertyDeclaration> getAllPropertyDeclarations();

	// -----------------------------------------------------

	/**
	 * Stores or overrides the given ResourceSchema with the given one.
	 * @param schema - the {@link ResourceSchema}
	 */
	void storeOrOverrideResourceSchema(ResourceSchema schema);

	// -----------------------------------------------------
	/**
	 * Stores or overrides the given ResourceSchema with the given one.
	 * @param declaration - the {@link PropertyDeclaration}
	 */
	void storeOrOverridePropertyDeclaration(PropertyDeclaration declaration);

	// -----------------------------------------------------

	/**
	 * Stores or overrides the given ResourceSchema's.
	 * @param schemas - the {@link Collection} of {@link ResourceSchema}'s
	 */
	void storeOrOverrideResourceSchema(Collection<ResourceSchema> schemas);

	// -----------------------------------------------------

	/**
	 * Stores or overrides the given PropertyDeclarations's.
	 * @param declarations - the {@link Collection} of {@link PropertyDeclaration}'s
	 */
	void storeOrOverridePropertyDeclaration(Collection<PropertyDeclaration> declarations);

	/**
	 * @param format -
	 * @return -
	 */
	SchemaImporter getImporter(RSFormat format);

}
