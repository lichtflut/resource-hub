/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.resources;

import java.util.List;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.SortCriteria;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;

/**
 * <p>
 *  Model of a list of Resource Nodes.
 * </p>
 *
 * <p>
 * 	Created Nov 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class ResourceListModel extends AbstractLoadableDetachableModel<List<ResourceNode>> {

	private final ResourceID type;
	
	private final String[] sortColumns;
	
	// -----------------------------------------------------
	
	/**
	 * @param id The id of the entity to load.
	 */
	public ResourceListModel(final ResourceID type, final String... sortColumns) {
		this.type = type;
		this.sortColumns = sortColumns;
	}

	// -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	public List<ResourceNode> load() {
		final Query query = getServiceProvider().getArastejuGate().createQueryManager().buildQuery();
		query.addField(RDF.TYPE, SNOPS.uri(type));
		if (sortColumns != null && sortColumns.length > 0) {
			query.setSortCriteria(new SortCriteria(sortColumns));
		}
		return query.getResult().toList(500);
	}
	
	// -----------------------------------------------------
	
	protected abstract ServiceProvider getServiceProvider();

}
