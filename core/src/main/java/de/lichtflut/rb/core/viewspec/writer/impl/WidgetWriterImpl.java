/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.core.viewspec.writer.impl;

import de.lichtflut.rb.core.io.writers.CommonFormatWriter;
import de.lichtflut.rb.core.viewspec.ColumnDef;
import de.lichtflut.rb.core.viewspec.Selection;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetAction;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.writer.WidgetWriter;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.io.NamespaceMap;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;

import static org.arastreju.sge.SNOPS.fetchObject;
import static org.arastreju.sge.SNOPS.fetchObjectAsResource;
import static org.arastreju.sge.SNOPS.string;

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
    public void write(WidgetSpec widget, NamespaceMap nsMap, CommonFormatWriter out) {
        writeWidget(out, widget);
    }

    // ----------------------------------------------------

    private void writeWidget(CommonFormatWriter out, WidgetSpec widget) {
        out.openScope("widget");

        out.writeFieldIfNotNull("title", widget.getTitle());
        out.writeFieldIfNotNull("description", widget.getDescription());
        out.writeFieldIfNotNull("display", display(widget));
        out.writeFieldIfNotNull("content-id", widget.getContentID());
        out.writeFieldIfNotNull("implementing-class", implementingClass(widget));

        for (WidgetAction action : widget.getActions()) {
           writeAction(out, action);
        }

        for (ColumnDef column : widget.getColumns()) {
            writeColumn(out, column);
        }

        Selection selection = widget.getSelection();
        if (selection != null) {
            writeSelection(out, selection);
        }

        out.closeScope();
        out.flush();
    }

    private void writeSelection(CommonFormatWriter out, Selection selection) {
        out.openScope("selection");

        String expr = string(selection.getQueryExpression());
        switch (selection.getType()) {
            case BY_QUERY:
                out.writeField("query", expr);
                break;
            case BY_RELATION:
                out.writeField("by-reference", expr);
                break;
            case BY_VALUE:
                out.writeField("by-value", expr);
                break;
            case BY_TYPE:
                out.writeField("by-type", expr);
                break;
            case BY_SCRIPT:
                out.indent().writeRaw("<script>" + expr + "\n");
                out.indent().writeRaw("<script>\n");
                break;
        }

        out.closeScope();
    }

    private void writeAction(CommonFormatWriter out, WidgetAction action) {
        out.openScope("action");
        out.writeFieldIfNotNull("create", typeToCreate(action));
        out.writeFieldIfNotNull("label", action.getLabel());
        out.closeScope();

    }

    private void writeColumn(CommonFormatWriter out, ColumnDef columnDef) {
        out.openScope("column");
        out.writeFieldIfNotNull("label", columnDef.getHeader());
        out.writeFieldIfNotNull("property", string(columnDef.getProperty()));
        out.closeScope();
    }

    // ----------------------------------------------------

    private String display(WidgetSpec widget) {
        final SemanticNode type = fetchObject(widget, RDF.TYPE);
        if (WDGT.ENTITY_LIST.equals(type)) {
            return "list";
        } else if (WDGT.ENTITY_DETAILS.equals(type)) {
            return "details";
        } else if (WDGT.PREDEFINED.equals(type)) {
            return null;
        } else if (WDGT.INFOVIS.equals(type)) {
            return "infovis";
        } else {
            return null;
        }
    }

    private String implementingClass(WidgetSpec widget) {
        return stringValue(widget, WDGT.IS_IMPLEMENTED_BY_CLASS);
    }

    private String typeToCreate(WidgetAction action) {
        return SNOPS.uri(fetchObjectAsResource(action, WDGT.CREATE_INSTANCE_OF));
    }

    private String stringValue(ResourceNode subject, ResourceID predicate) {
        return string(fetchObject(subject, predicate));
    }

}
