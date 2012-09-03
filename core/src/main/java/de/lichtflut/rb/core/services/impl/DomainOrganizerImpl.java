/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.organizer.ContextDeclaration;
import de.lichtflut.rb.core.organizer.NamespaceDeclaration;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.core.services.ServiceContext;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.Organizer;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.naming.Namespace;
import org.arastreju.sge.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.arastreju.sge.SNOPS.assure;
import static org.arastreju.sge.SNOPS.singleAssociation;

/**
 * <p>
 *  Implementation of {@link DomainOrganizer}.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class DomainOrganizerImpl implements DomainOrganizer {
	
	private final Logger logger = LoggerFactory.getLogger(EntityManagerImpl.class);

    private ServiceContext context;

    private Organizer organizer;

    private ModelingConversation conversation;

    // -----------------------------------------------------

	/**
	 * Constructor.
	 */
	public DomainOrganizerImpl(ServiceContext context, ModelingConversation conversation, Organizer organizer) {
        this.context = context;
		this.conversation = conversation;
        this.organizer = organizer;
    }
	
	// ----------------------------------------------------
	
	@Override
	public List<Namespace> getNamespaces() {
		return new ArrayList<Namespace>(organizer.getNamespaces());
	}

    @Override
	public void registerNamespace(NamespaceDeclaration decl) {
        organizer.registerNamespace(decl.getUri(), decl.getPrefix());
    }
	
	// ----------------------------------------------------
	
	@Override
	public List<Context> getContexts() {
		return new ArrayList<Context>(organizer.getContexts());
	}
	
	@Override
	public void registerContext(ContextDeclaration decl) {
        organizer.registerContext(decl.getQualifiedName());
	}
	
	// ----------------------------------------------------

	@Override
	public void setDomainOrganization(final ResourceID organization) {
		logger.info("Setting domain organization to: " + organization);
		final ModelingConversation mc = conversation;
		final ResourceNode previous = getDomainOrganization();
		if (previous != null) {
			ResourceNode attached = mc.resolve(previous);
			Statement association = singleAssociation(attached, RBSystem.IS_DOMAIN_ORGANIZATION);
			attached.removeAssociation(association);
		}
		
		ResourceNode attached = mc.resolve(organization);
		assure(attached, RBSystem.IS_DOMAIN_ORGANIZATION, new SNValue(ElementaryDataType.BOOLEAN, Boolean.TRUE));
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ResourceNode getDomainOrganization() {
		final Query query = conversation.createQuery();
		query.addField(RDF.TYPE, RB.ORGANIZATION);
		query.and();
		query.addField(RBSystem.IS_DOMAIN_ORGANIZATION, "true");
		return query.getResult().getSingleNode();
	}
	
	// ----------------------------------------------------
	
	@Override
	public ResourceID getUsersPerson() {
		ResourceNode user = currentUser();
        if (user == null) {
            return null;
        }
		SemanticNode person = SNOPS.singleObject(user, RBSystem.IS_RESPRESENTED_BY);
		if (person != null) {
			return person.asResource();
		} else {
			return null;
		}
	}

	@Override
	public void setUsersPerson(ResourceID person) {
        ResourceNode user = currentUser();
        if (user != null) {
            SNOPS.assure(user, RBSystem.IS_RESPRESENTED_BY, person);
        } else {
            throw new IllegalStateException("Cannot set current user's 'person', no user in context.");
        }
	}

    // ----------------------------------------------------

    protected ResourceNode currentUser() {
        final RBUser user = context.getUser();
        if (user == null) {
            return null;
        } else {
            return conversation.resolve(new SimpleResourceID(user.getQualifiedName()));
        }
    }

}
