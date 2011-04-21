/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.persistence;

import java.math.BigInteger;

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNScalar;
import org.arastreju.sge.model.nodes.views.SNUri;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

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
	
	public SNResourceSchema store(final ResourceSchema schema) {
		final Context ctx = null;
		final SNResourceSchema snSchema = new SNResourceSchema();
		
		for (PropertyAssertion assertion : schema.getPropertyAssertions()) {
			final SNPropertyAssertion snAssertion = new SNPropertyAssertion();
			snAssertion.setMinOccurs(toScalar(assertion.getCardinality().getMinOccurs()), ctx);
			snAssertion.setMaxOccurs(toScalar(assertion.getCardinality().getMaxOccurs()), ctx);
			snAssertion.setDescriptor(assertion.getPropertyDescriptor(), ctx);
			snSchema.addPropertyAssertion(snAssertion, ctx);
		}
		
		return snSchema;
	}
	
	// -----------------------------------------------------
	
	protected void addDeclaration(final SNPropertyAssertion assertion, PropertyDeclaration decl) {
		final Context ctx = null;
		final String id = decl.getName();
		final ResourceNode existing = gate.startConversation().findResource(new QualifiedName(id));
		if (existing != null) {
			throw new NotYetImplementedException();
		} else {
			final SNPropertyDeclaration snDecl = new SNPropertyDeclaration();
			snDecl.setDatatype(decl.getElementaryDataType(), ctx);
			snDecl.setIdentifier(new SNUri(decl.getName()), ctx);
			assertion.setPropertyDeclaration(snDecl, ctx);
		}
	}
	
	// -----------------------------------------------------
	
	private SNScalar toScalar(final int value) {
		return new SNScalar(BigInteger.valueOf(value));
	}
	

}
