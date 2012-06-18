/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.schema.model.FieldLabelDefinition;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.impl.DefaultRBServiceProvider;
import de.lichtflut.rb.core.services.impl.SchemaManagerImpl;
import de.lichtflut.rb.mock.schema.ResourceSchemaFactory;

/**
 * <p>
 * Test cases for {@link SchemaManagerImpl}.
 * </p>
 * 
 * <p>
 * Created Oct 7, 2011
 * </p>
 * 
 * @author Oliver Tigges
 */
public class SchemaManagerImplTest {

	private DefaultRBServiceProvider serviceProvider;

	// -----------------------------------------------------

	@Before
	public void setUp() {
		serviceProvider = new DefaultRBServiceProvider(new RBConfig());
	}

	// -----------------------------------------------------

	@Test
	public void testStoreAndRetrieve() {
		final SchemaManager manager = new SchemaManagerImpl(serviceProvider);
		final ResourceSchema original = ResourceSchemaFactory.buildPersonSchema();
		manager.store(original);

		final ResourceSchema found = manager.findSchemaForType(original.getDescribedType());

		assertThat(found, notNullValue());
		assertThat(found.getPropertyDeclarations().size(), is(original.getPropertyDeclarations().size()));
		PropertyDeclaration decl = found.getPropertyDeclarations().get(0);
		PropertyDeclaration originalDecl = original.getPropertyDeclarations().get(0);
		assertThat(decl.getCardinality().getMaxOccurs(), is(originalDecl.getCardinality().getMaxOccurs()));
		assertThat(decl.getCardinality().getMinOccurs(), is(originalDecl.getCardinality().getMinOccurs()));
		assertThat(decl.getConstraint(), nullValue());
		assertThat(decl.getDatatype(), equalTo(originalDecl.getDatatype()));
		assertThat(decl.getFieldLabelDefinition(), instanceOf(FieldLabelDefinition.class));
		assertThat(decl.getPropertyDescriptor(), equalTo(originalDecl.getPropertyDescriptor()));
	}

	@Test
	public void testReplacing() {
		final SchemaManager manager = new SchemaManagerImpl(serviceProvider);
		final ResourceSchema original = ResourceSchemaFactory.buildPersonSchema();
		manager.store(original);

		final ResourceSchema found = manager.findSchemaForType(original.getDescribedType());
		assertThat(found, notNullValue());
		assertThat(found.getPropertyDeclarations().size(), is(original.getPropertyDeclarations().size()));

		final ResourceSchema other = new ResourceSchemaImpl(original.getDescribedType());
		manager.store(other);

		ResourceSchema retrieved = manager.findSchemaForType(original.getDescribedType());
		assertNotNull(retrieved);
		assertThat(found.getPropertyDeclarations().size(), is(original.getPropertyDeclarations().size()));

		manager.store(original);

		retrieved = manager.findSchemaForType(original.getDescribedType());
		assertNotNull(retrieved);
		assertThat(found.getPropertyDeclarations().size(), is(original.getPropertyDeclarations().size()));

	}

}
