/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.core.system;

import de.lichtflut.rb.core.io.loader.ClasspathBulkLoader;
import de.lichtflut.rb.core.io.loader.FileSystemBulkLoader;
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

    public static final String BULK_LOAD_PACKAGE = "de.lichtflut.rb.domain-init-package";

    public static final String DEFAULT_BULK_LOAD_PACKAGE = "_rb_domain_init";

    private static final Logger LOGGER = LoggerFactory.getLogger(BulkLoadingDomainInitializer.class);

    private final File baseDir;

    private final String basePackage;

    // ----------------------------------------------------

    public BulkLoadingDomainInitializer() {
        String dirName = System.getProperty(BULK_LOAD_DIR);
        if (dirName == null) {
            LOGGER.info("No directory for bulk initialization specified by System Property '" + BULK_LOAD_DIR + "'.");
            baseDir = null;
        } else {
            LOGGER.info("Directory for bulk initialization specified: '{}'", dirName);
            File dir = new File(dirName);
            if (!dir.isDirectory()) {
                throw new IllegalStateException(dir + " is not an existing directory.");
            }
            this.baseDir = dir;
        }

        String pckgName = System.getProperty(BULK_LOAD_PACKAGE);
        if (pckgName == null) {
            LOGGER.info("No package for bulk initialization specified by System Property '" + BULK_LOAD_PACKAGE + "'.");
            basePackage = DEFAULT_BULK_LOAD_PACKAGE;
        } else {
            LOGGER.info("Package for bulk initialization specified: '{}'", pckgName);
            basePackage = pckgName;
        }

    }

    public BulkLoadingDomainInitializer(File baseDir) {
        this.baseDir = baseDir;
        this.basePackage = DEFAULT_BULK_LOAD_PACKAGE;
    }

    // ----------------------------------------------------

    @Override
    public void initializeDomain(ArastrejuGate gate, String domain) {
        LOGGER.info("Initializing domain {}.", domain);
        if (baseDir != null) {
            LOGGER.info("Directory for bulk loading is set: {}.", baseDir);
            FileSystemBulkLoader loader = new FileSystemBulkLoader(gate, baseDir, domain);
            loader.load();
        }
        ClasspathBulkLoader loader = new ClasspathBulkLoader(gate, basePackage, domain);
        loader.load();

        super.initializeDomain(gate, domain);
    }

}
