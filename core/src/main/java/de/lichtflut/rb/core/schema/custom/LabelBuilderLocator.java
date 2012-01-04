/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.custom;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.common.EntityLabelBuilder;

/**
 * <p>
 *  Locator for {@link EntityLabelBuilder}s.
 * </p>
 *
 * <p>
 * 	Created Sep 2, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface LabelBuilderLocator {

	/**
	 * Locates the label builder for the given type.
	 * @param type The resource type.
	 * @return The corresponding label builder.
	 */
	EntityLabelBuilder forType(ResourceID type);

}
