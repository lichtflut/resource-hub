/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.webck.browsing;

import de.lichtflut.rb.core.entity.EntityHandle;

import java.io.Serializable;
import java.util.Arrays;

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