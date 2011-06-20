/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.markup.html.panel.Panel;

import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.ck.behavior.CKBehavior;

/**
 * [TODO Insert description here.
 * <p>
 *  Basic interface of the generic-resource components-family.
 *  Specifies some methods which have to be implemented
 * </p>
 *
 * Created: Jun 20, 2011
 *
 * @author Ravi Knox
 */
public abstract class CKComponent extends Panel {

	private static final long serialVersionUID = 1L;
	private Map<String, CKBehavior> behaviorRegistry = new HashMap<String, CKBehavior>();

	/**
	 * Component view modes.
	 * TODO: DESCRIPTION.
	 */
	public enum ViewMode{
		WRITE,
		READ_WRITE,
		READ
	}
	// ------------- Constructor ---------------------------
	/**
	 * Constructor.
	 * @param id -
	 */
	public CKComponent(final String id){
		super(id);
	}

	// -----------------------------------------------------


	/**
	 * Returns ServiceProvider.
	 * This method musst be overridden.
	 * @return An instance of {@link RBServiceProvider}
	 */
	public abstract RBServiceProvider getServiceProvider();


	/**
	 * Sets the view mode for the component. This method musst be overridden.
	 * If the ViewMode is not supported, an {@link UnsupportedOperationException} is raised.
	 * @param mode -
	 * @return -
	 */

	public abstract CKComponent setViewMode(final ViewMode mode);

	/**
	 * Adds a behavior to a component.
	 * @param key - Name of the behavior
	 * @param behavior - Desired behavior
	 * @return {@link CKComponent}
	 */
	public final CKComponent addBehavior(final String key,final CKBehavior behavior){
		behaviorRegistry.put(key, behavior);
		return this;
	}

	/**
	 * Returns the behavior for the passed Key.
	 * @param key - Name of the behavior
	 * @return {@link CKBehavior}
	 */
	public final CKBehavior getBehavior(final String key){
		return behaviorRegistry.get(key);
	}

	/**
	 * Removes a behavior for this component.
	 * @param key - Name of the behavior which is to be removed
	 * @return {@link CKBehavior}
	 */
	public final CKBehavior removeBehavior(final String key){
		return behaviorRegistry.remove(key);
	}

	/**
	 * Clears all behaviors for this component.
	 */
	public final void resetBehaviors(){
		behaviorRegistry.clear();
	}
}
