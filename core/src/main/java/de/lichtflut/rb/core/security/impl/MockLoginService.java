/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security.impl;

import java.util.ArrayList;
import java.util.List;

import de.lichtflut.rb.core.security.IAuthenticationService;
import de.lichtflut.rb.core.security.IUser;

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

	private List<User> authenticatedUser = new ArrayList<User>();
	/**
	 * Default Constructor.
	 */
	public MockLoginService() {
		User user = new User();
		user.setName("test");
		user.setPassword("test");
		authenticatedUser.add(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean authenticateUser(final String username, final String password) {
		for (User user : authenticatedUser) {
			if(user.getName().equals(username) && user.getPassword().equals(password)){
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean registerNewUser(final IUser user) {
		authenticatedUser.add((User) user);
		return true;
	}

}
