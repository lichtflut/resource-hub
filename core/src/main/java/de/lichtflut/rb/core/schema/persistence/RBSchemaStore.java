/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.persistence;

import java.math.BigInteger;

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNScalar;
import org.arastreju.sge.model.nodes.views.SNUri;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityFactory;
import de.lichtflut.rb.core.schema.model.impl.ConstraintFactory;
import de.lichtflut.rb.core.schema.model.impl.PropertyAssertionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;

/**
 * <p>
 *  Store handling the persistence of {@link ResourceSchema}s.
 * </p>
 *
 * <p>
 * 	Created Apr 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBSchemaStore {
	
	private final ArastrejuGate gate;

	// -----------------------------------------------------
	
	/**
	 * Constructor. 
	 */
	public RBSchemaStore(final ArastrejuGate gate) {
		this.gate = gate;
	}
	
	// -----------------------------------------------------
	
	/**
	 * Store the given schema.
	 * @return The corresponding persistence Resource Schema Node.
	 */
	public SNResourceSchema store(final ResourceSchema schema) {
		final Context ctx = null;
		final SNResourceSchema snSchema = new SNResourceSchema();
		snSchema.setDescribedClass(schema.getResourceID(), ctx);
		
		final ModelingConversation mc = gate.startConversation();
		
		for (PropertyAssertion assertion : schema.getPropertyAssertions()) {
			final SNPropertyAssertion snAssertion = new SNPropertyAssertion();
			snAssertion.setMinOccurs(toScalar(assertion.getCardinality().getMinOccurs()), ctx);
			snAssertion.setMaxOccurs(toScalar(assertion.getCardinality().getMaxOccurs()), ctx);
			snAssertion.setDescriptor(assertion.getPropertyDescriptor(), ctx);
			snSchema.addPropertyAssertion(snAssertion, ctx);
			addDeclaration(snAssertion, assertion.getPropertyDeclaration(), ctx);
		}
		
		mc.attach(snSchema);
		
		return snSchema;
	}
	
	public SNPropertyDeclaration store(final PropertyDeclaration decl){
		throw new NotYetImplementedException();
	}
	
	public SNResourceSchema loadSchemaForResource(final ResourceID clazz) {
		throw new NotYetImplementedException();
	}
	
	public SNResourceSchema loadPropertyDeclaration(final ResourceID decl) {
		throw new NotYetImplementedException();
	}
	
	// -----------------------------------------------------
	
	public ResourceSchema convert(final SNResourceSchema snSchema) {
		/**
		 * TODO: Described Class seems not be the right choice....
		 */
		ResourceSchemaImpl schema = new ResourceSchemaImpl(snSchema.getDescribedClass());
		
		for (SNPropertyAssertion snAssertion : snSchema.getPropertyAssertions()){
			
			// create Property Declaration
			final SNPropertyDeclaration snDecl = snAssertion.getPropertyDeclaration();
			final PropertyDeclarationImpl decl = new PropertyDeclarationImpl();
			decl.setName(snDecl.getIdentifier().toString());
			decl.setElementaryDataType(snDecl.getDatatype());
			convertConstraints(decl, snDecl);
			
			// create Property Assertion
			final PropertyAssertionImpl pa = new PropertyAssertionImpl(snAssertion.getDescriptor(), decl);
			int min = toInteger(snAssertion.getMinOccurs());
			int max = toInteger(snAssertion.getMaxOccurs());
			pa.setCardinality(CardinalityFactory.getAbsoluteCardinality(max, min));
			schema.addPropertyAssertion(pa);
		}
		
		return schema;
	}
	
	// -----------------------------------------------------
	
	protected void addDeclaration(final SNPropertyAssertion assertion, PropertyDeclaration decl, final Context ctx) {
		final String id = decl.getName();
		final ResourceNode existing = gate.startConversation().findResource(new QualifiedName(id));
		if (existing != null) {
			throw new NotYetImplementedException();
		} else {
			final SNPropertyDeclaration snDecl = new SNPropertyDeclaration();
			snDecl.setDatatype(decl.getElementaryDataType(), ctx);
			snDecl.setIdentifier(new SNUri(decl.getName()), ctx);
			assertion.setPropertyDeclaration(snDecl, ctx);
			for (Constraint constraint: decl.getConstraints()){
				addConstraint(snDecl, constraint, ctx);
			}
		}
	}
	
	protected void addConstraint(final SNPropertyDeclaration decl, final Constraint constraint, final Context ctx) {
		if (constraint.isLiteralConstraint()) {
			decl.addLiteralConstraint(constraint.getLiteralConstraint(), ctx);
		} else if (constraint.isResourceTypeConstraint()) {
			decl.addTypeConstraint(constraint.getResourceTypeConstraint(), ctx);
		} else {
			throw new IllegalStateException();
		}
	}
	
	protected void convertConstraints(final PropertyDeclarationImpl impl, final SNPropertyDeclaration decl) {
		for (SNConstraint snConst : decl.getConstraints()){
			if (snConst.isLiteralConstraint()){
				final String value = snConst.getConstraintValue().asValue().getStringValue();
				final Constraint constraint = ConstraintFactory.buildConstraint(value);
				impl.addConstraint(constraint);
			} else if (snConst.isTypeConstraint()) {
				final ResourceID type = snConst.getConstraintValue().asResource();
				final Constraint constraint = ConstraintFactory.buildConstraint(type);
				impl.addConstraint(constraint);
			} else {
				throw new IllegalStateException();
			}
			
		}
	}
	
	// -----------------------------------------------------
	
	private SNScalar toScalar(final int value) {
		return new SNScalar(BigInteger.valueOf(value));
	}
	
	private Integer toInteger(final SNScalar value) {
		return value.getIntegerValue().intValue();
	}
	

}
