/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.model.nodes.views.SNProperty;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;
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
	
	final Logger logger = LoggerFactory.getLogger(TypeManagerImpl.class);

    private ModelingConversation conversation;

    private SchemaManager schemaManager;
	
	// -----------------------------------------------------
	
	/**
	 * Default Constructor.
	 */
	public TypeManagerImpl() {
	}

    public TypeManagerImpl(ModelingConversation conversation, SchemaManager schemaManager) {
        this.conversation = conversation;
        this.schemaManager = schemaManager;
    }

    // -----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SNClass findType(ResourceID type) {
		if (type == null) { 
			return null;
		}
		final ResourceNode existing = conversation.findResource(type.getQualifiedName());
		if (existing != null) {
			return existing.asClass();
		} else {
			return null;
		}
	};

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public SNClass getTypeOfResource(final ResourceID resource) {
		final ResourceNode node = conversation.resolve(resource);
		final Set<SemanticNode> objects = SNOPS.objects(node, RDF.TYPE);
		for (SemanticNode sn : objects) {
			if (RBSystem.ENTITY.equals(sn)) {
				continue;
			} 
			return sn.asResource().asClass();
		}
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SNClass> findAllTypes() {
		final List<SNClass> result = new ArrayList<SNClass>();
		final List<ResourceNode> nodes = findResourcesByType(RBSystem.TYPE);
		for (ResourceNode current : nodes) {
			result.add(current.asClass());
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SNClass createType(final QualifiedName qn) {
		final SNClass type = new SNResource(qn).asClass();
		SNOPS.associate(type, RDF.TYPE, RDFS.CLASS);
		SNOPS.associate(type, RDF.TYPE, RBSystem.TYPE);
		conversation.attach(type);
		return type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeType(final ResourceID type) {
		schemaManager.removeSchemaForType(type);
		conversation.remove(type);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void addSuperClass(ResourceID type, ResourceID superClass) {
		conversation.resolve(type).addAssociation(RDFS.SUB_CLASS_OF, superClass);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void removeSuperClass(ResourceID type, ResourceID superClass) {
		final ResourceNode typeNode = conversation.findResource(type.getQualifiedName());
		if (typeNode != null) {
			SNOPS.remove(typeNode, RDFS.SUB_CLASS_OF, superClass);
		} else {
			logger.warn("The type of which the subclass {} should have been removed does not exist: {}", 
					superClass, type);
		}
	}
	
	// ----------------------------------------------------
	
	@Override
	public SNProperty findProperty(QualifiedName qn) {
		if (qn == null) { 
			return null;
		}
		final ResourceNode existing = conversation.findResource(qn);
		if (existing != null) {
			return existing.asProperty();
		} else {
			return null;
		}
	};
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public SNProperty createProperty(QualifiedName qn) {
		final SNProperty property = new SNProperty(qn).asProperty();
		SNOPS.associate(property, RDF.TYPE, RDF.PROPERTY);
		conversation.attach(property);
		return property;
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public void removeProperty(SNProperty property) {
		conversation.remove(property);
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public List<SNProperty> findAllProperties() {
		final List<SNProperty> result = new ArrayList<SNProperty>();
		final List<ResourceNode> nodes = findResourcesByType(RDF.PROPERTY);
		for (ResourceNode current : nodes) {
			result.add(current.asProperty());
		}
		return result;
	}

    // ----------------------------------------------------

    protected List<ResourceNode> findResourcesByType(ResourceID type) {
        final Query query = conversation.createQuery();
        query.addField(RDF.TYPE, type);
        return query.getResult().toList(2000);
    }
	
}
