/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.junit.Test;

import de.lichtflut.rb.core.common.EntityLabelBuilder;
import de.lichtflut.rb.core.common.EntityLabelBuilder.DefaultBuilder;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.mock.RBEntityFactory;
import de.lichtflut.rb.mock.RBMock;
import de.lichtflut.rb.mock.schema.PropertyDeclarationFactory;

/**
 * <p>
 * Testclass for {@link ResourceSchemaImpl}.
 * </p>
 * Created: May 7, 2012
 *
 * @author Ravi Knox
 */
public class ResourceSchemaImplTest {

	private ResourceSchema schema;
	private final ResourceID describedType = RBMock.PERSON;

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl#ResourceSchemaImpl()}.
	 */
	@Test
	public void testResourceSchemaImpl() {
		schema = new ResourceSchemaImpl();
		assertNotNull("Schema is null.", schema);
		assertNotNull("Label builder should not be null.", schema.getLabelBuilder());
		assertNull("Described type should be null.", schema.getDescribedType());
		assertEquals("PropertyDeclarations should be empty.", Collections.EMPTY_LIST, schema.getPropertyDeclarations());
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl#ResourceSchemaImpl(org.arastreju.sge.model.ResourceID)}.
	 */
	@Test
	public void testResourceSchemaImplResourceID() {
		RBEntity entity = RBEntityFactory.createPersonEntity();
		schema = new ResourceSchemaImpl(describedType);
		assertNotNull("Schema is null.", schema);
		assertNotNull("Label builder should not be null.", schema.getLabelBuilder());
		assertEquals("Label is not as expexted.", entity.getNode().getQualifiedName().getSimpleName(), schema.getLabelBuilder().build(entity));
		assertEquals("Described type is not as expected.", entity.getType(), schema.getDescribedType());
		assertEquals("PropertyDeclarations should be empty.", Collections.EMPTY_LIST, schema.getPropertyDeclarations());
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl#getDescribedType()}.
	 */
	@Test
	public void testGetDescribedType() {
		schema = new ResourceSchemaImpl(describedType);
		assertEquals("Described type is not as expected.", describedType, schema.getDescribedType());
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl#setDescribedType(org.arastreju.sge.model.ResourceID)}.
	 */
	@Test
	public void testSetDescribedType() {
		ResourceSchemaImpl schema = new ResourceSchemaImpl();
		schema.setDescribedType(describedType);
		assertEquals("Described type is not as expected.", describedType, schema.getDescribedType());
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl#getPropertyDeclarations()}.
	 */
	@Test
	public void testGetPropertyDeclarations() {
		schema = new ResourceSchemaImpl(describedType);
		List<PropertyDeclaration> initialDEcls = PropertyDeclarationFactory.buildPersonPropertyDecls();
		for (PropertyDeclaration declaration: initialDEcls) {
			schema.addPropertyDeclaration(declaration);
		}
		assertEquals("Number of PropertyDecls is not as expected.", initialDEcls.size(), schema.getPropertyDeclarations().size());
		for (PropertyDeclaration declaration : schema.getPropertyDeclarations()) {
			assertTrue("PropertyDecl is not containt in initial input", initialDEcls.contains(declaration));
		}
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl#addPropertyDeclaration(de.lichtflut.rb.core.schema.model.PropertyDeclaration)}.
	 */
	@Test
	public void testAddPropertyDeclaration() {
		schema = new ResourceSchemaImpl(describedType);
		List<PropertyDeclaration> initialDEcls = PropertyDeclarationFactory.buildPersonPropertyDecls();
		for (PropertyDeclaration declaration: initialDEcls) {
			schema.addPropertyDeclaration(declaration);
		}
		assertEquals("Number of PropertyDecls is not as expected.", initialDEcls.size(), schema.getPropertyDeclarations().size());
		for (PropertyDeclaration declaration : schema.getPropertyDeclarations()) {
			assertTrue("PropertyDecl is not containt in initial input", initialDEcls.contains(declaration));
		}
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl#addPropertyDeclaration(de.lichtflut.rb.core.schema.model.PropertyDeclaration)}.
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testAddPropertyDeclarationDuplicate() {
		schema = new ResourceSchemaImpl(describedType);
		schema.addPropertyDeclaration(PropertyDeclarationFactory.buildHasNameProperty());
		schema.addPropertyDeclaration(PropertyDeclarationFactory.buildHasNameProperty());
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl#getLabelBuilder()}.
	 */
	@Test
	public void testGetLabelBuilder() {
		ResourceSchemaImpl schema = new ResourceSchemaImpl(describedType);
		assertNotNull("Default label shouldnot be null", schema.getLabelBuilder());

		EntityLabelBuilder builder = DefaultBuilder.DEFAULT;
		schema.setLabelBuilder(builder);

		assertNotNull("Labelbuilder should not be null.", schema.getLabelBuilder());
		assertEquals("Labelbuilder is not asexpected.", builder, schema.getLabelBuilder());
	}

	@Test
	public void testGetQuickInfo() {
		ResourceSchemaImpl schema = new ResourceSchemaImpl(describedType);
		PropertyDeclaration declHasName = PropertyDeclarationFactory.buildHasNameProperty();
		PropertyDeclaration declHasChildren = PropertyDeclarationFactory.buildHasChildrenPropertyDecl();

		schema.addPropertyDeclaration(declHasChildren);
		schema.addPropertyDeclaration(declHasName);

		schema.addQuickInfo(declHasName.getPropertyDescriptor());
		schema.addQuickInfo(declHasChildren.getPropertyDescriptor());

		assertThat(schema.getQuickInfo().size(), is(2));
	}

}
