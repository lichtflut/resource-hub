package de.lichtflut.rb.rest.delegate.providers;

import org.springframework.beans.factory.annotation.Autowired;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.security.SecurityConfiguration;
import de.lichtflut.rb.core.services.ArastrejuResourceFactory;
import de.lichtflut.rb.core.services.DomainInitializer;
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
	private DomainInitializer domainInitializer;

	@Autowired
	private AuthModule authModule;

	@Autowired
	private SecurityConfiguration securityConfiguration;

	@Autowired
	private FileService fileService;

	// ----------------------------------------------------

	public RBServiceProviderFactory() {
	}

	// ----------------------------------------------------

	public ServiceProvider createServiceProvider(final String domain, final RBUser user) {
		ServiceContext ctx = new ServiceContext(config, domain, user);
		ArastrejuResourceFactory factory = new ArastrejuResourceFactory(ctx);
		factory.setDomainInitializer(domainInitializer);

		RBServiceProvider rbServiceProvider = new RBServiceProvider(ctx, factory, authModule, securityConfiguration);
		rbServiceProvider.setFileService(fileService);

		return rbServiceProvider;
	}
}
