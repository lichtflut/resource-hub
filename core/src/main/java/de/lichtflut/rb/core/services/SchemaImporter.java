package de.lichtflut.rb.core.services;

import java.io.IOException;
import java.io.InputStream;

import de.lichtflut.rb.core.io.IOReport;


/**
 * <p>
 *  Importer for Resource Schemas.
 * </p>
 *
 * <p>
 * 	Created Oct 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface SchemaImporter {

	IOReport read(InputStream in) throws IOException;

}
