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
package de.lichtflut.rb.core.entity.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.model.Statement;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.entity.RBFieldValue;

/**
 * <p>
 * Default implementation of {@link RBField}.
 * </p>
 *
 * Created: Aug 8, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public abstract class AbstractRBField implements RBField, Serializable {

	private final List<RBFieldValue> fieldValues = new ArrayList<RBFieldValue>();

	private int slots;

	//------------------------------------------------------------

	public AbstractRBField(final Set<Statement> statements) {
		initValues(statements);
	}

	//------------------------------------------------------------

	public int getSlots() {
		return slots;
	}

	@Override
	public RBFieldValue getValue(final int index) {
		if (index >= slots) {
			throw new IllegalArgumentException("Index out of bounds: " + index);
		}
		return fieldValues.get(index);
	}

	@Override
	public void setValue(final int index, final Object value) {
		if (index > slots) {
			throw new IllegalArgumentException("Index out of bounds: " + index);
		} else if (index < slots) {
			RBFieldValue fv = fieldValues.get(index);
			fv.setValue(value);
		} else {
			addValue(value);
		}
	}

	@Override
	public void addValue(final Object value) {
		fieldValues.add(new RBFieldValue(this, value));
		slots++;
	}

	@Override
	public void removeSlot(final int index) {
		if (slots > 1) {
			fieldValues.remove(index);
			slots--;
		} else {
			setValue(index, null);
		}
	}

	@Override
	public List<RBFieldValue> getValues() {
		final List<RBFieldValue> copy = new ArrayList<RBFieldValue>(slots);
		for (RBFieldValue value : fieldValues) {
			if (value != null && !value.isRemoved()) {
				copy.add(value);
			}
		}
		return copy;
	}

	// -----------------------------------------------------

	/**
	 * String representation of this field with getLabel().
	 * @return {@link String}
	 */
	@Override
	public String toString(){
		return fieldValues.toString();
	}

	// -----------------------------------------------------

	/**
	 * Initialize the slots of this field.
	 * @param statements The existing values.
	 */
	protected void initValues(final Set<Statement> statements) {
		this.slots = statements.size();
		for (Statement stmt : statements) {
			this.fieldValues.add(new RBFieldValue(this, stmt));
		}
		if (fieldValues.isEmpty()) {
			this.fieldValues.add(new RBFieldValue(this));
			this.slots = 1;
		}
	}

}
