/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.application;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.browsing.BrowsingHistory;


/**
 * <p>
 *  WebSession extension.
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
	
	@SpringBean
	private ServiceProvider provider;
	
	// ----------------------------------------------------

	public RBWebSession(final Request request) {
		super(request);
	}
	
	// ----------------------------------------------------
	
	public static RBWebSession get() {
		return (RBWebSession)Session.get();
	}
	
	// ----------------------------------------------------
	
	public BrowsingHistory getHistory() {
		return history;
	}
	
	public boolean isAuthenticated() {
		return provider != null && provider.getContext().getUser() != null;
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void detach() {
		super.detach();
		if (provider != null) {
			provider.onDetach();
		}
	}
	
}
