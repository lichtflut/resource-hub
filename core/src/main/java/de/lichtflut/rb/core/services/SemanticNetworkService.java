package de.lichtflut.rb.core.services;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

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

    public ResourceNode resolve(ResourceID rid);
}
