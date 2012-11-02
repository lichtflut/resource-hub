/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api;

import de.lichtflut.rb.core.services.DomainValidator;
import org.arastreju.sge.context.DomainIdentifier;
import org.springframework.beans.factory.annotation.Autowired;

import de.lichtflut.rb.core.config.RBConfig;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.services.ArastrejuResourceFactory;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.rest.delegate.providers.RBServiceProvider;

/**
 * <p>
 *  Provider for test cases.
 * </p>
 * 
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created May 11, 2012
 */
public class TestServiceProvider extends RBServiceProvider {

	@Autowired
	private AuthModule module;

	private final ArastrejuResourceFactory factory;

	// ----------------------------------------------------

	/**
	 * @param config
	 */
	public TestServiceProvider(final RBConfig config, final AuthModule module, final DomainValidator initializer) {
		ServiceContext ctx = new ServiceContext(config, DomainIdentifier.MASTER_DOMAIN);
		factory = new ArastrejuResourceFactory(ctx);

		setServiceContext(ctx);
		setArastrejuResourceFactory(factory);
		setAuthModule(module);
		setSecurityConfiguration(new TestSecurityConfig());
	}

	// ----------------------------------------------------

	public void closeConservation() {
		factory.closeConversations();
	}

	public void closeGate() {
		factory.closeGate();
	}

}
