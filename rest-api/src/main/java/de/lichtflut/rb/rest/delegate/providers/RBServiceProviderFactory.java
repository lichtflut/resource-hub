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

import org.springframework.beans.factory.annotation.Autowired;

import de.lichtflut.rb.core.config.RBConfig;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ArastrejuResourceFactory;
import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.rb.core.services.ServiceContext;

/**
 * <p>
 *     Factory for RB Services.
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBServiceProviderFactory {

	@Autowired
	private RBConfig config;

	@Autowired
	private AuthModule authModule;

	@Autowired
	private FileService fileService;

	// ----------------------------------------------------

	public RBServiceProviderFactory() {
	}

	// ----------------------------------------------------

	public ServiceProvider createServiceProvider(final String domain, final RBUser user) {
		ServiceContext ctx = new ServiceContext(config, domain, user);
		ArastrejuResourceFactory factory = new ArastrejuResourceFactory(ctx);

		RBServiceProvider rbServiceProvider = new RBServiceProvider(ctx, factory, authModule);
		rbServiceProvider.setFileService(fileService);

		return rbServiceProvider;
	}
}
