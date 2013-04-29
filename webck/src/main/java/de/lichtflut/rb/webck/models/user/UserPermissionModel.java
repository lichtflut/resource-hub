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
package de.lichtflut.rb.webck.models.user;

import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Collections;
import java.util.Set;

/**
 * <p>
 *  Model for a user's permissions.
 * </p>
 *
 * <p>
 * 	Created May 7, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class UserPermissionModel extends DerivedDetachableModel<Set<String>, RBUser> {
	
	@SpringBean
	private SecurityService securityService;
	
	@SpringBean 
	private ServiceContext context;
	
	// ----------------------------------------------------
	
	public UserPermissionModel(RBUser user) {
		super(user);
		Injector.get().inject(this);
	}
	
	public UserPermissionModel(IModel<RBUser> user) {
		super(user);
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected Set<String> derive(RBUser user) {
		if (user != null) {
			String domain = context.getDomain();
			return securityService.getUserPermissions(user, domain);
		} else {
			return Collections.emptySet();
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getDefault() {
		return Collections.emptySet();
	}
	
}
