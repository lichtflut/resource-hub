/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.apache.wicket.Session;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.security.User;

import de.lichtflut.rb.webck.application.RBWebSession;

/**
 * <p>
 *  Model for the currently logged in user. 
 * </p>
 *
 * <p>
 * 	Created Dec 8, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class CurrentUserModel extends AbstractReadOnlyModel<User> {

	public static IModel<String> displayNameModel() {
		return new AbstractReadOnlyModel<String>() {
			@Override
			public String getObject() {
				final User current = currentUser();
				if (current != null) {
					return displayName(current);
				} else {
					return "";
				}
			}
		};
	}
	
	public static ConditionalModel<User> isLoggedIn() {
		return new ConditionalModel<User>(new CurrentUserModel()) {
			@Override
			public boolean isFulfilled() {
				return getObject() != null;
			}
		};
	}
	
	public static ConditionalModel<User> isDomainAdmin() {
		return new ConditionalModel<User>(new CurrentUserModel()) {
			@Override
			public boolean isFulfilled() {
				return getObject() != null;
			}
		};
	}
	
	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public User getObject() {
		return currentUser();
	}
	
	// ----------------------------------------------------
	
	public static User currentUser() {
		if (Session.exists()) {
			return RBWebSession.get().getUser();
		} else {
			return null;
		}
	}
	
	public static ResourceID currentUserID() {
		if (Session.exists()) {
			User user = RBWebSession.get().getUser();
			if (user != null) {
				return user.getAssociatedResource();
			}
		}
		return null;
	}
	
	/**
	 * @param user
	 * @return The display name.
	 */
	public static String displayName(User user) {
		if (user.getEmail() != null) {
			return user.getEmail();
		} else {
			return user.getName();
		}
	}
}
