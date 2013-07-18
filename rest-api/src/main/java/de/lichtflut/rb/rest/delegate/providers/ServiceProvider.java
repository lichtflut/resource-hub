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
package de.lichtflut.rb.rest.delegate.providers;

import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.core.services.ViewSpecificationService;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.context.Context;

import java.io.File;


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
	 * @return An active Arastreju conversation.
	 */
	Conversation getConversation();

    /**
     * @return An active Arastreju conversation.
     */
    Conversation getConversation(Context context);

	// -----------------------------------------------------

    /**
     * @return The {@link EntityManager}
     */
    EntityManager getEntityManager();

	/**
	 * {@link de.lichtflut.rb.core.services.SchemaManager} provides the ability to generate, manipulate, maintain,
	 * persist and store an ResourceSchema through several I/O sources.
	 * It's also interpreting the schema, checks for consistency and contains powerful error-processing mechanisms.
	 * @return {@link de.lichtflut.rb.core.services.SchemaManager}
	 */
	SchemaManager getSchemaManager();

    /**
     * Obtain service for view specifications.
     * @return The view specification service.
     */
    ViewSpecificationService getViewSpecificationService();

	/**
	 * The {@link de.lichtflut.rb.core.services.TypeManager} is used for resolving of types.
	 * @return The type manager.
	 */
	TypeManager getTypeManager();

	/**
	 * @return the file service to store and retrieve {@link File}s
	 */
	FileService getFileService();

}
