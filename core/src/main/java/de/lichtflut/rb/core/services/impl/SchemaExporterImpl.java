/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.io.IOException;
import java.io.OutputStream;

import de.lichtflut.rb.core.io.IOReport;
import de.lichtflut.rb.core.schema.writer.OutputElements;
import de.lichtflut.rb.core.schema.writer.ResourceSchemaWriter;
import de.lichtflut.rb.core.services.SchemaExporter;
import de.lichtflut.rb.core.services.SchemaManager;

/**
 * <p>
 *  Exports schemas.
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

	@Override
	public IOReport exportAll(final OutputStream out) {
		IOReport report = new IOReport();
		
		final OutputElements elements = new OutputElements();
		elements.addConstraints(manager.findPublicConstraints());
		elements.addSchemas(manager.findAllResourceSchemas());
		
		try {
			writer.write(out, elements);
			report.add("TypeDefinitions", elements.getConstraints().size());
			report.add("Schemas", elements.getSchemas().size());
			report.add("Statements", elements.getStatements().size());
			report.success();
		} catch (IOException e) {
			report.setAdditionalInfo(e.getMessage());
			report.error();
		}
		return report;
	}
	
}