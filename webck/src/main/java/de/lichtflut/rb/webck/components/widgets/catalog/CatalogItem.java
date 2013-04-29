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
package de.lichtflut.rb.webck.components.widgets.catalog;

import de.lichtflut.rb.core.viewspec.WidgetSpec;
import org.apache.wicket.model.IModel;

import java.io.Serializable;

/**
 * <p>
 *  Item of widget catalog.
 * </p>
 *
 * <p>
 * 	Created Feb 9, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class CatalogItem implements Serializable {
	
	private IModel<String> name;
	
	private IModel<String> description;
	
	// ----------------------------------------------------
	
	/**
	 * Default constructor.
	 */
	public CatalogItem() {
	}
	
	/**
	 * @param name
	 * @param description
	 */
	public CatalogItem(IModel<String> name, IModel<String> description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	// ----------------------------------------------------
	
	public abstract WidgetSpec createWidget();
	
	// ----------------------------------------------------
	
	/**
	 * @return the name
	 */
	public IModel<String> getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(IModel<String> name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public IModel<String> getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(IModel<String> description) {
		this.description = description;
	}
	
}
