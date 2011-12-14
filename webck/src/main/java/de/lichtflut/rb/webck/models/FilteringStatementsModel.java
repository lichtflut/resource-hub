/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.SemanticNode;

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
	
	protected abstract List<Statement> filter(List<? extends Statement> List);
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void reset() {
		target.reset();
		super.reset();
	}
	
	// ----------------------------------------------------
	
	protected List<Statement> filterByObjectType(List<? extends Statement> statements, ResourceID type) {
		final List<Statement> result = new ArrayList<Statement>();
		for (Statement stmt : statements) {
			if (isObjectOfType(stmt, type)) {
				result.add(stmt);
			}
		}
		return result;
	}
	
	protected boolean isObjectOfType(Statement stmt, ResourceID type) {
		final SemanticNode node = stmt.getObject();
		if (node.isResourceNode()) {
			return node.asResource().asEntity().isInstanceOf(type);
		} else {
			return false;
		}
	}
	

}
