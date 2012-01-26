/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.listview;

import java.util.List;

import org.apache.wicket.model.IModel;

import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;

/**
 * <p>
 *  Model for column headers.
 * </p>
 *
 * <p>
 * 	Created Jan 26, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ColumnHeaderModel extends DerivedDetachableModel<List<ColumnHeader>, ColumnConfiguration> {

	/**
	 * @param configModel
	 */
	public ColumnHeaderModel(IModel<ColumnConfiguration> configModel) {
		super(configModel);
	}
	
	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	protected List<ColumnHeader> derive(ColumnConfiguration original) {
		return original.getHeaders();
	};
	
}
