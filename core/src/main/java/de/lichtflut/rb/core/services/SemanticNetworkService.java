package de.lichtflut.rb.core.services;

import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;

/**
 * <p>
 *  This service allows interactions with the semantic network, i.e. the arastreju engine.
 *  This service operates on the current conversation.
 * </p>
 *
 * <p>
 *  Created 01.08.12
 * </p>
 *
 * @author Oliver Tigges
 */
public interface SemanticNetworkService {

    /**
     * Resolve a resource.
     * @param rid The ID to resolve.
     * @return The resolved node.
     */
    ResourceNode resolve(ResourceID rid);

    /**
     * Resolve a resource using th given read contexts.
     * @param rid The ID to resolve.
     * @param contexts The contexts to be regarded when traversing the node.
     * @return The resolved node.
     */
    ResourceNode resolve(ResourceID rid, Context... contexts);

    ResourceNode find(QualifiedName qn);

    void attach(ResourceNode node);
}
