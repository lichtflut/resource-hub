/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;

/**
 * <p>
 *  Model providing filtered statements.
 * </p>
 *
 * <p>
 * 	Created Dec 9, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class FilteringStatementsModel 
		extends AbstractLoadableModel<List<? extends Statement>> 
		implements StatementsModel {
	
	private final StatementsModel target;
	
	// ----------------------------------------------------
	
	/**
	 * @param target
	 */
	public FilteringStatementsModel(final StatementsModel target) {
		this.target = target;
	}
	
	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public List<? extends Statement> load() {
		return filter(target.getObject());
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void detach() {
		super.detach();
		reset();
	}
	
	// ----------------------------------------------------
	
	protected abstract List<Statement> filter(List<? extends Statement> List);
	
	// ----------------------------------------------------
	
	protected List<Statement> includeObjectType(List<? extends Statement> statements, ResourceID... type) {
		final List<Statement> result = new ArrayList<Statement>();
		for (Statement stmt : statements) {
			if (stmt.getObject().isValueNode()) {
				continue;
			}
			if (isOfType(stmt.getObject().asResource(), type)) {
				result.add(stmt);
			}
		}
		return result;
	}
	
	protected List<Statement> excludeObjectType(List<? extends Statement> statements, ResourceID... type) {
		final List<Statement> result = new ArrayList<Statement>();
		for (Statement stmt : statements) {
			if (stmt.getObject().isValueNode()) {
				continue;
			}
			if (!isOfType(stmt.getObject().asResource(), type)) {
				result.add(stmt);
			}
		}
		return result;
	}
	
	protected List<Statement> excludePredicates(List<? extends Statement> statements, ResourceID... predicates) {
		final List<Statement> result = new ArrayList<Statement>();
		for (Statement stmt : statements) {
			if (!isOfType(stmt.getPredicate().asResource(), predicates)) {
				result.add(stmt);
			}
		}
		return result;
	}
	
	protected boolean isOfType(ResourceNode node, ResourceID... types) {
		for (ResourceID current : types) {
			if(node.asEntity().isInstanceOf(current)) {
				return true;	
			}
		}
		return false;
	}
	
}
