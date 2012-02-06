/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import java.util.Collection;

import org.apache.wicket.Component;
import org.apache.wicket.model.IComponentAssignedModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IWrapModel;
import org.arastreju.sge.security.User;

import de.lichtflut.infra.Infra;
import de.lichtflut.rb.core.entity.RBEntity;

/**
 * <p>
 *  Conditional model.
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
	
	public static ConditionalModel<?> isNull(final IModel<?> model) {
		return new ConditionalModel<Object>(model) {
			@Override
			public boolean isFulfilled() {
				return getObject() == null;
			}
		};
	}
	
	public static ConditionalModel<?> isNotNull(final IModel<?> model) {
		return new ConditionalModel<Object>(model) {
			@Override
			public boolean isFulfilled() {
				return getObject() != null;
			}
		};
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ConditionalModel<?> isEmpty(final IModel<? extends Collection<?>> model) {
		return new ConditionalModel(model) {
			@Override
			public boolean isFulfilled() {
				Collection collection = (Collection) getObject();
				return collection == null || collection.isEmpty();
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
	
	public static ConditionalModel<?> and(final ConditionalModel<?>... model) {
		return new ConditionalModel<Boolean>(model) {
			@Override
			public boolean isFulfilled() {
				boolean fulfilled = true;
				for (ConditionalModel<?> current : model) {
					if (current == null) {
						System.out.println("null");
					}
					fulfilled &= current.isFulfilled();
				}
				return fulfilled;
			}
		};
	}
	
	public static <T> ConditionalModel<T> areEqual(final IModel<T> model, final IModel<T> other) {
		return new ConditionalModel<T>(model) {
			@Override
			public boolean isFulfilled() {
				return Infra.equals(model.getObject(), other.getObject());
			}
		};
	}
	
	public static <T> ConditionalModel<T> areEqual(final IModel<T> model, final Object other) {
		return new ConditionalModel<T>(model) {
			@Override
			public boolean isFulfilled() {
				return Infra.equals(model.getObject(), other);
			}
		};
	}
	
	public static <T> ConditionalModel<T> lessThan(final IModel<? extends Number> model, final IModel<?extends Number> other) {
		return new ConditionalModel<T>(model) {
			@Override
			public boolean isFulfilled() {
				return model.getObject().doubleValue() < other.getObject().doubleValue();
			}
		};
	}
	

	public static <T> ConditionalModel<T> hasPermission(final IModel<User> userModel, final String permission) {
		return new ConditionalModel<T>(userModel) {
			@Override
			public boolean isFulfilled() {
				final User user = userModel.getObject();
				return user != null && user.hasPermission(permission);
			}
		};
	}
	
	public static <T> ConditionalModel<T> hasSchema(final IModel<RBEntity> entityModel) {
		return new ConditionalModel<T>(entityModel) {
			@Override
			public boolean isFulfilled() {
				final RBEntity entity = entityModel.getObject();
				return entity != null && entity.hasSchema();
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
