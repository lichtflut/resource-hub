package de.lichtflut.rb.core.schema.writer.rsf;

import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.FieldLabelDefinition;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.VisualizationInfo;
import de.lichtflut.rb.core.schema.writer.OutputElements;
import de.lichtflut.rb.core.schema.writer.ResourceSchemaWriter;
import org.apache.commons.lang3.StringUtils;
import org.arastreju.sge.io.NamespaceMap;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.naming.Namespace;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.naming.SimpleNamespace;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * <p>
     Writer for RSF.
 * </p>
 *
 * <p>
 *  Created 19.10.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class RsfWriter implements ResourceSchemaWriter {

    @Override
    public void write(OutputStream out, OutputElements elements) throws IOException {
        PrintWriter writer = new PrintWriter(out);

        WriteTask task = new WriteTask(writer, elements.getNamespaceMap());
        task.writeNamespaces();
        for (ResourceSchema schema : elements.getSchemas()) {
            task.writeSchema(schema);
        }

        writer.close();
    }

    // ----------------------------------------------------

    private static class WriteTask {

        public static final String LVL3 = "\t\t\t";
        public static final String LVL2 = "\t\t";

        private final PrintWriter writer;

        private final NamespaceMap nsMap;

        private int scope = 0;

        // ----------------------------------------------------

        private WriteTask(PrintWriter writer, NamespaceMap nameSpaceMap) {
            this.writer = writer;
            this.nsMap = nameSpaceMap;
        }

        // ----------------------------------------------------

        public void writeNamespaces() throws IOException {
            for(String prefix : nsMap.getPrefixes()) {
                Namespace namespace = nsMap.getNamespace(prefix);
                writer.write("namespace \"" + namespace.getUri() + "\" prefix \"" + prefix + "\"\n");
            }
            newLine();
        }

        public void writeSchema(ResourceSchema schema) throws IOException {
            String type = toQName(schema.getDescribedType());
            String labelExpression = schema.getLabelBuilder().getExpression();

            openScope("schema for \"" + type + "\"");
            newLine();
            if (StringUtils.isNotBlank(labelExpression)) {
                writeField("label-rule", labelExpression);
                newLine();
            }
            for (PropertyDeclaration propertyDeclaration : schema.getPropertyDeclarations()) {
                writeProperty(propertyDeclaration);
            }

            closeScope();
        }

        public void writeProperty(PropertyDeclaration property) throws IOException {
            String predicate = toQName(property.getPropertyDescriptor());
            String cardinality = cardinalityExpression(property.getCardinality());
            openScope("property \"" + predicate + "\" " + cardinality);

            writeFieldLabels(property.getFieldLabelDefinition());
            writeField("datatype", property.getDatatype().toString());
            if (property.hasConstraint()) {
                writeConstraints(property.getConstraint());
            }
            if (isNonDefault(property.getVisualizationInfo())) {
                writeVisualization(property.getVisualizationInfo());
            }
            closeScope();
            newLine();
        }

        private void writeFieldLabels(FieldLabelDefinition labelDef) {
            writeField("field-label", labelDef.getDefaultLabel());
            for (Locale locale : labelDef.getSupportedLocales()) {
                String field = "field-label[" + locale.getLanguage() + "]";
                writeField(field, labelDef.getLabel(locale));
            }
        }

        private void writeConstraints(Constraint constraint) {
            if (constraint.isLiteral()) {
                writeField("literal-constraint", constraint.getLiteralConstraint());
            } else {
                String resConst = toQName(constraint.getTypeConstraint());
                writeField("resource-constraint", resConst);
            }
        }

        private void writeVisualization(VisualizationInfo visInfo) {
            openScope("visualize");
            if (visInfo.isEmbedded()) {
                writeField("embedded", "true");
            }
            if (visInfo.isFloating()) {
                writeField("floating", "true");
            }
            if (!StringUtils.isBlank(visInfo.getStyle())) {
                writeField("style", visInfo.getStyle());
            }
            closeScope();
        }

        // ----------------------------------------------------

        private void writeField(String field, String value) {
            indent(scope);
            writer.write(field);
            writer.write(" : ");
            writer.write("\"" + value + "\"\n");
        }

        private String toQName(ResourceID id) {
            String uri = id.toURI();
            String namespace = QualifiedName.getNamespace(uri);
            String simpleName = QualifiedName.getSimpleName(uri);
            String prefix = nsMap.getPrefix(new SimpleNamespace(namespace));

            return prefix + ":" + simpleName;
        }

        private String cardinalityExpression(Cardinality cardinality) {
            StringBuilder sb = new StringBuilder("[");
            sb.append(cardinality.getMinOccurs());
            sb.append("..");
            if (cardinality.isUnbound()) {
                sb.append("n");
            } else {
                sb.append(cardinality.getMaxOccurs());
            }
            sb.append("]");
            return sb.toString();
        }

        private boolean isNonDefault(VisualizationInfo visInfo) {
            return visInfo != null &&
                    (visInfo.isEmbedded() || visInfo.isFloating() || !StringUtils.isBlank(visInfo.getStyle()));
        }

        private void newLine() {
            writer.append("\n");
        }

        private void openScope(String expression) {
            indent(scope);
            writer.write(expression);
            writer.write(" {\n");
            scope++;

        }

        private void closeScope() {
            writer.append("}\n");
            scope--;
        }

        private void indent(int lvl) {
            switch (lvl) {
                case 0:
                    break;
                case 1:
                    writer.append("\t");
                    break;
                case 2:
                    writer.append(LVL2);
                    break;
                case 3:
                    writer.append(LVL3);
                    break;
                default:
                    for(int i = 0; i < lvl; i++) {
                        writer.write("\t");
                    }
            }
        }

    }

}


