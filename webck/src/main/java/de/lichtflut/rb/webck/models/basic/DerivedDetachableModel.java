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

import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *  Model derived from another model, that will cache the data until detach phase.
 * </p>
 *
 * <p>
 * 	Created Dec 6, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class DerivedDetachableModel<T, M> extends AbstractLoadableDetachableModel<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DerivedDetachableModel.class);

	private final IModel<M> originalModel;

	private final M original;

	// ----------------------------------------------------

	/**
	 * @param original
	 */
	public DerivedDetachableModel(final IModel<M> original) {
		this.originalModel = original;
		this.original = null;
	}

	/**
	 * Constructor.
	 * @param original
	 */
	public DerivedDetachableModel(final M original) {
		if (original == null) {
			throw new IllegalStateException("Original value may not be null");
		}
		if(original instanceof IModel<?>){
			LOGGER.warn("Wrong class typisation. IModel should not be used with this constructor.");
		}
		this.original = original;
		this.originalModel = null;
	}

	// ----------------------------------------------------

	@Override
	public T load() {
		if (original != null) {
			return derive(original);
		} else if (originalModel != null && originalModel.getObject() != null) {
			return derive(originalModel.getObject());
		} else {
			return null;
		}
	}

	@Override
	public void detach() {
		super.detach();
		if (originalModel != null) {
			originalModel.detach();
		}
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

	/**
	 * @return The original model.
	 */
	protected IModel<M> getOriginalModel() {
		return originalModel;
	}

}
