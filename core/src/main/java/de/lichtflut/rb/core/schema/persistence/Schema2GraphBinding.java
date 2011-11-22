/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.persistence;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.views.SNScalar;
import org.arastreju.sge.model.nodes.views.SNText;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.FieldLabelDefinition;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.core.schema.model.impl.ExpressionBasedLabelBuilder;
import de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionImpl;

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
	
	private TypeDefinitionResolver resolver = new VoidTypeDefResovler();
	
	// -----------------------------------------------------
	
	/**
	 * Constructor with special resolver.
	 * @param resolver Resolver for persistent type definitions.
	 */
	public Schema2GraphBinding(final TypeDefinitionResolver resolver) {
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
			decl.setTypeDefinition(toModelObject(snDecl.getTypeDefinition()));
			decl.setFieldLabelDefinition(createFieldLabelDef(snDecl));
			
			schema.addPropertyDeclaration(decl);
		}
		if (snSchema.hasLabelExpression()) {
			final String exp = snSchema.getLabelExpression().getStringValue();
			schema.setLabelBuilder(new ExpressionBasedLabelBuilder(exp));
		}

		return schema;
	}
	
	/**
	 * Convert a property type definition node to a model element.
	 * @param snTypeDef The type definition node.
	 * @return The schema model element.
	 */
	public TypeDefinition toModelObject(final SNPropertyTypeDefinition snTypeDef) {
		if(snTypeDef == null) {
			return null;
		}
		final TypeDefinitionImpl typeDef = new TypeDefinitionImpl(SNOPS.id(snTypeDef), snTypeDef.isPublic());
		typeDef.setElementaryDataType(snTypeDef.getDatatype());
		typeDef.setName(snTypeDef.getDisplayName());
		typeDef.setConstraints(buildConstraints(snTypeDef.getConstraints()));
		return typeDef;
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
		if (schema.getLabelBuilder().getExpression() != null) {
			sn.setLabelExpression(new SNText(schema.getLabelBuilder().getExpression()), RBSchema.CONTEXT);
		}
		
		SNPropertyDeclaration predecessor = null;
		for(PropertyDeclaration decl : schema.getPropertyDeclarations()) {
			final SNPropertyDeclaration snDecl = new SNPropertyDeclaration();
			snDecl.setPropertyDescriptor(decl.getPropertyDescriptor(), RBSchema.CONTEXT);
			snDecl.setMinOccurs(minAsScalar(decl.getCardinality()), RBSchema.CONTEXT);
			snDecl.setMaxOccurs(maxAsScalar(decl.getCardinality()), RBSchema.CONTEXT);
			snDecl.setTypeDefinition(toSemanticNode(decl.getTypeDefinition()), RBSchema.CONTEXT);
			setFieldLabels(snDecl, decl.getFieldLabelDefinition());
			if (null != predecessor) {
				predecessor.setSuccessor(snDecl, RBSchema.CONTEXT);
			}
			predecessor = snDecl;
			sn.addPropertyDeclaration(snDecl);
		}
		return sn;
	}
	
	/**
	 * Creates a new semantic node for given Type Definition.
	 * @param typeDef The type definition model object.
	 * @return A new semantic node representing this definition.
	 */
	public SNPropertyTypeDefinition toSemanticNode(final TypeDefinition typeDef) {
		if(typeDef == null) {
			return null;
		} else if (typeDef.isPublicTypeDef()) {
			final SNPropertyTypeDefinition resolved = resolver.resolve(typeDef);
			if (resolved != null) {
				return resolved;
			}
		}
		return createSemanticNode(typeDef);	
	}

	// -----------------------------------------------------
	
	/**
	 * Create a node corresponding to type definition.
	 * @param typeDef The type definition.
	 * @return The created node.
	 */
	protected SNPropertyTypeDefinition createSemanticNode(final TypeDefinition typeDef) {
		final SNResource node = new SNResource(typeDef.getID().getQualifiedName());
		final SNPropertyTypeDefinition sn = new SNPropertyTypeDefinition(node);
		sn.setDatatype(typeDef.getElementaryDataType(), RBSchema.CONTEXT);
		sn.setDisplayName(typeDef.getName(), RBSchema.CONTEXT);
		if (typeDef.isPublicTypeDef()) {
			sn.setPublic(RBSchema.CONTEXT);
		} else {
			sn.setPrivate(RBSchema.CONTEXT);
		}
		for(Constraint constraint : typeDef.getConstraints()) {
			if (constraint.isLiteralConstraint()) {
				sn.addLiteralConstraint(constraint.getLiteralConstraint(), RBSchema.CONTEXT);
			} else {
				sn.addTypeConstraint(constraint.getResourceTypeConstraint(), RBSchema.CONTEXT);
			}
		}
		return sn;
	}
	
	protected Cardinality buildCardinality(final SNPropertyDeclaration snDecl) {
		int min = snDecl.getMinOccurs().getIntegerValue().intValue();
		int max = snDecl.getMaxOccurs().getIntegerValue().intValue();
		if (max > 0) {
			return CardinalityBuilder.between(min, max);
		} else {
			return CardinalityBuilder.hasAtLeast(min);
		}
	}
	
	/**
	 * Build constraint objects from {@link SNConstraint} nodes.
	 * @param src The source.
	 * @return The constraints.
	 */
	protected Set<Constraint> buildConstraints(final Collection<SNConstraint> src) {
		final Set<Constraint> result = new HashSet<Constraint>();
		for (SNConstraint snConst : src){
			result.add(toModelConstraint(snConst));
		}
		return result;
	}

	protected Constraint toModelConstraint(final SNConstraint snConst) {
		if (snConst.isLiteralConstraint()){
			final String value = snConst.getConstraintValue().asValue().getStringValue();
			return ConstraintBuilder.buildConstraint(value);
		} else if (snConst.isTypeConstraint()) {
			final ResourceID type = snConst.getConstraintValue().asResource();
			return  ConstraintBuilder.buildConstraint(type);
		} else {
			throw new IllegalStateException();
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
		final FieldLabelDefinition def = new FieldLabelDefinitionImpl();
		final Set<Association> assocs = snDecl.getAssociations(RB.HAS_FIELD_LABEL);
		for (Association current : assocs) {
			// TODO: Evaluate context to locale
			def.setDefaultLabel(current.getObject().asValue().getStringValue());
		}
		return def;
	}
	
	protected void setFieldLabels(final SNPropertyDeclaration snDecl, final FieldLabelDefinition def) {
		if (def != null && def.getDefaultLabel() != null) {
			SNOPS.associate(snDecl, RB.HAS_FIELD_LABEL, new SNText(def.getDefaultLabel()));
		}
		// TODO: set i18n labels.
	}
	
	// -----------------------------------------------------
	
	private static final class VoidTypeDefResovler implements TypeDefinitionResolver {
		public SNPropertyTypeDefinition resolve(TypeDefinition typeDef) {
			return null;
		}
	}

}
