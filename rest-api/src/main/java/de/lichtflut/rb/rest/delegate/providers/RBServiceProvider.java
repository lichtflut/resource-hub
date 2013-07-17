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

import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.services.ArastrejuResourceFactory;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.services.impl.EntityManagerImpl;
import de.lichtflut.rb.core.services.impl.SchemaManagerImpl;
import de.lichtflut.rb.core.services.impl.SecurityServiceImpl;
import de.lichtflut.rb.core.services.impl.TypeManagerImpl;
import de.lichtflut.rb.core.services.impl.ViewSpecificationServiceImpl;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.context.Context;

/**
 * <p>
 *  Abstract base for Service Providers.
 * </p>
 *
 * <p>
 * 	Created Jan 10, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBServiceProvider implements ServiceProvider {

	private ArastrejuResourceFactory arastrejuResourceFactory;

	private ServiceContext ctx;

	private AuthModule authModule;

	private FileService fileService;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 */
	public RBServiceProvider(final ServiceContext ctx, final ArastrejuResourceFactory factory,
			final AuthModule authModule) {
		this.ctx = ctx;
		this.arastrejuResourceFactory = factory;
		this.authModule = authModule;
	}

	protected RBServiceProvider() { }

	// ----------------------------------------------------

	@Override
	public Conversation getConversation() {
		return arastrejuResourceFactory.getConversation();
	}

	@Override
	public Conversation getConversation(final Context context) {
		return arastrejuResourceFactory.getConversation(context);
	}

	// ----------------------------------------------------

	@Override
	public ServiceContext getContext() {
		return ctx;
	}

	@Override
	public SchemaManager getSchemaManager() {
		return new SchemaManagerImpl(arastrejuResourceFactory);
	}

	@Override
	public SecurityService getSecurityService() {
		return new SecurityServiceImpl(getContext(), authModule);
	}

	@Override
	public TypeManager getTypeManager() {
		return new TypeManagerImpl(arastrejuResourceFactory, getSchemaManager());
	}
	
	@Override
	public EntityManager getEntityManager() {
		return new EntityManagerImpl(getTypeManager(), getSchemaManager(), getConversation());
	}

    @Override
    public ViewSpecificationService getViewSpecificationService() {
        return new ViewSpecificationServiceImpl(ctx, arastrejuResourceFactory);
    }

    @Override
	public FileService getFileService(){
		return fileService;
	}

	// ----------------------------------------------------

	public void setArastrejuResourceFactory(final ArastrejuResourceFactory arastrejuResourceFactory) {
		this.arastrejuResourceFactory = arastrejuResourceFactory;
	}

	public void setServiceContext(final ServiceContext ctx) {
		this.ctx = ctx;
	}

	public void setAuthModule(final AuthModule authModule) {
		this.authModule = authModule;
	}

	public void setFileService(final FileService fileService){
		this.fileService = fileService;
	}


}