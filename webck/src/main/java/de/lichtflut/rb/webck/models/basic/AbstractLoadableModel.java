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


/**
 * <p>
 *  Model that loads it data and holds it until explicitly reseted.
 * </p>
 *
 * <p>
 * 	Created Nov 17, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class AbstractLoadableModel<T> implements LoadableModel<T> {
	
	private T loaded;
	
	// ----------------------------------------------------
	
	@Override
	public final void reset() {
		loaded = null;
	}
	
	@Override
	public final T getObject() {
		if (loaded == null) {
			loaded = load();
		}
		if (loaded != null) {
			return loaded;	
		} else {
			return getDefault();
		}
	}
	
	@Override
	public void setObject(final T loaded) {
		this.loaded = loaded;
	}
	
	@Override
	public void detach() {
	}
	
	public T getDefault() {
		return null;
	}
	
	// ----------------------------------------------------

	public abstract T load(); 
	
	
}