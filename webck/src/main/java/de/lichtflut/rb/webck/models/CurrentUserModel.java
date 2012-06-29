/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.user.UserPermissionModel;
import org.apache.wicket.Session;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Set;

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

	public static ConditionalModel<RBUser> isLoggedIn() {
		return new ConditionalModel<RBUser>() {
			@Override
			public boolean isFulfilled() {
				 return Session.exists() && RBWebSession.get().isAuthenticated();
			}
		};
	}
	
	/**
	 * Creates a new model for testing if a user has a specific permission.
	 * @param name The permission's name.
	 * @return True if the current user has this permission.
	 */
	public static ConditionalModel<?> hasPermission(final String name) {
		return new ConditionalModel<Set<String>>(new UserPermissionModel(new CurrentUserModel())) {
			@Override
			public boolean isFulfilled() {
				return getObject().contains(name);
			}
		};
	}

	// ----------------------------------------------------

	@SpringBean 
	private ServiceContext context;

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
		return context.getUser();
	}

}
