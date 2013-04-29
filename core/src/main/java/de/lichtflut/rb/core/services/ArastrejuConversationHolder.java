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
import org.arastreju.sge.ConversationContext;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.persistence.TransactionControl;
import org.arastreju.sge.query.Query;

import java.util.Set;

/**
 * <p>
 *  Holder of the current Arastreju conversation.
 * </p>
 *
 * <p>
 * 	Created Jun 29, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ArastrejuConversationHolder implements Conversation {

	private final ConversationFactory factory;

	// ----------------------------------------------------

	public ArastrejuConversationHolder(final ConversationFactory factory) {
		this.factory = factory;
	}

	// ----------------------------------------------------

	@Override
	public void addStatement(final Statement stmt) {
		delegate().addStatement(stmt);
	}

	@Override
	public void attach(final SemanticGraph graph) {
		delegate().attach(graph);
	}

	@Override
	public ConversationContext getConversationContext() {
		return delegate().getConversationContext();
	}

	@Override
	public void attach(final ResourceNode node) {
		delegate().attach(node);
	}

	@Override
	public void detach(final ResourceNode node) {
		delegate().detach(node);
	}

	@Override
	public void close() {
		delegate().close();
	}

	@Override
	public ResourceNode resolve(final ResourceID resourceID) {
		return delegate().resolve(resourceID);
	}

	@Override
	public void detach(final SemanticGraph graph) {
		delegate().detach(graph);
	}

	@Override
	public TransactionControl beginTransaction() {
		return delegate().beginTransaction();
	}

	@Override
	public Query createQuery() {
		return delegate().createQuery();
	}

	@Override
	public Set<Statement> findIncomingStatements(final ResourceID object) {
		return delegate().findIncomingStatements(object);
	}

	@Override
	public void reset(final ResourceNode node) {
		delegate().reset(node);
	}

	@Override
	public void remove(final ResourceID id) {
		delegate().remove(id);
	}

	@Override
	public boolean removeStatement(final Statement stmt) {
		return delegate().removeStatement(stmt);
	}

	@Override
	public ResourceNode findResource(final QualifiedName qn) {
		return delegate().findResource(qn);
	}

	// ----------------------------------------------------

	private Conversation delegate() {
		return factory.getConversation();
	}
}
