/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec.impl;

import java.util.Set;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.query.Query;

import de.lichtflut.rb.core.viewspec.Selection;
import de.lichtflut.rb.core.viewspec.WDGT;

/**
 * <p>
 *  Implementation of a selection. Each selection constists of 
 *  <ul>
 *  	<li>Expressions and/or</li>
 *  	<li>Parameters</li>
 *  </ul>
 * </p>
 *
 * <p>
 * 	Created Jan 23, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNSelection extends ResourceView implements Selection {

	/**
	 * Default constructor.
	 */
	public SNSelection() {
		super();
	}

	/**
	 * Constructor.
	 * @param resource
	 */
	public SNSelection(ResourceNode resource) {
		super(resource);
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void adapt(Query query) {
		appendExpressions(this, query);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDefined() {
		return !getAssociations(WDGT.HAS_EXPRESSION).isEmpty()
			|| !getAssociations(WDGT.HAS_PARAMETER).isEmpty();
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Selection[" + getQualifiedName().getSimpleName() + "]");
		sb.append("\n    Expressions: ");
		for (SemanticNode parameter : SNOPS.objects(this, WDGT.HAS_EXPRESSION)) {
			sb.append(parameter);
		}
		sb.append("\n    Parameters: ");
		for (SemanticNode parameter : SNOPS.objects(this, WDGT.HAS_PARAMETER)) {
			sb.append(new SNSelectionParameter(parameter.asResource()));
		}
		return sb.toString();
	}
	
	// ----------------------------------------------------
	
	protected void appendExpressions(ResourceNode node, Query query) {
		final Set<Statement> expressions = SNOPS.associations(node, WDGT.HAS_EXPRESSION);
		final SemanticNode operator = SNOPS.singleObject(node, WDGT.HAS_OPERATOR);
		if (WDGT.NOT_OPERATOR.equals(operator)) {
			query.not();
		}
		if (expressions.size() > 1) {
			if (WDGT.AND_OPERATOR.equals(operator)) {
				query.beginAnd();
			} else if (WDGT.OR_OPERATOR.equals(operator)) {
				query.beginOr();
			} else {
				throw new IllegalStateException("Wrong operator for mulitple expresssions: " + operator);
			}
		}
		
		for (Statement statement : expressions) {
			appendExpressions(statement.getObject().asResource(), query);
		}
		
		for (SemanticNode parameter : SNOPS.objects(node, WDGT.HAS_PARAMETER)) {
			appendParameter(new SNSelectionParameter(parameter.asResource()), query);
		}
		
		if (expressions.size() > 1) {
			query.end();
		}
	}

	/**
	 * @param param The parameter node.
	 * @param query The query to append to.
	 */
	private void appendParameter(SNSelectionParameter param, Query query) {
		final ResourceID field = param.getField();
		final SemanticNode termNode = param.getTerm();
		final boolean isResourceReference = termNode != null && termNode.isResourceNode();
		final String term = SNOPS.string(termNode);
		
		if (field != null) {
			query.addField(field, term);
		} else if (isResourceReference){
			query.addRelation(term);
		} else {
			query.addValue(term);
		}
		
	}

}
