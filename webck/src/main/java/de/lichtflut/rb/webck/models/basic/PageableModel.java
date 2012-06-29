/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.basic;

import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * <p>
 *  Model supporting paging of the results.
 * </p>
 *
 * <p>
 * 	Created Feb 21, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface PageableModel<T> extends IModel<List<T>>{

	/**
	 * @return the full result size.
	 */
	IModel<Integer> getResultSize();
	
	/**
	 * @return the maximum number of entries displayed on a page.
	 */
	IModel<Integer> getPageSize();

	/**
	 * @return the offset
	 */
	IModel<Integer> getOffset();
	
	// ----------------------------------------------------
	
	void rewind();
	
	void back();
	
	void next();

}