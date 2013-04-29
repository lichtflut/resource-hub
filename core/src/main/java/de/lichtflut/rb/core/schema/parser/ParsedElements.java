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
	
	@Override
	public String toString() {
		return schemas.size() + " schema(s) and " 
				+ constraints.size() + " constraint(s) with "
				+ statements.size() + " additional statement(s)"; 
	}
	
}
