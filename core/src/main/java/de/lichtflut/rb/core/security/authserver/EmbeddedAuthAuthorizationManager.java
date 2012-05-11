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

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.infra.Infra;
import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.security.RBUser;

/**
 * <p>
 *  Implementation of {@link AuthorizationManagement}.
 * </p>
 *
 * <p>
 * 	Created May 7, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class EmbeddedAuthAuthorizationManager {
	
	private final ArastrejuGate masterGate;

	private final EmbeddedAuthDomainManager domainManager;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param domainManager TODO
	 */
	public EmbeddedAuthAuthorizationManager(ArastrejuGate masterGate, EmbeddedAuthDomainManager domainManager) {
		this.masterGate = masterGate;
		this.domainManager = domainManager;
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
		for(SemanticNode roleNode : SNOPS.objects(userNode, Aras.HAS_ROLE)) {
			String roleDomain = getDomain(roleNode);
			if (Infra.equals(roleDomain, domain)) {
				result.add(uniqueName(roleNode));
			}
		}
		return result;
	}

	/**
	 * Get the user's permissions.
	 * @param user The user who's permissions are requested.
	 * @param domain The domain.
	 * @return The list of permissions.
	 */
	public Set<String> getUserPermissions(RBUser user, String domain) {
		final Set<String> result = new HashSet<String>();
		final ResourceNode userNode = mc().findResource(user.getQualifiedName());
		for(SemanticNode roleNode : SNOPS.objects(userNode, Aras.HAS_ROLE)) {
			String roleDomain = getDomain(roleNode);
			if (Infra.equals(roleDomain, domain) && roleNode.isResourceNode()) {
				for (SemanticNode permission : SNOPS.objects(roleNode.asResource(), Aras.HAS_PERMISSION)) {
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
	 * @return The list of permissions.
	 * @throws RBAuthException 
	 */
	public void setUserRoles(RBUser user, String domain, List<String> roles) throws RBAuthException {
		ResourceNode userNode = mc().findResource(user.getQualifiedName());
		if (userNode == null) {
			userNode = new SNResource(user.getQualifiedName());
		}
		SNOPS.remove(userNode, Aras.HAS_ROLE);
		
		final ResourceNode domainNode = domainManager.findDomainNode(domain);
		for (String current : roles) {
			final ResourceNode roleNode = domainManager.getOrCreateRole(domainNode, current);
			if (roleNode != null) {
				userNode.addAssociation(Aras.HAS_ROLE, roleNode);
			} else {
				throw new RBAuthException(0, "Role not found: " + current + "@" + domain);
			}
		}
	}
	
	// ----------------------------------------------------
	
	protected String getDomain(SemanticNode node) {
		if (node != null && node.isResourceNode()) {
			final SemanticNode domain = singleObject(node.asResource(), Aras.BELONGS_TO_DOMAIN);
			return uniqueName(domain);
		} else {
			return null;
		}
	}
	
	// ----------------------------------------------------
	
	private String uniqueName(SemanticNode node) {
		if (node != null && node.isResourceNode()) {
			return string(singleObject(node.asResource(), Aras.HAS_UNIQUE_NAME));
		} else {
			return null;
		}
	}
	
	private ModelingConversation mc() {
		return masterGate.startConversation();
	}
	
}
