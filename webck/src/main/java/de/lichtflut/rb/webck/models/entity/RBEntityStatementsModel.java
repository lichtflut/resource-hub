/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.entity;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.Statement;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.webck.common.RelationshipAccess;
import de.lichtflut.rb.webck.common.RelationshipFilter;
import de.lichtflut.rb.webck.models.basic.AbstractDerivedListModel;

/**
 * <p>
 *  Model providing an Entitie's statments.
 * </p>
 *
 * <p>
 * 	Created Dec 9, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBEntityStatementsModel extends AbstractDerivedListModel<Statement, RBEntity> {
	
	private final RelationshipFilter filter;
	
	// ----------------------------------------------------

	/**
	 * @param source
	 */
	public RBEntityStatementsModel(IModel<RBEntity> source, RelationshipFilter filter) {
		super(source);
		this.filter = filter;
	}
	
	// ----------------------------------------------------

	/** 
	* {@inheritDoc}
	*/
	@Override
	public List<Statement> derive(final IModel<RBEntity> source) {
		return new RelationshipAccess(source).getStatements(filter);
	}

}
