/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.services.ConversationFactory;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.TypeManager;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.model.nodes.views.SNProperty;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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

    private SchemaManager schemaManager;
	
	// -----------------------------------------------------

    /**
     * Constructor.
     * @param conversationFactory The factory for conversations.
     * @param schemaManager The schema manager.
     */
    public TypeManagerImpl(ConversationFactory conversationFactory, SchemaManager schemaManager) {
        this.conversationFactory = conversationFactory;
        this.schemaManager = schemaManager;
    }

    // -----------------------------------------------------
	
	@Override
	public SNClass findType(ResourceID type) {
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

        SemanticNode type = null;
        for (Statement assoc : attached.getAssociations()) {
            if (RBSystem.HAS_SCHEMA_IDENTIFYING_TYPE.equals(assoc.getPredicate())) {
                // return directly
                return SNClass.from(assoc.getObject());
            }
            if (RDF.TYPE.equals(assoc.getPredicate()) && !RBSystem.ENTITY.equals(assoc.getObject())) {
                // store until we find a better (system:hasSchemaIdenfifyingType)
                type = assoc.getObject();
            }
        }
        return SNClass.from(type);
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
	public void addSuperClass(ResourceID type, ResourceID superClass) {
        conversation().resolve(type).addAssociation(RDFS.SUB_CLASS_OF, superClass);
	}
	
	@Override
	public void removeSuperClass(ResourceID type, ResourceID superClass) {
		final ResourceNode typeNode = conversation().findResource(type.getQualifiedName());
		if (typeNode != null) {
			SNOPS.remove(typeNode, RDFS.SUB_CLASS_OF, superClass);
		} else {
			LOGGER.warn("The type of which the subclass {} should have been removed does not exist: {}",
                    superClass, type);
		}
	}
	
	// ----------------------------------------------------
	
	@Override
	public SNProperty findProperty(QualifiedName qn) {
		if (qn == null) { 
			return null;
		}
		final ResourceNode existing = conversation().findResource(qn);
        return SNProperty.from(existing);
	}
	
	@Override
	public SNProperty createProperty(QualifiedName qn) {
		final SNProperty property = new SNProperty(qn);
		SNOPS.associate(property, RDF.TYPE, RDF.PROPERTY);
        conversation().attach(property);
		return property;
	}

	@Override
	public void removeProperty(SNProperty property) {
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

    protected List<ResourceNode> findResourcesByType(ResourceID type) {
        final Query query = conversation().createQuery();
        query.addField(RDF.TYPE, type);
        return query.getResult().toList(2000);
    }

    // ----------------------------------------------------

    private ModelingConversation conversation() {
        return conversationFactory.getConversation(RBSystem.TYPE_SYSTEM_CTX);
    }

}
