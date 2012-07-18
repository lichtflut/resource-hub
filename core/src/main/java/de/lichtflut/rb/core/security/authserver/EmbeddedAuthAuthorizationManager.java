/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security.authserver;

import static org.arastreju.sge.SNOPS.singleObject;
import static org.arastreju.sge.SNOPS.string;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.naming.QualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.infra.Infra;
import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.security.RBUser;

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
	
	private final ModelingConversation conversation;

	private final EmbeddedAuthDomainManager domainManager;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param domainManager The domain manager.
	 */
	public EmbeddedAuthAuthorizationManager(ModelingConversation conversation, EmbeddedAuthDomainManager domainManager) {
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
	public List<String> getUserRoles(RBUser user, String domain) {
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
	public Set<String> getUserPermissions(QualifiedName userQN, String domain) {
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
	public void setUserRoles(RBUser user, String domain, List<String> roles) throws RBAuthException {
		ResourceNode userNode = mc().findResource(user.getQualifiedName());
		if (userNode == null) {
			throw new RBAuthException(0, "User not found: " + user.getEmail() + " in domain" + domain);
		}
		
		final ResourceNode domainNode = domainManager.findDomainNode(domain);
		// Remove old roles
		Set<Statement> oldRoles = userNode.getAssociations(EmbeddedAuthModule.HAS_ROLE);
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
	
	protected String getDomain(SemanticNode node) {
		if (node != null && node.isResourceNode()) {
			final SemanticNode domain = singleObject(node.asResource(), EmbeddedAuthModule.BELONGS_TO_DOMAIN);
			return uniqueName(domain);
		} else {
			return null;
		}
	}
	
	// ----------------------------------------------------
	
	private String uniqueName(SemanticNode node) {
		if (node != null && node.isResourceNode()) {
			return string(singleObject(node.asResource(), EmbeddedAuthModule.HAS_UNIQUE_NAME));
		} else {
			return null;
		}
	}
	
	private ModelingConversation mc() {
		return conversation;
	}
	
}
