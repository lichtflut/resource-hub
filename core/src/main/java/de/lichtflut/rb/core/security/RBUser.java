/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.security.Permission;
import org.arastreju.sge.security.Role;
import org.arastreju.sge.security.impl.SNUser;

import de.lichtflut.infra.Infra;
import de.lichtflut.rb.core.RBSystem;

/**
 * <p>
 *  RB specific decorator for user nodes.
 * </p>
 *
 * <p>
 * 	Created Mar 1, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBUser implements Serializable {
	
	private QualifiedName qn;
	
	private String username;
	
	private String email;
	
	private String domesticDomain;
	
	private Date lastLogin;
	
	private Set<String> roles = new HashSet<String>();
	
	private Set<String> permissions = new HashSet<String>();
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param userNode The node representing the user.
	 */
	public RBUser(final ResourceNode userNode) {
		this(userNode.getQualifiedName());
		email = SNOPS.string(SNOPS.singleObject(userNode, RBSystem.HAS_EMAIL));
		username = SNOPS.string(SNOPS.singleObject(userNode, RBSystem.HAS_USERNAME));
		domesticDomain = SNOPS.string(SNOPS.singleObject(userNode, Aras.BELONGS_TO_DOMAIN));
		lastLogin = SNOPS.date(SNOPS.singleObject(userNode, RBSystem.HAS_LAST_LOGIN));

		final SNUser arasUser = new SNUser(userNode);
		for(Role role : arasUser.getRoles()) {
			this.roles.add(role.getName());
		}
		for(Permission permission : arasUser.getPermissions()) {
			this.permissions.add(permission.getName());
		}
	}
	
	/**
	 * Constructor.
	 * @param userNode The node representing the user.
	 */
	public RBUser(final QualifiedName qn) {
		this.qn = qn;
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return the qualified name.
	 */
	public QualifiedName getQualifiedName() {
		return qn;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getName() {
		return Infra.coalesce(getUsername(), getEmail(), qn.getSimpleName());
	}
	
	public String getDomesticDomain() {
		return domesticDomain;
	}
	
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	// ----------------------------------------------------

	public Set<String> getRoles() {
		return roles;
	}
	
	/**
	 * @param permission The permission to check.
	 * @return true if this user has the required permission.
	 */
	public boolean hasPermission(String permission) {
		return permissions.contains(permission);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getName();
	}

}
