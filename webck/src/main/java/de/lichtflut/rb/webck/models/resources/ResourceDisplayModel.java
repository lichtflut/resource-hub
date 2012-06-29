/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.resources;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;

import java.util.Locale;

/**
 * <p>
 *  Model providing a cachable display String for a {@link ResourceID}. 
 * </p>
 *
 * <p>
 * 	Created Feb 28, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public final class ResourceDisplayModel implements IModel<String> {

	private final IModel<ResourceID> resourceModel;
	
	private String value;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param resource The original resource model.
	 */
	public ResourceDisplayModel(final IModel<ResourceID> resource) {
		this.resourceModel = resource;
	}
	
	// ----------------------------------------------------

	public String getObject() {
		final ResourceID orig = resourceModel.getObject();
		if (orig == null) {
			value = null;
			return "";
		} else if (value == null) {
			value = ResourceLabelBuilder.getInstance().getLabel(orig, Locale.getDefault());
		} 
		return value;
	}

	public void setObject(final String object) {
		value = object;
	}
	
	public void detach() {
		resourceModel.detach();
	}


	
}