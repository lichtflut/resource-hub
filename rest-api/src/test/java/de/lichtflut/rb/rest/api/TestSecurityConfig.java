package de.lichtflut.rb.rest.api;

import de.lichtflut.rb.core.security.SecurityConfiguration;

/**
 * <p>
 *     Security configuration for JUnit tests.
 * </p>
* @author Oliver Tigges
*/
class TestSecurityConfig implements SecurityConfiguration {

    @Override
    public String[] getRolesOfDomainAdmin() {
        return new String[] {"TEST_ROLE"};
    }

}
