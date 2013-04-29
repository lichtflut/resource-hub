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
import java.io.InputStream;

import org.arastreju.sge.Conversation;
import org.arastreju.sge.model.Statement;

import de.lichtflut.rb.core.io.IOReport;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.parser.ParsedElements;
import de.lichtflut.rb.core.schema.parser.ResourceSchemaParser;
import de.lichtflut.rb.core.schema.parser.exception.SchemaParsingException;
import de.lichtflut.rb.core.services.SchemaImporter;
import de.lichtflut.rb.core.services.SchemaManager;

/**
 * <p>
 *  Implementation of {@link SchemaImporter}.
 * </p>
 *
 * <p>
 * 	Created Oct 21, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SchemaImporterImpl implements SchemaImporter {

	private final Conversation conversation;
	private final ResourceSchemaParser parser;
	private final SchemaManager manager;

	// -----------------------------------------------------

	/**
	 * Constructor.
	 */
	public SchemaImporterImpl(final SchemaManager manager, final Conversation conversation, final ResourceSchemaParser parser) {
		this.manager = manager;
		this.conversation = conversation;
		this.parser = parser;
	}

	// -----------------------------------------------------

	@Override
	public IOReport read(final InputStream in) throws IOException {
		IOReport report = new IOReport();

		try {
			ParsedElements elements = parser.parse(in);
			for(Constraint constr : elements.getConstraints()) {
				manager.store(constr);
			}
			for(ResourceSchema schema : elements.getSchemas()) {
				resolvePublicConstraints(schema);
				manager.store(schema);
			}

			for(Statement stmt : elements.getStatements()) {
				conversation.addStatement(stmt);
			}

			report.add("Constraints", elements.getConstraints().size());
			report.add("Schemas", elements.getSchemas().size());
			report.add("Statements", elements.getStatements().size());

			report.success();
		} catch (SchemaParsingException e) {
			report.setAdditionalInfo("Error while reading schema: " + e.getMessage());
			report.error();
		}

		return report;
	}

	// -----------------------------------------------------

	/**
	 * Checks whether a Public Constraint is existent or not.
	 * If it is not, a {@link IllegalStateException} will be thrown.
	 * @param schema
	 */
	private void resolvePublicConstraints(final ResourceSchema schema) {
		for(PropertyDeclaration decl : schema.getPropertyDeclarations()){
			if(decl.hasConstraint() && decl.getConstraint().isPublic()){
				ensureExistenceOf(decl.getConstraint());
			}
		}
	}

	/**
	 * Ensures the existence of a {@link Constraint}.
	 * @param constraint
	 */
	private void ensureExistenceOf(final Constraint constraint) {
		final Constraint existing = manager.findConstraint(constraint.getQualifiedName());
		if (existing == null){
			throw new IllegalStateException("Could not resolve constraint " + constraint.getName());
		}
	}

}
