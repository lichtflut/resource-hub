/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.entity;

import java.util.Locale;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.entity.RBEntityReference;
import de.lichtflut.rb.webck.models.basic.DerivedModel;

/**
 * <p>
 *  Model for obtaining the locale specific label of an {@link RBEntityReference}.
 * </p>
 *
 * <p>
 * 	Created Dec 23, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBEntityReferenceLabelModel extends DerivedModel<String, RBEntityReference> {

	/**
	 * Constuctor.
	 * @param model The entity reference model.
	 */
	public RBEntityReferenceLabelModel(final IModel<RBEntityReference> model) {
		super(model);
	}

	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	protected String derive(RBEntityReference original) {
		final Locale locale = RequestCycle.get().getRequest().getLocale();
		return original.getLabel(locale);
	}
	
}
