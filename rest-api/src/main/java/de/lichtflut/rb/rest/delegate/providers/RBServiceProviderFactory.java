package de.lichtflut.rb.rest.delegate.providers;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ServiceContext;

/**
 * <p>
 *     Factory for RB Services.
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBServiceProviderFactory {

    private AuthModule authModule;

    private RBConfig config;

    // ----------------------------------------------------

    public RBServiceProviderFactory(RBConfig config, AuthModule authModule) {
        this.config = config;
        this.authModule = authModule;
    }

    // ----------------------------------------------------

    public ServiceProvider createServiceProvider(String domain, RBUser user) {
        return new RBServiceProvider(new ServiceContext(config, domain, user), authModule);
    }
}
