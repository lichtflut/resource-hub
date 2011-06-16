/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

import org.arastreju.sge.model.nodes.ResourceNode;

/**
 * <p>
 *  TODO: [DESCRIPTION].
 * </p>
 *
 * <p>
 * 	Created Jan 5, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface Permission {

	/**
	 * TODO: DISCRIPTION.
	 * @return {@link ResourceNode}
	 */
	ResourceNode getAssociatedResource();

	/**
	 * Returns name of permission.
	 * @return String
	 */
	String getName();

}
