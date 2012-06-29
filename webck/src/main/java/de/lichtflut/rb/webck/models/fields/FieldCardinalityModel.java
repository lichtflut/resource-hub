/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.fields;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import org.apache.wicket.model.IModel;

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
public class FieldCardinalityModel extends DerivedModel<Integer, RBField> {

	/**
	 * @param original
	 */
	public FieldCardinalityModel(final IModel<RBField> original) {
		super(original);
	}
	
	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	protected Integer derive(RBField original) {
		return original.getCardinality().getMaxOccurs();
	}

}
