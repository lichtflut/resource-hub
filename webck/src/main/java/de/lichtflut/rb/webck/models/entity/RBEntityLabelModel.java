/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.entity;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.model.IModel;

/**
 * <p>
 *  Model for obtaining the locale specific label of an {@link RBEntity}.
 * </p>
 *
 * <p>
 * 	Created Dec 23, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBEntityLabelModel extends DerivedModel<String, RBEntity> {

	/**
	 * Constuctor.
	 * @param model The entity model.
	 */
	public RBEntityLabelModel(final IModel<RBEntity> model) {
		super(model);
	}

	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	protected String derive(RBEntity original) {
		final String label = original.getLabel();
		if (StringUtils.isBlank(label)) {
			return getDefault();
		} else {
			return label;
		}
	}
	
}
