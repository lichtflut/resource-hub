/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.delegate.providers;

import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.context.Context;

import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.SecurityConfiguration;
import de.lichtflut.rb.core.services.ArastrejuResourceFactory;
import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.core.services.impl.SchemaManagerImpl;
import de.lichtflut.rb.core.services.impl.SecurityServiceImpl;
import de.lichtflut.rb.core.services.impl.TypeManagerImpl;

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

	private SecurityConfiguration securityConfiguration;

	private FileService fileService;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 */
	public RBServiceProvider(final ServiceContext ctx, final ArastrejuResourceFactory factory,
			final AuthModule authModule, final SecurityConfiguration securityConfiguration) {
		this.ctx = ctx;
		this.arastrejuResourceFactory = factory;
		this.authModule = authModule;
		this.securityConfiguration = securityConfiguration;
	}

	protected RBServiceProvider() { }

	// ----------------------------------------------------

	@Override
	public ModelingConversation getConversation() {
		return arastrejuResourceFactory.getConversation();
	}

	@Override
	public ModelingConversation getConversation(final Context context) {
		return arastrejuResourceFactory.getConversation(context);
	}

	// ----------------------------------------------------

	/**
	 *{@inheritDoc}
	 */
	@Override
	public ServiceContext getContext() {
		return ctx;
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public SchemaManager getSchemaManager() {
		return new SchemaManagerImpl(arastrejuResourceFactory);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SecurityService getSecurityService() {
		final SecurityServiceImpl service = new SecurityServiceImpl(getContext(), getConversation(), authModule);
		service.setSecurityConfiguration(securityConfiguration);
		return service;
	}

	@Override
	public TypeManager getTypeManager() {
		return new TypeManagerImpl(arastrejuResourceFactory, getSchemaManager());
	}

	/**
	 * {@inheritDoc}
	 */
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

	public void setSecurityConfiguration(final SecurityConfiguration securityConfiguration) {
		this.securityConfiguration = securityConfiguration;
	}

	public void setFileService(final FileService fileService){
		this.fileService = fileService;
	}
}