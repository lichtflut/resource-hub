/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.RB;
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
	
	private final static Set<ResourceID> BLACKLIST = new HashSet<ResourceID>() {{
		add(RDF.TYPE);
		add(RDFS.SUB_CLASS_OF);
		add(RDFS.LABEL);
		add(RB.HAS_FIELD_LABEL);
		add(RB.HAS_IMAGE);
		add(RB.HAS_SHORT_DESC);
	}};
	
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
		final List<Statement> stmts = new ArrayList<Statement>();
		for (Statement current : node.getAssociations()) {
			if (!BLACKLIST.contains(current.getPredicate())) {
				stmts.add(current);
			}
		}
		return stmts;
	}

	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void detach() {
		model.detach();
		reset();
	}

}
