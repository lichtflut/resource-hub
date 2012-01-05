/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.lichtflut.rb.webck.application.RBWebSession;
import de.lichtflut.rb.webck.browsing.BrowsingHistory;
import de.lichtflut.rb.webck.browsing.EntityBrowsingStep;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;

/**
 * <p>
 *  Model providing a list of the entities in {@link BrowsingHistory}.
 * </p>
 *
 * <p>
 * 	Created Jan 5, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class EntityHistoryModel extends AbstractLoadableDetachableModel<List<EntityBrowsingStep>> {

	private final int max;
	private final boolean skipFirst;
	
	// ----------------------------------------------------
	
	/**
	 * @param max Max steps to retrieve.
	 */
	public EntityHistoryModel(int max, boolean skipFirst) {
		this.max = max;
		this.skipFirst = skipFirst;
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<EntityBrowsingStep> load() {
		final List<EntityBrowsingStep> result = new ArrayList<EntityBrowsingStep>(max); 
		final Iterator<EntityBrowsingStep> steps = RBWebSession.get().getHistory().getSteps();
		int i = 1;
		if (skipFirst && steps.hasNext()) {
			steps.next();
		}
		while (steps.hasNext() && i++ <= max) {
			result.add(0, steps.next());
		}
		return result;
	}

	

}
