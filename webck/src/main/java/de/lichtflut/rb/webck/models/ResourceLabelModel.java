/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.entity.RBEntityReference;

/**
 * <p>
 *  Model for obtaining the locale specific label of an {@link ResourceID}.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourceLabelModel extends AbstractReadOnlyModel<String> {

	private final IModel<? extends ResourceID> model;
	
	// ----------------------------------------------------
	
	/**
	 * Constuctor.
	 * @param fiel The field model.
	 */
	public ResourceLabelModel(final IModel<? extends ResourceID> model) {
		this.model = model;
	}

	// ----------------------------------------------------
	
	@Override
	public String getObject() {
		final ResourceID id = model.getObject();
		if (id instanceof RBEntityReference) {
			return ((RBEntityReference)id ).toString();
		} else {
			return ResourceLabelBuilder.getInstance().getLabel(model.getObject(), 
				RequestCycle.get().getRequest().getLocale());
		}
	}

	
}
