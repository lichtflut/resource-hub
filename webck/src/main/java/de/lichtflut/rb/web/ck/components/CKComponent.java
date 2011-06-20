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
	 *
	 * @return An instance of {@link RBServiceProvider}
	 */
	public abstract RBServiceProvider getServiceProvider();


	/**
	 * TODO: DESCRIPTION.
	 * If the ViewMode is not supported, an {@link UnsupportedOperationException} is raised.
	 * @param mode -
	 * @return -
	 */
	public abstract CKComponent setViewMode(final ViewMode mode);

	/**
	 * TODO DESCRIPTION.
	 * @param key -
	 * @param behavior -
	 * @return -
	 */
	public CKComponent addBehavior(final String key,final CKBehavior behavior){
		behaviorRegistry.put(key, behavior);
		return this;
	}

	/**
	 * TODO DESCRIPTION.
	 * @param key -
	 * @return {@link CKBehavior}
	 */
	public CKBehavior getBehavior(final String key){
		return behaviorRegistry.get(key);
	}

	/**
	 * TODO DESCRIPTION.
	 * @param key -
	 * @return {@link CKBehavior}
	 */
	public CKBehavior removeBehavior(final String key){
		return behaviorRegistry.remove(key);
	}

	/**
	 * TODO DESCRIPTION.
	 */
	public void resetBehaviors(){
		behaviorRegistry.clear();
	}
}
