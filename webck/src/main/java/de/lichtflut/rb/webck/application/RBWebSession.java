/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.application;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.arastreju.sge.eh.ArastrejuRuntimeException;
import org.arastreju.sge.security.User;

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
		try {
			if (user != null) {
				// trigger fetching of associations to check if object is still alive
				user.getName();
				return true;
			}
		} catch (ArastrejuRuntimeException e) {
			user = null;
			invalidate();
		}
		return false;
	}
	
	/**
	 * @return this session's user.
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * @param user The logged in user.
	 */
	public void setUser(final User user) {
		this.user = user;
	}
	
}
