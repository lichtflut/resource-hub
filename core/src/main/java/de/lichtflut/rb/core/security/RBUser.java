/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

import java.io.Serializable;
import java.util.Date;

import de.lichtflut.rb.core.security.authserver.EmbeddedAuthModule;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.infra.Infra;
import de.lichtflut.rb.core.RBSystem;

import static org.arastreju.sge.SNOPS.singleObject;
import static org.arastreju.sge.SNOPS.string;

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
	
	// ----------------------------------------------------

    /**
     * Creates a new user with a random URI.
     */
    public RBUser() {
        this(new SimpleResourceID().getQualifiedName());
    }

    /**
	 * Creates a new user with given URI.
	 * @param qn The qualified name representing the user.
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

    public void setDomesticDomain(String domesticDomain) {
        this.domesticDomain = domesticDomain;
    }
	
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
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
