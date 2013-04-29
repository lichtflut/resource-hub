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
