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
package de.lichtflut.rb.core.common;

import java.util.Set;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.entity.RBEntity;
import org.arastreju.sge.model.nodes.views.SNClass;

/**
 * <p>
 *  Simple helper to eliminate redundant implementations.
 * </p>
 *
 * <p>
 *  Created 07.12.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class EntityType {

	public static SNClass of(final ResourceID resource) {
		Set<SemanticNode> types = SNOPS.objects(resource.asResource(), RDF.TYPE);
		// We are not interested in RBSystem.ENTITY
		for (SemanticNode node : types) {
			if(!node.equals(RBSystem.ENTITY)){
				return SNClass.from(node);
			}
		}
		return null;
	}

	public static SNClass of(final RBEntity entity) {
		return SNClass.from(entity.getType());
	}

}
