/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity.impl;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.entity.RBFieldValue;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.SemanticNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public AbstractRBField(Set<Statement> statements) {
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
	public void removeSlot(int index) {
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
