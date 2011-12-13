/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api.impl;

import static org.arastreju.sge.SNOPS.assure;
import static org.arastreju.sge.SNOPS.singleAssociation;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.Organizer;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.Namespace;
import org.arastreju.sge.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.api.DomainOrganizer;
import de.lichtflut.rb.core.organizer.ContextDeclaration;
import de.lichtflut.rb.core.organizer.NamespaceDeclaration;
import de.lichtflut.rb.core.services.ServiceProvider;

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
	
	private final ServiceProvider provider;
	
	// -----------------------------------------------------

	/**
	 * Constructor.
	 * @param provider The service provider.
	 */
	public DomainOrganizerImpl(final ServiceProvider provider) {
		this.provider = provider;
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setDomainOrganization(final ResourceID organization) {
		logger.info("Setting domain organization to: " + organization);
		final ModelingConversation mc = provider.getArastejuGate().startConversation();
		final ResourceNode previous = getDomainOrganization();
		if (previous != null) {
			ResourceNode attached = mc.resolve(previous);
			Association association = singleAssociation(attached, RB.IS_DOMAIN_ORGANIZATION);
			attached.remove(association);
		}
		
		ResourceNode attached = mc.resolve(organization);
		assure(attached, RB.IS_DOMAIN_ORGANIZATION, Aras.TRUE);
		mc.close();
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ResourceNode getDomainOrganization() {
		final Query query = provider.getArastejuGate().createQueryManager().buildQuery();
		query.addField(RDF.TYPE, RB.ORGANIZATION);
		query.and();
		query.addField(RB.IS_DOMAIN_ORGANIZATION, Aras.TRUE);
		return query.getSingleNode();
	}
	
	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public List<Namespace> getNamespaces() {
		return new ArrayList<Namespace>(arasOrganizer().getNamespaces());
	}
	
	/** 
	* {@inheritDoc}
	*/
	public void registerNamespace(NamespaceDeclaration decl) {
		arasOrganizer().registerNamespace(decl.getUri(), decl.getPrefix());
	};
	
	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public List<Context> getContexts() {
		return new ArrayList<Context>(arasOrganizer().getContexts());
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void registerContext(ContextDeclaration decl) {
		arasOrganizer().registerContext(decl.getQualifiedName());
	}

	// ----------------------------------------------------
	
	protected Organizer arasOrganizer() {
		return provider.getArastejuGate().getOrganizer();
	}

}