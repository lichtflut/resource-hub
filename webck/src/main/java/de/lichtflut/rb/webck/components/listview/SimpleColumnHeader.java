/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.listview;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Nov 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SimpleColumnHeader implements ColumnHeader {

	private final IModel<String> label;
	
	private final ResourceID predicate;
	
	private final ColumnType type;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param label
	 * @param predicate
	 * @param type
	 */
	public SimpleColumnHeader(final String label, final ResourceID predicate, final ColumnType type) {
		this.label = Model.of(label);
		this.predicate = predicate;
		this.type = type;
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public IModel<String> getLabel() {
		return label;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getPredicate() {
		return predicate;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ColumnType getType() {
		return type;
	}
	
	

}
