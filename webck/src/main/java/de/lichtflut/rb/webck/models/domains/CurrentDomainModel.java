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
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.context.DomainIdentifier;

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
public class CurrentDomainModel extends AbstractLoadableDetachableModel<RBDomain> {

	@SpringBean
	private AuthModule authModule;
	
	@SpringBean 
	private ServiceContext context;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public CurrentDomainModel() {
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBDomain load() {
		final String domain = context.getDomain();
		if (domain != null) {
			return authModule.getDomainManager().findDomain(domain);
		} else {
			return null;
		}
	}

	/**
	 * @return A model providing the domain's name.
	 */
	public static IModel<String> displayNameModel() {
		return new DerivedModel<String, RBDomain>(new CurrentDomainModel()) {
			@Override
			protected String derive(RBDomain original) {
				if (original.getTitle() != null) {
					return original.getTitle();
				} else {
					return original.getName();
				}
			}
		};
	}
	
	/**
	 * @return A conditional model checking if the current domain is the master domain.
	 */
	public static ConditionalModel<RBDomain> isMasterDomain() {
		return new ConditionalModel<RBDomain>(new CurrentDomainModel()) {
			@Override
			public boolean isFulfilled() {
				return DomainIdentifier.MASTER_DOMAIN.equals((getObject().getName()));
			}
		};
	}
}
