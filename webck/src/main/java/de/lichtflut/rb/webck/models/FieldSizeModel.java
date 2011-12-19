/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.webck.models.basic.DerivedModel;

/**
 * <p>
 *  Model delivering the maximum cardinality of an {@link RBField}.
 * </p>
 *
 * <p>
 * 	Created Dec 16, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class FieldSizeModel extends DerivedModel<Integer, RBField> {

	/**
	 * @param original
	 */
	public FieldSizeModel(final IModel<RBField> original) {
		super(original);
	}
	
	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	protected Integer derive(RBField original) {
		return original.getSlots();
	}

}
