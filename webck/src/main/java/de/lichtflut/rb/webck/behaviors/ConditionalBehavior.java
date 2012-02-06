/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;

import de.lichtflut.rb.webck.models.ConditionalModel;

/**
 * <p>
 *  Behavior only active if condition is fulfilled.
 *  @see ConditionalModel
 * </p>
 *
 * <p>
 * 	Created Sep 29, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class ConditionalBehavior<T> extends Behavior {

	/**
	 * Apply the behavior.
	 */
	protected abstract void apply(Component component);
	
	// -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void onConfigure(Component component) {
		super.onConfigure(component);
		apply(component);
	}
	
	// -----------------------------------------------------
	
	@SuppressWarnings("rawtypes")
	public static ConditionalBehavior visibleIf(final ConditionalModel model) {
		return new ConditionalBehavior() {
			@Override
			protected void apply(Component component) {
				component.setVisible(model.isFulfilled());
			}
		};
	}
	
	@SuppressWarnings("rawtypes")
	public static ConditionalBehavior requiredIf(final ConditionalModel model) {
		return new ConditionalBehavior() {
			@Override
			protected void apply(Component component) {
				if (component instanceof FormComponent) {
					final FormComponent<?> fc = (FormComponent<?>) component;
					fc.setRequired(model.isFulfilled()); 
				}
			}
		};
	}
	
	@SuppressWarnings("rawtypes")
	public static ConditionalBehavior enableIf(final ConditionalModel model) {
		return new ConditionalBehavior() {
			@Override
			protected void apply(Component component) {
				component.setEnabled(model.isFulfilled());
			}
		};
	}
	
	@SuppressWarnings("rawtypes")
	public static ConditionalBehavior defaultButtonIf(final ConditionalModel model) {
		return new ConditionalBehavior() {
			@Override
			protected void apply(Component component) {
				if (component.isVisibleInHierarchy() && model.isFulfilled()) {
					final Form<?> form = component.findParent(Form.class);
					form.setDefaultButton((IFormSubmittingComponent) component);
				}
			}
		};
	}
	

}
