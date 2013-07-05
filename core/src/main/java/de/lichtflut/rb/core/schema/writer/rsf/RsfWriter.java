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
package de.lichtflut.rb.core.schema.writer.rsf;

import de.lichtflut.rb.core.io.writers.CommonFormatWriter;
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
	public void write(final OutputStream out, final OutputElements elements) throws IOException {
		PrintWriter writer = new PrintWriter(out);

		RSFWriteTask task = new RSFWriteTask(writer, elements.getNamespaceMap());
		task.writeNamespaces();
		for (ResourceSchema schema : elements.getSchemas()) {
			task.writeSchema(schema);
		}

		writer.close();
	}

	// ----------------------------------------------------

	private static class RSFWriteTask extends CommonFormatWriter {

        // ----------------------------------------------------

		private RSFWriteTask(final PrintWriter writer, final NamespaceMap nameSpaceMap) {
            super(nameSpaceMap, writer);
        }

		// ----------------------------------------------------

        public void writeSchema(final ResourceSchema schema) throws IOException {
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
            newLine();
		}

		public void writeProperty(final PropertyDeclaration property) throws IOException {
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

		private void writeFieldLabels(final FieldLabelDefinition labelDef) {
			writeField("field-label", labelDef.getDefaultLabel());
			for (Locale locale : labelDef.getSupportedLocales()) {
				String field = "field-label[" + locale.getLanguage() + "]";
				writeField(field, labelDef.getLabel(locale));
			}
		}

		private void writeConstraints(final Constraint constraint) {
			if (constraint.isLiteral()) {
				writeField("literal-constraint", constraint.getLiteralConstraint());
			} else if(null != constraint.getTypeConstraint()){
				String resConst = toQName(constraint.getTypeConstraint());
				writeField("resource-constraint", resConst);
			}
		}

		private void writeVisualization(final VisualizationInfo visInfo) {
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

        private String cardinalityExpression(final Cardinality cardinality) {
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

		private boolean isNonDefault(final VisualizationInfo visInfo) {
			return visInfo != null &&
					(visInfo.isEmbedded() || visInfo.isFloating() || !StringUtils.isBlank(visInfo.getStyle()));
		}

    }

}


