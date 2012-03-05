/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.apache.wicket.Session;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.security.User;

import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.application.RBWebSession;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;

/**
 * <p>
 * Model for the currently logged in user.
 * </p>
 * 
 * <p>
 * Created Dec 8, 2011
 * </p>
 * 
 * @author Oliver Tigges
 */
public class CurrentUserModel extends AbstractLoadableDetachableModel<RBUser> {

	public static ResourceID currentUserID() {
		if (Session.exists()) {
			return RBWebSession.get().getUser();
		}
		return null;
	}
	
	// ----------------------------------------------------

	public static IModel<String> displayNameModel() {
		return new DerivedDetachableModel<String, RBUser>(new CurrentUserModel()) {
			@Override
			protected String derive(RBUser user) {
				return user.getName();
			}

			@Override
			public String getDefault() {
				return "";
			}
		};
	}

	public static ConditionalModel<User> isLoggedIn() {
		return new ConditionalModel<User>() {
			@Override
			public boolean isFulfilled() {
				return currentUserID() != null;
			}
		};
	}

	public static ConditionalModel<User> hasPermission(final String permission) {
		return new ConditionalModel<User>(new CurrentUserModel()) {
			@Override
			public boolean isFulfilled() {
				final User user = getObject();
				return user != null && user.hasPermission(permission);
			}
		};
	}
	
	// ----------------------------------------------------

	@SpringBean
	private ServiceProvider provider;

	// ----------------------------------------------------

	/**
	 * Default constructor.
	 */
	public CurrentUserModel() {
		Injector.get().inject(this);
	}

	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	public RBUser load() {
		final ResourceID userID = currentUserID();
		if (userID != null) {
			return new RBUser(provider.getResourceResolver().resolve(userID));
		} else {
			return null;
		}
	}
	
}
