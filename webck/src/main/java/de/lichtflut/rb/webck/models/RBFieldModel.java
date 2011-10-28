/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.entity.RBField;

/**
 * [TODO Insert description here.
 * <p>
 * This Model is not detachable.
 *</p>
 * Created: Aug 24, 2011
 *
 * @author Ravi Knox
 *
 * @param <T> -
 */
@SuppressWarnings("serial")
public class RBFieldModel<T> implements IModel<T> {

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
	public RBFieldModel(final RBField field, final int index){
		this.field = field;
		this.index = index;
	}
 // -----------------------------------------------------
	

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getObject() {
		return (T) field.getValue(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setObject(final T object) {
		field.setValue(index, object);
	}

	// -----------------------------------------------------
	
	@Override
	public void detach() {
		// Do nothing
	}
	
}
