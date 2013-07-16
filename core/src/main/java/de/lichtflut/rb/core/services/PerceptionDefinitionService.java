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
package de.lichtflut.rb.core.services;

import de.lichtflut.rb.core.perceptions.Perception;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;

import java.util.List;

/**
 * <p>
 *  Service for the definitions of perceptions.
 * </p>
 *
 * <p>
 *  Created 16.11.12
 * </p>
 *
 * @author Oliver Tigges
 */
public interface PerceptionDefinitionService {

	/**
	 * Store a single perception.
	 * @param perception
	 */
	void store(Perception perception);

	/**
	 * Overloads {@link PerceptionDefinitionService#store(Perception)}.
	 * @param list A list of perceptions
	 */
	void store(List<Perception> list);

	/**
	 * Deletes a perception.
	 * @param perception Perception to delete
	 */
	void delete(Perception perception);

	Perception findByQualifiedName(QualifiedName qn);

	List<Perception> findAllPerceptions();

	// ----------------------------------------------------

	/**
	 * Define the base perception of the given perception.
	 * @param perception The perception.
	 * @param base The qualified base perception.
	 */
	void definePerceptionBase(Perception perception, QualifiedName base);

	/**
	 * Clone all items (not yet cloned) from base perception.
	 * @param target The target perception to contain the cloned items.
	 * @param base The base perception containing the original items.
	 * @return The perception containing the cloned items.
	 */
	Perception cloneItems(QualifiedName target, QualifiedName base);

	/**
	 * Retrieves a List of all known perception categories.
	 * @return a List of ResourceNodes
	 */
	List<ResourceNode> findAllPerceptionCategories();

}
