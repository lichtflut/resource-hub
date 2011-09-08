/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.builders;

//import javax.enterprise.context.SessionScoped;
//import javax.enterprise.inject.Produces;
//import javax.inject.Inject;

import de.lichtflut.rb.core.spi.IRBServiceProvider;
import de.lichtflut.rb.mock.MockRBServiceProvider;

/**
 * <p>
 * This builder is a CDI affected "session-scope" builder.
 * The own time to live is the same as the user's session- one.
 * It builds instance of:
 * <ul>
 * <li>{@link IRBServiceProvider}</li>
 * </ul>
 * </p>
 *
 * <p>
 * 	Created May 09, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
//@SessionScoped
public final class SessionBuilder {

	//Members
	private IRBServiceProvider serviceProvider;

	// -----------------------------------------------------
	//@Inject
	/**
	 *
	 */
	public SessionBuilder(){
		init();
	}

	// -----------------------------------------------------

	/**
	 *
	 */
	private void init(){
		this.serviceProvider = new MockRBServiceProvider();
	}

	// -----------------------------------------------------
	//@Produces
	/**
	 *@return /
	 */
	public IRBServiceProvider getServiceProvider() {
		return this.serviceProvider;
	}

}
