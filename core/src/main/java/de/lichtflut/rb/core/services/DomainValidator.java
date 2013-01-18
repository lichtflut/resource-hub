
package de.lichtflut.rb.core.services;

import org.arastreju.sge.ArastrejuGate;

/**
 * <p>
 *  Initializer and validator of a domain.
 * </p>
 *
 * @author Oliver Tigges
 */
public interface DomainValidator {

    /**
     * Called before a newly created domain is used the first time.
     * @param gate The gate.
     * @param domainName The name of the domain.
     */
    void initializeDomain(ArastrejuGate gate, String domainName);

    /**
     * Called before the first usage of a domain after each application restart.
     * @param gate The gate.
     * @param domainName The name of the domain.
     */
    void validateDomain(ArastrejuGate gate, String domainName);
}
