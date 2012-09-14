package de.lichtflut.rb.core.services.impl;

import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.services.ConversationFactory;
import de.lichtflut.rb.core.services.SemanticNetworkService;

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

	private final ConversationFactory conversationFactory;

	// ----------------------------------------------------

	public SemanticNetworkServiceImpl(final ConversationFactory conversationFactory) {
		this.conversationFactory = conversationFactory;
	}

	// ----------------------------------------------------

	@Override
	public ResourceNode resolve(final ResourceID rid) {
		return conversation().resolve(rid);
	}

	@Override
	public ResourceNode resolve(final ResourceID rid, final Context... contexts) {
        ModelingConversation conversation = conversationFactory.startConversation();
        conversation.getConversationContext().setReadContexts(contexts);
        try {
            return conversation.resolve(rid);
        } finally {
            conversation.close();
        }
	}

	@Override
	public ResourceNode find(final QualifiedName qn) {
		return conversation().findResource(qn);
	}

	@Override
	public void attach(final ResourceNode node) {
		conversation().attach(node);
	}

	// ----------------------------------------------------

	private ModelingConversation conversation() {
		return conversationFactory.getConversation();
	}

}
