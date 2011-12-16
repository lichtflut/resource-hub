/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.application;

import java.io.Serializable;
import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.actors.threadpool.Arrays;
import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.entity.RBEntityReference;
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
	
	
	
	private Deque<EntityHandle> viewHistory = new LinkedBlockingDeque<EntityHandle>();
	
	private Deque<EditAction> editHistory = new LinkedBlockingDeque<EditAction>(50);
	
	// ----------------------------------------------------
	
	public EntityHandle getCurrentEntity() {
		if (!editHistory.isEmpty()) {
			return editHistory.peek().handle;
		} else if (!viewHistory.isEmpty()) {
			return viewHistory.peek();
		} else {
			return null;
		}
	}
	
	public Action<?>[] getCurrentActions() {
		if (!editHistory.isEmpty()) {
			return editHistory.peek().actions;
		} else {
			return new Action[0];
		}
	}
	
	public boolean isEditing() {
		return !editHistory.isEmpty();
	}
	
	public boolean hasPredecessors() {
		if (editHistory.isEmpty()) {
			return viewHistory.size() > 1;
		} else {
			return editHistory.size() > 1;
		}
	}

	// -- PROCEED -----------------------------------------
	
	public void browseTo(EntityHandle handle) {
		editHistory.clear();
		viewHistory.push(handle);
		logger.debug("Browsing to " + handle + "  ----  " + this);
	}
	
	public void beginEditing() {
		Validate.isTrue(!viewHistory.isEmpty());
		editHistory.push(new EditAction(viewHistory.peek()));
		logger.debug("Starting Editing " + this);
	}
	
	public void createReferencedEntity(EntityHandle handle, Action<?>... actions) {
		Validate.isTrue(!editHistory.isEmpty(), "Not yet in edit mode.");
		editHistory.peek().actions = actions;
		editHistory.push(new EditAction(handle));
		logger.debug("Creating sub reference " + this);
	}
	
	// --- STEP BACK --------------------------------------
	
	public void back() {
		if (editHistory.isEmpty()){
			viewHistory.pop();
		} else {
			editHistory.pop();
		}
		logger.debug("Creating sub reference " + getCurrentEntity() + " ----- " + this);
	}
	
	public void finishEditing() {
		Validate.isTrue(!editHistory.isEmpty(), "Cancelling not possible if edit stack is empty");
		editHistory.pop();
		logger.debug("Cancel " + this);
	}
	
	@SuppressWarnings("unchecked")
	public void applyReferencedEntity(final RBEntityReference ref) {
		Validate.isTrue(editHistory.size() > 1, "Less than two entities on edit stack");
		editHistory.pop();
		final Action<Object>[] actions = (Action<Object>[]) editHistory.peek().actions;
		for (Action<Object> action : actions) {
			action.setValue(ref);
		}
		logger.debug("applying referenced " +  ref + " ----- " + this);
	}
	
	// ----------------------------------------------------
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Browsing History\n");
		sb.append("  view stack: " + viewHistory + "\n");
		sb.append("  edit stack: " + editHistory);
		return sb.toString();
	}
	
	// ----------------------------------------------------
	
	class EditAction implements Serializable {
		
		public EditAction(EntityHandle handle, Action<?>... actions) {
			this.handle = handle;
			this.actions = actions;
		}
		
		EntityHandle handle;
		Action<?>[] actions;
		
		@Override
		public String toString() {
			return handle + " "  + Arrays.toString(actions) + ")";
		}
	}
	
}
