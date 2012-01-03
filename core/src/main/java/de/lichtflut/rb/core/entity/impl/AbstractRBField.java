/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.entity.RBField;

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
	
	private final List<Object> values = new ArrayList<Object>();

	private int slots;

	//------------------------------------------------------------

	/**
	 * Constructor.
	 * @param values - values of this field
	 */
	public AbstractRBField(final Set<SemanticNode> values, boolean isResourceReference) {
		initSlots(values, isResourceReference); 
	}

	//------------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	public int getSlots() {
		return slots;
	};
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Object getValue(final int index) {
		if (index >= slots) {
			throw new IllegalArgumentException("Index out of bounds: " + index);
		}
		return values.get(index);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(final int index, final Object value) {
		if (index > slots) {
			throw new IllegalArgumentException("Index out of bounds: " + index);
		} else if (index < slots) {
			values.remove(index);	
		} else {
			slots++;
		}
		values.add(index, value);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void addValue(final Object value) {
		values.add(value);
		slots++;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void removeSlot(int index) {
		if (slots > 1) {
			values.remove(index);	
			slots--;
		} else {
			setValue(index, null);
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<Object> getValues() {
		final List<Object> copy = new ArrayList<Object>(slots);
		for (Object object : values) {
			if (object != null) {
				copy.add(object);
			}
		}
		return copy;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setValues(final List<Object> fieldValues) {
		this.values.clear();
		this.values.addAll(fieldValues);
	}

	// -----------------------------------------------------

	/**
	 * String representation of this field with getLabel().
	 * @return {@link String}
	 */
	@Override
	public String toString(){
		return values.toString();
	}
	
	// -----------------------------------------------------
	
	/**
	 * @param givenValues
	 * @param isResourceReference 
	 */
	protected void initSlots(final Set<SemanticNode> givenValues, boolean isResourceReference) {
		this.slots = givenValues.size();	
		if (givenValues == null || givenValues.isEmpty()) {
			this.values.add(null);
			this.slots = 1;
		} else if (isResourceReference) {
			addReferences(givenValues);
		} else {
			addValues(givenValues);
		}
	}
	
	protected void addReferences(final Set<SemanticNode> givenValues) {
		for (SemanticNode sn : givenValues) {
			if (sn.isResourceNode()) {
				this.values.add(sn.asResource());	
			} else {
				this.values.add(null);
			}
		}
	}
	
	protected void addValues(final Set<SemanticNode> givenValues) {
		for (SemanticNode sn : givenValues) {
			if (sn.isValueNode()) {
				this.values.add(sn.asValue().getValue());
			} else {
				this.values.add(sn.asResource().getQualifiedName().toURI());
			}
		}
	}
	
}
