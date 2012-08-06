package de.lichtflut.rb.core.services;

import org.arastreju.sge.ConversationContext;
import org.arastreju.sge.ModelingConversation;
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
public class ArastrejuConversationHolder implements ModelingConversation {

    private ArastrejuResourceFactory factory;

    // ----------------------------------------------------

    public ArastrejuConversationHolder(ArastrejuResourceFactory factory) {
        this.factory = factory;
    }

    // ----------------------------------------------------

    public void addStatement(Statement stmt) {
        delegate().addStatement(stmt);
    }

    public void attach(SemanticGraph graph) {
        delegate().attach(graph);
    }

    public ConversationContext getConversationContext() {
        return delegate().getConversationContext();
    }

    public void attach(ResourceNode node) {
        delegate().attach(node);
    }

    public void detach(ResourceNode node) {
        delegate().detach(node);
    }

    public void close() {
        delegate().close();
    }

    public ResourceNode resolve(ResourceID resourceID) {
        return delegate().resolve(resourceID);
    }

    public void detach(SemanticGraph graph) {
        delegate().detach(graph);
    }

    public TransactionControl beginTransaction() {
        return delegate().beginTransaction();
    }

    public Query createQuery() {
        return delegate().createQuery();
    }

    public Set<Statement> findIncomingStatements(ResourceID object) {
        return delegate().findIncomingStatements(object);
    }

    public void reset(ResourceNode node) {
        delegate().reset(node);
    }

    public void remove(ResourceID id) {
        delegate().remove(id);
    }

    public boolean removeStatement(Statement stmt) {
        return delegate().removeStatement(stmt);
    }

    public ResourceNode findResource(QualifiedName qn) {
        return delegate().findResource(qn);
    }

    // ----------------------------------------------------

    private ModelingConversation delegate() {
        return factory.getConversation();
    }
}
