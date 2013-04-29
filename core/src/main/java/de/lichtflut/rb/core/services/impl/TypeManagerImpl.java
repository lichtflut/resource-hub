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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.Conversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.model.nodes.views.SNProperty;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.common.SchemaIdentifyingType;
import de.lichtflut.rb.core.services.ConversationFactory;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.TypeManager;

/**
 * <p>
 *  Implementation of {@link TypeManager}.
 * </p>
 *
 * <p>
 * 	Created Sep 19, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class TypeManagerImpl implements TypeManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(TypeManagerImpl.class);

	private final ConversationFactory conversationFactory;

	private final SchemaManager schemaManager;

	// -----------------------------------------------------

	/**
	 * Constructor.
	 * @param conversationFactory The factory for conversations.
	 * @param schemaManager The schema manager.
	 */
	public TypeManagerImpl(final ConversationFactory conversationFactory, final SchemaManager schemaManager) {
		this.conversationFactory = conversationFactory;
		this.schemaManager = schemaManager;
	}

	// -----------------------------------------------------

	@Override
	public SNClass find(final ResourceID type) {
		if (type == null) {
			return null;
		}
		final ResourceNode existing = conversation().findResource(type.getQualifiedName());
		if (existing != null) {
			return SNClass.from(existing);
		} else {
			return null;
		}
	}

	@Override
	public SNClass getTypeOfResource(final ResourceID resource) {
		final ResourceNode attached = conversation().resolve(resource);
		return SchemaIdentifyingType.of(attached);
	}

	@Override
	public List<SNClass> findAllTypes() {
		final List<SNClass> result = new ArrayList<SNClass>();
		final List<ResourceNode> nodes = findResourcesByType(RBSystem.TYPE);
		for (ResourceNode current : nodes) {
			result.add(SNClass.from(current));
		}
		return result;
	}

	@Override
	public SNClass createType(final QualifiedName qn) {
		final SNClass type = SNClass.from(new SNResource(qn));
		SNOPS.associate(type, RDF.TYPE, RDFS.CLASS);
		SNOPS.associate(type, RDF.TYPE, RBSystem.TYPE);
		conversation().attach(type);
		return type;
	}

	@Override
	public void removeType(final ResourceID type) {
		schemaManager.removeSchemaForType(type);
		conversation().remove(type);
	}

	@Override
	public Set<SNClass> getSuperClasses(final ResourceID base) {
		SNClass baseClass = find(base);
		if (baseClass != null) {
			return baseClass.getSuperClasses();
		}
		return Collections.emptySet();
	}

	@Override
	public void addSuperClass(final ResourceID type, final ResourceID superClass) {
		conversation().resolve(type).addAssociation(RDFS.SUB_CLASS_OF, superClass);
	}

	@Override
	public void removeSuperClass(final ResourceID type, final ResourceID superClass) {
		final ResourceNode typeNode = conversation().findResource(type.getQualifiedName());
		if (typeNode != null) {
			SNOPS.remove(typeNode, RDFS.SUB_CLASS_OF, superClass);
		} else {
			LOGGER.warn("The type of which the subclass {} should have been removed does not exist: {}",
					superClass, type);
		}
	}

	@Override
	public Set<SNClass> getSubClasses(final ResourceID base) {
		Set<SNClass> subClasses = new HashSet<SNClass>();
		Query query = conversation().createQuery();
		query.addField(RDFS.SUB_CLASS_OF, base);
		for (ResourceNode node : query.getResult()) {
			subClasses.add(SNClass.from(node));
		}
		return subClasses;
	}

	// ----------------------------------------------------

	@Override
	public SNProperty findProperty(final QualifiedName qn) {
		if (qn == null) {
			return null;
		}
		final ResourceNode existing = conversation().findResource(qn);
		return SNProperty.from(existing);
	}

	@Override
	public SNProperty createProperty(final QualifiedName qn) {
		final SNProperty property = new SNProperty(qn);
		SNOPS.associate(property, RDF.TYPE, RDF.PROPERTY);
		conversation().attach(property);
		return property;
	}

	@Override
	public void removeProperty(final SNProperty property) {
		conversation().remove(property);
	}

	@Override
	public List<SNProperty> findAllProperties() {
		final List<SNProperty> result = new ArrayList<SNProperty>();
		final List<ResourceNode> nodes = findResourcesByType(RDF.PROPERTY);
		for (ResourceNode current : nodes) {
			result.add(SNProperty.from(current));
		}
		return result;
	}

	// ----------------------------------------------------

	protected List<ResourceNode> findResourcesByType(final ResourceID type) {
		final Query query = conversation().createQuery();
		query.addField(RDF.TYPE, type);
		return query.getResult().toList(2000);
	}

	// ----------------------------------------------------

	private Conversation conversation() {
		return conversationFactory.getConversation(RBSystem.TYPE_SYSTEM_CTX);
	}

}
