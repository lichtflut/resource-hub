/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.entity.RBEntity;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Dec 9, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBEntityStatementsModel implements StatementsModel {

	private final IModel<RBEntity> model;
	
	// ----------------------------------------------------

	/**
	 * @param model
	 */
	public RBEntityStatementsModel(final IModel<RBEntity> model) {
		this.model = model;
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends Statement> getObject() {
		if (model.getObject() == null || model.getObject().getNode() == null) {
			return Collections.emptyList();
		}
		final ResourceNode node = model.getObject().getNode();
		return new ArrayList<Statement>(node.getAssociations());
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setObject(List<? extends Statement> object) {
		throw new UnsupportedOperationException();
	}

	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void detach() {
		model.detach();
	}

}
