/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

/**
 * <p>
 *  Builder for labels based on a resource's attributes.
 * </p>
 *
 * <p>
 * 	Created Aug 26, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface LabelBuilder {

	String build(INewRBEntity entity);
	
}
