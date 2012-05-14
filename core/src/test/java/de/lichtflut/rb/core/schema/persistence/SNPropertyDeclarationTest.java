/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.persistence;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.views.SNScalar;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.mock.schema.ConstraintsFactory;

/**
 * <p>
 * Testclass for {@link PropertyDeclaration}.
 * </p>
 * Created: May 11, 2012
 *
 * @author Ravi Knox
 */
public class SNPropertyDeclarationTest {

	private SNPropertyDeclaration snDecl;
	private Context ctx;
	
	@Before
	public void setUp(){
		ctx= RBSchema.CONTEXT;
		snDecl = new SNPropertyDeclaration();
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNPropertyDeclaration#SNPropertyDeclaration()}.
	 */
	@Test
	public void testSNPropertyDeclaration() {
		SNPropertyDeclaration decl = new SNPropertyDeclaration();
		assertThat(decl, notNullValue());
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNPropertyDeclaration#SNPropertyDeclaration(org.arastreju.sge.model.nodes.ResourceNode)}.
	 */
	@Test
	public void testSNPropertyDeclarationResourceNode() {
		SNResource resource = new SNResource();
		SNPropertyDeclaration decl = new SNPropertyDeclaration(resource);
		assertThat(decl, notNullValue());
		assertThat(decl.getQualifiedName(), notNullValue());
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNPropertyDeclaration#getConstraint()}.
	 */
	@Test
	public void testGetPublicResourceConstraint() {
		Constraint constraint = ConstraintsFactory.buildPublicPersonConstraint();
		SNConstraint snConstraint = new SNConstraint(constraint.getResourceConstraint(), ctx);
		snConstraint.setID(constraint.getID(), ctx);
		snConstraint.setName(constraint.getName(), ctx);
		snConstraint.setPublic(constraint.isPublicConstraint(), ctx);
		
		snDecl.setConstraint(snConstraint, ctx);
		Constraint retrieved = snDecl.getConstraint();
		
		assertConstraints(retrieved, constraint);
	}


	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNPropertyDeclaration#getConstraint()}.
	 */
	@Test
	public void testGetPrivateResourceConstraint() {
		Constraint constraint = ConstraintsFactory.buildPrivatePersonConstraint();
		SNConstraint snConstraint = new SNConstraint(constraint.getResourceConstraint(), ctx);
		
		snDecl.setConstraint(snConstraint, ctx);
		
		assertConstraints(snDecl.getConstraint(), constraint);
	}
	
	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNPropertyDeclaration#getConstraint()}.
	 */
	@Test
	public void testGetPublicLiteralConstraint() {
		Constraint constraint = ConstraintsFactory.buildPublicEmailConstraint();
		SNConstraint snConstraint = new SNConstraint(constraint.getLiteralConstraint(), constraint.getApplicableDatatypes(), ctx);
		snConstraint.setID(constraint.getID(), ctx);
		snConstraint.setName(constraint.getName(), ctx);
		snConstraint.setPublic(constraint.isPublicConstraint(), ctx);
		
		snDecl.setConstraint(snConstraint, ctx);
		Constraint retrieved = snDecl.getConstraint();
		
		assertConstraints(retrieved, constraint);
	}
	
	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNPropertyDeclaration#getConstraint()}.
	 */
	@Test
	public void testGetPrivateLiteralConstraint() {
		Constraint constraint = ConstraintsFactory.buildPrivateLiteralConstraint();
		SNConstraint snConstraint = new SNConstraint(constraint.getLiteralConstraint(), constraint.getApplicableDatatypes(), ctx);
		
		snDecl.setConstraint(snConstraint, ctx);
		
		assertConstraints(snDecl.getConstraint(), constraint);
	}
	
	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNPropertyDeclaration#getPropertyDescriptor()}.
	 */
	@Test
	public void testGetPropertyDescriptor() {
		ResourceID descriptor = new SimpleResourceID("http://test.lf.de/common#Descriptor");
		snDecl.setPropertyDescriptor(descriptor, ctx);
		assertThat(snDecl.getPropertyDescriptor(), equalTo(descriptor));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNPropertyDeclaration#getDatatype()}.
	 */
	@Test
	public void testGetDatatype() {
		snDecl.setDatatype(Datatype.RESOURCE, ctx);
		assertThat(snDecl.getDatatype(), equalTo(Datatype.RESOURCE));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNPropertyDeclaration#getMinOccurs()}.
	 */
	@Test
	public void testGetMinOccurs() {
		int min = 3;
		snDecl.setMinOccurs(new SNScalar(min), ctx);
		assertThat(snDecl.getMinOccurs().getIntegerValue().intValue(), is(min));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNPropertyDeclaration#getMaxOccurs()}.
	 */
	@Test
	public void testGetMaxOccurs() {
		int max = 3;
		snDecl.setMaxOccurs(new SNScalar(max), ctx);
		assertThat(snDecl.getMaxOccurs().getIntegerValue().intValue(), is(max));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNPropertyDeclaration#setSuccessor(de.lichtflut.rb.core.schema.persistence.SNPropertyDeclaration, org.arastreju.sge.context.Context[])}.
	 */
	@Test
	@Ignore("Implement")
	public void testSetSuccessor() {
		fail("Not yet implemented");
	}


	/**
	 * @param constraint
	 * @param retrieved
	 */
	private void assertConstraints(Constraint retrieved, Constraint constraint) {
		assertThat(retrieved.getApplicableDatatypes().size(), is(constraint.getApplicableDatatypes().size()));
		assertThat(retrieved.getID(), equalTo(constraint.getID()));
		assertThat(retrieved.getLiteralConstraint(), equalTo(constraint.getLiteralConstraint()));
		assertThat(retrieved.getName(), equalTo(constraint.getName()));
		assertThat(retrieved.getResourceConstraint(), equalTo(constraint.getResourceConstraint()));
	}
}
