/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.apache.wicket.Component;
import org.apache.wicket.model.IComponentAssignedModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IWrapModel;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Sep 29, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class ConditionalModel<T> implements IComponentAssignedModel<T> {

	private Object target;
	
	// -----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public ConditionalModel(final IModel<T> model) {
		this.target = model;
	}
	
	/**
	 * Constructor.
	 */
	public ConditionalModel(final Object value) {
		this.target = value;
	}
	
	/**
	 * Constructor.
	 */
	public ConditionalModel() {
		this.target = null;
	}
	
	// -----------------------------------------------------
	
	/**
	 * Check if the condition is fulfilled.
	 */
	public abstract boolean isFulfilled();
	 
	// -----------------------------------------------------
	
	public static ConditionalModel<Boolean> isTrue(final IModel<Boolean> model) {
		return new ConditionalModel<Boolean>(model) {
			@Override
			public boolean isFulfilled() {
				return Boolean.TRUE.equals(getObject());
			}
		};
	}
	
	public static ConditionalModel<Boolean> isFalse(final IModel<Boolean> model) {
		return new ConditionalModel<Boolean>(model) {
			@Override
			public boolean isFulfilled() {
				return Boolean.FALSE.equals(getObject());
			}
		};
	}
	
	public static ConditionalModel<?> not(final ConditionalModel<?> model) {
		return new ConditionalModel<Boolean>(model) {
			@Override
			public boolean isFulfilled() {
				return !model.isFulfilled();
			}
		};
	}
	
	// -----------------------------------------------------
	 
	/** 
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getObject() {
		if (target instanceof IModel) {
			return ((IModel<T>) target).getObject();
		} else {
			return (T) target;
		}
	}

	/** 
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setObject(T object) {
		if (target instanceof IModel) {
			((IModel<T>) target).setObject(object);
		} else {
			target = object;
		}
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void detach() {
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public IWrapModel<T> wrapOnAssignment(final Component component) {
		return new IWrapModel<T>() {

			@Override
			public T getObject() {
				return ConditionalModel.this.getObject();
			}

			@Override
			public void setObject(T object) {
				ConditionalModel.this.setObject(object);
			}

			@Override
			public void detach() {
				ConditionalModel.this.detach();
			}

			@Override
			public IModel<?> getWrappedModel() {
				return ConditionalModel.this;
			}
		};
	}

}
