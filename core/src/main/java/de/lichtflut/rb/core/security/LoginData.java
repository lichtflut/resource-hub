/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

import java.io.Serializable;

/**
 * <p>
 * Represents a user's login data.
 * </p>
 * 
 * Created: Aug 10, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class LoginData implements Serializable {

	private String password;
	private String id;
	private boolean stayLoggedIn;
	
	// ----------------------------------------------------
	
	/**
	 * Default Constructor.
	 */
	public LoginData() {
	}
	
	// ----------------------------------------------------

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the stayLoggedIn
	 */
	public boolean getStayLoggedIn() {
		return stayLoggedIn;
	}

	/**
	 * @param stayLoggedIn the stayLoggedIn to set
	 */
	public void setStayLoggedIn(boolean stayLoggedIn) {
		this.stayLoggedIn = stayLoggedIn;
	}

}
