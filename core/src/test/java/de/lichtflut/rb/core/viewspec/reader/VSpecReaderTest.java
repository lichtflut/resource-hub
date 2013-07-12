package de.lichtflut.rb.core.viewspec.reader;

import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.Selection;
import de.lichtflut.rb.core.viewspec.ViewPort;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetAction;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import org.antlr.runtime.RecognitionException;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.arastreju.sge.SNOPS.qualify;
import static org.arastreju.sge.SNOPS.singleObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * <p>
 *  Test case for VSpec reader.
 * </p>
 *
 * <p>
 *  Created July 5, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class VSpecReaderTest {

    @Test
    public void shouldReadPerspectives() throws RecognitionException, IOException {

        InputStream in = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream("test-perspectives.vspec");
        Assert.assertNotNull(in);

        VSpecReader reader = new VSpecReader();
        List<Perspective> perspectives = reader.read(in);

        assertEquals(1, perspectives.size());
        Perspective perspective1 = perspectives.get(0);
        Assert.assertEquals(qualify("local:perspectives:Organization"), perspective1.getQualifiedName());

        List<ViewPort> ports = perspective1.getViewPorts();
        assertEquals(2, ports.size());

        ViewPort port1 = ports.get(0);
        ViewPort port2 = ports.get(1);

        assertEquals(2, port1.getWidgets().size());
        assertEquals(2, port2.getWidgets().size());

        WidgetSpec widget1 = port1.getWidgets().get(0);
        assertEquals("a.test.Class",
                singleObject(widget1, WDGT.IS_IMPLEMENTED_BY_CLASS).asValue().getStringValue());
        assertEquals(WDGT.PREDEFINED,
                singleObject(widget1, RDF.TYPE).asResource());

        WidgetSpec widget2 = port1.getWidgets().get(1);
        assertEquals(WDGT.ENTITY_LIST,
                singleObject(widget2, RDF.TYPE).asResource());
        assertEquals("All organizations", widget2.getTitle());
        Selection selection2 = widget2.getSelection();
        assertNotNull(selection2);
        assertEquals(1, widget2.getActions().size());
        WidgetAction action2 = widget2.getActions().get(0);
        assertEquals(WDGT.ACTION_INSTANTIATE, action2.getActionType());
        ResourceNode typeToCreate2 = SNOPS.singleObject(action2, WDGT.CREATE_INSTANCE_OF).asResource();
        assertEquals(typeToCreate2.getQualifiedName().toURI(), "http://rb.lichtflut.de/common#Organization");

        WidgetSpec widget3 = port2.getWidgets().get(0);
        assertEquals(WDGT.ENTITY_LIST,
                singleObject(widget3, RDF.TYPE).asResource());
        assertEquals("All processes", widget3.getTitle());

        WidgetSpec widget4 = port2.getWidgets().get(1);
        assertEquals(WDGT.ENTITY_DETAILS,
                singleObject(widget4, RDF.TYPE).asResource());
        assertEquals("My current project", widget4.getTitle());
    }

}
