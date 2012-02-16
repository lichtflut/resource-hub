/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.resources;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
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

	private IModel<Integer> maxResults;
	
	private IModel<Integer> offset;

	// ----------------------------------------------------
	
	/**
	 * @param queryResult
	 */
	public ResourceQueryResultModel(IModel<QueryResult> queryResult) {
		this(queryResult, new Model<Integer>(Integer.MAX_VALUE));
	}
	
	/**
	 * @param queryResult
	 */
	public ResourceQueryResultModel(IModel<QueryResult> queryResult, IModel<Integer> maxResults) {
		this(queryResult, maxResults, Model.of(0));
	}
	
	/**
	 * @param queryResult
	 */
	public ResourceQueryResultModel(IModel<QueryResult> queryResult, IModel<Integer> maxResults, IModel<Integer> offset) {
		super(queryResult);
		this.maxResults = maxResults;
		this.offset = offset;
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<ResourceNode> derive(QueryResult result) {
		int max = Math.min(result.size(), maxResults.getObject());
		return result.toList(offset.getObject(), max);
	}


	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<ResourceNode> getDefault() {
		return Collections.emptyList();
	}
	
}
