/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.models;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ElementaryDataType;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.entity.impl.RBEntityImpl;
import de.lichtflut.rb.core.schema.model.Constraint;

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
	 * TODO: DESCRIPTION
	 * </p>
	 * @param field - instance of {@link RBField}
	 * @param index - index of the value in {@link RBField}
	 */
	public NewGenericResourceModel(final RBField field, final int index){
		this.index = index;
		values = field.getFieldValues();
		if(index >= values.size()){
			if(field.getDataType().equals(ElementaryDataType.STRING)){
				values.add("");
			}
			if(field.getDataType().equals(ElementaryDataType.RESOURCE)){
				for (Constraint c : field.getConstraints()) {
					if(c.isResourceTypeConstraint()){
						values.add(new RBEntityImpl(c.getResourceTypeConstraint().asResource()));
					}
				}
			}
		}else{
			value = values.get(index);
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
			value = object;
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
				return (T) Boolean.valueOf(finalValue.toLowerCase());
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
