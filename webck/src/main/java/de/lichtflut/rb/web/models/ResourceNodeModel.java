/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.models;

import org.apache.wicket.Component;
import org.apache.wicket.model.AbstractPropertyModel;
import org.apache.wicket.model.IChainingModel;
import org.apache.wicket.model.IComponentInheritedModel;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IWrapModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.arastreju.sge.model.nodes.ResourceNode;

/**
 * <p>
 *  [DESCRIPTION].
 * </p>
 *
 * <p>
 * 	Created May 11, 2011
 * </p>
 *
 * @author Oliver Tigges
 * @param <T>
 */
@SuppressWarnings("serial")
public class ResourceNodeModel<T extends ResourceNode> implements IComponentInheritedModel<T>, IChainingModel<T> {

	private Object target;

	// -----------------------------------------------------

	/**
	 * @param model /
	 */
	public ResourceNodeModel(final IModel<T> model) {
		target = model;
	}

	/**
	 * @param node /
	 */
	public ResourceNodeModel(final ResourceNode node) {
		target = node;
	}

	// -----------------------------------------------------

	/**
	 * @see org.apache.wicket.model.IModel#getObject()
	 * @return /
	 */
	@SuppressWarnings("unchecked")
	public T getObject(){
		if (target instanceof IModel){
			return ((IModel<T>)target).getObject();
		}
		return (T)target;
	}

	/**
	 * @see org.apache.wicket.model.IModel#setObject(java.lang.Object)
	 * @param object /
	 */
	@SuppressWarnings("unchecked")
	public void setObject(final T object){
		if (target instanceof IModel){
			((IModel<T>)target).setObject(object);
			}else{
			target = object;
		}
	}

	/**
	 * @see org.apache.wicket.model.IChainingModel#getChainedModel()
	 * @return /
	 */
	public IModel<?> getChainedModel(){
		if (target instanceof IModel){
			return (IModel<?>)target;
		}
		return null;
	}

	/**
	 * @see org.apache.wicket.model.IChainingModel#setChainedModel(org.apache.wicket.model.IModel)
	 * @param model /
	 */
	public void setChainedModel(final IModel<?> model){
		target = model;
	}

	/**
	 * @see org.apache.wicket.model.IDetachable#detach()
	 */
	public void detach(){
		if (target instanceof IDetachable){
			((IDetachable)target).detach();
		}
	}

	/**
	 * Returns the property expression that should be used against the target object.
	 *
	 * @param component /
	 * @return property expression that should be used against the target object /
	 */
	protected String propertyExpression(final Component component){
		return component.getId();
	}

	/**
	 * @see org.apache.wicket.model.IComponentInheritedModel#wrapOnInheritance(org.apache.wicket.Component)
	 * @param <C> /
	 * @param component /
	 * @return /
	 */
	public <C> IWrapModel<C> wrapOnInheritance(final Component component){
		return new SemanticAttributeCompoundModel<C>(component);
	}

	/**
	 * Binds this model to a special property by returning a model that has this compound model as
	 * its nested/wrapped model and the property which should be evaluated. This can be used if the
	 * id of the Component isn't a valid property for the data object.
	 *
	 * @param property /
	 * @return The IModel that is a wrapper around the current model and the property /
	 * @param <S> /
	 *            the type of the property
	 */
	public <S> IModel<S> bind(final String property){
		return new PropertyModel<S>(this, property);
	}

	/**
	 * Component aware variation of the {@link CompoundPropertyModel} that components that inherit.
	 * the model get
	 *
	 * @author ivaynberg
	 * @param <C>
	 *            The model object type
	 */
	private class SemanticAttributeCompoundModel<C> extends AbstractPropertyModel<C>
		implements
			IWrapModel<C>{

		private final Component owner;

		/**
		 * Constructor.
		 * @param owner The component that this model has been attached to
		 */
		public SemanticAttributeCompoundModel(final Component owner){
			super(ResourceNodeModel.this);
			this.owner = owner;
		}

		/**
		 * @see org.apache.wicket.model.AbstractPropertyModel#propertyExpression()
		 * @return /
		 */
		@Override
		protected String propertyExpression(){
			return ResourceNodeModel.this.propertyExpression(owner);
		}

		/**
		 * @see org.apache.wicket.model.IWrapModel#getWrappedModel()
		 * @return /
		 */
		public IModel<T> getWrappedModel(){
			return ResourceNodeModel.this;
		}

		/**
		 * @see org.apache.wicket.model.AbstractPropertyModel#detach()
		 */
		@Override
		public void detach(){
			super.detach();
			ResourceNodeModel.this.detach();
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 * @return /
	 */
	@Override
	public String toString(){
		AppendingStringBuffer sb = new AppendingStringBuffer().append("Model:classname=["
				+ getClass().getName() + "]");
		sb.append(":nestedModel=[").append(target).append("]");
		return sb.toString();
	}

}
