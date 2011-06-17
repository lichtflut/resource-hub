/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.genericresource;

import de.lichtflut.rb.core.spi.RBServiceProvider;

/**
 *  <p>
 *  Basic interface of the generic-resource components-family.
 *  Specifies some methods which have to be implemented
 * </p>
 *
 * <p>
 * 	Created May 25, 2011
 * </p>
 *
 * @author Nils Bleisch
 *
 */
public interface GenericResourceComponent {

	/**
	 *
	 *
	 */
	public enum ViewMode{
		READ,
		WRITE,
		READ_WRITE
	}


	/**
	 * Setting up the ViewMode in self returning idiom style.
	 * @param view / the given view
	 * @return /
	 */
	GenericResourceComponent setViewMode(ViewMode view);


	/**
	 *
	 * @return An instance of {@link RBServiceProvider}
	 */
	RBServiceProvider getServiceProvider();

}
