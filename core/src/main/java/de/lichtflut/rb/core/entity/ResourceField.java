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
package de.lichtflut.rb.core.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;

/**
 * <p>
 *  Basic representation of a {@link ResourceNode}s field.
 * </p>
 *
 * <p>
 * 	Created Dec 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourceField {
	
	private final ResourceID predicate;
	
	private final List<SemanticNode> values;

	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param predicate The predicate of the field.
	 * @param objects The objects.
	 */
	public ResourceField(ResourceID predicate, List<SemanticNode> objects) {
		this.predicate = predicate;
		this.values = objects;
	}
	
	/**
	 * Constructor.
	 * @param predicate The predicate of the field.
	 * @param objects The objects.
	 */
	public ResourceField(ResourceID predicate, Collection<SemanticNode> objects) {
		this.predicate = predicate;
		this.values = new ArrayList<SemanticNode>(objects);
	}
	
	// ----------------------------------------------------

	/**
	 * @return the predicate
	 */
	public ResourceID getPredicate() {
		return predicate;
	}
	
	/**
	 * @return the value
	 */
	public List<SemanticNode> getValues() {
		return values;
	}
	

}
