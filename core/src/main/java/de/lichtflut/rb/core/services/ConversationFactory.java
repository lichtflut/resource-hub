package de.lichtflut.rb.core.services;

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

    ModelingConversation getConversation();

    ModelingConversation getConversation(Context primary);

    // ----------------------------------------------------

    ModelingConversation startConversation();

}
