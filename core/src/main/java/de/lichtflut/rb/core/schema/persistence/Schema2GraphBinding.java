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
package de.lichtflut.rb.core.schema.persistence;

import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.VisualizationInfo;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintImpl;
import de.lichtflut.rb.core.schema.model.impl.ExpressionBasedLabelBuilder;
import de.lichtflut.rb.core.schema.model.impl.LabelExpressionParseException;
import de.lichtflut.rb.core.schema.model.impl.PlainVisualizationInfo;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.views.SNScalar;
import org.arastreju.sge.model.nodes.views.SNText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *  Binding class for elements of schema model (plain java) and elements of schema semantic graph.
 * </p>
 *
 * <p>
 * 	Created Sep 29, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class Schema2GraphBinding {

	private static final Logger LOGGER = LoggerFactory.getLogger(Schema2GraphBinding.class);

	// -----------------------------------------------------

	/**
	 * Constructor with special resolver.
	 */
	public Schema2GraphBinding() {
	}

	// -----------------------------------------------------

	/**
	 * Convert a schema node to a model element.
	 * @param snSchema The schema node.
	 * @return The schema model element.
	 */
	public ResourceSchema toModelObject(final SNResourceSchema snSchema) {
		if(snSchema == null) {
			return null;
		}
		final ResourceSchemaImpl schema = new ResourceSchemaImpl(snSchema.getDescribedType());
		for (SNPropertyDeclaration snDecl : snSchema.getPropertyDeclarations()){
			final PropertyDeclarationImpl decl = new PropertyDeclarationImpl();
			decl.setPropertyDescriptor(snDecl.getPropertyDescriptor());
			decl.setCardinality(buildCardinality(snDecl));
			decl.setDatatype(snDecl.getDatatype());
			decl.setFieldLabelDefinition(snDecl.getFieldLabelDefinition());
			setVisualizationInfo(decl, snDecl.getVisualizationInfo());
			setConstraint(decl, snDecl.getConstraint());
			schema.addPropertyDeclaration(decl);
		}
		for (ResourceID resourceID : snSchema.getQuickInfo()) {
			schema.addQuickInfo(resourceID);
		}
		if (snSchema.hasLabelExpression()) {
			final String exp = snSchema.getLabelExpression().getStringValue();
			try {
				schema.setLabelBuilder(new ExpressionBasedLabelBuilder(exp));
			} catch (LabelExpressionParseException e) {
				LOGGER.error("label expression for {} could not be parsed: '{}'",
						snSchema.getDescribedType(), exp);
			}
		}
		return schema;
	}

	public Constraint toModelObject(final SNConstraint snConstraint) {
		ConstraintImpl constraint = new ConstraintImpl(snConstraint.getQualifiedName());
		constraint.setName(snConstraint.getName());
		constraint.setLiteralConstraint(snConstraint.getLiteralConstraint());
		constraint.setTypeConstraint(snConstraint.getTypeConstraint());
		constraint.setApplicableDatatypes(snConstraint.getApplicableDatatypes());
		constraint.setPublic(snConstraint.isPublic());
		return constraint;
	}

	protected VisualizationInfo toModelObject(final SNVisualizationInfo snInfo) {
		PlainVisualizationInfo info = new PlainVisualizationInfo();
		info.setEmbedded(snInfo.isEmbedded());
		info.setFloating(snInfo.isFloating());
		info.setStyle(snInfo.getStyle());
		return info;
	}

	// -----------------------------------------------------

	/**
	 * Creates a new semantic node for given Resource Schema.
	 * @param schema The schema model object.
	 * @return A new semantic node representing this schema.
	 */
	public SNResourceSchema toSemanticNode(final ResourceSchema schema) {
		if(schema == null) {
			return null;
		}
		final SNResource node = new SNResource();
		final SNResourceSchema sn = new SNResourceSchema(node);
		sn.setDescribedType(schema.getDescribedType());
		if (schema.getLabelBuilder() != null && schema.getLabelBuilder().getExpression() != null) {
			sn.setLabelExpression(new SNText(schema.getLabelBuilder().getExpression()));
		}

		SNQuickInfo predecessorQuickInfo = null;
		for (PropertyDeclaration decl : schema.getQuickInfo()) {
			SNQuickInfo current = new SNQuickInfo(decl.getPropertyDescriptor());
			if(null != predecessorQuickInfo){
				predecessorQuickInfo.addSuccessor(current);
			}
			predecessorQuickInfo = current;
			sn.addQuickInfo(current);
		}

		SNPropertyDeclaration predecessor = null;
		for(PropertyDeclaration decl : schema.getPropertyDeclarations()) {
			final SNPropertyDeclaration snDecl = new SNPropertyDeclaration();
			snDecl.setPropertyDescriptor(decl.getPropertyDescriptor());
			snDecl.setMinOccurs(minAsScalar(decl.getCardinality()));
			snDecl.setMaxOccurs(maxAsScalar(decl.getCardinality()));
			snDecl.setDatatype(decl.getDatatype());
            snDecl.setFieldLabelDefinition(decl.getFieldLabelDefinition());
			setVisualizationInfo(snDecl, decl.getVisualizationInfo());
			setConstraint(snDecl, decl.getConstraint());
			if (null != predecessor) {
				predecessor.setSuccessor(snDecl);
			}
			predecessor = snDecl;
			sn.addPropertyDeclaration(snDecl);
		}
		return sn;
	}

	public SNConstraint toSemanticNode(final Constraint constraint) {
		SNConstraint snConstraint;
		if (constraint.isPublic()) {
			snConstraint = new SNConstraint(constraint.getQualifiedName());
		} else {
			snConstraint = new SNConstraint();
		}
		snConstraint.setName(constraint.getName());
		snConstraint.setLiteralConstraint(constraint.getLiteralConstraint());
		snConstraint.setTypeConstraint(constraint.getTypeConstraint());
		snConstraint.setApplicableDatatypes(constraint.getApplicableDatatypes());
		snConstraint.setPublic(constraint.isPublic());
		return snConstraint;
	}

	protected SNVisualizationInfo toSemanticNode(final VisualizationInfo info) {
		SNVisualizationInfo snInfo = new SNVisualizationInfo();
		snInfo.setEmbedded(info.isEmbedded());
		snInfo.setFloating(info.isFloating());
		snInfo.setStyle(info.getStyle());
		return snInfo;
	}

	// ----------------------------------------------------

	protected Cardinality buildCardinality(final SNPropertyDeclaration snDecl) {
		int min = snDecl.getMinOccurs().getIntegerValue().intValue();
		int max = snDecl.getMaxOccurs().getIntegerValue().intValue();
		if (max > 0) {
			if(max ==Integer.MAX_VALUE){
				return CardinalityBuilder.hasAtLeast(min);
			}else{
				return CardinalityBuilder.between(min, max);
			}
		} else {
			return CardinalityBuilder.hasAtLeast(min);
		}
	}

	protected SNScalar minAsScalar(final Cardinality cardinality) {
		return new SNScalar(cardinality.getMinOccurs());
	}

	protected SNScalar maxAsScalar(final Cardinality cardinality) {
		if (cardinality.isUnbound()) {
			return new SNScalar(-1);
		} else {
			return new SNScalar(cardinality.getMaxOccurs());
		}
	}

	protected void setVisualizationInfo(final SNPropertyDeclaration snDecl, final VisualizationInfo visualizationInfo) {
		if (visualizationInfo != null) {
			snDecl.setVisualizationInfo(toSemanticNode(visualizationInfo));
		}
	}

	protected void setVisualizationInfo(final PropertyDeclarationImpl decl, final SNVisualizationInfo visualizationInfo) {
		if (visualizationInfo != null) {
			decl.setVisualizationInfo(toModelObject(visualizationInfo));
		}
	}

	protected void setConstraint(final SNPropertyDeclaration snDecl, final Constraint constraint) {
		if (constraint != null) {
			snDecl.setConstraint(toSemanticNode(constraint));
		}
	}

	private void setConstraint(final PropertyDeclarationImpl decl, final SNConstraint constraint) {
		if (constraint != null) {
			decl.setConstraint(toModelObject(constraint));
		}
	}

}
