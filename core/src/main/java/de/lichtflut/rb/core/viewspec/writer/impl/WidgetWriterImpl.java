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
        out.openScope("widget '" + widget.getID().getQualifiedName() + "'");

        out.writeFieldIfNotNull("title", widget.getTitle());
        out.writeFieldIfNotNull("description", widget.getDescription());
        out.writeFieldIfNotNull("display", display(widget));
        out.writeFieldIfNotNull("content-id", widget.getContentID());
        out.writeFieldIfNotNull("predefined-class", predefinedClass(widget));

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

        out.writeField("query", queryCollector.toQueryString());

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

    private String predefinedClass(WidgetSpec widget) {
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

        public String toQueryString() {
            final StringBuilder sb = new StringBuilder();
            append(getRoot(), sb);
            return sb.toString();
        }

        private void append(final QueryExpression exp, final StringBuilder sb) {
            if (exp.isLeaf()) {
                QueryParam param = exp.getQueryParam();
                sb.append(param.getName()).append(" = ").append(param.getValue());
            } else {
                if (QueryOperator.NOT.equals(exp.getOperator())) {
                    sb.append(" ").append(exp.getOperator().name()).append(" ");
                }
                sb.append("(");
                boolean first = true;
                for (QueryExpression child : exp.getChildren()) {
                    if (first) {
                        first = false;
                    } else if (!QueryOperator.NOT.equals(exp.getOperator())) {
                        sb.append(" ").append(exp.getOperator().name()).append(" ");
                    }
                    append(child, sb);
                }
                sb.append(")");
            }
        }

    }

}
