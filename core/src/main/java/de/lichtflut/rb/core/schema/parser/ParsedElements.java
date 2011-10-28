/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

import java.util.ArrayList;
import java.util.List;

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
	
	private List<ResourceSchema> schemas = new ArrayList<ResourceSchema>();
	
	private List<TypeDefinition> typeDefs = new ArrayList<TypeDefinition>();
	
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
	
	// -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return schemas.size() + " schema(s) and " + typeDefs.size() + " type definition(s)"; 
	}
	
}
