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

import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.entity.RBFieldValue;

/**
 * <p>
 * 	Model representing one value of an RBField.
 * </p>
 * 
 * <p>
 * 	Created: Aug 24, 2011
 * </p>
 *
 * @author Ravi Knox
 */
public class RBFieldValueModel implements IModel<Object> {

	private final RBField field;

	private final int index;

	// -----------------------------------------------------

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * <p>
	 * </p>
	 * @param field - instance of {@link RBField}
	 * @param index - index of the value in {@link RBField}
	 */
	public RBFieldValueModel(final RBField field, final int index){
		this.field = field;
		this.index = index;
	}

	// -----------------------------------------------------

	@Override
	public Object getObject() {
		RBFieldValue fieldValue = getFieldValue();
		if (fieldValue != null) {
			return fieldValue.getValue();
		} else {
			return null;
		}
	}

	@Override
	public void setObject(final Object object) {
		RBFieldValue fieldValue = getFieldValue();
		if (fieldValue != null) {
			getFieldValue().setValue(object);
		}
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	public RBField getField() {
		return field;
	}

	public RBFieldValue getFieldValue() {
		return field.getValue(index);
	}

	// -----------------------------------------------------

	@Override
	public void detach() {
		// Do nothing
	}

}
