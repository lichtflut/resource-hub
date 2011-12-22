/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.apache.wicket.Session;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebSession;
import org.arastreju.sge.eh.ArastrejuRuntimeException;
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
					try {
						return current.getEmail();
					} catch (ArastrejuRuntimeException e) {
						WebSession.get().invalidate();
						return "error";
					}
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
	
	private static User currentUser() {
		if (Session.exists()) {
			final User user = RBWebSession.get().getUser();
//			try {
//				// Workaround an issue with serialized sessions
//				user.getEmail();
//			} catch (ArastrejuRuntimeException e) {
//				return null;
//			}
			return user;
		} else {
			return null;
		}
	}
	
}
