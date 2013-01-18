/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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
