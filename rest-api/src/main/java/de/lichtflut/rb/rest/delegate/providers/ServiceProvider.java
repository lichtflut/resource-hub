/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.delegate.providers;

import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.TypeManager;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;


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
	 * @return The context of this service provider.
	 */
	ServiceContext getContext();
	
	/**
	 * @return an instance of {@link ArastrejuGate} which depends on the specific ServiceProvider
	 */
	ArastrejuGate getArastejuGate();
	
	/**
	 * @return An active Arastreju conversation.
	 */
	ModelingConversation getConversation();
	
	// -----------------------------------------------------

	/**
	 * {@link de.lichtflut.rb.core.services.SchemaManager} provides the ability to generate, manipulate, maintain,
	 * persist and store an ResourceSchema through several I/O sources.
	 * It's also interpreting the schema, checks for consistency and contains powerful error-processing mechanisms.
	 * @return {@link de.lichtflut.rb.core.services.SchemaManager}
	 */
	SchemaManager getSchemaManager();

	/**
	 * The {@link de.lichtflut.rb.core.services.TypeManager} is used for resolving of types.
	 * @return The type manager.
	 */
	TypeManager getTypeManager();
	
	/**
	 * @return The security service.
	 */
	SecurityService getSecurityService();

}
