/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.spi;

import de.lichtflut.rb.core.spi.impl.DefaultRBServiceProvider;

/**
 * Factory class to build several RBServiceProvider-instances through static members
 * 
 * Created: Apr 28, 2011
 *
 * @author Nils Bleisch 
 */
public class RBServiceProviderFactory {

	/**
	 * @return {@link DefaultRBServiceProvider} with Arastreju's root-context inside
	 */
	public static RBServiceProvider getDefaultServiceProvider(){
		return new DefaultRBServiceProvider();
	}
}
