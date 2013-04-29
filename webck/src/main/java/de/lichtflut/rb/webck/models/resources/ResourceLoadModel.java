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
package de.lichtflut.rb.webck.models.resources;

import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

/**
 * <p>
 *  Model loading a resource.
 * </p>
 *
 * <p>
 * 	Created Mar 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourceLoadModel extends AbstractLoadableDetachableModel<ResourceNode> {

	@SpringBean
	private SemanticNetworkService service;
	
	private final ResourceID id;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public ResourceLoadModel(ResourceID id) {
		this.id = id;
		Injector.get().inject(this);
	}

	@Override
	public ResourceNode load() {
		return service.find(id.getQualifiedName());
	}
	
}
