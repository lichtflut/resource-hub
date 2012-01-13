/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.resources;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.webck.models.basic.DerivedModel;

/**
 * <p>
 * 	Model for obtaining the URI of an {@link ResourceID}.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourceUriModel extends DerivedModel<String, ResourceID> {

	/**
	 * Constuctor.
	 * @param fiel The field model.
	 */
	@SuppressWarnings("unchecked")
	public ResourceUriModel(final IModel<? extends ResourceID> model) {
		super((IModel<ResourceID>) model);
	}

	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String derive(ResourceID source) {
		return source.getQualifiedName().toURI();
	}
	
}
