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
package de.lichtflut.rb.webck.common.impl;

import de.lichtflut.rb.core.common.SerialNumberOrderedNodesContainer;
import de.lichtflut.rb.webck.common.OrderedNodesContainer;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.nodes.ResourceNode;

import java.util.List;

/**
 * <p>
 *  Implementation of {@link OrderedNodesContainer} for resource nodes ordered by a serial number.
 * </p>
 *
 * <p>
 * 	Created Jan 31, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class OrderedModelContainer extends SerialNumberOrderedNodesContainer implements OrderedNodesContainer{

	private final IModel<List<? extends ResourceNode>> model;
	
	// ----------------------------------------------------
	
	/**
	 * @param model
	 */
	public OrderedModelContainer(IModel<List<? extends ResourceNode>> model) {
		this.model = model;
	}
	
	// ----------------------------------------------------

    @SuppressWarnings("unchecked")
    protected List<ResourceNode> getList() {
        return (List<ResourceNode>) model.getObject();
    }

}
