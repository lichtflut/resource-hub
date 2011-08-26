/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.models;

import java.util.List;

import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.schema.model.RBEntity;

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
public class NewGenericResourceModel<T> implements IModel<T> {

	private List<Object> values;
	private Object value;
	private int index;

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * <p>
	 * If no value is defiened for this TextField,
	 * pass an empty {@link String} ("") as parameter o.
	 * </p>
	 * @param field - instance of {@link IRBField}
	 * @param o - value of this TextField
	 */
	public NewGenericResourceModel(final IRBField field, final Object o){
		value = o;
		values = field.getFieldValues();
		index = values.indexOf(o);
		if(index == -1){
			index = values.size();
		}
	}

	@Override
	public void detach() {
		// Do nothing
	}

	@Override
	public T getObject() {
		T finalValue = null;

		if(value instanceof String) {
			finalValue = convertStringToT((String) value);
		}else {
			finalValue = convertObjectToT(value);
		}
		return finalValue;
	}

	@Override
	public void setObject(final T object) {
		try {
			values.set(index, object);
		} catch (Exception e){
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * @param finalValue -
	 * @return -
	 */
	@SuppressWarnings("unchecked")
	private T convertStringToT(final String finalValue) {
		if(value instanceof Boolean){
				return (T) new Boolean(finalValue.toLowerCase());
		}
		return (T) value;
	}

	/**
	 * @param value /
	 * @return /
	 */
	@SuppressWarnings("unchecked")
	private T convertObjectToT(final Object value) {
		return (T) value;
	}

}
