/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.persistence;

import java.util.Set;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNScalar;
import org.arastreju.sge.model.nodes.views.SNText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.FieldLabelDefinition;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ExpressionBasedLabelBuilder;
import de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl;
import de.lichtflut.rb.core.schema.model.impl.LabelExpressionParseException;
import de.lichtflut.rb.core.schema.model.impl.LiteralConstraint;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ReferenceConstraint;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;

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
	
	private final Logger logger = LoggerFactory.getLogger(Schema2GraphBinding.class);
	// TODO resolve public constraint
	private ConstraintResolver resolver = new VoidTypeDefResovler();
	
	// -----------------------------------------------------
	
	/**
	 * Constructor with special resolver.
	 * @param resolver Resolver for persistent type definitions.
	 */
	public Schema2GraphBinding(final ConstraintResolver resolver) {
		this.resolver = resolver;
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
			decl.setConstraint(snDecl.getConstraint());
			schema.addPropertyDeclaration(decl);
		}
		if (snSchema.hasLabelExpression()) {
			final String exp = snSchema.getLabelExpression().getStringValue();
			try {
				schema.setLabelBuilder(new ExpressionBasedLabelBuilder(exp));
			} catch (LabelExpressionParseException e) {
				logger.error("label expression for {} could not be parsed: '{}'", 
						snSchema.getDescribedType(), exp);
			}
		}

		return schema;
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
		final SNResourceSchema sn = new SNResourceSchema(node, RBSchema.CONTEXT);
		sn.setDescribedType(schema.getDescribedType(), RBSchema.CONTEXT);
		if (schema.getLabelBuilder() != null && schema.getLabelBuilder().getExpression() != null) {
			sn.setLabelExpression(new SNText(schema.getLabelBuilder().getExpression()), RBSchema.CONTEXT);
		}
		
		SNPropertyDeclaration predecessor = null;
		for(PropertyDeclaration decl : schema.getPropertyDeclarations()) {
			final SNPropertyDeclaration snDecl = new SNPropertyDeclaration();
			snDecl.setPropertyDescriptor(decl.getPropertyDescriptor(), RBSchema.CONTEXT);
			snDecl.setMinOccurs(minAsScalar(decl.getCardinality()), RBSchema.CONTEXT);
			snDecl.setMaxOccurs(maxAsScalar(decl.getCardinality()), RBSchema.CONTEXT);
			snDecl.setDatatype(decl.getDatatype(), RBSchema.CONTEXT);
			setFieldLabels(snDecl, decl.getFieldLabelDefinition());
			if(decl.hasConstraint()){
				snDecl.setConstraint(decl.getConstraint(), RBSchema.CONTEXT);
			}
			if (null != predecessor) {
				predecessor.setSuccessor(snDecl, RBSchema.CONTEXT);
			}
			predecessor = snDecl;
			sn.addPropertyDeclaration(snDecl);
		}
		return sn;
	}
	
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
	
	protected FieldLabelDefinition createFieldLabelDef(final SNPropertyDeclaration snDecl) {
		final String defaultName = snDecl.getPropertyDescriptor().getQualifiedName().getSimpleName();
		final FieldLabelDefinition def = new FieldLabelDefinitionImpl(defaultName);
		final Set<? extends Statement> assocs = snDecl.getAssociations(RBSystem.HAS_FIELD_LABEL);
		for (Statement current : assocs) {
			// TODO: Evaluate context to locale
			def.setDefaultLabel(current.getObject().asValue().getStringValue());
		}
		return def;
	}
	
	protected void setFieldLabels(final SNPropertyDeclaration snDecl, final FieldLabelDefinition def) {
		if (def != null && def.getDefaultLabel() != null) {
			SNOPS.associate(snDecl, RBSystem.HAS_FIELD_LABEL, new SNText(def.getDefaultLabel()));
		}
		// TODO: set i18n labels.
	}
	
	// -----------------------------------------------------
	
	private static final class VoidTypeDefResovler implements ConstraintResolver {
		@Override
		public Constraint resolve(Constraint constraint) {
			return null;
		}
	}

}
