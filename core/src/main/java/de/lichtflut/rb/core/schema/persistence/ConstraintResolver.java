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
package de.lichtflut.rb.core.schema.persistence;

import de.lichtflut.rb.core.schema.model.Constraint;

/**
 * <p>
 *  Resolver for public Type Definitions.
 *  @see TypeDefinition
 * </p>
 *
 * <p>
 * 	Created Oct 7, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ConstraintResolver {
	
	/**
	 * Try to find the persistent type definition node for the given
	 * public type definition. If not found return null.
	 * @param typeDef The type definition to be resolved.
	 * @return The corresponding node or null.
	 */
	Constraint resolve(Constraint constraint);

}
