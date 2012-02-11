/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.application;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.security.User;

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
	
	private User user;
	
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
		return getUser() != null;
	}
	
	/**
	 * @return this session's user.
	 */
	public User getUser() {
		if (user != null) {
			return user;
		} else if (provider != null) {
			retriveUserFromContext();
		}
		return user;
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void detach() {
		super.detach();
		user = null;
	}
	
	// ----------------------------------------------------
	
	private void retriveUserFromContext() {
		user = provider.getContext().getUser();
		if (user == null) {
			invalidate();
		}
	}
	
}
