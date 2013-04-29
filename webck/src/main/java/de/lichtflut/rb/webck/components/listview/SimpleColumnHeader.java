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
package de.lichtflut.rb.webck.components.listview;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.arastreju.sge.model.ResourceID;

import java.io.Serializable;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Nov 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SimpleColumnHeader implements ColumnHeader, Serializable {

	private final IModel<String> label;
	
	private final ResourceID predicate;
	
	private final ColumnType type;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param label
	 * @param predicate
	 * @param type
	 */
	public SimpleColumnHeader(final String label, final ResourceID predicate, final ColumnType type) {
		this.label = Model.of(label);
		this.predicate = predicate;
		this.type = type;
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public IModel<String> getLabel() {
		return label;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getPredicate() {
		return predicate;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ColumnType getType() {
		return type;
	}
	
	

}
