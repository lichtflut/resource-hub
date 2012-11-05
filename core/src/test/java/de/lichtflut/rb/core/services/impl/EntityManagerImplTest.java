/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.naming.QualifiedName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import de.lichtflut.rb.RBCoreTest;
import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.eh.ValidationException;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.entity.impl.RBEntityImpl;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.mock.RBEntityFactory;
import de.lichtflut.rb.mock.RBMock;

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
public class EntityManagerImplTest extends RBCoreTest{

	private EntityManager entityManager;

	// ------------- SetUp & tearDown -----------------------

	@Before
	@Override
	public void setUp() {
		entityManager = new EntityManagerImpl(typeManager, schemaManager, conversation);
	}

	// ------------------------------------------------------

	/**
	 * Test method for {@link de.lichtflut.rb.core.services.impl.EntityManagerImpl#find(org.arastreju.sge.model.ResourceID)}.
	 */
	@Test
	public void testFind() {
		ResourceNode user = getUser();

		// null returns null
		when(conversation.findResource(null)).thenReturn(null);

		RBEntity nullEntity = entityManager.find(null);

		verify(conversation, times(0)).findResource(null);
		assertThat(nullEntity, equalTo(null));

		// find non-existing Entity
		ResourceID randomID = new SimpleResourceID();
		when(conversation.findResource(randomID.getQualifiedName())).thenReturn(null);

		RBEntity nonExistingEntity = entityManager.find(randomID);

		verify(conversation, times(1)).findResource(randomID.getQualifiedName());
		assertThat(nonExistingEntity, equalTo(null));


		// find Entity without type, schema
		ResourceID id = new SimpleResourceID("http://test.de/", "t1");
		when(conversation.findResource(id.getQualifiedName())).thenReturn(new SNResource(id.getQualifiedName()));

		RBEntity entity = entityManager.find(id);

		verify(conversation, times(1)).findResource(id.getQualifiedName());
		assertThat(id.getQualifiedName(), equalTo(entity.getID().getQualifiedName()));
		assertThat(entity.hasSchema(), is(false));
		assertThat(entity.getType(), equalTo(null));


		// find entity with type, no schema
		when(conversation.findResource(user.getQualifiedName())).thenReturn(user);
		when(typeManager.getTypeOfResource(user)).thenReturn(RBMock.PERSON.asResource().asClass());
		when(schemaManager.findSchemaForType(RBMock.PERSON)).thenReturn(null);

		RBEntity e = entityManager.find(new SimpleResourceID(user.getQualifiedName()));

		verify(conversation, times(1)).findResource(user.getQualifiedName());
		verify(typeManager, times(1)).getTypeOfResource(user);
		verify(schemaManager, times(1)).findSchemaForType(RBMock.PERSON);
		assertThat(e.getType(), equalTo(RBMock.PERSON));
		assertThat(e.hasSchema(), is(false));

		reset(conversation);
		reset(typeManager);
		reset(schemaManager);

		// find entity with type, schema
		when(conversation.findResource(user.getQualifiedName())).thenReturn(user);
		when(typeManager.getTypeOfResource(user)).thenReturn(RBMock.PERSON.asResource().asClass());
		when(schemaManager.findSchemaForType(RBMock.PERSON)).thenReturn(new ResourceSchemaImpl(RBMock.PERSON));

		RBEntity e1 = entityManager.find(new SimpleResourceID(user.getQualifiedName()));

		verify(conversation, times(1)).findResource(user.getQualifiedName());
		verify(typeManager, times(1)).getTypeOfResource(user);
		verify(schemaManager, times(1)).findSchemaForType(RBMock.PERSON);
		assertThat(e.getType(), equalTo(RBMock.PERSON));
		assertThat(e1.getType(), equalTo(RBMock.PERSON));
		assertThat(e1.hasSchema(), is(true));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.services.impl.EntityManagerImpl#create(org.arastreju.sge.model.ResourceID)}.
	 */
	@Test
	public void testCreate() {
		ResourceID personType = RB.PERSON;

		// with schema
		when(schemaManager.findSchemaForType(personType)).thenReturn(new ResourceSchemaImpl(personType));

		RBEntity entity = entityManager.create(personType);

		verify(schemaManager, times(1)).findSchemaForType(personType);
		assertThat(entity.hasSchema(), is(true));
		assertThat(entity.getType(), equalTo(personType));

		reset(schemaManager);

		// without schema
		when(schemaManager.findSchemaForType(personType)).thenReturn(null);

		RBEntity entity1 = entityManager.create(personType);

		verify(schemaManager, times(1)).findSchemaForType(personType);
		assertThat(entity1.hasSchema(), is(false));
		assertThat(entity1.getType(), equalTo(personType));
	}

	/**
	 * Test method for
	 * {@link de.lichtflut.rb.core.services.impl.EntityManagerImpl#store(de.lichtflut.rb.core.entity.RBEntity)}.
	 * @throws ValidationException
	 */
	@Test
	public void testStore() throws ValidationException {
		RBEntity person = RBEntityFactory.createPersonEntity();

		entityManager.store(person);

		ResourceNode node = nodeRepresentationOf(person);
		verify(conversation, times(1)).attach(node);
	}

	/**
	 * Test method for
	 * {@link de.lichtflut.rb.core.services.impl.EntityManagerImpl#store(de.lichtflut.rb.core.entity.RBEntity)}.
	 * @throws ValidationException
	 */
	@Test (expected=ValidationException.class)
	public void testStoreInvalidEntity() throws ValidationException {
		RBEntity person = RBEntityFactory.createPersonEntity();
		// Add a second field to 1..1 cardinality
		person.getField(RBMock.HAS_FIRST_NAME).addValue("Peter");
		entityManager.store(person);
	}

	/**
	 * Test method for
	 * {@link de.lichtflut.rb.core.services.impl.EntityManagerImpl#delete(org.arastreju.sge.model.ResourceID)}.
	 */
	@Test
	public void testDelete() {
		ResourceNode user = getUser();

		when(conversation.resolve(user)).thenReturn(user);

		entityManager.delete(user);

		verify(conversation, times(1)).resolve(user);
		verify(conversation, times(1)).remove(user);
	}

	/**
	 * Test method for
	 * {@link de.lichtflut.rb.core.services.impl.EntityManagerImpl#changeType(de.lichtflut.rb.core.entity.RBEntity, org.arastreju.sge.model.ResourceID)}.
	 */
	@Test
	public void testChangeType() {
		RBEntity entity = new RBEntityImpl(getUser(), RBMock.PERSON);

		when(conversation.resolve(entity.getID())).thenReturn(entity.getNode());

		entityManager.changeType(entity, RBMock.ADDRESS);

		verify(conversation, times(1)).resolve(entity.getID());
		assertThat(entity.getType(), equalTo(RBMock.ADDRESS));
	}

	// ------------------------------------------------------

	private ResourceNode getUser() {
		ResourceNode node = new SNResource(new QualifiedName("http://test/user"));
		node.addAssociation(RDF.TYPE, RBMock.PERSON);
		return node;
	}

	/**
	 * Convert {@link RBEntity} to node as seen in implementation.
	 * @param person
	 * @return
	 */
	private ResourceNode nodeRepresentationOf(final RBEntity entity) {
		final ResourceNode node = entity.getNode();
		SNOPS.associate(node, RDF.TYPE, RBSystem.ENTITY);
		for (RBField field : entity.getAllFields()) {
			final Collection<SemanticNode> nodes = toSemanticNodes(field);
			SNOPS.assure(node, field.getPredicate(), nodes);
		}
		// Set label after all entity references have been resolved
		SNOPS.assure(node, RDFS.LABEL, new SNText(entity.getLabel()));
		return node;
	}

	private Collection<SemanticNode> toSemanticNodes(final RBField field) {
		final Collection<SemanticNode> result = new ArrayList<SemanticNode>();
		for (Object value : field.getValues()) {
			if (value == null) {
				// ignore
			} else if (field.getVisualizationInfo().isEmbedded() && value instanceof RBEntity) {
				final RBEntity ref = (RBEntity) value;
				result.add(ref.getID());
			} else if (field.isResourceReference()) {
				final ResourceID ref = (ResourceID) value;
				result.add(new SimpleResourceID(ref.getQualifiedName()));
			} else {
				final ElementaryDataType datatype = Datatype.getCorrespondingArastrejuType(field.getDataType());
				result.add(new SNValue(datatype, value));
			}
		}
		return result;
	}
}
