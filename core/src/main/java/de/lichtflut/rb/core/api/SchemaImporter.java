package de.lichtflut.rb.core.api;

import java.io.IOException;
import java.io.InputStream;


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

	void read(InputStream in) throws IOException;

}
