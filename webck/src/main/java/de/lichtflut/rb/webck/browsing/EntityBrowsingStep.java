/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.browsing;

import java.io.Serializable;

import scala.actors.threadpool.Arrays;
import de.lichtflut.rb.core.entity.EntityHandle;

/**
 * <p>
 *  A element on the browsing stack.
 * </p>
 *
 * <p>
 * 	Created Dec 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("rawtypes")
public class EntityBrowsingStep implements Serializable {
	
	private static final ReferenceReceiveAction[] NO_ACTIONS = new ReferenceReceiveAction[0];
	
	// ----------------------------------------------------
	
	private final EntityHandle handle;
	
	private final BrowsingState state;
	
	private ReferenceReceiveAction<?>[] actions;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param handle
	 * @param state
	 */
	public EntityBrowsingStep(EntityHandle handle, BrowsingState state) {
		this(handle, state, NO_ACTIONS);
	}
	
	/**
	 * Constructor with actions.
	 * @param handle
	 * @param state
	 * @param actions
	 */
	public EntityBrowsingStep(EntityHandle handle, BrowsingState state, ReferenceReceiveAction<?>... actions) {
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
	 * @return the actions, will never be null.
	 */
	public ReferenceReceiveAction<?>[] getActions() {
		if (actions == null) {
			return NO_ACTIONS;
		}
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
	public void setActions(ReferenceReceiveAction<?>[] actions) {
		this.actions = actions;
	}
	
	// ----------------------------------------------------
	
	@Override
	public String toString() {
		return handle + " "  + Arrays.toString(actions) + ")";
	}
}