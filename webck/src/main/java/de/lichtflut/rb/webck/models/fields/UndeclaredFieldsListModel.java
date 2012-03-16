/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.fields;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.entity.ResourceField;
import de.lichtflut.rb.webck.components.listview.ColumnConfiguration;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;

/**
 * <p>
 *  Model of undeclared entity fields derived from a ResourceNode and a ColumnConfiguration.
 * </p>
 *
 * <p>
 * 	Created Dec 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class UndeclaredFieldsListModel extends DerivedDetachableModel<List<ResourceField>, ResourceNode> {

	private final ColumnConfiguration config;
	
	// ----------------------------------------------------

	/**
	 * Constructor
	 * @param source The source model.
	 * @param config The column configuration.
	 */
	public UndeclaredFieldsListModel(IModel<ResourceNode> source, final ColumnConfiguration config) {
		super(source);
		this.config = config;
	}

	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public List<ResourceField> derive(final ResourceNode subject) {
		final List<ResourceField> result = new ArrayList<ResourceField>();
		for(ResourceID predicate : config.getPredicatesToDisplay()) {
			result.add(new ResourceField(predicate, SNOPS.objects(subject, predicate)));
		}
		return result;
	}

}
