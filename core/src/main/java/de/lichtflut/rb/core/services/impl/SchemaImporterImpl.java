/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.io.IOException;
import java.io.InputStream;

import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.Statement;

import de.lichtflut.rb.core.io.IOReport;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.parser.ParsedElements;
import de.lichtflut.rb.core.schema.parser.ResourceSchemaParser;
import de.lichtflut.rb.core.services.SchemaImporter;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.ServiceProvider;

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

	private final ResourceSchemaParser parser;
	private final SchemaManager manager;
	private final ServiceProvider provider;
	
	// -----------------------------------------------------
	
	/**
	 * @param provider The service provider.
	 * @param parser The parser.
	 */
	public SchemaImporterImpl(final ServiceProvider provider, final ResourceSchemaParser parser) {
		this.provider = provider;
		this.manager = provider.getSchemaManager();
		this.parser = parser;
	}
	
	// -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public IOReport read(final InputStream in) throws IOException {
		IOReport report = new IOReport();
		
		ParsedElements elements;
		elements = parser.parse(in);
		
		for(Constraint constr : elements.getConstraints()) {
			manager.store(constr);
		}
		for(ResourceSchema schema : elements.getSchemas()) {
			resolveTypeDefReferences(schema);
			manager.store(schema);
		}
		final ModelingConversation mc = provider.getConversation();
		for(Statement stmt : elements.getStatements()) {
			mc.addStatement(stmt);
		}
		
		report.add("TypeDefinitions", elements.getConstraints().size());
		report.add("Schemas", elements.getSchemas().size());
		report.add("Statements", elements.getStatements().size());
		report.success();
		
		return report;
	}
	
	// -----------------------------------------------------

	private void resolveTypeDefReferences(final ResourceSchema schema) {
//		for (PropertyDeclaration decl : schema.getPropertyDeclarations()) {
//			final TypeDefinition typeDef = decl.getTypeDefinition();
//			if (typeDef instanceof TypeDefinitionReference) {
//				final TypeDefinitionReference ref = (TypeDefinitionReference) typeDef;
//				if (!resolveTypeDefReference(ref)) {
//					throw new IllegalStateException("Could not resolve type def " + ref);
//				}
//			}
//		}
	}
	
	private boolean resolveTypeDefReference(Constraint ref) {
		final Constraint existing = manager.findConstraint(ref.asResourceNode());
		if (existing != null) {
			ref = existing;
			return true;
		} else {
			return false;
		}
	}

}
