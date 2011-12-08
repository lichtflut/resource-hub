/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.webck.components.listview.ColumnConfiguration;

/**
 * 
 * <p>
 *  Model for the list of an entities fields.
 * </p>
 *
 * <p>
 * 	Created Nov 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class RBFieldsListModel implements IModel<List<RBField>> {

	private final IModel<RBEntity> model;
	private final ColumnConfiguration config;
	
	// -----------------------------------------------------

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * @param model - model of {@link RBEntity}
	 */
	public RBFieldsListModel(final IModel<RBEntity> model){
		this(model, null);
	}
	
	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * @param model - model of {@link RBEntity}
	 * @param config - the configuration.
	 */
	public RBFieldsListModel(final IModel<RBEntity> model, final ColumnConfiguration config){
		this.model = model;
		this.config = config;
	}
	
	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<RBField> getObject() {
		if (model == null || model.getObject() == null) {
			return null;
		} 
		if (config == null) {
			return model.getObject().getAllFields();
		} else {
			return fetchConfiguredFields(model.getObject().getAllFields());
		}
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setObject(final List<RBField> object) {
		throw new UnsupportedOperationException("Value may not be set.");
	}

	// -----------------------------------------------------
	
	@Override
	public void detach() {
		// Do nothing
	}
	
	// ----------------------------------------------------
	
	private List<RBField> fetchConfiguredFields(final List<RBField> allFields) {
		final List<RBField> result = new ArrayList<RBField>();
		final Set<ResourceID> predicates = config.getPredicatesToDisplay();
		for (RBField field : allFields) {
			if (predicates.contains(field.getPredicate())) {
				result.add(field);
			}
		}
		return result;
	}

}
