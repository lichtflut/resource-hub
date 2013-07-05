package de.lichtflut.rb.core.viewspec.writer;

import de.lichtflut.rb.core.io.writers.CommonFormatWriter;
import de.lichtflut.rb.core.viewspec.Perspective;
import org.arastreju.sge.io.NamespaceMap;

/**
 * <p>
 *  Interface for writers of perspectives.
 * </p>
 *
 * <p>
 *  Created June 25, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public interface PerspectiveWriter {

    void write(Perspective perspective, NamespaceMap nsMap, CommonFormatWriter out);

}
