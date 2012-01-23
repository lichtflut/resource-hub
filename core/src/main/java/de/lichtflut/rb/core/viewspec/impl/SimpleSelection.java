/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec.impl;

import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.SubQuery;

import de.lichtflut.rb.core.viewspec.Selection;

/**
 * <p>
 *  A simple selection, just a query.
 * </p>
 *
 * <p>
 * 	Created Jan 20, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SimpleSelection implements Selection {

	private String queryString;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor. 
	 */
	public SimpleSelection() {
	}
	
	/**
	 * @param queryString
	 */
	public SimpleSelection(String queryString) {
		this.queryString = queryString;
	}
	
	// ----------------------------------------------------

	/**
	 * Set the query string. 
	 * @param query The query.
	 */ 
	public void setQueryString(String query) {
		this.queryString = query;

	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void adapt(Query query) {
		if (this.queryString != null) {
			query.add(new SubQuery(queryString));
		};
	}

}
