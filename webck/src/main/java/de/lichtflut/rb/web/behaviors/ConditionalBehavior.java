/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;

import de.lichtflut.rb.web.models.ConditionalModel;

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
public abstract class ConditionalBehavior<T> extends Behavior {

	private Component component;
	
	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public ConditionalBehavior() {
	}
	
	// -----------------------------------------------------
	
	/**
	 * Apply the behavior.
	 */
	protected abstract void apply();
	
	/**
	 * @return the component
	 */
	protected Component getComponent() {
		return component;
	}
	
	// -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void bind(final Component component) {
		this.component = component;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void onConfigure(Component component) {
		super.onConfigure(component);
		apply();
	}
	
	// -----------------------------------------------------
	
	@SuppressWarnings("rawtypes")
	public static ConditionalBehavior visibleIf(final ConditionalModel model) {
		return new ConditionalBehavior() {
			@Override
			protected void apply() {
				getComponent().setVisible(model.isFulfilled());
			}
		};
	}
	

}
