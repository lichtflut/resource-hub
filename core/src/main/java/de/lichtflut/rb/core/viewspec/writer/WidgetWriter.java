package de.lichtflut.rb.core.viewspec.writer;

import de.lichtflut.rb.core.viewspec.WidgetSpec;
import org.arastreju.sge.io.NamespaceMap;

import java.io.OutputStream;

/**
 * <p>
 *  Interface for writers of widget specifications.
 * </p>
 *
 * <p>
 *  Created June 25, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public interface WidgetWriter {

    void write(WidgetSpec widget, NamespaceMap nsMap, OutputStream out);

}
