/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.browsing;

import java.io.Serializable;

import scala.actors.threadpool.Arrays;
import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.entity.RBEntityReference;
import de.lichtflut.rb.webck.common.Action;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Dec 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class EntityBrowsingStep implements Serializable {
	
	private static Action[] NO_ACTIONS = new Action[0];
	
	private final EntityHandle handle;
	private final BrowsingState state;
	
	private Action<?>[] actions;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param handle
	 * @param state
	 * @param actions
	 */
	public EntityBrowsingStep(EntityHandle handle, BrowsingState state) {
		this.handle = handle;
		this.state = state;
		this.actions = NO_ACTIONS;
	}
	
	/**
	 * Constructor.
	 * @param handle
	 * @param state
	 * @param actions
	 */
	public EntityBrowsingStep(EntityHandle handle, BrowsingState state, Action<?>... actions) {
		this.handle = handle;
		this.state = state;
		this.actions = actions;
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return the handle
	 */
	public EntityHandle getHandle() {
		return handle;
	}
	
	/**
	 * @return the actions
	 */
	public Action<?>[] getActions() {
		return actions;
	}
	
	/**
	 * @return the state
	 */
	public BrowsingState getState() {
		return state;
	}
	
	/**
	 * @param actions the actions to set
	 */
	public void setActions(Action<?>[] actions) {
		this.actions = actions;
	}
	
	public void loadActions(RBEntityReference ref) {
		for (Action action : actions) {
			action.setValue(ref);
		}
	}
	
	// ----------------------------------------------------
	
	@Override
	public String toString() {
		return handle + " "  + Arrays.toString(actions) + ")";
	}
}