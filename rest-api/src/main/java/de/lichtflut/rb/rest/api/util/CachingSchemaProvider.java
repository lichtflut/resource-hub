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
