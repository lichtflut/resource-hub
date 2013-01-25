package de.lichtflut.rb.core.io.loader;

import org.arastreju.sge.ArastrejuGate;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * <p>
 *  Loads and imports a complete directory structure into a domain.
 *
 *  The directory should contain *.rdf.xml and *.rsf files in these directories:
 *  <ul>
 *      <li>metamodel (always loaded first)</li>
 *      <li>schemas</li>
 *      <li>viewspecs</li>
 *      <li>data (loaded last)</li>
 *  </ul>
 *
 *  This class is not thread safe.
 *
 * </p>
 *
 * <p>
 *  Created 18.01.13
 * </p>
 *
 * @author Oliver Tigges
 */
public class ClasspathBulkLoader extends AbstractBulkLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClasspathBulkLoader.class);

    private String basePackage;

    // ----------------------------------------------------

    public ClasspathBulkLoader(ArastrejuGate gate, String basePackage, String domain) {
        super(gate, domain);
        this.basePackage = basePackage;
    }

    // ----------------------------------------------------

    public void load() {
        LOGGER.info("Starting bulk load in package {}", basePackage);
        String[] pckges = new String[] { METAMODEL, VIEWSPECS, SCHEMAS, DATA };
        for (String pckg : pckges) {
            setContext(pckg);
            loadPackage(basePackage + "." + pckg);
            unsetContext();
        }
    }

    public void loadPackage(String pckg) {
        LOGGER.info("Loading package {}", pckg);
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(pckg))
                .setScanners(new ResourcesScanner()));

        Set<String> resources = reflections.getResources(Pattern.compile(pckg.replace(".", "\\/") + ".*\\.(rdf\\.xml|rsf)"));
        for (String resource : resources) {
            loadResource(resource);
        }
    }

    public void loadResource(String resource) {
        if (resource.endsWith(AbstractBulkLoader.RDF_XML)) {
            LOGGER.info("Loading RDF: {}", resource);
            doImportRDF(resource);
        }  else if (resource.endsWith(AbstractBulkLoader.RSF)) {
            LOGGER.info("Loading RSF: {}", resource);
            doImportRSF(resource);
        } else {
            LOGGER.info("Ignoring file: {}", resource);
        }
    }

    // ----------------------------------------------------

    protected void doImportRDF(String resource) {
        InputStream in = open(resource);
        if (in != null) {
            doImportRDF(in, resource);
        } else {
            LOGGER.error("File could not be imported: {}", resource);
        }
    }

    protected void doImportRSF(String resource) {
        InputStream in = open(resource);
        if (in != null) {
            doImportRSF(in, resource);
        } else {
            LOGGER.error("File could not be imported: {} ", resource);
        }
    }

    // ----------------------------------------------------

    private InputStream open(String resource) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
    }

}
