/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security.impl;

import java.util.ArrayList;
import java.util.List;

import de.lichtflut.rb.core.security.IAuthenticationService;
import de.lichtflut.rb.core.security.LoginData;

/**
 * <p>
 * This is a Mock-Implementation of {@link IAuthenticationService}.
 * </p>
 * <p>
 * == ATTENTION ==
 * This is for testing purposes only!!!
 * </p>
 *
 * Created: Aug 10, 2011
 *
 * @author Ravi Knox
 */
public class MockLoginService implements IAuthenticationService {

	private List<LoginData> authenticatedUser = new ArrayList<LoginData>();
	/**
	 * Default Constructor.
	 */
	public MockLoginService() {
		LoginData user = new LoginData();
		user.setId("test");
		user.setPassword("test");
		authenticatedUser.add(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean authenticateUser(final String username, final String password) {
		for (LoginData user : authenticatedUser) {
			if(user.getId().equals(username) && user.getPassword().equals(password)){
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean logoutUser(){
		return true;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean registerNewUser(final LoginData user) {
		return authenticatedUser.add((LoginData) user);
	}

}
