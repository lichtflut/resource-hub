/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.browsing;

import java.io.Serializable;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.commons.lang3.Validate;
import org.apache.wicket.Application;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.entity.EntityHandle;

/**
 * <p>
 *  History of browsing through entities.
 * </p>
 *
 * <p>
 * 	Created Dec 2, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class BrowsingHistory implements Serializable {

	private final Logger logger = LoggerFactory.getLogger(BrowsingHistory.class);

	private final Deque<EntityBrowsingStep> stack = new LinkedBlockingDeque<EntityBrowsingStep>(200);

	private JumpTarget offset;

	// ----------------------------------------------------

	/**
	 * Default constructor.
	 */
	public BrowsingHistory() {
		clear(new JumpTarget(Application.get().getHomePage()));
	}

	// -- ACCESS THE STACK --------------------------------

	/**
	 * Get the top element on the stack.
	 * @return The top element or null if the stack is empty.
	 */
	public EntityBrowsingStep getCurrentStep() {
		if (!stack.isEmpty()) {
			return stack.peek();
		} else {
			return null;
		}
	}

	/**
	 * Get an iterator over the steps on the stack beginning with top.
	 * @return The iterator.
	 */
	public Iterator<EntityBrowsingStep> getSteps() {
		return stack.iterator();
	}

	/**
	 * @return The stack size.
	 */
	public int size() {
		return stack.size();
	}

	// -- CLEAR -------------------------------------------

	/**
	 * Clear the stack and set the offset element, which will be activated if the stack is empty
	 * and back() is called.
	 * @param offset The element to return.
	 */
	public void clear(final JumpTarget offset) {
		stack.clear();
		this.offset = offset;
	}

	// -- PROCEED -----------------------------------------

	/**
	 * Add a element on the stack to be viewed.
	 * @param handle The handle representing the entity.
	 */
	public void view(final EntityHandle handle) {
		rollbackEditingSteps();
		removeExisting(handle);
		stack.push(new EntityBrowsingStep(handle, BrowsingState.VIEW));
		logger.debug("Browsing to " + handle + "  ----  " + this);
	}

	/**
	 * Add a element on the stack to be edited.
	 * @param handle The handle representing the entity.
	 */
	public void edit(final EntityHandle handle) {
		rollbackEditingSteps();
		stack.push(new EntityBrowsingStep(handle, BrowsingState.EDIT));
		logger.debug("Creating " + handle + "  ----  " + this);
	}

	/**
	 * Add a EntityBrowsingStep-element on the stack to be created.
	 * @param handle The handle representing the entity.
	 */
	public void create(final EntityHandle handle) {
		rollbackEditingSteps();
		stack.push(new EntityBrowsingStep(handle, BrowsingState.CREATE));
		logger.debug("Creating " + handle + "  ----  " + this);
	}

	/**
	 * Add a element on the stack to created and returned to a recipient.
	 * @param handle The handle representing the entity.
	 * @param actions The actions that will receive the created entity.
	 */
	public void createReference(final EntityHandle handle, final ReferenceReceiveAction<?>... actions) {
		stack.push(new EntityBrowsingStep(handle, BrowsingState.CREATE_REFERENCE, actions));
		logger.debug("Creating sub reference " + this);
	}

	// --- STEP BACK --------------------------------------

	/**
	 * Step back. If the stack is empty, activate the offset.
	 */
	public BrowsingResponse back() {
		stack.pop();
		if (stack.isEmpty()) {
			offset.activate(RequestCycle.get());
			return BrowsingResponse.DONE;
		}
		return BrowsingResponse.CONTINUE;
	}

	/**
	 * Step back, but don't go to offset. If the stack will be empty, show the last entity in VIEW mode.
	 */
	public BrowsingResponse finishEditing() {
		final EntityBrowsingStep last = stack.pop();
		Validate.isTrue(BrowsingState.EDIT == last.getState() || BrowsingState.CREATE == last.getState());
		if (stack.isEmpty()) {
			stack.push(new EntityBrowsingStep(last.getHandle(), BrowsingState.VIEW));
		}
		return BrowsingResponse.CONTINUE;
	}

	// ----------------------------------------------------

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Browsing History\n");
		sb.append("  stack: ").append(stack).append("\n");
		return sb.toString();
	}

	// ----------------------------------------------------

	/**
	 * Rollback all create and edit steps, until only view steps are on the stack.
	 */
	private void rollbackEditingSteps() {
		while (!stack.isEmpty() && !(BrowsingState.VIEW.equals(stack.peek().getState()))) {
			stack.pop();
		}
	}

	private void removeExisting(final EntityHandle handle) {
		for (EntityBrowsingStep step : stack) {
			if (step.getHandle().equals(handle)) {
				stack.remove(step);
			}
		}
	}

}
