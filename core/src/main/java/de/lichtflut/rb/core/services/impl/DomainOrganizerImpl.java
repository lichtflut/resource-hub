/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import static org.arastreju.sge.SNOPS.assure;
import static org.arastreju.sge.SNOPS.singleAssociation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.Organizer;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.naming.Namespace;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.security.Domain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.organizer.ContextDeclaration;
import de.lichtflut.rb.core.organizer.NamespaceDeclaration;
import de.lichtflut.rb.core.services.DomainOrganizer;
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
public class DomainOrganizerImpl extends AbstractService implements DomainOrganizer {
	
	private final Logger logger = LoggerFactory.getLogger(EntityManagerImpl.class);
	
	// -----------------------------------------------------

	/**
	 * Constructor.
	 * @param provider The service provider.
	 */
	public DomainOrganizerImpl(final ServiceProvider provider) {
		super(provider);
	}
	
	// -- DOMAINS -----------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Domain getMasterDomain() {
		return arasOrganizer().getDomesticDomain();
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Domain> getDomains() {
		return arasOrganizer().getDomains();
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Domain registerDomain(Domain domain) {
		final Domain registered = arasOrganizer()
				.registerDomain(domain.getUniqueName(), domain.getTitle(), domain.getDescription());
		getProvider().getSecurityService().createDomainAdmin(domain);
		logger.info("Created new domain: " + registered);
		return registered;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void updateDomain(Domain domain) {
		arasOrganizer().updateDomain(domain);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void deleteDomain(Domain domain) {
		if (!domain.isDomesticDomain()) {
			mc().remove(domain);
		}
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setDomainOrganization(final ResourceID organization) {
		logger.info("Setting domain organization to: " + organization);
		final ModelingConversation mc = gate().startConversation();
		final ResourceNode previous = getDomainOrganization();
		if (previous != null) {
			ResourceNode attached = mc.resolve(previous);
			Statement association = singleAssociation(attached, RBSystem.IS_DOMAIN_ORGANIZATION);
			attached.removeAssociation(association);
		}
		
		ResourceNode attached = mc.resolve(organization);
		assure(attached, RBSystem.IS_DOMAIN_ORGANIZATION, new SNValue(ElementaryDataType.BOOLEAN, Boolean.TRUE));
		mc.close();
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ResourceNode getDomainOrganization() {
		final Query query = gate().createQueryManager().buildQuery();
		query.addField(RDF.TYPE, RB.ORGANIZATION);
		query.and();
		query.addField(RBSystem.IS_DOMAIN_ORGANIZATION, "true");
		return query.getResult().getSingleNode();
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
		return gate().getOrganizer();
	}

}
