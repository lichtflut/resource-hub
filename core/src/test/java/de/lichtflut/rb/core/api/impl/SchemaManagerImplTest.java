/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api.impl;

import junit.framework.Assert;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.QualifiedName;
import org.junit.Before;
import org.junit.Test;

import de.lichtflut.rb.core.api.SchemaManager;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionImpl;
import de.lichtflut.rb.core.services.impl.DefaultRBServiceProvider;

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
		serviceProvider = new DefaultRBServiceProvider();
	}
	
	// -----------------------------------------------------
	
	@Test
	public void testStoreAndRetrieve() {
		final SchemaManager manager = new SchemaManagerImpl(serviceProvider);
		
		
		final ResourceSchema original = createSchema();
		manager.store(original);
		
		final ResourceSchema found = manager.findByType(SNOPS.id(personQN));
		
		Assert.assertNotNull(found);
		
		Assert.assertEquals(3, found.getPropertyDeclarations().size());
		
	}
	
	// -----------------------------------------------------
	
	/**
	 * @return the created {@link ResourceSchema}
	 */
	private ResourceSchema createSchema() {
		ResourceSchemaImpl schema = new ResourceSchemaImpl().setDescribedType(SNOPS.id(personQN));
		
		TypeDefinitionImpl p1 = new TypeDefinitionImpl();
		TypeDefinitionImpl p2 = new TypeDefinitionImpl();
		TypeDefinitionImpl p3 = new TypeDefinitionImpl();
		p1.setName("http://lichtflut.de#geburtsdatum");
		p2.setName("http://lichtflut.de#email");
		p3.setName("http://lichtflut.de#alter");

		p1.setElementaryDataType(ElementaryDataType.DATE);
		p2.setElementaryDataType(ElementaryDataType.STRING);
		p3.setElementaryDataType(ElementaryDataType.INTEGER);

		p2.addConstraint(ConstraintBuilder.buildConstraint(".*@.*"));
		PropertyDeclarationImpl pa1 = new PropertyDeclarationImpl(new SimpleResourceID("http://lichtflut.de#","hatGeburtstag"), p1);
		PropertyDeclarationImpl pa2 = new PropertyDeclarationImpl(new SimpleResourceID("http://lichtflut.de#","hatEmail"), p2);
		PropertyDeclarationImpl pa3 = new PropertyDeclarationImpl(new SimpleResourceID("http://lichtflut.de#","hatAlter"), p3);
		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		pa2.setCardinality(CardinalityBuilder.hasAtLeastOneUpTo(3));
		pa3.setCardinality(CardinalityBuilder.hasExcactlyOne());

		schema.addPropertyDeclaration(pa1);
		schema.addPropertyDeclaration(pa2);
		schema.addPropertyDeclaration(pa3);

		return schema;
	}
	
}