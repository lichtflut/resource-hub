/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Model for obtaining the URI of an {@link ResourceID}.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
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
		return model.getObject().getQualifiedName().toURI();
	}

	
}
