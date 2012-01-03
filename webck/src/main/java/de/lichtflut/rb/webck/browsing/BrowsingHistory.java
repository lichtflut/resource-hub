/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.browsing;

import java.io.Serializable;
import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.commons.lang3.Validate;
import org.apache.wicket.Application;
import org.apache.wicket.request.cycle.RequestCycle;
import org.arastreju.sge.model.ResourceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.webck.common.Action;

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
	
	private Deque<EntityBrowsingStep> stack = new LinkedBlockingDeque<EntityBrowsingStep>(200);
	
	private JumpTarget offset;
	
	// ----------------------------------------------------
	
	/**
	 * Default constructor.
	 */
	public BrowsingHistory() {
		clear();
	}
	
	// ----------------------------------------------------
	
	public EntityHandle getCurrentEntity() {
		final EntityBrowsingStep step = getCurrentStep();
		if (step != null) {
			return step.getHandle();
		} else {
			return null;
		}
	}
	
	public Action<?>[] getCurrentActions() {
		final EntityBrowsingStep step = getCurrentStep();
		if (step != null) {
			return step.getActions();
		} else {
			return new Action[0];
		}
	}
	
	public BrowsingState getState() {
		final EntityBrowsingStep step = getCurrentStep();
		if (step != null) {
			return step.getState();
		} else {
			return null;
		}
	}
	
	public boolean hasPredecessors() {
		return stack.size() > 1;
	}
	
	public void clear() {
		stack.clear();
		offset = new JumpTarget(Application.get().getHomePage());
	}
	
	public void clear(JumpTarget target) {
		stack.clear();
		offset = target;
	}

	// -- PROCEED -----------------------------------------
	
	public void view(EntityHandle handle) {
		rollbackEditingSteps();
		stack.push(new EntityBrowsingStep(handle, BrowsingState.VIEW));
		logger.debug("Browsing to " + handle + "  ----  " + this);
	}
	
	public void create(EntityHandle handle) {
		rollbackEditingSteps();
		stack.push(new EntityBrowsingStep(handle, BrowsingState.CREATE));
		logger.debug("Creating " + handle + "  ----  " + this);
	}
	
	public void edit(EntityHandle handle) {
		rollbackEditingSteps();
		stack.push(new EntityBrowsingStep(handle, BrowsingState.EDIT));
		logger.debug("Creating " + handle + "  ----  " + this);
	}
	
	public void beginEditing() {
		Validate.isTrue(!stack.isEmpty());
		stack.push(new EntityBrowsingStep(stack.peek().getHandle(), BrowsingState.EDIT));
		logger.debug("Starting Editing " + this);
	}
	
	public void beginClassifying() {
		Validate.isTrue(!stack.isEmpty());
		stack.push(new EntityBrowsingStep(getCurrentEntity(), BrowsingState.CLASSIFY));
		logger.debug("Starting Classify " + this);
	}
	
	public void createReferencedEntity(EntityHandle handle, Action<?>... actions) {
		Validate.isTrue(!stack.isEmpty());
		stack.peek().setActions(actions);
		stack.push(new EntityBrowsingStep(handle, BrowsingState.CREATE));
		logger.debug("Creating sub reference " + this);
	}
	
	// --- STEP BACK --------------------------------------
	
	/**
	 * Step back.
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
	 * Step back, but don't go to offset.
	 */
	public BrowsingResponse finishEditing() {
		final EntityBrowsingStep last = stack.pop();
		if (stack.isEmpty()) {
			stack.push(new EntityBrowsingStep(last.getHandle(), BrowsingState.VIEW));
		}
		return BrowsingResponse.CONTINUE;
	}
	
	public void applyReferencedEntity(final ResourceID ref) {
		stack.peek().loadActions(ref);
		logger.debug("applying referenced " +  ref + " ----- " + this);
	}
	
	// ----------------------------------------------------
	
	private EntityBrowsingStep getCurrentStep() {
		if (!stack.isEmpty()) {
			return stack.peek();
		} else {
			return null;
		}
	}
	
	private void rollbackEditingSteps() {
		while (!stack.isEmpty() && !(BrowsingState.VIEW.equals(stack.peek().getState()))) {
			stack.pop();
		}
	}
	
	// ----------------------------------------------------
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Browsing History\n");
		sb.append("  stack: " + stack + "\n");
		return sb.toString();
	}
	
}
