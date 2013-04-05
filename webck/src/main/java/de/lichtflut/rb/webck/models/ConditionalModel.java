/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import java.util.Collection;

import org.apache.wicket.Component;
import org.apache.wicket.model.IComponentAssignedModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IWrapModel;

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

	public static ConditionalModel<Boolean> isTrue(final IModel<?> model) {
		return new ConditionalModel<Boolean>(model) {
			@Override
			public boolean isFulfilled() {
				return Boolean.TRUE.equals(getObject());
			}
		};
	}

	public static ConditionalModel<Boolean> isFalse(final IModel<?> model) {
		return new ConditionalModel<Boolean>(model) {
			@Override
			public boolean isFulfilled() {
				return Boolean.FALSE.equals(getObject());
			}
		};
	}

	public static ConditionalModel<Boolean> isNull(final IModel<?> model) {
		return new ConditionalModel<Boolean>(model) {
			@Override
			public boolean isFulfilled() {
				return getObject() == null;
			}
		};
	}

	public static ConditionalModel<Boolean> isNotNull(final IModel<?> model) {
		return new ConditionalModel<Boolean>(model) {
			@Override
			public boolean isFulfilled() {
				return getObject() != null;
			}
		};
	}

	public static ConditionalModel<Boolean> isEmpty(final IModel<? extends Collection<?>> model) {
		return new ConditionalModel<Boolean>(model) {
			@Override
			public boolean isFulfilled() {
				@SuppressWarnings("unchecked")
				Collection<Object> collection = (Collection<Object>) model.getObject();
				return collection == null || collection.isEmpty();
			}
		};
	}

	public static ConditionalModel<Boolean> isNotEmpty(final IModel<? extends Collection<?>> model) {
		return new ConditionalModel<Boolean>(model) {
			@Override
			public boolean isFulfilled() {
				@SuppressWarnings("unchecked")
				Collection<Object> collection = (Collection<Object>) model.getObject();
				return collection != null && !collection.isEmpty();
			}
		};
	}

	public static ConditionalModel<Boolean> isNotBlank(final IModel<String> model) {
		return new ConditionalModel<Boolean>(model) {
			@Override
			public boolean isFulfilled() {
				return model.getObject() != null && model.getObject().trim().length() > 0;
			}
		};
	}

	public static ConditionalModel<Boolean> not(final ConditionalModel<?> model) {
		return new ConditionalModel<Boolean>(model) {
			@Override
			public boolean isFulfilled() {
				return !model.isFulfilled();
			}
		};
	}

	public static ConditionalModel<Boolean> and(final ConditionalModel<?>... model) {
		return new ConditionalModel<Boolean>(model) {
			@Override
			public boolean isFulfilled() {
				boolean fulfilled = true;
				for (ConditionalModel<?> current : model) {
					fulfilled &= current.isFulfilled();
				}
				return fulfilled;
			}
		};
	}

	public static ConditionalModel<Boolean> or(final ConditionalModel<?>... model) {
		return new ConditionalModel<Boolean>(model) {
			@Override
			public boolean isFulfilled() {
				for (ConditionalModel<?> current : model) {
					if (current.isFulfilled()) {
						return true;
					}
				}
				return false;
			}
		};
	}

	public static ConditionalModel<Boolean> areEqual(final IModel<?> model, final IModel<?> other) {
		return new ConditionalModel<Boolean>(model) {
			@Override
			public boolean isFulfilled() {
				return Infra.equals(model.getObject(), other.getObject());
			}
		};
	}

	public static ConditionalModel<Boolean> areEqual(final IModel<?> model, final Object other) {
		return new ConditionalModel<Boolean>(model) {
			@Override
			public boolean isFulfilled() {
				return Infra.equals(model.getObject(), other);
			}
		};
	}

	public static ConditionalModel<Boolean> lessThan(final IModel<? extends Number> model, final IModel<?extends Number> other) {
		return new ConditionalModel<Boolean>(model) {
			@Override
			public boolean isFulfilled() {
				return model.getObject().doubleValue() < other.getObject().doubleValue();
			}
		};
	}

	public static ConditionalModel<Boolean> greaterThan(final IModel<? extends Number> model, final IModel<?extends Number> other) {
		return new ConditionalModel<Boolean>(model) {
			@Override
			public boolean isFulfilled() {
				return model.getObject().doubleValue() > other.getObject().doubleValue();
			}
		};
	}

	public static ConditionalModel<Boolean> hasSchema(final IModel<RBEntity> entityModel) {
		return new ConditionalModel<Boolean>(entityModel) {
			@Override
			public boolean isFulfilled() {
				final RBEntity entity = entityModel.getObject();
				return entity != null && entity.hasSchema();
			}
		};
	}

	// -----------------------------------------------------

	@SuppressWarnings("unchecked")
	@Override
	public T getObject() {
		if (target instanceof IModel) {
			return ((IModel<T>) target).getObject();
		} else {
			return (T) target;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setObject(final T object) {
		if (target instanceof IModel) {
			((IModel<T>) target).setObject(object);
		} else {
			target = object;
		}
	}

	@Override
	public void detach() {
	}

	@Override
	public IWrapModel<T> wrapOnAssignment(final Component component) {
		return new IWrapModel<T>() {

			@Override
			public T getObject() {
				return ConditionalModel.this.getObject();
			}

			@Override
			public void setObject(final T object) {
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
