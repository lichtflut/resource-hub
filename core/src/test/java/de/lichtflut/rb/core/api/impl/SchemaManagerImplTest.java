/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api.impl;

import junit.framework.Assert;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.QualifiedName;
import org.junit.Before;
import org.junit.Test;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.impl.DefaultRBServiceProvider;
import de.lichtflut.rb.core.services.impl.SchemaManagerImpl;

/**
 * <p>
 *  Test cases for {@link SchemaManagerImpl}.
 * </p>
 *
 * <p>
 * 	Created Oct 7, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SchemaManagerImplTest {
	
	private final static QualifiedName personQN = new QualifiedName("http://lichtflut.de#Person");

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
		final ResourceSchema original = createSchema();
		manager.store(original);
		
		final ResourceSchema found = manager.findSchemaForType(SNOPS.id(personQN));
		
		Assert.assertNotNull(found);
		
		Assert.assertEquals(3, found.getPropertyDeclarations().size());
	}
	
	@Test
	public void testReplacing() {
		final SchemaManager manager = new SchemaManagerImpl(serviceProvider);
		final ResourceSchema original = createSchema();
		manager.store(original);
		
		final ResourceSchema found = manager.findSchemaForType(SNOPS.id(personQN));
		Assert.assertNotNull(found);
		Assert.assertEquals(3, found.getPropertyDeclarations().size());
		
		final ResourceSchema other = new ResourceSchemaImpl(SNOPS.id(personQN));
		manager.store(other);
		
		ResourceSchema retrieved = manager.findSchemaForType(SNOPS.id(personQN));
		Assert.assertNotNull(retrieved);
		Assert.assertEquals(0, retrieved.getPropertyDeclarations().size());
		
		manager.store(original);
		
		retrieved = manager.findSchemaForType(SNOPS.id(personQN));
		Assert.assertNotNull(retrieved);
		Assert.assertEquals(3, retrieved.getPropertyDeclarations().size());
		
	}
	
	// -----------------------------------------------------
	
	/**
	 * @return the created {@link ResourceSchema}
	 */
	private ResourceSchema createSchema() {
		ResourceSchemaImpl schema = new ResourceSchemaImpl(SNOPS.id(personQN));
		
		PropertyDeclarationImpl pa1 = new PropertyDeclarationImpl(new SimpleResourceID("http://lichtflut.de#","hatGeburtstag"), Datatype.DATE);
		PropertyDeclarationImpl pa2 = new PropertyDeclarationImpl(new SimpleResourceID("http://lichtflut.de#","hatEmail"), Datatype.STRING);
		pa2.setConstraint(ConstraintBuilder.buildLiteralConstraint(".*@.*"));
		PropertyDeclarationImpl pa3 = new PropertyDeclarationImpl(new SimpleResourceID("http://lichtflut.de#","hatAlter"), Datatype.INTEGER);
		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		pa2.setCardinality(CardinalityBuilder.hasAtLeastOneUpTo(3));
		pa3.setCardinality(CardinalityBuilder.hasExcactlyOne());

		schema.addPropertyDeclaration(pa1);
		schema.addPropertyDeclaration(pa2);
		schema.addPropertyDeclaration(pa3);

		return schema;
	}
	
}
