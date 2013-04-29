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
package de.lichtflut.rb.rest.api.util;

import de.lichtflut.rb.core.schema.model.ResourceSchema;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

/**
 * <p>
 *  Provides schemas for entities or types.
 * </p>
 *
 * <p>
 *  Created Mar 15, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public interface SchemaProvider {

    /**
     * Get the schema describing given type.
     * @param type The type.
     * @return The type's schema or null.
     */
    ResourceSchema ofType(ResourceID type);

    /**
     * Get the schema describing the entity's type.
     * @param entity The entity.
     * @return The entity's schema or null.
     */
    ResourceSchema ofEntity(ResourceNode entity);

}
