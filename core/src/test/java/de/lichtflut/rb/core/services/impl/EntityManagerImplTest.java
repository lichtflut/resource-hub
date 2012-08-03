/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.impl.RBEntityImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.TypeManager;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.SimpleQueryResult;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * <p>
 *  JUnit test class for {@link EntityManagerImpl}
 * </p>
 *
 * <p>
 * 	Created Feb 20, 2012
 * </p>
 *
 * @author Ravi Knox
 */
@RunWith(MockitoJUnitRunner.class)
public class EntityManagerImplTest {

	private EntityManager em;
	private SchemaManager sm;
	private TypeManager tm;
	private ModelingConversation mc;
	private Query query;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		sm = mock(SchemaManager.class);
		mc = mock(ModelingConversation.class);
		query = mock(Query.class);
		tm = mock(TypeManager.class);
		
		when(mc.createQuery()).thenReturn(query);

		em = new EntityManagerImpl(tm, sm, mc);

	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.services.impl.EntityManagerImpl#find(org.arastreju.sge.model.ResourceID)}.
	 */
	@Test
	public void testFind() {
		ResourceNode user = getUser();

		// find Entity without type, schema
		ResourceID id = new SimpleResourceID("http://test.de/", "t1");
		when(mc.findResource(id.getQualifiedName())).thenReturn(new SNResource(id.getQualifiedName()));

		RBEntity entity = em.find(id);

		verify(mc, times(1)).findResource(id.getQualifiedName());
		assertTrue(id.getQualifiedName() == entity.getID().getQualifiedName());
		assertFalse(entity.hasSchema());
		assertNotNull(entity);
		assertNull(entity.getType());

		
		// find entity with type
		when(tm.getTypeOfResource(user)).thenReturn(RB.PERSON.asResource().asClass());
		when(mc.findResource(user.getQualifiedName())).thenReturn(user);
		when(sm.findSchemaForType(RB.PERSON)).thenReturn(null);

		RBEntity e = em.find(new SimpleResourceID(user.getQualifiedName()));

		assertNotNull(e.getType());
		assertFalse(e.hasSchema());

		
		// find entity with type, schema
		when(mc.findResource(user.getQualifiedName())).thenReturn(user);
		when(sm.findSchemaForType(RB.PERSON)).thenReturn(new ResourceSchemaImpl(RB.PERSON));

		RBEntity e1 = em.find(new SimpleResourceID(user.getQualifiedName()));

		assertNotNull(e1.getType());
		assertTrue(e1.hasSchema());

		
		// find non-existing Entity
		ResourceID nullID = new SimpleResourceID();
		when(mc.findResource(nullID.getQualifiedName())).thenReturn(null);

		RBEntity nullEntity = em.find(nullID);

		verify(mc, times(1)).findResource(nullID.getQualifiedName());
		assertNull(nullEntity);

	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.services.impl.EntityManagerImpl#create(org.arastreju.sge.model.ResourceID)}.
	 */
	@Test
	public void testCreate() {
		ResourceID type = RB.PERSON;
		// with schema
		when(sm.findSchemaForType(type)).thenReturn(new ResourceSchemaImpl(type));

		RBEntity e = em.create(type);

		assertTrue(e.hasSchema());
		assertEquals(type, e.getType());

		// without schema
		when(sm.findSchemaForType(type)).thenReturn(null);

		RBEntity e1 = em.create(type);

		assertFalse(e1.hasSchema());
		assertEquals(type, e1.getType());
	}

	/**
	 * Test method for
	 * {@link de.lichtflut.rb.core.services.impl.EntityManagerImpl#changeType(de.lichtflut.rb.core.entity.RBEntity, org.arastreju.sge.model.ResourceID)}.
	 */
	@Test
    @Ignore
	public void testChangeType() {
		RBEntity entity = new RBEntityImpl(getUser(), RB.PERSON);
		when(mc.resolve(entity.getID())).thenReturn(entity.getNode());

		em.changeType(entity, RB.ADDRESS);

		verify(mc, times(1)).resolve(entity.getID());
		assertEquals(RB.ADDRESS, entity.getType());
	}

	private ResourceNode getUser() {
		ResourceNode node = new SNResource(new QualifiedName("http://test/user"));
		node.addAssociation(RDF.TYPE, RB.PERSON);
		return node;
	}
	
}
