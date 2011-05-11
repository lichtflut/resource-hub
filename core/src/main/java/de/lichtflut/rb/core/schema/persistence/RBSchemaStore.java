/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.persistence;

import java.math.BigInteger;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNScalar;
import org.arastreju.sge.query.QueryManager;
import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.schema.RBSchema;
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
	 * @param ctx The context.
	 * @return The corresponding persistence Resource Schema Node.
	 */
	public SNResourceSchema store(final ResourceSchema schema, Context ctx) {
		/*
		 * How can we check if this schema does exists, and if so, replace it with the new one
		 * SNResourceSchema snSchema = loadSchemaForResource(schema.getDescribedResourceID());
		 * 
		 * 
		 */
		SNResourceSchema snSchema;
		if(schema.getResourceID()!=null){
			snSchema = new SNResourceSchema(schema.getResourceID().asResource());
		}else{
			snSchema = new SNResourceSchema(ctx);
		}
		snSchema.setDescribedClass(schema.getDescribedResourceID(), ctx);
		
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
	
	// -----------------------------------------------------
	
	/**
	 * Loads all defined and persisted PropertyDeclarations from System
	 */
	public Collection<PropertyDeclaration> loadAllPropertyDeclarations(final Context ctx){
		//Load all properties from store
		LinkedList<PropertyDeclaration> output = new LinkedList<PropertyDeclaration>();
		QueryManager qManager = this.gate.startConversation().createQueryManager();
		Collection<Statement> statements = qManager.findIncomingStatements(RBSchema.PROPERTY_DECL);
		for (Statement stmt : statements) {
			if(stmt==null) continue;
			output.add(convert(new SNPropertyDeclaration((ResourceNode) stmt.getSubject())));
		}
		return output;
	}
	
	// -----------------------------------------------------
	
	/**
	 * Loads all defined and persisted ResourceSchema'S from System
	 */
	public Collection<ResourceSchema> loadAllResourceSchemas(final Context ctx){
		//Load all properties from store
		LinkedList<ResourceSchema> output = new LinkedList<ResourceSchema>();
		QueryManager qManager = this.gate.startConversation().createQueryManager();
		Collection<Statement> statements = qManager.findIncomingStatements(RBSchema.ACTIVITY_CLASS);
		for (Statement stmt : statements) {
			if(stmt==null) continue;
			output.add(convert(new SNResourceSchema((ResourceNode) stmt.getSubject())));
		}
		return output;
	}
	
	
	// -----------------------------------------------------
	
	/**
	 * Converts a {@link SNPropertyDeclaration} to {@link PropertyDeclaration}
	 */
	protected PropertyDeclaration convert(final SNPropertyDeclaration snDecl){
		
		PropertyDeclarationImpl pDec = new PropertyDeclarationImpl();
		
		pDec.setIdentifier(snDecl.getQualifiedName().toURI());
		pDec.setElementaryDataType(snDecl.getDatatype());
		convertConstraints(snDecl, pDec);
		return pDec;
	}
	
	// -----------------------------------------------------
	
	public SNPropertyDeclaration store(final PropertyDeclaration decl, final Context ctx){
		final ResourceNode existing = gate.startConversation().findResource(decl.getIdentifier().getQualifiedName());
		
		final SNPropertyDeclaration snDecl;
		if (existing != null) {
			snDecl = new SNPropertyDeclaration(existing);
		} else {
			snDecl = new SNPropertyDeclaration(ctx);
		}
		convertDeclaration(decl, snDecl, ctx);
		
		gate.startConversation().attach(snDecl);
		return snDecl;
	}
	
	public SNResourceSchema loadSchemaForResource(final ResourceID clazz) {
		return null;
	}
	
	// -----------------------------------------------------
	
	public SNResourceSchema loadPropertyDeclaration(final ResourceID decl) {
		throw new NotYetImplementedException();
	}
	
	// -----------------------------------------------------
	
	public ResourceSchema convert(final SNResourceSchema snSchema) {
		ResourceSchemaImpl schema = new ResourceSchemaImpl(snSchema);
		schema.setDescribedResourceID(snSchema.getDescribedClass());
		for (SNPropertyAssertion snAssertion : snSchema.getPropertyAssertions()){
			
			// create Property Declaration
			final SNPropertyDeclaration snDecl = snAssertion.getPropertyDeclaration();
			if(snDecl==null) continue;
			final PropertyDeclaration decl = convert(snDecl);
			
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
		final ResourceNode existing = gate.startConversation().findResource((decl.getIdentifier().getQualifiedName()));
		
		List<ResourceNode> found = gate.startConversation().createQueryManager().
										findByTag(decl.getIdentifier().getQualifiedName().toURI());
		found.size();
		
		if (existing != null) {
			final SNPropertyDeclaration snDecl = new SNPropertyDeclaration(existing);
			assertion.setPropertyDeclaration(snDecl, ctx);
			convertDeclaration(decl, snDecl, ctx);
		} else {
			final SNPropertyDeclaration snDecl = new SNPropertyDeclaration(ctx);
			snDecl.setName(decl.getName());
			snDecl.setNamespace(decl.getIdentifier().getNamespace());
			assertion.setPropertyDeclaration(snDecl, ctx);
			convertDeclaration(decl, snDecl, ctx);
		}
	}
	
	// -----------------------------------------------------
	
	protected void addConstraint(final SNPropertyDeclaration decl, final Constraint constraint, final Context ctx) {
		if (constraint.isLiteralConstraint()) {
			decl.addLiteralConstraint(constraint.getLiteralConstraint(), ctx);
		} else if (constraint.isResourceTypeConstraint()) {
			decl.addTypeConstraint(constraint.getResourceTypeConstraint(), ctx);
		} else {
			throw new IllegalStateException();
		}
	}
	
	// -----------------------------------------------------
	
	protected void convertDeclaration(final PropertyDeclaration src, final SNPropertyDeclaration target, final Context ctx) {
		target.setDatatype(src.getElementaryDataType(), ctx);
		target.setIdentifier(src.getIdentifier(), ctx);
		for (Constraint constraint: src.getConstraints()){
			addConstraint(target, constraint, ctx);
		}
	}
	
	// -----------------------------------------------------
	
	protected void convertConstraints(final SNPropertyDeclaration src, final PropertyDeclarationImpl target) {
		for (SNConstraint snConst : src.getConstraints()){
			if (snConst.isLiteralConstraint()){
				final String value = snConst.getConstraintValue().asValue().getStringValue();
				final Constraint constraint = ConstraintFactory.buildConstraint(value);
				target.addConstraint(constraint);
			} else if (snConst.isTypeConstraint()) {
				final ResourceID type = snConst.getConstraintValue().asResource();
				final Constraint constraint = ConstraintFactory.buildConstraint(type);
				target.addConstraint(constraint);
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
