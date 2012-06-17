/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security.authserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryResult;

import de.lichtflut.rb.core.security.SearchResult;

/**
 * <p>
 *  Search result based on a {@link Query}.
 * </p>
 *
 * <p>
 * 	Created Jun 16, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class QueryBasedSearchResult<T> implements SearchResult<T> {
	
	private final QueryResult queryResult;
	
	// ----------------------------------------------------

	/**
	 * @param queryResult The underlying query result.
	 */
	public QueryBasedSearchResult(QueryResult queryResult) {
		this.queryResult = queryResult;
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<T> iterator() {
		return null;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		queryResult.close();
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return queryResult.size();
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<T> toList() {
		return map(queryResult.toList());
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<T> toList(int max) {
		return map(queryResult.toList(max));
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<T> toList(int offset, int max) {
		return map(queryResult.toList(offset, max));
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		return queryResult.isEmpty();
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public T getSingleItem() {
		return map(queryResult.getSingleNode());
	}
	
	// ----------------------------------------------------
	
	protected abstract T map(ResourceNode node);
	
	// ----------------------------------------------------
	
	protected List<T> map(List<ResourceNode> nodes) {
		final List<T> result = new ArrayList<T>(nodes.size());
		for (ResourceNode current : nodes) {
			result.add(map(current));
		}
		return result;
	}
	
}
