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

import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;

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
public class CurrentPersonModel extends AbstractLoadableDetachableModel<RBEntity> {

	@SpringBean
	private EntityManager entityManager;

	@SpringBean
	private ServiceContext context;

	@SpringBean
	private SemanticNetworkService semanticNetwork;

	// ----------------------------------------------------

	public CurrentPersonModel() {
		Injector.get().inject(this);
	}

	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RBEntity load() {
		RBUser user = context.getUser();
		if (user == null) {
			return null;
		}

		final ResourceNode userNode = semanticNetwork.resolve(SNOPS.id(user.getQualifiedName()));
		final SemanticNode person = SNOPS.fetchObject(userNode, RBSystem.IS_RESPRESENTED_BY);
		if (person != null && person.isResourceNode()) {
			return entityManager.find(person.asResource());
		} else {
			return null;
		}
	}

}
