package de.lichtflut.rb.core.services;

import java.util.Set;

import org.arastreju.sge.ConversationContext;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.persistence.TransactionControl;
import org.arastreju.sge.query.Query;

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
public class ArastrejuConversationHolder implements ModelingConversation {

	private final ArastrejuResourceFactory factory;

	// ----------------------------------------------------

	public ArastrejuConversationHolder(final ArastrejuResourceFactory factory) {
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

	private ModelingConversation delegate() {
		return factory.getConversation();
	}
}
