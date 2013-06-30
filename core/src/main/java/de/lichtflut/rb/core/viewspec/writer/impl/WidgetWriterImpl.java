package de.lichtflut.rb.core.viewspec.writer.impl;

import de.lichtflut.rb.core.io.writers.AbstractWriteTask;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.writer.PerspectiveWriter;
import de.lichtflut.rb.core.viewspec.writer.WidgetWriter;
import org.arastreju.sge.io.NamespaceMap;

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * <p>
 *  Default writer of widgets.
 * </p>
 *
 * <p>
 *  Created June 25, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class WidgetWriterImpl implements WidgetWriter {

    @Override
    public void write(WidgetSpec widget, NamespaceMap nsMap, OutputStream out) {
        PrintWriter writer = new PrintWriter(out);
        WriteTask task = new WriteTask(nsMap, writer);
        task.writeWidget(widget);
        writer.flush();
        writer.close();
    }

    // ----------------------------------------------------

    private static class WriteTask extends AbstractWriteTask {

        public WriteTask(NamespaceMap nameSpaceMap, PrintWriter writer) {
            super(nameSpaceMap, writer);
        }

        // ----------------------------------------------------

        private void writeWidget(WidgetSpec widget) {
            openScope("widget " + widget.getID());

            closeScope();
        }

    }

}
