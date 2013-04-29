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
package de.lichtflut.rb.webck.models.domains;

import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.models.CurrentUserModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  Model providing the current domain.
 * </p>
 *
 * <p>
 * 	Created Jan 16, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class AlternateDomainsModel extends DerivedDetachableModel<List<RBDomain>, RBUser> {

	@SpringBean
	private AuthModule authModule;
	
	@SpringBean 
	private ServiceContext context;
	
	// ----------------------------------------------------
	
	/**
	 * Default constructor. Will use current user.
	 */
	public AlternateDomainsModel() {
		this(new CurrentUserModel());
	}
	
	/**
	 * Constructor.
	 */
	public AlternateDomainsModel(IModel<RBUser> userModel) {
		super(userModel);
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------
	
	@Override
	protected List<RBDomain> derive(final RBUser user) {
		final String currentDomain = context.getDomain();
		
		final List<RBDomain> domains = new ArrayList<RBDomain>();
		final Collection<RBDomain> all = authModule.getDomainManager().getDomainsForUser(user);
		for (RBDomain current : all) {
			if (!current.getName().equals(currentDomain)) {
				domains.add(current);
			}
		}
		return domains;
	}

}
