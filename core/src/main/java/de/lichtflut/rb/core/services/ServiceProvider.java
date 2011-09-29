/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import org.arastreju.sge.ArastrejuGate;

import de.lichtflut.rb.core.api.EntityManager;
import de.lichtflut.rb.core.api.SchemaManager;
import de.lichtflut.rb.core.api.TypeManager;

/**
  * <p>
 * The ServiceProvider which provides all existing RB-Services.
 * </p>
 *
 * Created: Aug 29, 2011
 *
 * @author Ravi Knox
 */
public interface ServiceProvider {
	/**
	 * @return an instance of {@link ArastrejuGate} which depends on the specific ServiceProvider
	 */
	ArastrejuGate getArastejuGate();

	// -----------------------------------------------------

	/**
	 * {@link EntityManager} provides the ability to manage,
	 * persist and store RB-Entities.
	 * @return {@link EntityManager}
	 */
	EntityManager getEntityManager();
	
	/**
	 * {@link ISchemaManagement} provides the ability to generate, manipulate, maintain,
	 * persist and store an ResourceSchema through several I/O sources.
	 * It's also interpreting the schema, checks for consistency and contains powerful error-processing mechanisms.
	 * @return {@link ISchemaManagement}
	 */
	SchemaManager getSchemaManager();

	/**
	 * The {@link TypeManager} is used for resolving of types.
	 * @return The type manager.
	 */
	TypeManager getTypeManager();

	
	
}