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
package de.lichtflut.rb.core.services.impl;

import de.lichtflut.rb.core.services.ConversationFactory;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final static Logger LOGGER = LoggerFactory.getLogger(SemanticNetworkServiceImpl.class);

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
        Conversation conversation = conversationFactory.startConversation();
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
    public void remove(QualifiedName qn) {
        remove(SNOPS.id(qn));
    }

    @Override
    public void remove(ResourceID resource) {
        conversation().remove(resource);
    }

    @Override
	public void attach(final ResourceNode node) {
		conversation().attach(node);
	}

    @Override
    public void add(Statement statement) {
        LOGGER.info("Adding statement {}.", statement);
        conversation().addStatement(statement);
    }

    @Override
    public void remove(Statement statement) {
        LOGGER.info("Removing statement {}.", statement);
        conversation().removeStatement(statement);
    }

    // ----------------------------------------------------

	private Conversation conversation() {
		return conversationFactory.getConversation();
	}

}
