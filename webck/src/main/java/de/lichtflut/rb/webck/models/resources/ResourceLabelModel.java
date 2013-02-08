/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.resources;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Model for obtaining the locale specific label of an {@link ResourceID}.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourceLabelModel extends DerivedModel<String, ResourceID> {

	/**
	 * Constructor.
	 * @param model The resource model.
	 */
	@SuppressWarnings("unchecked")
	public ResourceLabelModel(final IModel<? extends ResourceID> model) {
		super((IModel<ResourceID>) model);
	}
	
	/**
	 * Constructor.
	 * @param resource The resource.
	 */
	public ResourceLabelModel(ResourceID resource) {
		super(resource);
	}

	// ----------------------------------------------------
	
	@Override
	public String derive(ResourceID source) {
		return ResourceLabelBuilder.getInstance().getLabel(source, RequestCycle.get().getRequest().getLocale());
	}

	
}
