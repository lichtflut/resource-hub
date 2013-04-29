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
package de.lichtflut.rb.core.viewspec.impl;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;

import de.lichtflut.rb.core.viewspec.WidgetAction;

/**
 * <p>
 *  Action supported by a widget.
 * </p>
 *
 * <p>
 * 	Created Feb 1, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNWidgetAction extends ResourceView implements WidgetAction {

	/**
	 * Constructor for new actions.
	 */
	public SNWidgetAction() {
		super();
	}

	/**
	 * @param resource
	 */
	public SNWidgetAction(ResourceNode resource) {
		super(resource);
	}

	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getActionType() {
		final SemanticNode type = SNOPS.fetchObject(this, RDF.TYPE);
		if (type != null && type.isResourceNode()) {
			return type.asResource();
		} else {
			return null;
		}
	}
}
