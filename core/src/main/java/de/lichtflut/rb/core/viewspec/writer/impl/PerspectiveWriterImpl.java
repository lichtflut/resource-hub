package de.lichtflut.rb.core.viewspec.writer.impl;

import de.lichtflut.rb.core.io.writers.AbstractWriteTask;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.ViewPort;
import de.lichtflut.rb.core.viewspec.writer.PerspectiveWriter;
import org.arastreju.sge.io.NamespaceMap;

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * <p>
 *  Default writer of perspectives.
 * </p>
 *
 * <p>
 *  Created June 25, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class PerspectiveWriterImpl implements PerspectiveWriter {

    @Override
    public void write(Perspective perspective, NamespaceMap nsMap, OutputStream out) {
        PrintWriter writer = new PrintWriter(out);
        WriteTask task = new WriteTask(nsMap, writer);
        task.writePerspective(perspective);
        writer.flush();
        writer.close();
    }

    // ----------------------------------------------------

    private static class WriteTask extends AbstractWriteTask {

        public WriteTask(NamespaceMap nameSpaceMap, PrintWriter writer) {
            super(nameSpaceMap, writer);
        }

        // ----------------------------------------------------

        private void writePerspective(Perspective perspective) {
            openScope("perspective " + perspective.getName());

            for (ViewPort port : perspective.getViewPorts()) {
                openScope("port");
                closeScope();
            }

            closeScope();
        }

    }

}
