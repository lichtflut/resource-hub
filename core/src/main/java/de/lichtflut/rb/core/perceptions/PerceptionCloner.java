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
package de.lichtflut.rb.core.perceptions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.lichtflut.rb.core.RBSystem;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *  Clones a perception or updates a clone.
 * </p>
 * <p>
 *  Created 03.01.13
 * </p>
 *
 * @author Oliver Tigges
 */
public class PerceptionCloner {

	private static final Logger LOGGER = LoggerFactory.getLogger(PerceptionCloner.class);

	private final Perception original;

	// ----------------------------------------------------

	public PerceptionCloner(final Perception original) {
		this.original = original;
	}

	// ----------------------------------------------------

	public void clone(final Perception target) {
		target.setBasePerception(original);

		// First Round: Create plain clones
		Set<ResourceID> alreadyCloned = getAlreadyCloned(target);
		Set<ResourceID> rootItems = getRootItems(original);

		Map<PerceptionItem, PerceptionItem> originalToCloneMap = new HashMap<PerceptionItem, PerceptionItem>();

		ItemCloner itemCloner = new ItemCloner();
		for (PerceptionItem originalItem : original.getItems()) {
			if (alreadyCloned.contains(originalItem)) {
				continue;
			}
			PerceptionItem clone = itemCloner.createClone(originalItem);
			target.addItem(clone);
			clone.setPerception(target);
			if (rootItems.contains(originalItem)) {
				target.addTreeRootItem(clone);
			}
			originalToCloneMap.put(originalItem, clone);
		}

		// Second round: Add tree structure

		for (PerceptionItem clonedItem : target.getItems()) {
			PerceptionItem originalItem = PerceptionItem.from(SNOPS.fetchObject(clonedItem, RBSystem.BASED_ON));
			for (PerceptionItem originalSubItem : originalItem.getSubItems()) {
				PerceptionItem clonedSubItem = originalToCloneMap.get(originalSubItem);
				clonedItem.addSubItem(clonedSubItem);
			}
		}

		LOGGER.debug("Created clone of perception {} \n -- clone -- \n {} ",
				original.printHierarchy(), target.printHierarchy());
	}

	// ----------------------------------------------------

	private Set<ResourceID> getAlreadyCloned(final Perception clonedPerception) {
		Set<ResourceID> result = new HashSet<ResourceID>();
		for (PerceptionItem clonedItem : clonedPerception.getItems()) {
			ResourceNode node = SNOPS.fetchObjectAsResource(clonedItem, RBSystem.BASED_ON);
			// cloned perceptions may also have own item's, which are not cloned
			if (node != null) {
				result.add(clonedItem);
			}
		}
		return result;
	}

	private Set<ResourceID> getRootItems(final Perception perception) {
		Set<ResourceID> result = new HashSet<ResourceID>();
		for (PerceptionItem item : original.getTreeRootItems()) {
			result.add(item);
		}
		return result;
	}

}
