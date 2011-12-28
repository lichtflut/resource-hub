/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.Query;

import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;

/**
 * <p>
 *  Model loading and providing search results.
 * </p>
 *
 * <p>
 * 	Created Dec 17, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class QueryResultModel extends AbstractLoadableDetachableModel<List<ResourceNode>> {

	final IModel<Query> queryModel;
	
	// ----------------------------------------------------
	
	/**
	 * @param queryModel
	 */
	public QueryResultModel(IModel<Query> queryModel) {
		this.queryModel = queryModel;
	}
	
	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public List<ResourceNode> load() {
		if (queryModel.getObject() != null) {
			return queryModel.getObject().getResult().toList();
		} else {
			return Collections.emptyList();
		}
	}

}
