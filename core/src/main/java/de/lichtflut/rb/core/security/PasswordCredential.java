/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

/**
 * <p>
 *  TODO: [DESCRIPTION].
 * </p>
 *
 * <p>
 * 	Created Jan 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class PasswordCredential implements Credential {

	private String password;

	public PasswordCredential(final String password){
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see org.arastreju.sge.security.Credential#stringRepesentation()
	 */
	public String stringRepesentation(){
		return password;
	}

}
