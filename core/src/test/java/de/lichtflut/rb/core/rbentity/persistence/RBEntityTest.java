/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.rbentity.persistence;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.Assert;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.SimpleResourceID;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.RBEntity;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintFactory;
import de.lichtflut.rb.core.schema.model.impl.PropertyAssertionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.core.spi.RBServiceProviderFactory;


/**
 * Testcase to test ResourceTypeInstance- validators, ticket-algorithms and constraints.
 *
 * Created: May 20, 2011
 *
 * @author Nils Bleisch
 */
public final class RBEntityTest {

	/**
	 * Test to persist and find entities.
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testPersistAndFindRBEntities(){
		RBServiceProvider provider = RBServiceProviderFactory.getDefaultServiceProvider();

		ResourceSchema schema = createSchema();
		//Store the schema
		provider.getResourceSchemaManagement().storeOrOverrideResourceSchema(schema);

		//Build an instance
		RBEntity<Object> instance = schema.generateRBEntity();
		Assert.assertNotNull(instance);

		try{
			instance.addValueFor("http://lichtflut.de#hatGeburtstag", "test1");
			instance.addValueFor("http://lichtflut.de#hatEmail", "test1@test.com");
			instance.addValueFor("http://lichtflut.de#hatAlter", "24");

		}catch(Exception any){
			any.printStackTrace();
		}
		//Try to create this
		Assert.assertTrue(provider.getRBEntityManagement().createOrUpdateRTInstance(instance));
		Collection<RBEntity> instances = provider.getRBEntityManagement().loadAllResourceTypeInstancesForSchema(schema);

		Assert.assertTrue(instances.size()==1);
		for (RBEntity i : instances) {
			Assert.assertTrue(i.getValuesFor("http://lichtflut.de#hatGeburtstag").contains("test1"));
			Assert.assertTrue(i.getValuesFor("http://lichtflut.de#hatEmail").contains("test1@test.com"));
			Assert.assertTrue(i.getValuesFor("http://lichtflut.de#hatAlter").contains("24"));
		}
	}

	/**
	 * Test to persist and find the specific entity.
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testPersistAndFindASpecificEntity(){
	RBServiceProvider provider = RBServiceProviderFactory.getDefaultServiceProvider();

		ResourceSchema schema = createSchema();
		//Store the schema
		provider.getResourceSchemaManagement().storeOrOverrideResourceSchema(schema);
		//Build an instance
		RBEntity<Object> instance = schema.generateRBEntity();
		Assert.assertNotNull(instance);
		try{
			instance.addValueFor("http://lichtflut.de#hatGeburtstag", "test1");
			instance.addValueFor("http://lichtflut.de#hatEmail", "test1@test.com");
			instance.addValueFor("http://lichtflut.de#hatAlter", "24");

		}catch(Exception any){
			any.printStackTrace();
		}
		Assert.assertTrue(provider.getRBEntityManagement().createOrUpdateRTInstance(instance));
		Collection<RBEntity> instances = provider.getRBEntityManagement().loadAllResourceTypeInstancesForSchema(schema);
		Assert.assertTrue(instances.size()==1);
		RBEntity entity = new ArrayList<RBEntity>(instances).get(0);

		//Made some proofs

		Assert.assertEquals(provider.getRBEntityManagement().loadRBEntity(entity), entity);
		Assert.assertEquals(provider.getRBEntityManagement().loadRBEntity(entity.getQualifiedName()), entity);
		Assert.assertEquals(provider.getRBEntityManagement().loadRBEntity(entity.getQualifiedName().toURI()), entity);
		//Add a hash on the entities identifier to generate an identifier which shouldnt exists.
		//Now try to assert that loadRBEntity should return null for those identifiers
		Assert.assertNull(provider.getRBEntityManagement().loadRBEntity(entity.getQualifiedName().toURI()+ "#"));
	}


	/**
	 * DEMO-TEST to test hudson.
	 */
	@Test
	public void testHudson(){
		Assert.assertTrue(false);
	}

	/**
	 * Tests.
	 * @return shema
	 */
	private ResourceSchema createSchema() {
		ResourceSchemaImpl schema = new ResourceSchemaImpl("http://lichtflut.de#","personschema");
		PropertyDeclarationImpl p1 = new PropertyDeclarationImpl();
		PropertyDeclarationImpl p2 = new PropertyDeclarationImpl();
		PropertyDeclarationImpl p3 = new PropertyDeclarationImpl();
		p1.setName("http://lichtflut.de#geburtsdatum");
		p2.setName("http://lichtflut.de#email");
		p3.setName("http://lichtflut.de#alter");

		p1.setElementaryDataType(ElementaryDataType.STRING);
		p2.setElementaryDataType(ElementaryDataType.STRING);
		p3.setElementaryDataType(ElementaryDataType.INTEGER);

		p2.addConstraint(ConstraintFactory.buildConstraint(".*@.*"));
		PropertyAssertionImpl pa1 = new PropertyAssertionImpl(new SimpleResourceID("http://lichtflut.de#","hatGeburtstag"), p1);
		PropertyAssertionImpl pa2 = new PropertyAssertionImpl(new SimpleResourceID("http://lichtflut.de#","hatEmail"), p2);
		PropertyAssertionImpl pa3 = new PropertyAssertionImpl(new SimpleResourceID("http://lichtflut.de#","hatAlter"), p3);
		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		pa2.setCardinality(CardinalityBuilder.hasAtLeastOneUpTo(2));
		pa3.setCardinality(CardinalityBuilder.hasExcactlyOne());

		schema.addPropertyAssertion(pa1);
		schema.addPropertyAssertion(pa2);
		schema.addPropertyAssertion(pa3);

		return schema;
	}

}
