/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.application;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;


/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Dec 2, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBWebSession extends WebSession {
	
	private final BrowsingHistory history = new BrowsingHistory();
	
	// ----------------------------------------------------

	public RBWebSession(final Request request) {
		super(request);
	}
	
	// ----------------------------------------------------
	
	public BrowsingHistory getHistory() {
		return history;
	}
	
	public static RBWebSession get() {
		return (RBWebSession)Session.get();
	}

}
