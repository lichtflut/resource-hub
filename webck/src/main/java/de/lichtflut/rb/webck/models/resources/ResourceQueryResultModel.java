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
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import de.lichtflut.rb.webck.models.basic.PageableModel;

/**
 * <p>
 *  Model of a list of resource nodes derived from a query result.
 * </p>
 *
 * <p>
 * 	Created Feb 15, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourceQueryResultModel extends DerivedDetachableModel<List<ResourceNode>, QueryResult> implements PageableModel<ResourceNode> {

	private IModel<Integer> resultSize;
	
	private IModel<Integer> pagesize;
	
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
	public ResourceQueryResultModel(IModel<QueryResult> queryResult, IModel<Integer> pagesize, IModel<Integer> offset) {
		super(queryResult);
		this.pagesize = pagesize;
		this.offset = offset;
		this.resultSize = new DerivedModel<Integer, QueryResult>(queryResult) {
			@Override
			protected Integer derive(QueryResult result) {
				return result.size();
			}
			@Override
			public Integer getDefault() {
				return 0;
			}
		};
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<ResourceNode> derive(QueryResult result) {
		int max = Math.min(result.size(), pagesize.getObject());
		return result.toList(offset.getObject(), max);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<ResourceNode> getDefault() {
		return Collections.emptyList();
	}
	
	// -- PAGING ------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public IModel<Integer> getResultSize() {
		return resultSize;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public IModel<Integer> getPageSize() {
		return pagesize;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public IModel<Integer> getOffset() {
		return offset;
	}

	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void rewind() {
		offset.setObject(0);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void back() {
		int newOffset = Math.max(offset.getObject() - pagesize.getObject(), 0);
		offset.setObject(newOffset);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void next() {
		int newOffset = offset.getObject() + pagesize.getObject();
		if (newOffset <= resultSize.getObject()) {
			offset.setObject(newOffset);
		}
	}
	
}
