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
 *  Model derived from another model.
 * </p>
 *
 * <p>
 * 	Created Dec 6, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class DerivedModel<T, M> extends AbstractReadOnlyModel<T> {

	private final IModel<M> originalModel;
	
	private final M original;
	
	// ----------------------------------------------------
	
	/**
	 * @param original
	 */
	public DerivedModel(IModel<M> original) {
		this.originalModel = original;
		this.original = null;
	}
	
	/**
	 * @param original
	 */
	public DerivedModel(M original) {
		this.original = original;
		this.originalModel = null;
	}
	
	// ----------------------------------------------------

	public T getObject() {
		if (original != null) {
			return derive(original);
		} else if (originalModel != null && originalModel.getObject() != null) {
			return derive(originalModel.getObject());
		} else {
			return getDefault();
		}
	}
	
	public T getDefault() {
		return null;
	}

	// ----------------------------------------------------
	
	/**
	 * Derive the value from the original. The original will never be null.
	 * @param original The original model value.
	 * @return The derived value.
	 */
	protected abstract T derive(M original);

	// ----------------------------------------------------
	
	protected M getOriginal() {
		if (original != null) {
			return original;
		} else if (originalModel != null) {
			return originalModel.getObject();
		} else {
			return null;
		}
	}
	
}
