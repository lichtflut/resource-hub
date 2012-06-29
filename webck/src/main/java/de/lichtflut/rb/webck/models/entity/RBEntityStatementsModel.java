/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.entity;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.webck.common.RelationshipAccess;
import de.lichtflut.rb.webck.common.RelationshipFilter;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.Statement;

import java.util.List;

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
public class RBEntityStatementsModel extends DerivedDetachableModel<List<Statement>, RBEntity> {
	
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
	protected List<Statement> derive(RBEntity entity) {
		return new RelationshipAccess(entity).getStatements(filter);
	}

}
