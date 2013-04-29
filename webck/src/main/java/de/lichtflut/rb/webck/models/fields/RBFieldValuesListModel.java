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

import de.lichtflut.rb.core.entity.RBField;
import org.apache.wicket.model.IModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>
 *  Model for the list of values of an RBField.
 * </p>
 *
 * <p>
 * 	Created Nov 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class RBFieldValuesListModel implements IModel<List<RBFieldValueModel>> {

	private final IModel<RBField> model;

	// -----------------------------------------------------

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * @param model - model of {@link RBField}
	 */
	public RBFieldValuesListModel(final IModel<RBField> model){
		this.model = model;
	}

	// -----------------------------------------------------

	@Override
	public List<RBFieldValueModel> getObject() {
		if (model == null || model.getObject() == null) {
			return null;
		}
		final RBField rbField = model.getObject();
		final List<RBFieldValueModel> result = new ArrayList<RBFieldValueModel>(rbField.getSlots());
		for (int i=0; i < rbField.getSlots(); i++) {
			result.add(new RBFieldValueModel(rbField, i));
		}
		return result;
	}

	@Override
	public void setObject(final List<RBFieldValueModel> object) {
		throw new UnsupportedOperationException("Value may not be set.");
	}

	// -----------------------------------------------------

	@Override
	public void detach() {
		// Do nothing
	}

}
