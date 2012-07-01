package de.lichtflut.rb.core.services;

import org.arastreju.sge.ArastrejuGate;

/**
 * <p>
 *     Initializer of a domain.
 * </p>
 *
 * @author Oliver Tigges
 */
public interface DomainInitializer {

    void initializeDomain(ArastrejuGate gate, String domainName);
}
