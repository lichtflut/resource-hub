package de.lichtflut.rb.core.system;

import de.lichtflut.rb.core.io.loader.DomainBulkLoader;
import org.arastreju.sge.ArastrejuGate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

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

    public static final String BULK_LOAD_DIR = "de.lichtflut.rb.domain-init-dir";

    private static final Logger LOGGER = LoggerFactory.getLogger(BulkLoadingDomainInitializer.class);

    private final File baseDir;

    // ----------------------------------------------------

    public BulkLoadingDomainInitializer() {
        String dirName = System.getProperty(BULK_LOAD_DIR);
        if (dirName == null) {
            LOGGER.info("No directory for bulk initialization specified by System Property '" + BULK_LOAD_DIR + "'.");
            baseDir = null;
            return;
        }
        File dir = new File(dirName);
        if (!dir.isDirectory()) {
            throw new IllegalStateException(dir + " is not an existing directory.");
        }
        this.baseDir = dir;
    }

    public BulkLoadingDomainInitializer(File baseDir) {
        this.baseDir = baseDir;
    }

    // ----------------------------------------------------

    @Override
    public void initializeDomain(ArastrejuGate gate, String domain) {
        LOGGER.info("Initializing domain {}.", domain);
        if (baseDir != null) {
            DomainBulkLoader loader = new DomainBulkLoader(gate, baseDir, domain);
            loader.load();
        }

        super.initializeDomain(gate, domain);
    }

}
