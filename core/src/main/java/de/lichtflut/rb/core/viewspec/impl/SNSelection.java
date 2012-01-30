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
 *  Implementation of a selection.
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
	
	// ----------------------------------------------------
	
	protected void appendExpressions(ResourceNode node, Query query) {
		final Set<Statement> expressions = node.getAssociations(WDGT.HAS_EXPRESSION);
		final SemanticNode operator = SNOPS.singleObject(this, WDGT.HAS_OPERATOR);
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
		final String term = SNOPS.string(param.getTerm());
		
		if (field != null) {
			query.addField(field, term);
		} else {
			query.addValue(term);
		}
		
	}

}
