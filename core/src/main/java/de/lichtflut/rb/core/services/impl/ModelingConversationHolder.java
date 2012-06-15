/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import org.arastreju.sge.ConversationContext;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.persistence.TransactionControl;
import org.arastreju.sge.query.Query;

import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * <p>
 *  Holder for a modeling conversation.
 * </p>
 *
 * <p>
 * 	Created Jun 15, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ModelingConversationHolder implements ModelingConversation {
	
	private final ServiceProvider provider;

	// ----------------------------------------------------
	
	/**
	 * Constructor. 
	 */
	public ModelingConversationHolder(ServiceProvider provider) {
		this.provider = provider;
	}
	
	// ----------------------------------------------------

	/**
	 * @param stmt
	 * @see org.arastreju.sge.ModelingConversation#addStatement(org.arastreju.sge.model.Statement)
	 */
	public void addStatement(Statement stmt) {
		delegate().addStatement(stmt);
	}

	/**
	 * @param stmt
	 * @return
	 * @see org.arastreju.sge.ModelingConversation#removeStatement(org.arastreju.sge.model.Statement)
	 */
	public boolean removeStatement(Statement stmt) {
		return delegate().removeStatement(stmt);
	}

	/**
	 * @return
	 * @see org.arastreju.sge.ModelingConversation#createQuery()
	 */
	public Query createQuery() {
		return delegate().createQuery();
	}

	/**
	 * @param qn
	 * @return
	 * @see org.arastreju.sge.ModelingConversation#findResource(org.arastreju.sge.naming.QualifiedName)
	 */
	public ResourceNode findResource(QualifiedName qn) {
		return delegate().findResource(qn);
	}

	/**
	 * @param resourceID
	 * @return
	 * @see org.arastreju.sge.ModelingConversation#resolve(org.arastreju.sge.model.ResourceID)
	 */
	public ResourceNode resolve(ResourceID resourceID) {
		return delegate().resolve(resourceID);
	}

	/**
	 * @param node
	 * @see org.arastreju.sge.ModelingConversation#attach(org.arastreju.sge.model.nodes.ResourceNode)
	 */
	public void attach(ResourceNode node) {
		delegate().attach(node);
	}

	/**
	 * @param node
	 * @see org.arastreju.sge.ModelingConversation#detach(org.arastreju.sge.model.nodes.ResourceNode)
	 */
	public void detach(ResourceNode node) {
		delegate().detach(node);
	}

	/**
	 * @param node
	 * @see org.arastreju.sge.ModelingConversation#reset(org.arastreju.sge.model.nodes.ResourceNode)
	 */
	public void reset(ResourceNode node) {
		delegate().reset(node);
	}

	/**
	 * @param id
	 * @see org.arastreju.sge.ModelingConversation#remove(org.arastreju.sge.model.ResourceID)
	 */
	public void remove(ResourceID id) {
		delegate().remove(id);
	}

	/**
	 * @param graph
	 * @see org.arastreju.sge.ModelingConversation#attach(org.arastreju.sge.model.SemanticGraph)
	 */
	public void attach(SemanticGraph graph) {
		delegate().attach(graph);
	}

	/**
	 * @param graph
	 * @see org.arastreju.sge.ModelingConversation#detach(org.arastreju.sge.model.SemanticGraph)
	 */
	public void detach(SemanticGraph graph) {
		delegate().detach(graph);
	}

	/**
	 * @return
	 * @see org.arastreju.sge.ModelingConversation#getConversationContext()
	 */
	public ConversationContext getConversationContext() {
		return delegate().getConversationContext();
	}

	/**
	 * @return
	 * @see org.arastreju.sge.ModelingConversation#beginTransaction()
	 */
	public TransactionControl beginTransaction() {
		return delegate().beginTransaction();
	}

	/**
	 * 
	 * @see org.arastreju.sge.ModelingConversation#close()
	 */
	public void close() {
		delegate().close();
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return the delegate
	 */
	public ModelingConversation delegate() {
		return provider.getConversation();
	}

}
