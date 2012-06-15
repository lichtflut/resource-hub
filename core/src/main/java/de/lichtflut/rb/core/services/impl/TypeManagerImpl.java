/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.services.ServiceProvider;
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
public class TypeManagerImpl extends AbstractService implements TypeManager {
	
	final Logger logger = LoggerFactory.getLogger(TypeManagerImpl.class);
	
	// -----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param provider The service provider.
	 */
	public TypeManagerImpl(final ServiceProvider provider) {
		super(provider);
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
		final ResourceNode existing = mc().findResource(type.getQualifiedName());
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
		final ResourceNode node = mc().resolve(resource);
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
		SNOPS.associate(type, RDF.TYPE, RDFS.CLASS, RB.TYPE_SYSTEM_CONTEXT);
		SNOPS.associate(type, RDF.TYPE, RBSystem.TYPE, RB.TYPE_SYSTEM_CONTEXT);
		mc().attach(type);
		return type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeType(final ResourceID type) {
		getProvider().getSchemaManager().removeSchemaForType(type);
		mc().remove(type);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void addSuperClass(ResourceID type, ResourceID superClass) {
		mc().resolve(type).addAssociation(RDFS.SUB_CLASS_OF, superClass, RB.TYPE_SYSTEM_CONTEXT);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void removeSuperClass(ResourceID type, ResourceID superClass) {
		final ResourceNode typeNode = mc().findResource(type.getQualifiedName());
		if (typeNode != null) {
			SNOPS.remove(typeNode, RDFS.SUB_CLASS_OF, superClass);
		} else {
			logger.warn("The type of which the subclass {} should have been removed does not exist: {}", 
					superClass, type);
		}
	}
	
	// ----------------------------------------------------
	
	public SNProperty findProperty(QualifiedName qn) {
		if (qn == null) { 
			return null;
		}
		final ResourceNode existing = mc().findResource(qn);
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
		SNOPS.associate(property, RDF.TYPE, RDF.PROPERTY, RB.TYPE_SYSTEM_CONTEXT);
		mc().attach(property);
		return property;
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public void removeProperty(SNProperty property) {
		mc().remove(property);
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
	
}
