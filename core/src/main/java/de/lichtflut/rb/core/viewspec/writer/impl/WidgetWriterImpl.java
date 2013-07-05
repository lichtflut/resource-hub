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
import de.lichtflut.rb.core.viewspec.Selection;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetAction;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.writer.WidgetWriter;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.io.NamespaceMap;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.query.QueryBuilder;
import org.arastreju.sge.query.QueryExpression;
import org.arastreju.sge.query.QueryOperator;
import org.arastreju.sge.query.QueryParam;
import org.arastreju.sge.query.QueryResult;

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
            out.openScope("action " + action.getActionType());
            out.closeScope();
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

        QueryCollector queryCollector = new QueryCollector();
        selection.adapt(queryCollector);

        out.writeField("query", queryCollector.toString());

        out.closeScope();
    }

    // ----------------------------------------------------

    private String display(WidgetSpec widget) {
        final SemanticNode type = SNOPS.fetchObject(widget, RDF.TYPE);
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
        final SemanticNode javaClass = SNOPS.fetchObject(widget, WDGT.IS_IMPLEMENTED_BY_CLASS);
        if (javaClass != null && javaClass.isValueNode()) {
            return javaClass.asValue().toString();
        } else {
            return null;
        }
    }

    // ----------------------------------------------------

    private static class QueryCollector extends QueryBuilder {

        @Override
        public QueryResult getResult() {
            throw new UnsupportedOperationException();
        }

    }

}
