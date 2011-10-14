/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.api.SchemaImporter;
import de.lichtflut.rb.core.api.SchemaManager;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.parser.RSFormat;

/**
 * Mock-Implementation of {@link ISchemaManagement}.
 *
 * Created: Aug 30, 2011
 *
 * @author Ravi Knox
 */
public class MockSchemaManager implements SchemaManager {

	private static final long serialVersionUID = 1L;

	private Map<ResourceID, ResourceSchema> typeSchemaMap = new HashMap<ResourceID, ResourceSchema>();
	
	// -----------------------------------------------------
	
	/**
	 * Default Constructor.
	 */
	public MockSchemaManager() {
		for(ResourceSchema schema : MockResourceSchemaFactory.getAllShemas()) {
			typeSchemaMap.put(schema.getDescribedType(), schema);
		}
	}
	
	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceSchema findByType(final ResourceID type) {
		return typeSchemaMap.get(type);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<ResourceSchema> findAllResourceSchemas() {
		return typeSchemaMap.values();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<TypeDefinition> findAllTypeDefinitions() {
		throw new NotYetImplementedException();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void store(final ResourceSchema schema) {
		typeSchemaMap.put(schema.getDescribedType(), schema);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SchemaImporter getImporter(RSFormat format) {
		throw new NotYetImplementedException();
	}

}
