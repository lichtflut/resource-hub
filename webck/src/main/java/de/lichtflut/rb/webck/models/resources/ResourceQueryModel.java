/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.resources;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.Query;

import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;

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
public class ResourceQueryModel extends DerivedDetachableModel<List<ResourceNode>, Query> {

	private int maxResults;

	// ----------------------------------------------------
	
	/**
	 * @param queryModel
	 */
	public ResourceQueryModel(IModel<Query> queryModel) {
		super(queryModel);
	}
	
	/**
	 * @param queryModel
	 */
	public ResourceQueryModel(IModel<Query> queryModel, int maxResults) {
		super(queryModel);
		this.maxResults = maxResults;
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected List<ResourceNode> derive(final Query query) {
		if (maxResults > 0) {
			return query.getResult().toList(maxResults);
		} else {
			return query.getResult().toList();
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
