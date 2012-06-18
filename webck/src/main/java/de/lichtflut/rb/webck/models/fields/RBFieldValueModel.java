/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.fields;

import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.entity.RBField;

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
 *
 * @param <T> -
 */
@SuppressWarnings("serial")
public class RBFieldValueModel<T> implements IModel<T> {

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
	
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	public RBField getField(){
		return field;
	}
	// -----------------------------------------------------
	
	@Override
	public void detach() {
		// Do nothing
	}
	
}
