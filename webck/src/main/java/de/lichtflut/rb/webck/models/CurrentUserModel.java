/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.webck.models;

import java.util.Set;

import org.apache.wicket.Session;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.user.UserPermissionModel;

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
			protected String derive(final RBUser user) {
				return user.getName();
			}

			@Override
			public String getDefault() {
				return "";
			}
		};
	}

	/**
	 * Checks whether the current user is logged.
	 * @return {@link ConditionalModel} true if user is logged in, false if not
	 */
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
	@Override
	public RBUser load() {
		return context.getUser();
	}

}
