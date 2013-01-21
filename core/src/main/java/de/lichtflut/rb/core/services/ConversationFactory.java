package de.lichtflut.rb.core.services;

import org.arastreju.sge.Conversation;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.context.Context;

/**
 * <p>
 *  Factory for conversations.
 * </p>
 *
 * <p>
 *  Created 01.08.12
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ConversationFactory {

    /**
     * Get the current conversation for the current context.
     * @return The current conversation.
     */
    ModelingConversation getConversation();

    /**
     * Get the current conversation for the given context.
     * @return The conversation.
     */
    ModelingConversation getConversation(Context primary);

    // ----------------------------------------------------

    /**
     * Start a new conversation, which will not be managed by this factory.
     * The caller of this method is responsible for closing this conversation.
     * @return a new conversation.
     */
    Conversation startConversation();

    // ----------------------------------------------------

    /**
     * Close all conversations managed by this factory.
     */
    void closeConversations();

}
