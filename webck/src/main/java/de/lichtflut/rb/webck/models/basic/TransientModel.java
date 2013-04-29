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

/**
 * <p>
 *  Model for transient objects.
 * </p>
 *
 * <p>
 * 	Created Dec 17, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class TransientModel<T> implements IModel<T> {

	private transient T object;
	
	// ----------------------------------------------------
	
	/**
	 * Default constructor.
	 */
	public TransientModel() {
	}
	
	/**
	 * @param object
	 */
	public TransientModel(T object) {
		this.object = object;
	}
	
	// ----------------------------------------------------

	/** 
	* {@inheritDoc}
	*/
	@Override
	public T getObject() {
		return object;
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public void setObject(T object) {
		this.object = object;
	}
	
	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void detach() {
		this.object = null;
	}

}
