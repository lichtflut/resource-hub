/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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
