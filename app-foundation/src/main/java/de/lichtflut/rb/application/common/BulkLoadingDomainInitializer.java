package de.lichtflut.rb.application.common;

import de.lichtflut.rb.core.config.RBConfig;
import de.lichtflut.rb.core.io.loader.DomainBulkLoader;
import org.arastreju.sge.ArastrejuGate;

/**
 * <p>
 *  Domain initializer using the bulk loader to fill domain with RDF and RSF from file system.
 * </p>
 *
 * <p>
 * Created 18.01.13
 * </p>
 *
 * @author Oliver Tigges
 */
public class BulkLoadingDomainInitializer extends DefaultDomainValidator {

    public static final String DOMAIN_WORK_DIRECTORY = "de.lichtflut.rb.domain-init-dir";

    // ----------------------------------------------------

    public BulkLoadingDomainInitializer() {

        String dirName = System.getProperty(DOMAIN_WORK_DIRECTORY);

    }

    // ----------------------------------------------------

    @Override
    public void initializeDomain(ArastrejuGate gate, String domainName) {
        super.initializeDomain(gate, domainName);
    }

}
