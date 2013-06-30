package de.lichtflut.rb.core.viewspec.writer;

import de.lichtflut.rb.core.viewspec.Perspective;
import org.arastreju.sge.io.NamespaceMap;

import java.io.OutputStream;

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

    void write(Perspective perspective, NamespaceMap nsMap, OutputStream out);

}
