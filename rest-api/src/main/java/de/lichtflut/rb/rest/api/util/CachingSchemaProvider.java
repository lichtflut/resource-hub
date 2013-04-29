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

import de.lichtflut.rb.core.common.SchemaIdentifyingType;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.services.SchemaManager;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  As the name says: a schema provider that caches the retrieved schema data.
 * </p>
 *
 * <p>
 *  Created Mar 15, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class CachingSchemaProvider implements SchemaProvider {

    private final SchemaManager manager;

    Map<ResourceID, ResourceSchema> cache = new HashMap<ResourceID, ResourceSchema>();

    // ----------------------------------------------------

    public CachingSchemaProvider(SchemaManager manager) {
        this.manager = manager;
    }

    // ----------------------------------------------------

    @Override
    public ResourceSchema ofType(ResourceID type) {
        if (cache.containsKey(type)) {
            return cache.get(type);
        }
        ResourceSchema schema = manager.findSchemaForType(type);
        cache.put(type, schema);
        return schema;
    }

    @Override
    public ResourceSchema ofEntity(ResourceNode entity) {
        return ofType(SchemaIdentifyingType.of(entity));
    }
}
