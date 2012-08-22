package de.lichtflut.rb.webck.common;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.services.ServiceContext;
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

	private final Logger LOGGER = LoggerFactory.getLogger(RBWebSession.class);

	private final BrowsingHistory history = new BrowsingHistory();

	@SpringBean
	private ServiceContext context;

	private String token;

	// ----------------------------------------------------

	public RBWebSession(final Request request) {
		super(request);
	}

	// ----------------------------------------------------

	public static RBWebSession get() {
		return (RBWebSession) Session.get();
	}

	// ----------------------------------------------------

	public void setToken(final String token) {
		this.token = token;
	}

	// ----------------------------------------------------

	public BrowsingHistory getHistory() {
		return history;
	}

	public boolean isAuthenticated() {
		boolean authenticated = context != null && context.getUser() != null;
		if (authenticated) {
			String currentToken = CookieAccess.getInstance().getSessionToken();
			if (currentToken == null)  {
				LOGGER.warn("User is authenticated but has lost session token! Will set it again. " + token);
				CookieAccess.getInstance().setSessionToken(token);
			}
		}
		return authenticated;
	}

	public void onLogout() {
		context = null;
	}

}
