package de.lichtflut.rb.core.common;

/**
 * <p>
 *  Handler for namespaces inside a domain.
 * </p>
 *
 * <p>
 *  Created 06.07.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class DomainNamespacesHandler {

    private String domainUri;

    // ----------------------------------------------------

    public DomainNamespacesHandler(String domainUri) {
        this.domainUri = domainUri;
    }

    // ----------------------------------------------------

    public String getEntitiesNamespace() {
        return domainUri + "entities/";
    }

}
