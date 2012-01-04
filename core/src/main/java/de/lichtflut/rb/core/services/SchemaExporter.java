package de.lichtflut.rb.core.services;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>
 *  Exporter of Resource Schemas.
 * </p>
 *
 * <p>
 * 	Created Oct 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface SchemaExporter {
	
	void exportAll(OutputStream out) throws IOException;
	
}
