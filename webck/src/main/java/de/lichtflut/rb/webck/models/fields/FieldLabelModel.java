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

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.webck.common.RBWebSession;

/**
 * <p>
 *  Model for obtaining the locale specific label of an {@link RBField}.
 * </p>
 *
 * <p>
 * 	Created Nov 18, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class FieldLabelModel extends AbstractReadOnlyModel<String> {

	private final IModel<RBField> fieldModel;

	// ----------------------------------------------------

	/**
	 * Constuctor.
	 * @param fieldModel The field model.
	 */
	public FieldLabelModel(final IModel<RBField> fieldModel) {
		this.fieldModel = fieldModel;
	}

	// ----------------------------------------------------

	@Override
	public String getObject() {
		return fieldModel.getObject().getLabel(RBWebSession.get().getLocale());
	}


}
