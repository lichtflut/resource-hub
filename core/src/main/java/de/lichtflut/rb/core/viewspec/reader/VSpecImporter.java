package de.lichtflut.rb.core.viewspec.reader;

import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.Perspective;
import org.antlr.runtime.RecognitionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 *  Importer for VSPecs.
 * </p>
 *
 * <p>
 *  Created July 5, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class VSpecImporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(VSpecImporter.class);

    private final ViewSpecificationService service;

    // ----------------------------------------------------

    public VSpecImporter(ViewSpecificationService service) {
        this.service = service;
    }

    // ----------------------------------------------------

    public void doImport(InputStream in) throws IOException {
        try {
            List<Perspective> perspectives = new VSpecReader().read(in);
            LOGGER.info("Parsed {} perspectives.", perspectives.size());

            for (Perspective perspective : perspectives) {
                LOGGER.info("Importing perspective '{}'.", perspective);

                service.remove(perspective.getQualifiedName());

                service.store(perspective);

            }


        } catch (RecognitionException e) {
           throw new RuntimeException(e);
        }
    }

}
