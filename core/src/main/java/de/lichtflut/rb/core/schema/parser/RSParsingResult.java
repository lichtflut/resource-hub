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

import java.util.List;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * <p>
 * Wraps the parsing result in ResourceSchema- and Constraint-sets,
 * TODO: additional to the error messages which have been occurred.
 * </p>
 *
 * <p>
 * Created Apr 20, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
public interface RSParsingResult {

	// ------------------------------------------------------

	/**
	 * @return a Collection of {@link Constraint}s. 
	 */
	List<Constraint> getPublicConstraints();

	// -----------------------------------------------------

	/**
	 * @return Collection of ResourceSchemas.
	 */
	List<ResourceSchema> getResourceSchemas();

	// -----------------------------------------------------

	/**
	 * Determines if at lest one error is occurred or not.
	 * @return boolean true if error occured, false if not
	 */
	boolean isErrorOccured();

	// -----------------------------------------------------

}
