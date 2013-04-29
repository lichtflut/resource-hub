/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
import java.util.List;
import java.util.Map;

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
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.naming.QualifiedName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBCoreTest;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.data.RBEntityFactory;
import de.lichtflut.rb.core.data.RBTestConstants;
import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.eh.ValidationException;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.entity.RBFieldValue;
import de.lichtflut.rb.core.entity.impl.RBEntityImpl;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.services.EntityManager;

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
		when(typeManager.getTypeOfResource(user)).thenReturn(SNClass.from(RB.PERSON));
		when(schemaManager.findSchemaForType(RB.PERSON)).thenReturn(null);

		RBEntity e = entityManager.find(new SimpleResourceID(user.getQualifiedName()));

		verify(conversation, times(1)).findResource(user.getQualifiedName());
		verify(typeManager, times(1)).getTypeOfResource(user);
		verify(schemaManager, times(1)).findSchemaForType(RB.PERSON);
		assertThat(e.getType(), equalTo(RB.PERSON));
		assertThat(e.hasSchema(), is(false));

		reset(conversation);
		reset(typeManager);
		reset(schemaManager);

		// find entity with type, schema
		when(conversation.findResource(user.getQualifiedName())).thenReturn(user);
		when(typeManager.getTypeOfResource(user)).thenReturn(SNClass.from(RB.PERSON));
		when(schemaManager.findSchemaForType(RB.PERSON)).thenReturn(new ResourceSchemaImpl(RB.PERSON));

		RBEntity e1 = entityManager.find(new SimpleResourceID(user.getQualifiedName()));

		verify(conversation, times(1)).findResource(user.getQualifiedName());
		verify(typeManager, times(1)).getTypeOfResource(user);
		verify(schemaManager, times(1)).findSchemaForType(RB.PERSON);
		assertThat(e.getType(), equalTo(RB.PERSON));
		assertThat(e1.getType(), equalTo(RB.PERSON));
		assertThat(e1.hasSchema(), is(true));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.services.impl.EntityManagerImpl#create(org.arastreju.sge.model.ResourceID)}.
	 */
	@Test
	public void testCreate() {
		ResourceID personType = RB.PERSON;
		ResourceNode personSchema = new SNResource(personType.getQualifiedName());
		personSchema.addAssociation(RBSystem.HAS_SCHEMA_IDENTIFYING_TYPE, personType);

		// with schema
		when(schemaManager.findSchemaForType(personType)).thenReturn(new ResourceSchemaImpl(personType));
		when(conversation.findResource(personType.getQualifiedName())).thenReturn(personSchema);

		RBEntity entity = entityManager.create(personType);

		verify(schemaManager, times(1)).findSchemaForType(personType);
		verify(conversation, times(3)).findResource(personType.getQualifiedName());
		assertThat(entity.hasSchema(), is(true));
		assertThat(entity.getType(), equalTo(personType));

		reset(schemaManager);
		reset(conversation);

		// without schema
		when(schemaManager.findSchemaForType(personType)).thenReturn(null);
		when(conversation.findResource(personType.getQualifiedName())).thenReturn(personType.asResource());

		RBEntity entity1 = entityManager.create(personType);

		verify(schemaManager, times(1)).findSchemaForType(null);
		verify(conversation, times(2)).findResource(personType.getQualifiedName());
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
	 * {@link de.lichtflut.rb.core.services.impl.EntityManagerImpl#validate(de.lichtflut.rb.core.entity.RBEntity)}.
	 * @throws ValidationException
	 */
	@Test
	public void testValidate() throws ValidationException {
		RBEntity person = RBEntityFactory.createPersonEntity();
		// Add a second field to 1..1 cardinality
		person.getField(RB.HAS_FIRST_NAME).addValue("Peter");
		Map<Integer, List<RBField>> errors = entityManager.validate(person);
		assertThat(errors.get(ErrorCodes.CARDINALITY_EXCEPTION).size(), is(1));
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
		RBEntity entity = new RBEntityImpl(getUser(), RB.PERSON);

		when(conversation.resolve(entity.getID())).thenReturn(entity.getNode());

		entityManager.changeType(entity, RBTestConstants.ADDRESS);

		verify(conversation, times(1)).resolve(entity.getID());
		assertThat(entity.getType(), equalTo(RBTestConstants.ADDRESS));
	}

	// ------------------------------------------------------

	private ResourceNode getUser() {
		ResourceNode node = new SNResource(new QualifiedName("http://test/user"));
		node.addAssociation(RDF.TYPE, RB.PERSON);
		return node;
	}

	/**
	 * Convert {@link RBEntity} to node as seen in implementation.
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
		for (RBFieldValue fieldvalue : field.getValues()) {
			Object value = fieldvalue.getValue();
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
