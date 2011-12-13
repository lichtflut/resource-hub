/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api;

import java.util.Collection;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

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
	 * Returns the {@link ResourceSchema} for the given ResourceType.
	 * @param type The Resource Type
	 * @return the Resource Schema for the given Type.
	 */
	ResourceSchema findSchemaForType(ResourceID type);
	
	/**
	 * Finds a Type Definition by it's ID.
	 * @param id The IT
	 * @return the TypeDefinition or null.
	 */
	TypeDefinition findTypeDefinition(ResourceID id);

	// -----------------------------------------------------

	/**
	 * @return all registered resource schemas.
	 */
	Collection<ResourceSchema> findAllResourceSchemas();

	// -----------------------------------------------------

	/**
	 * @return all public {@link TypeDefinition}s.
	 */
	Collection<TypeDefinition> findPublicTypeDefinitions();

	// -----------------------------------------------------

	/**
	 * Stores the given Resource Schema with the given one.
	 * @param schema - the {@link ResourceSchema}
	 */
	void store(ResourceSchema schema);
	
	/**
	 * Stores the given Type Definition.
	 * @param definition The Type Definition.
	 */
	void store(TypeDefinition definition);
	
	/**
	 * Prepare a transient Type Definition.
	 * @param qn The qualified name.
	 * @param displayName The display name.
	 * @return The transient Type Definition.
	 */
	TypeDefinition prepareTypeDefinition(QualifiedName qn, String displayName);

	// -----------------------------------------------------

	/**
	 * @param format -
	 * @return -
	 */
	SchemaImporter getImporter(String format);
	
	/**
	 * Obtain the exporter for the given format.
	 * @param format A format.
	 * @return The exporter.
	 */
	SchemaExporter getExporter(String format);

}
