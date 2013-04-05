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
