/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.model.Statement;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

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
	
	private final List<Constraint> constraints = new ArrayList<Constraint>();
	
	private final List<Statement> statements = new ArrayList<Statement>();

    private final List<String> messages = new ArrayList<String>();
	
	// -----------------------------------------------------

	/**
	 * @param schema
	 */
	public void add(final ResourceSchema schema) {
		this.schemas.add(schema);
	}

	/**
	 * @param constraint
	 */
	public void add(final Constraint constraint) {
		this.constraints.add(constraint);
	}
	
	/**
	 * @param statement
	 */
	public void add(final Statement statement) {
		this.statements.add(statement);
	}

    /**
     * @param errorMsg The error message.
     */
    public void addError(final String errorMsg) {
        this.messages.add(errorMsg);
    }
	
	// -----------------------------------------------------
	
	/**
	 * @return the {@link ResourceSchema}s
	 */
	public List<ResourceSchema> getSchemas() {
		return schemas;
	}
	
	/**
	 * @return the {@link Constraint}s
	 */
	public List<Constraint> getConstraints() {
		return constraints;
	}
	
	/**
	 * @return additional statements.
	 */
	public List<Statement> getStatements() {
		return statements;
	}

    public List<String> getErrorMessages() {
        return messages;
    }

    // -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return schemas.size() + " schema(s) and " 
				+ constraints.size() + " constraint(s) with "
				+ statements.size() + " additional statement(s)"; 
	}
	
}
