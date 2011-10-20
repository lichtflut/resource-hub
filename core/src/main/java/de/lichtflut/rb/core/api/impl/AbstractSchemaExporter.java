/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import de.lichtflut.rb.core.api.SchemaExporter;
import de.lichtflut.rb.core.api.SchemaManager;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Oct 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class AbstractSchemaExporter implements SchemaExporter {

	private final SchemaManager manager;

	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public AbstractSchemaExporter(final SchemaManager manager) {
		this.manager = manager;
	}
	
	// -----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void exportAll(final OutputStream out) throws IOException {
		final Collection<TypeDefinition> typeDefs = manager.findAllTypeDefinitions();
		write(out, typeDefs.toArray(new TypeDefinition[typeDefs.size()]));
		
		final Collection<ResourceSchema> schemas = manager.findAllResourceSchemas();
		write(out, schemas.toArray(new ResourceSchema[schemas.size()]));
	}
	
	// -----------------------------------------------------
	
	protected abstract void write(final OutputStream out, final ResourceSchema... schemas) throws IOException;
	
	protected abstract void write(final OutputStream out, final TypeDefinition... typeDefinitions) throws IOException;

}