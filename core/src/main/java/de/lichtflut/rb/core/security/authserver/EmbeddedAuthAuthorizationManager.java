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

import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.security.RBUser;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.Infra;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.naming.QualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.arastreju.sge.SNOPS.singleObject;
import static org.arastreju.sge.SNOPS.string;

/**
 * <p>
 *  Implementation of AuthorizationManagement.
 * </p>
 *
 * <p>
 * 	Created May 7, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class EmbeddedAuthAuthorizationManager {

	private final static Logger logger = LoggerFactory.getLogger(EmbeddedAuthAuthorizationManager.class);

	private final Conversation conversation;

	private final EmbeddedAuthDomainManager domainManager;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param domainManager The domain manager.
	 */
	public EmbeddedAuthAuthorizationManager(final Conversation conversation, final EmbeddedAuthDomainManager domainManager) {
		this.domainManager = domainManager;
		this.conversation = conversation;
	}

	// -- ROLE ASSIGNMENT ---------------------------------

	/**
	 * Get the user's roles in a given domain.
	 * @param user The user who's roles are requested.
	 * @param domain The domain.
	 * @return The list of roles.
	 */
	public List<String> getUserRoles(final RBUser user, final String domain) {
		final List<String> result = new ArrayList<String>();
		final ResourceNode userNode = mc().findResource(user.getQualifiedName());
		for(SemanticNode roleNode : SNOPS.objects(userNode, EmbeddedAuthModule.HAS_ROLE)) {
			String roleDomain = getDomain(roleNode);
			if (Infra.equals(roleDomain, domain)) {
				result.add(uniqueName(roleNode));
			}
		}
		return result;
	}

	/**
	 * Get the user's permissions.
	 * @param userQN The user who's permissions are requested.
	 * @param domain The domain.
	 * @return The list of permissions.
	 */
	public Set<String> getUserPermissions(final QualifiedName userQN, final String domain) {
		final Set<String> result = new HashSet<String>();
		final ResourceNode userNode = mc().findResource(userQN);
		for(SemanticNode roleNode : SNOPS.objects(userNode, EmbeddedAuthModule.HAS_ROLE)) {
			String roleDomain = getDomain(roleNode);
			if (Infra.equals(roleDomain, domain) && roleNode.isResourceNode()) {
				for (SemanticNode permission : SNOPS.objects(roleNode.asResource(), EmbeddedAuthModule.HAS_PERMISSION)) {
					result.add(permission.toString());
				}
			}
		}
		return result;
	}

	/**
	 * Get the user's roles.
	 * @param user The user who's roles are set.
	 * @param domain The domain.
	 * @param roles The roles.
	 * @throws RBAuthException
	 */
	public void setUserRoles(final RBUser user, final String domain, final List<String> roles) throws RBAuthException {
		ResourceNode userNode = mc().findResource(user.getQualifiedName());
		if (userNode == null) {
			throw new RBAuthException(0, "User not found: " + user.getEmail() + " in domain" + domain);
		}

		final ResourceNode domainNode = domainManager.findDomainNode(domain);
		// Remove old roles
		Set<Statement> oldRoles = SNOPS.associations(userNode, EmbeddedAuthModule.HAS_ROLE);
		for (Statement stmt : oldRoles) {

			SemanticNode role = stmt.getObject();

			SemanticNode currentDomainNode = SNOPS.singleObject(role.asResource(), EmbeddedAuthModule.BELONGS_TO_DOMAIN);

			if (domainNode.equals(currentDomainNode)) {
				userNode.removeAssociation(stmt);
				logger.info("Removing role {} from user {}.", role, user + "#" + domain);
			} else {
				logger.info("Keeping role {} to user {}.", role, user + "#" + domain);
			}
		}

		// add new roles
		for (String current : roles) {
			final ResourceNode roleNode = domainManager.getOrCreateRole(domainNode, current);
			if (roleNode != null) {
				userNode.addAssociation(EmbeddedAuthModule.HAS_ROLE, roleNode);
			} else {
				throw new RBAuthException(0, "Role not found: " + current + "@" + domain);
			}
		}
		logger.info("Added roles {} to user {}.", roles, user + "#" + domain);
	}

	// ----------------------------------------------------

	protected String getDomain(final SemanticNode node) {
		if (node != null && node.isResourceNode()) {
			final SemanticNode domain = singleObject(node.asResource(), EmbeddedAuthModule.BELONGS_TO_DOMAIN);
			return uniqueName(domain);
		} else {
			return null;
		}
	}

	// ----------------------------------------------------

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

}
