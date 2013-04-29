/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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