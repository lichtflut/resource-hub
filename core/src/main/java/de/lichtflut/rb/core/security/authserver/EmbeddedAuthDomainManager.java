/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security.authserver;

import static org.arastreju.sge.SNOPS.singleObject;
import static org.arastreju.sge.SNOPS.string;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.security.DomainInitializer;
import de.lichtflut.rb.core.security.DomainManager;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;

/**
 * <p>
 *  Manager of domains inside an instance.
 * </p>
 *
 * <p>
 * 	Created May 7, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class EmbeddedAuthDomainManager implements DomainManager {
	
	private final Logger logger = LoggerFactory.getLogger(EmbeddedAuthDomainManager.class);
	
	private final ModelingConversation conversation;

	private final DomainInitializer initializer;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param gate The gate to Arastreju. 
	 */
	public EmbeddedAuthDomainManager(ArastrejuGate gate) {
		this(gate, null);
	}
	
	/**
	 * Constructor.
	 * @param gate The gate to Arastreju.
	 * @param initializer The initializer for new domains.
	 */
	public EmbeddedAuthDomainManager(ArastrejuGate gate, DomainInitializer initializer) {
		this.conversation = gate.startConversation();
		this.conversation.getConversationContext().setWriteContext(EmbeddedAuthModule.IDENT);
		this.conversation.getConversationContext().setReadContexts(EmbeddedAuthModule.IDENT);
		this.initializer = initializer;
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBDomain findDomain(String domain) {
		final ResourceNode domainNode = findDomainNode(domain);
		if (domainNode == null) {
			return null;
		}
		return new RBDomain(domainNode);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBDomain registerDomain(RBDomain domain) {
		final ResourceNode node = new SNResource();
		node.addAssociation(Aras.HAS_UNIQUE_NAME, new SNText(domain.getName()));
		if (domain.getTitle() != null) {
			node.addAssociation(Aras.HAS_TITLE, new SNText(domain.getTitle()));
		}
		if (domain.getDescription() != null) {
			node.addAssociation(Aras.HAS_DESCRIPTION, new SNText(domain.getDescription()));
		}
		node.addAssociation(RDF.TYPE, Aras.DOMAIN);
		final RBDomain created = new RBDomain(node);
		
		final ModelingConversation mc = mc();
		mc.attach(node);
		if (initializer != null) {
			initializer.initialize(created, this);
		}
		logger.info("Created new domain: " + created);
		return created;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void updateDomain(RBDomain domain) {
		throw new NotYetImplementedException();
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void deleteDomain(RBDomain domain) {
		ModelingConversation mc = mc();
		ResourceID domainNode = findDomainNode(domain.getName());
		if(domainNode!=null){
			mc.remove(domainNode);
		}
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Collection<RBDomain> getAllDomains() {
		final List<RBDomain> result = new ArrayList<RBDomain>();
		final Query query = query().addField(RDF.TYPE, Aras.DOMAIN);
		for (ResourceNode domainNode : query.getResult()) {
			result.add(new RBDomain(domainNode));
		}
		return result;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Collection<RBUser> loadUsers(String domainName, int offset, int max) {
		RBDomain domain = findDomain(domainName);
		final List<RBUser> result = new ArrayList<RBUser>();
		final Query query = query();
		query.addField(RDF.TYPE, Aras.USER);
		query.and();
		query.addField(Aras.BELONGS_TO_DOMAIN, domain.getQualifiedName());
		for (ResourceNode userNode : query.getResult().toList(offset, max)) {
			result.add(new RBUser(userNode));
		}
		return result;
	}
	
	// -- ROLE MANAGMENT ----------------------------------
	
	public Collection<String> getRoles(String domain) {
		final ResourceNode domainNode = findDomainNode(domain);
		Collection<String> result = new ArrayList<String>();
		for (SemanticNode current : SNOPS.objects(domainNode, Aras.DEFINES_ROLE)) {
			result.add(uniqueName(current));
		}
		return result;
	}
	
	public Collection<String> getPermissions(String domain, String role) {
		final ResourceNode domainNode = findDomainNode(domain);
		final ResourceNode roleNode = getRole(domainNode, role);
		if (roleNode == null) {
			return Collections.emptyList();
		}
		Collection<String> result = new ArrayList<String>();
		for (SemanticNode current : SNOPS.objects(roleNode, Aras.HAS_PERMISSION)) {
			result.add(current.asValue().getStringValue());
		}
		return result;
	}
	
	public void registerRole(String domain, String role, Set<String> permissions) {
		final ResourceNode domainNode = findDomainNode(domain);
		final ResourceNode roleNode = getOrCreateRole(domainNode, role);
		SNOPS.assure(roleNode, Aras.HAS_PERMISSION, toSemanticNodes(permissions));
		logger.info("Registered permissions {} for role {}.", permissions, role + "@" + uniqueName(domainNode));
	}
	
	// ----------------------------------------------------
	
	protected ResourceNode findDomainNode(String domain) {
		if (domain == null) {
			throw new IllegalArgumentException("Domain name may not be null.");
		}
		final Query query = query()
				.addField(RDF.TYPE, Aras.DOMAIN)
				.and()
				.addField(Aras.HAS_UNIQUE_NAME, domain);
		final ResourceNode domainNode = query.getResult().getSingleNode();
		return domainNode;
	}
	
	protected ResourceNode getOrCreateRole(ResourceNode domainNode, String role) {
		ResourceNode roleNode = getRole(domainNode, role);
		if (roleNode == null) {
			roleNode = new SNResource();
			domainNode.addAssociation(Aras.DEFINES_ROLE, roleNode);
			roleNode.addAssociation(Aras.BELONGS_TO_DOMAIN, domainNode);
			roleNode.addAssociation(Aras.HAS_UNIQUE_NAME, new SNText(role));
			logger.info("Registered role {} for domain {}.", role, uniqueName(domainNode));
		}
		return roleNode;
	}
	
	// ----------------------------------------------------
	
	private Set<SNText> toSemanticNodes(Collection<String> values) {
		final Set<SNText> result = new HashSet<SNText>();
		for (String current : values) {
			result.add(new SNText(current));
		}
		return result;
	}
	
	private ResourceNode getRole(ResourceNode domainNode, String role) {
		for (SemanticNode current : SNOPS.objects(domainNode, Aras.DEFINES_ROLE)) {
			String currentName = uniqueName(current);
			if (role.equals(currentName)) {
				return current.asResource();
			}
		} 
		return null;
	}
	
	private String uniqueName(SemanticNode node) {
		if (node != null && node.isResourceNode()) {
			return string(singleObject(node.asResource(), Aras.HAS_UNIQUE_NAME));
		} else {
			return null;
		}
	}
	
	private ModelingConversation mc() {
		return conversation;
	}
	
	private Query query() {
		return mc().createQuery();
	}
	
}
