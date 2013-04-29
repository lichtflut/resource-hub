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
package de.lichtflut.rb.core.schema.parser.impl;

import java.util.ArrayList;
import java.util.List;


import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.parser.RSErrorLevel;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;

/**
 * <p>
 *  This is the reference implementation for {@link RSParsingResult}.
 * </p>
 *
 * <p>
 * 	Created Apr 20, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
public class RSParsingResultImpl implements RSParsingResult{

	private final List<Constraint> constraints = new ArrayList<Constraint>();
	private final List<ResourceSchema> schemas = new ArrayList<ResourceSchema>();

	// -----------------------------------------------------

	/**
	 * Adds a resource schema.
	 * @param schema The schema to be added.
	 */
	public void addResourceSchema(final ResourceSchema schema) {
		if(!schemas.contains(schema)){
			this.schemas.add(schema);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ResourceSchema> getResourceSchemas() {
		return schemas;
	}
	
	// -----------------------------------------------------

	/**
	 * Adds a public-constraint.
	 * @param constraint The constraint.
	 */
	public void addConstraint(final Constraint constraint) {
		if(!constraints.contains(constraint)){
			this.constraints.add(constraint);
		}
	}
	
	/**
	 * Returns all {@link Constraint}s.
	 * @return Propertydeclarationsgit check
	 */
	@Override
	public List<Constraint> getPublicConstraints() {
		return constraints;
	}



	// -----------------------------------------------------
	/**
	 * {@inheritDoc}
	 */
	@Override
	// TODO: Introduce error-functionality
	public boolean isErrorOccured() {
		return false;
	}

	// ------------------------------------------------------
	
	/**
	 * Awesome tuple class, acts as tuple and is just needed here.
	 */
	// TODO: Introduce error-functionality
	class Error {
		  private final RSErrorLevel lvl;
		  private final String message;
		  /**
		   * Constructor.
		   * @param lvl -
		   * @param message -
		   */
		  public Error(final RSErrorLevel lvl,final String message) {
		    this.lvl = lvl;
		    this.message = message;
		  }

		  /**
		   * Returns Message.
		   * @return message
		   */
		  public String getMessage() {
		    return this.message;
		  }

		  /**
		   * Returns the Errorlevel.
		   * @return RSErrorLevel
		   */
		  public RSErrorLevel getErrorLevel() {
		    return this.lvl;
		  }
		}//End of class Error
}
