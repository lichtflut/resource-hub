/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.resources;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.QueryResult;

import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Feb 15, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourceQueryResultModel extends DerivedDetachableModel<List<ResourceNode>, QueryResult>{

	private int maxResults;

	// ----------------------------------------------------
	
	/**
	 * @param queryResult
	 */
	public ResourceQueryResultModel(IModel<QueryResult> queryResult) {
		super(queryResult);
	}
	
	/**
	 * @param queryResult
	 */
	public ResourceQueryResultModel(IModel<QueryResult> queryResult, int maxResults) {
		super(queryResult);
		this.maxResults = maxResults;
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<ResourceNode> derive(QueryResult result) {
		if (maxResults > 0) {
			return result.toList(maxResults);
		} else {
			return result.toList();
		}
	}


	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<ResourceNode> getDefault() {
		return Collections.emptyList();
	}
}
