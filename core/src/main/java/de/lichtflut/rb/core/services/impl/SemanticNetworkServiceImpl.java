package de.lichtflut.rb.core.services.impl;

import de.lichtflut.rb.core.services.ConversationFactory;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

/**
 * <p>
 *  Implementation of SemanticNetworkService.
 * </p>
 *
 * <p>
 *  Created 01.08.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class SemanticNetworkServiceImpl implements SemanticNetworkService {

    private ConversationFactory conversationFactory;

    // ----------------------------------------------------

    public SemanticNetworkServiceImpl(ConversationFactory conversationFactory) {
        this.conversationFactory = conversationFactory;
    }

    // ----------------------------------------------------

    @Override
    public ResourceNode resolve(ResourceID rid) {
        return conversation().resolve(rid);
    }

    // ----------------------------------------------------

    private ModelingConversation conversation() {
        return conversationFactory.getConversation();
    }
}
