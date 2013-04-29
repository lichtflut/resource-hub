/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.core.services;

import org.arastreju.sge.Conversation;
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
    Conversation getConversation();

    /**
     * Get the current conversation for the given context.
     * @return The conversation.
     */
    Conversation getConversation(Context primary);

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
