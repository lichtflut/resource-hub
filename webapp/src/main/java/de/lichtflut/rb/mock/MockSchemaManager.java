/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import java.util.Collection;
import java.util.Set;

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

	private Set<ResourceSchema> schemas;
	
	// -----------------------------------------------------
	
	/**
	 * Default Constructor.
	 */
	public MockSchemaManager() {
		schemas = MockResourceSchemaFactory.getAllShemas();
	}
	
	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceSchema findByType(final ResourceID id) {
		ResourceSchema rs = null;
		for (ResourceSchema schema : schemas) {
			if(schema.getDescribedType().equals(id)){
				return schema;
			}
		}
		return rs;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<ResourceSchema> findAllResourceSchemas() {
		return schemas;
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
		schemas.add(schema);
	}


	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.api.SchemaManager#getImporter(de.lichtflut.rb.core.schema.parser.RSFormat)
	 */
	@Override
	public SchemaImporter getImporter(RSFormat format) {
		throw new NotYetImplementedException();
	}

}
