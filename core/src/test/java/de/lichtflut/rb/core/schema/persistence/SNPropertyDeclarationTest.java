/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.persistence;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.views.SNScalar;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

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

	@Before
	public void setUp(){
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
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNPropertyDeclaration#getPropertyDescriptor()}.
	 */
	@Test
	public void testGetPropertyDescriptor() {
		ResourceID descriptor = new SimpleResourceID("http://test.lf.de/common#Descriptor");
		snDecl.setPropertyDescriptor(descriptor);
		assertThat(snDecl.getPropertyDescriptor(), equalTo(descriptor));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNPropertyDeclaration#getDatatype()}.
	 */
	@Test
	public void testGetDatatype() {
		snDecl.setDatatype(Datatype.RESOURCE);
		assertThat(snDecl.getDatatype(), equalTo(Datatype.RESOURCE));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNPropertyDeclaration#getMinOccurs()}.
	 */
	@Test
	public void testGetMinOccurs() {
		int min = 3;
		snDecl.setMinOccurs(new SNScalar(min));
		assertThat(snDecl.getMinOccurs().getIntegerValue().intValue(), is(min));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.persistence.SNPropertyDeclaration#getMaxOccurs()}.
	 */
	@Test
	public void testGetMaxOccurs() {
		int max = 3;
		snDecl.setMaxOccurs(new SNScalar(max));
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

}
