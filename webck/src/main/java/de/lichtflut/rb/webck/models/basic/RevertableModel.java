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

import org.apache.commons.lang3.SerializationUtils;
import org.apache.wicket.model.IModel;


/**
 * <p>
 *  IModel that can be reverted. Therefore it keeps a clone.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RevertableModel<T> implements IModel<T> {
	
	private IModel<T> original;
	private IModel<T> clone;
	
	// ----------------------------------------------------
	
	/**
	 * @param original
	 */
	public RevertableModel(final IModel<T> original) {
		this.original = original;
		revert();
	}
	
	// ----------------------------------------------------

	public void revert() {
		clone = SerializationUtils.clone(original);
	}
	
	// ----------------------------------------------------
	
	@Override
	public T getObject() {
		return clone.getObject();
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public void setObject(T object) {
		clone.setObject(object);
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void detach() {
		original.detach();
		clone.detach();
	}

}
