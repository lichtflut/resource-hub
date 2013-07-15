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
package de.lichtflut.rb.webck.models.fields;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.webck.components.listview.ColumnConfiguration;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 
 * <p>
 *  Model for the list of an entities fields.
 * </p>
 *
 * <p>
 * 	Created Nov 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class RBFieldsListModel implements IModel<List<RBField>> {

	private final IModel<RBEntity> model;
	private final ColumnConfiguration config;
	
	// -----------------------------------------------------

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * @param model - model of {@link RBEntity}
	 */
	public RBFieldsListModel(final IModel<RBEntity> model){
		this(model, null);
	}
	
	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * @param model - model of {@link RBEntity}
	 * @param config - the configuration.
	 */
	public RBFieldsListModel(final IModel<RBEntity> model, final ColumnConfiguration config){
		this.model = model;
		this.config = config;
	}
	
	// -----------------------------------------------------

	@Override
	public List<RBField> getObject() {
		if (model.getObject() == null) {
			return null;
		} 
		if (config == null) {
			return model.getObject().getAllFields();
		} else {
			return fetchConfiguredFields(model.getObject().getAllFields());
		}
	}

	@Override
	public void setObject(final List<RBField> object) {
		throw new UnsupportedOperationException("Value may not be set.");
	}

	// -----------------------------------------------------
	
	@Override
	public void detach() {
		// Do nothing
	}
	
	// ----------------------------------------------------
	
	private List<RBField> fetchConfiguredFields(final List<RBField> allFields) {
		final List<RBField> result = new ArrayList<RBField>();
		final Collection<ResourceID> predicates = config.getPredicatesToDisplay();
		for (RBField field : allFields) {
			if (predicates.contains(field.getPredicate())) {
				result.add(field);
				predicates.remove(field.getPredicate());
			}
		}
		return result;
	}

}
