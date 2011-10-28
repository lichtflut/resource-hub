/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api.impl;

import java.io.IOException;
import java.io.InputStream;

import de.lichtflut.rb.core.api.SchemaImporter;
import de.lichtflut.rb.core.api.SchemaManager;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionReference;
import de.lichtflut.rb.core.schema.parser.ParsedElements;
import de.lichtflut.rb.core.schema.parser.ResourceSchemaParser;

/**
 * <p>
 *  [DESCRIPTION]
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
	
	// -----------------------------------------------------
	
	/**
	 * @param parser
	 */
	public SchemaImporterImpl(final SchemaManager manager, final ResourceSchemaParser parser) {
		this.manager = manager;
		this.parser = parser;
	}
	
	// -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void read(final InputStream in) throws IOException {
		final ParsedElements elements = parser.parse(in);
		for(TypeDefinition def : elements.getTypeDefs()) {
			manager.store(def);
		}
		for(ResourceSchema schema : elements.getSchemas()) {
			if (resolveTypeDefReferences(schema)) {
				manager.store(schema);
			}
		}
	}
	
	// -----------------------------------------------------

	private boolean resolveTypeDefReferences(final ResourceSchema schema) {
		boolean resolved = true;
		for (PropertyDeclaration decl : schema.getPropertyDeclarations()) {
			final TypeDefinition typeDef = decl.getTypeDefinition();
			if (typeDef instanceof TypeDefinitionReference) {
				final TypeDefinitionReference ref = (TypeDefinitionReference) typeDef;
				resolved = resolved && resolveTypeDefReference(ref);
			}
		}
		return resolved;
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
