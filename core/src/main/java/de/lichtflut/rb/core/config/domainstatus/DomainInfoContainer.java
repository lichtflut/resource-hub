package de.lichtflut.rb.core.config.domainstatus;

/**
 * <p>
 *  Container for domain infos.
 * </p>
 * <p>
 *  Created 29.12.12
 * </p>
 *
 * @author Oliver Tigges
 */
public interface DomainInfoContainer {

    DomainInfo getInfo(String domain);

    DomainInfo registerDomain(String domain) throws DomainInfoException;

    void removeDomain(String domain) throws DomainInfoException;

    void unregisterDomain(String domain) throws DomainInfoException;

    void updateDomain(DomainInfo info);

}
