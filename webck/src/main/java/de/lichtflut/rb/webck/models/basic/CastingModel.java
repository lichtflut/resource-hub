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
package de.lichtflut.rb.webck.models.basic;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

/**
 * <p>
 *  Model for unchecked casts.
 * </p>
 *
 * <p>
 * 	Created Jan 31, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class CastingModel<T> extends AbstractReadOnlyModel<T> {

	private final IModel<?> model;
	
	// ----------------------------------------------------

	public CastingModel(IModel<?> model) {
		this.model = model;
	}
	
	// ----------------------------------------------------
	
	@SuppressWarnings("unchecked")
	@Override
	public T getObject() {
		return (T) model.getObject();
	}
	
	@Override
	public void detach() {
		super.detach();
		model.detach();
	}

}
