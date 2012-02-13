/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.io.IOException;
import java.io.InputStream;

import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.Statement;

import de.lichtflut.rb.core.reporting.IOReport;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionReference;
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
	
		for(TypeDefinition def : elements.getTypeDefs()) {
			manager.store(def);
		}
		for(ResourceSchema schema : elements.getSchemas()) {
			resolveTypeDefReferences(schema);
			manager.store(schema);
		}
		final ModelingConversation mc = provider.getArastejuGate().startConversation();
		for(Statement stmt : elements.getStatements()) {
			mc.addStatement(stmt);
		}
		
		report.add("TypeDefinitions", elements.getTypeDefs().size());
		report.add("Schemas", elements.getSchemas().size());
		report.add("Statements", elements.getStatements().size());
		report.success();
		
		return report;
	}
	
	// -----------------------------------------------------

	private void resolveTypeDefReferences(final ResourceSchema schema) {
		for (PropertyDeclaration decl : schema.getPropertyDeclarations()) {
			final TypeDefinition typeDef = decl.getTypeDefinition();
			if (typeDef instanceof TypeDefinitionReference) {
				final TypeDefinitionReference ref = (TypeDefinitionReference) typeDef;
				if (!resolveTypeDefReference(ref)) {
					throw new IllegalStateException("Could not resolve type def " + ref);
				}
			}
		}
	}
	
	private boolean resolveTypeDefReference(final TypeDefinitionReference ref) {
		final TypeDefinition existing = manager.findTypeDefinition(ref.getID());
		if (existing != null) {
			ref.setDelegate(existing);
			return true;
		} else {
			return false;
		}
	}

}
