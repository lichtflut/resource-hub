/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

import static org.arastreju.sge.SNOPS.assure;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.security.User;
import org.arastreju.sge.security.impl.AbstractIdentity;

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
public class RBUser extends AbstractIdentity implements User {

	/**
	 * Constructor.
	 * @param userNode The node representing the user.
	 */
	public RBUser(final ResourceNode userNode) {
		super(userNode);
	}
	
	// -- IDENTIFIERS -------------------------------------

	/**
	 * {@inheritDoc}
	 */
	public String getEmail() {
		return stringValue(RBSystem.HAS_EMAIL);
	}
	
	public void setEmail(String email) {
		assure(this, RBSystem.HAS_EMAIL, new SNText(email), Aras.IDENT);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getUsername() {
		return stringValue(RBSystem.HAS_USERNAME);
	}
	
	public void setUsername(String username) {
		if (username != null) {
			assure(this, RBSystem.HAS_USERNAME, new SNText(username), Aras.IDENT);
		} else {
			SNOPS.remove(this, RBSystem.HAS_USERNAME);
		}
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return Infra.coalesce(getUsername(), getEmail(), getQualifiedName().getSimpleName());
	}
	
	/** 
	 * {@inheritDoc}
	 */
	public String getDomain() {
		return stringValue(Aras.BELONGS_TO_DOMAIN);
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
