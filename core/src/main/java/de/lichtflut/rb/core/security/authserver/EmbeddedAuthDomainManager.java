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
package de.lichtflut.rb.core.security.authserver;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.security.AuthDomainInitializer;
import de.lichtflut.rb.core.security.DomainManager;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static de.lichtflut.rb.core.security.authserver.EmbeddedAuthFunctions.toRBDomain;
import static de.lichtflut.rb.core.security.authserver.EmbeddedAuthFunctions.toRBUser;
import static org.arastreju.sge.SNOPS.singleObject;
import static org.arastreju.sge.SNOPS.string;

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

	private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedAuthDomainManager.class);

	private final Conversation conversation;

	private final AuthDomainInitializer initializer;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param conversation The conversation..
	 */
	public EmbeddedAuthDomainManager(final Conversation conversation) {
		this(conversation, null);
	}

	/**
	 * Constructor.
	 * @param conversation The conversation..
	 * @param initializer The initializer for new domains.
	 */
	public EmbeddedAuthDomainManager(final Conversation conversation, final AuthDomainInitializer initializer) {
		this.conversation = conversation;
		this.initializer = initializer;
	}

	// ----------------------------------------------------

	@Override
	public RBDomain findDomain(final String domain) {
		final ResourceNode domainNode = findDomainNode(domain);
		if (domainNode == null) {
			return null;
		}
		return EmbeddedAuthFunctions.toRBDomain(domainNode);
	}

	@Override
	public RBDomain registerDomain(final RBDomain domain) {
		final ResourceNode node = new SNResource();
		node.addAssociation(EmbeddedAuthModule.HAS_UNIQUE_NAME, new SNText(domain.getName()));
		if (domain.getTitle() != null) {
			node.addAssociation(EmbeddedAuthModule.HAS_TITLE, new SNText(domain.getTitle()));
		}
		if (domain.getDescription() != null) {
			node.addAssociation(EmbeddedAuthModule.HAS_DESCRIPTION, new SNText(domain.getDescription()));
		}
		node.addAssociation(RDF.TYPE, EmbeddedAuthModule.DOMAIN);
		final RBDomain created = toRBDomain(node);

        mc().attach(node);
		if (initializer != null) {
			initializer.initialize(created, this);
		}
		LOGGER.info("Created new domain: " + created);
		return created;
	}

	@Override
	public void updateDomain(final RBDomain domain) {
		throw new NotYetImplementedException();
	}

	@Override
	public void deleteDomain(final RBDomain domain) {
		Conversation mc = mc();
		ResourceID domainNode = findDomainNode(domain.getName());
		if(domainNode!=null){
			mc.remove(domainNode);
		}
	}

	// ----------------------------------------------------

	@Override
	public Collection<RBDomain> getAllDomains() {
		final List<RBDomain> result = new ArrayList<RBDomain>();
		final Query query = query().addField(RDF.TYPE, EmbeddedAuthModule.DOMAIN);
		for (ResourceNode domainNode : query.getResult()) {
			result.add(toRBDomain(domainNode));
		}
		return result;
	}

	@Override
	public Collection<RBDomain> getDomainsForUser(final RBUser user) {
		final List<RBDomain> result = new ArrayList<RBDomain>();
		ResourceNode userNode = conversation.findResource(user.getQualifiedName());
		if (userNode == null) {
			throw new IllegalArgumentException("User not found");
		}
		for (Statement stmt : SNOPS.associations(userNode, EmbeddedAuthModule.HAS_ALTERNATE_DOMAIN)) {
			result.add(EmbeddedAuthFunctions.toRBDomain(stmt.getObject()));
		}
		result.add(findDomain(user.getDomesticDomain()));
		return result;
	}

	@Override
	public Collection<RBUser> loadUsers(final String domainName, final int offset, final int max) {
		RBDomain domain = findDomain(domainName);
		final List<RBUser> result = new ArrayList<RBUser>();
		final Query query = query();
		query.beginAnd();
		query.addField(RDF.TYPE, EmbeddedAuthModule.USER);
		query.beginOr();
		query.addField(EmbeddedAuthModule.BELONGS_TO_DOMAIN, domain.getQualifiedName());
		query.addField(EmbeddedAuthModule.HAS_ALTERNATE_DOMAIN, domain.getQualifiedName());
		query.end();
		query.end();
		query.end();
		for (ResourceNode userNode : query.getResult().toList(offset, max)) {
			result.add(toRBUser(userNode));
		}
		return result;
	}

	// -- ROLE MANAGMENT ----------------------------------

	public Collection<String> getRoles(final String domain) {
		final ResourceNode domainNode = findDomainNode(domain);
		Collection<String> result = new ArrayList<String>();
		for (SemanticNode current : SNOPS.objects(domainNode, EmbeddedAuthModule.DEFINES_ROLE)) {
			result.add(uniqueName(current));
		}
		return result;
	}

	public Collection<String> getPermissions(final String domain, final String role) {
		final ResourceNode domainNode = findDomainNode(domain);
		final ResourceNode roleNode = getRole(domainNode, role);
		if (roleNode == null) {
			return Collections.emptyList();
		}
		Collection<String> result = new ArrayList<String>();
		for (SemanticNode current : SNOPS.objects(roleNode, EmbeddedAuthModule.HAS_PERMISSION)) {
			result.add(current.asValue().getStringValue());
		}
		return result;
	}

	@Override
	public void registerRole(final String domain, final String role, final Set<String> permissions) {
		final ResourceNode domainNode = findDomainNode(domain);
		final ResourceNode roleNode = getOrCreateRole(domainNode, role);
		SNOPS.assure(roleNode, EmbeddedAuthModule.HAS_PERMISSION, toSemanticNodes(permissions));
		LOGGER.info("Registered permissions {} for role {}.", permissions, role + "@" + uniqueName(domainNode));
	}

	// ----------------------------------------------------

	protected ResourceNode findDomainNode(final String domain) {
		if (domain == null) {
			throw new IllegalArgumentException("Domain name may not be null.");
		}
		final Query query = query()
				.addField(RDF.TYPE, EmbeddedAuthModule.DOMAIN)
				.and()
				.addField(EmbeddedAuthModule.HAS_UNIQUE_NAME, domain);
		final ResourceNode domainNode = query.getResult().getSingleNode();
		return domainNode;
	}

	protected ResourceNode getOrCreateRole(final ResourceNode domainNode, final String role) {
		ResourceNode roleNode = getRole(domainNode, role);
		if (roleNode == null) {
			roleNode = new SNResource();
			domainNode.addAssociation(EmbeddedAuthModule.DEFINES_ROLE, roleNode);
			roleNode.addAssociation(EmbeddedAuthModule.BELONGS_TO_DOMAIN, domainNode);
			roleNode.addAssociation(EmbeddedAuthModule.HAS_UNIQUE_NAME, new SNText(role));
			LOGGER.info("Registered role {} for domain {}.", role, uniqueName(domainNode));
		}
		return roleNode;
	}

	// ----------------------------------------------------

	private Set<SNText> toSemanticNodes(final Collection<String> values) {
		final Set<SNText> result = new HashSet<SNText>();
		for (String current : values) {
			result.add(new SNText(current));
		}
		return result;
	}

	private ResourceNode getRole(final ResourceNode domainNode, final String role) {
		for (SemanticNode current : SNOPS.objects(domainNode, EmbeddedAuthModule.DEFINES_ROLE)) {
			String currentName = uniqueName(current);
			if (role.equals(currentName)) {
				return current.asResource();
			}
		}
		return null;
	}

	private String uniqueName(final SemanticNode node) {
		if (node != null && node.isResourceNode()) {
			return string(singleObject(node.asResource(), EmbeddedAuthModule.HAS_UNIQUE_NAME));
		} else {
			return null;
		}
	}

	private Conversation mc() {
		return conversation;
	}

	private Query query() {
		return mc().createQuery();
	}

}
