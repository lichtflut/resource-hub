/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.entity.RBEntity;

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
public class RBEntityStatementsModel extends AbstractLoadableModel<List<? extends Statement>> implements StatementsModel {

	private final LoadableModel<RBEntity> model;
	
	// ----------------------------------------------------

	/**
	 * @param model
	 */
	public RBEntityStatementsModel(final LoadableModel<RBEntity> model) {
		this.model = model;
	}
	
	// ----------------------------------------------------

	/** 
	* {@inheritDoc}
	*/
	@Override
	public List<? extends Statement> load() {
		if (model.getObject() == null || model.getObject().getNode() == null) {
			return Collections.emptyList();
		}
		final ResourceNode node = model.getObject().getNode();
		return new ArrayList<Statement>(node.getAssociations());
	}

	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void reset() {
		super.reset();
		model.reset();
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void detach() {
		model.detach();
		// reset this model but don't cascade reset!
		super.reset();
	}

}
