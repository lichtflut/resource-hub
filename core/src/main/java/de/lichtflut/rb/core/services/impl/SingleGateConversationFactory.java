package de.lichtflut.rb.core.services.impl;

import de.lichtflut.rb.core.services.ConversationFactory;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.context.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  Conversation Factory using a single gate, that needs to open during lifetime of this factory.
 * </p>
 *
 * <p>
 *  Created 18.01.13
 * </p>
 *
 * @author Oliver Tigges
 */
public class SingleGateConversationFactory implements ConversationFactory {

    private final ArastrejuGate gate;

    private ModelingConversation conversation;

    private Map<Context, ModelingConversation> conversationMap = new HashMap<Context, ModelingConversation>();

    // ----------------------------------------------------

    public SingleGateConversationFactory(ArastrejuGate gate) {
        this.gate = gate;
    }

    // ----------------------------------------------------

    /**
     * Get the current conversation managed by this factory. This conversations is shared.
     * The caller may not change this conversation's state or close this conversation.
     * @return The current conversation.
     */
    @Override
    public ModelingConversation getConversation() {
        if (conversation == null) {
            conversation = gate.startConversation();
        }
        return conversation;
    }

    /**
     * Get a conversation for the given context managed by this factory. This conversations may be shared.
     * The caller may not change this conversation's state or close this conversation.
     * @param primary The primary context of this conversation.
     * @return The current conversation.
     */
    @Override
    public ModelingConversation getConversation(Context primary) {
        if (conversationMap.containsKey(primary)) {
            ModelingConversation conversation = conversationMap.get(primary);
            return conversation;
        } else {
            ModelingConversation conversation = gate.startConversation(primary);
            conversationMap.put(primary, conversation);
            return conversation;
        }
    }

    /**
     * Start a new conversation. This conversations is not shared and has to be managed by the caller.
     * @return The new conversation.
     */
    @Override
    public Conversation startConversation() {
        return gate.startConversation();
    }

    // ----------------------------------------------------

    public void closeConversations() {
        if (conversation != null) {
            conversation.close();
            conversation = null;
        }
        for (Context ctx : conversationMap.keySet()) {
            Conversation conv = conversationMap.get(ctx);
            conv.close();
        }
        conversationMap.clear();
    }
}
