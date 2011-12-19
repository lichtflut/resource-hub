/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import java.util.List;

import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableModel;

/**
 * <p>
 *  Model for property rows.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class PropertyRowListModel extends AbstractLoadableModel<List<PropertyRow>> {

	private final IModel<ResourceSchema> schemaModel;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 */
	public PropertyRowListModel(IModel<ResourceSchema> schemaModel) {
		this.schemaModel = schemaModel;
	}
	
	// ----------------------------------------------------

	/** 
	* {@inheritDoc}
	*/
	@Override
	public List<PropertyRow> load() {
		return PropertyRow.toRowList(schemaModel.getObject());
	}
	
}
