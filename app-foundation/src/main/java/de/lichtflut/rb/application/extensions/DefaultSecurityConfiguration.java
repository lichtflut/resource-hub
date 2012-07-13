package de.lichtflut.rb.application.extensions;

import de.lichtflut.rb.application.common.RBRole;
import de.lichtflut.rb.core.security.SecurityConfiguration;

/**
 * <p>
 *  Default security configuration for RB applications.
 * </p>
 *
 * <p>
 * Created 13.07.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class DefaultSecurityConfiguration implements SecurityConfiguration {

    @Override
    public String[] getRolesOfDomainAdmin() {
        return new String[] {
                RBRole.ACTIVE_USER.name(),
                RBRole.IDENTITY_MANAGER.name(),
                RBRole.DOMAIN_ADMIN.name(),
        };
    }

}
