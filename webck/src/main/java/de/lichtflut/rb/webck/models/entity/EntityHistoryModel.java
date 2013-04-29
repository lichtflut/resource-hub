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
package de.lichtflut.rb.webck.models.entity;

import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.browsing.BrowsingHistory;
import de.lichtflut.rb.webck.browsing.EntityBrowsingStep;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	private final boolean skipTop;
	
	// ----------------------------------------------------
	
	/**
	 * @param max Max steps to retrieve.
	 * @param skipTop Flag indicating if the current step (on top of stack) shall be excluded.
	 */
	public EntityHistoryModel(int max, boolean skipTop) {
		this.max = max;
		this.skipTop = skipTop;
	}
	
	// ----------------------------------------------------

	@Override
	public List<EntityBrowsingStep> load() {
		final List<EntityBrowsingStep> result = new ArrayList<EntityBrowsingStep>(max); 
		final Iterator<EntityBrowsingStep> steps = RBWebSession.get().getHistory().getSteps();
		int i = 1;
		if (skipTop && steps.hasNext()) {
			steps.next();
		}
		while (steps.hasNext() && i++ <= max) {
			result.add(0, steps.next());
		}
		return result;
	}

	

}
