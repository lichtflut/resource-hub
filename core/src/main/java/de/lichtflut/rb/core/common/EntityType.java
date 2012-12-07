package de.lichtflut.rb.core.common;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.entity.RBEntity;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;

import java.util.Set;

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

    public static ResourceID typeOf(ResourceID resource) {
        Set<SemanticNode> types = SNOPS.objects(resource.asResource(), RDF.TYPE);
        // We are not interested in RBSystem.ENTITY
        for (SemanticNode node : types) {
            if(!node.equals(RBSystem.ENTITY)){
                return node.asResource();
            }
        }
        return null;
    }

    public static ResourceID typeOf(RBEntity entity) {
        return entity.getType();
    }

}
