/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
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
	 * Finds a {@link Constraint} by it's ID.
	 * @param qn The qualified name of the constraint.
	 * @return the TypeDefinition or null.
	 */
	Constraint findConstraint(QualifiedName qn);

	// -----------------------------------------------------

	/**
	 * @return all registered resource schemas.
	 */
	Collection<ResourceSchema> findAllResourceSchemas();

	// -----------------------------------------------------

	/**
	 * @return all public {@link Constraint}s.
	 */
	Collection<Constraint> findPublicConstraints();

	// -----------------------------------------------------

	/**
	 * Validates an Schema for consistency.
	 * @param schema {@link ResourceSchema} to validate
	 * @return a Map containting the {@link ErrorCodes} and a list of PropertyDeclarations, that produced an error, Map is empty if no error occured.
	 */
	Map<Integer, List<PropertyDeclaration>> validate(ResourceSchema schema);

	/**
	 * Stores the given Resource Schema.
	 * @param schema - the {@link ResourceSchema}
	 */
	void store(ResourceSchema schema);

	/**
	 * Remove the given Resource Schema.
	 * @param schema - the {@link ResourceSchema}
	 */
	void removeSchemaForType(ResourceID type);

	/**
	 * Stores the given Type Definition.
	 * @param constraint The Type Definition.
	 */
	void store(Constraint constraint);

	/**
	 * Remove the given {@link Constraint}.
	 * @param constraint - the Constraint to be removed
	 */
	void remove(Constraint constraint);

	/**
	 * Prepare a transient Type Definition.
	 * @param qn The qualified name.
	 * @param displayName The display name.
	 * @return The transient Type Definition.
	 */
	Constraint prepareConstraint(QualifiedName qn, String displayName);

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

	/**
	 * Check if a schema is defined for the given type.
	 * @param type The type.
	 * @return true if a schema is defined.
	 */
	boolean isSchemaDefinedFor(ResourceID type);

}
