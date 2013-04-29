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

import de.lichtflut.rb.webck.models.basic.DerivedModel;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 * 	Model for obtaining the URI of an {@link ResourceID}.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourceUriModel extends DerivedModel<String, ResourceID> {

	/**
	 * Constuctor.
	 * @param fiel The field model.
	 */
	@SuppressWarnings("unchecked")
	public ResourceUriModel(final IModel<? extends ResourceID> model) {
		super((IModel<ResourceID>) model);
	}

	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String derive(ResourceID source) {
		return source.getQualifiedName().toURI();
	}
	
}
