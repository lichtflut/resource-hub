/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.model.Statement;

import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;

/**
 * <p>
 *  Resulting elements of parsing process.
 * </p>
 *
 * <p>
 * 	Created Oct 21, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ParsedElements {
	
	private final List<ResourceSchema> schemas = new ArrayList<ResourceSchema>();
	
	private final List<TypeDefinition> typeDefs = new ArrayList<TypeDefinition>();
	
	private final List<Statement> statements = new ArrayList<Statement>();
	
	// -----------------------------------------------------

	/**
	 * @param schema
	 */
	public void add(final ResourceSchema schema) {
		this.schemas.add(schema);
	}

	/**
	 * @param typeDef
	 */
	public void add(final TypeDefinition typeDef) {
		this.typeDefs.add(typeDef);
	}
	
	/**
	 * @param statement
	 */
	public void add(final Statement statement) {
		this.statements.add(statement);
	}
	
	// -----------------------------------------------------
	
	/**
	 * @return the schemas
	 */
	public List<ResourceSchema> getSchemas() {
		return schemas;
	}
	
	/**
	 * @return the typeDefs
	 */
	public List<TypeDefinition> getTypeDefs() {
		return typeDefs;
	}
	
	/**
	 * @return additional statements.
	 */
	public List<Statement> getStatements() {
		return statements;
	}
	
	// -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return schemas.size() + " schema(s) and " 
				+ typeDefs.size() + " type definition(s) with "
				+ statements.size() + " additional statement(s)"; 
	}
	
}
