/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.rbentity.persistence;

import org.junit.Test;

/**
 * Testcase to test ResourceTypeInstance- validators, ticket-algorithms and
 * constraints.
 * 
 * Created: May 20, 2011
 * 
 * @author Nils Bleisch TODO: FIX TESTS!!!
 */
public final class RBEntityImplTest {

	/**
     *
     */
	@Test
	public void testNewRBEntity() {
//		// Create a shema
//		ResourceSchema schema = null;
//
//		// Create two entitys with given shema
//		RBEntityImpl e1 = new RBEntityImpl(new SNResource(), schema);
//		RBEntityImpl e2 = new RBEntityImpl(new SNResource(), schema);
//
//		ServiceProvider sp = new DefaultRBServiceProvider(new RBConfig());
//
//		// Get Entitymanager
//		EntityManager m = sp.getEntityManager();
//		SchemaManager sm = sp.getSchemaManager();
//
//		sm.store(schema);
//
//		// Add a Email to the entities
//		e1.getField(new SimpleResourceID("http://lichtflut.de#hatEmail")).addValue("mutter@fam.com");
//
//		// Store entity
//		m.store(e1);
//
//		// Add Field to entity
//		e2.getField(new SimpleResourceID("http://lichtflut.de#hatEmail")).addValue("kind@fam.com");
//
//		// Add a custom Field
//		AbstractRBField newField = new UndeclaredRBField(new SimpleResourceID("http://lichtflut.de#whatever"),
//				Collections.<SemanticNode> emptySet());
//		newField.addValue("haha");
//		newField.addValue("hoho");
//		newField.addValue("muhahaha");
//		e2.addField(newField);
//
//		// Add entity as field
//		e1.getField(new SimpleResourceID("http://lichtflut.de#hatKind")).addValue(e2.getNode());
//
//		// store entities
//		m.store(e1);
//		m.store(e2);
//
//		// Tests
//		Assert.assertEquals(4, m.find(e1.getID()).getAllFields().size());
//		Assert.assertEquals(2, m.findByType(new SimpleResourceID("http://lf.de#", "Person")).size());
//
//		m.delete(e1.getID());
	}

}
