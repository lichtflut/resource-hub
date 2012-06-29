/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.types;

import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableModel;
import org.apache.wicket.model.IModel;

import java.util.List;

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
	
	// ----------------------------------------------------

	/**
	 * @param index
	 */
	public void moveUp(int index) {
		if (index > 0) {
			final List<PropertyRow> list = getObject();
			PropertyRow current = list.get(index);
			PropertyRow predecessor = list.get(index -1);
			list.set(index, predecessor);
			list.set(index -1, current);
		}
	}

	/**
	 * @param index
	 */
	public void moveDown(int index) {
		final List<PropertyRow> list = getObject();
		if (index < list.size() -1) {
			PropertyRow current = list.get(index);
			PropertyRow successor = list.get(index +1);
			list.set(index, successor);
			list.set(index +1, current);	
		}
	}

	/**
	 * @param index
	 */
	public void remove(int index) {
		getObject().remove(index);
	}
	
}
