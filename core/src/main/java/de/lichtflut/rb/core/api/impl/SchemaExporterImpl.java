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
import de.lichtflut.rb.core.schema.parser.ResourceSchemaWriter;

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
public class SchemaExporterImpl implements SchemaExporter {

	private final SchemaManager manager;
	private final ResourceSchemaWriter writer;

	// -----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public SchemaExporterImpl(final SchemaManager manager, final ResourceSchemaWriter writer) {
		this.manager = manager;
		this.writer = writer;
	}
	
	// -----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void exportAll(final OutputStream out) throws IOException {
		final Collection<TypeDefinition> typeDefs = manager.findAllTypeDefinitions();
		writer.write(out, typeDefs.toArray(new TypeDefinition[typeDefs.size()]));
		
		final Collection<ResourceSchema> schemas = manager.findAllResourceSchemas();
		writer.write(out, schemas.toArray(new ResourceSchema[schemas.size()]));
	}
	
}