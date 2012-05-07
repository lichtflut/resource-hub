/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.apache.wicket.Session;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

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

	public static ConditionalModel<RBUser> hasPermission(final String permission) {
		return new ConditionalModel<RBUser>(new CurrentUserModel()) {
			@Override
			public boolean isFulfilled() {
				final RBUser user = getObject();
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
		return provider.getContext().getUser();
	}
	
}
