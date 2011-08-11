/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security.impl;

import java.io.Serializable;
import java.util.Set;

import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.security.IUser;
import de.lichtflut.rb.core.security.Permission;
import de.lichtflut.rb.core.security.Role;

/**
 * <p>
 * Represents a User.
 * </p>
 * <p>
 * == ATTENTION ==
 * This class is not ready for use, but solely
 * for testing purposes as it is not fully implemented!!!
 * </p>
 * Created: Aug 10, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class User implements IUser, Serializable {

	private String name;
	private String password;
	private String email;
	/**
	 * Default Constructor.
	 */
	public User() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceNode getAssociatedResource() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}


	/**
	 * Sets Name.
	 * @param name -
	 */
	public void setName(final String name) {
		this.name = name;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Role> getRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Permission> getPermissions() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isInRole(final Role role) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasPermission(final Permission permission) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 * @param email - Users email
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

}
