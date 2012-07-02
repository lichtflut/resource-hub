package de.lichtflut.rb.webck.common;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;

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

    private final BrowsingHistory history = new BrowsingHistory();

    @SpringBean
    private ServiceContext context;

    // ----------------------------------------------------

    public RBWebSession(final Request request) {
        super(request);
    }

    // ----------------------------------------------------

    public static RBWebSession get() {
        return (RBWebSession) Session.get();
    }

    // ----------------------------------------------------

    public BrowsingHistory getHistory() {
        return history;
    }

    public boolean isAuthenticated() {
        return context != null && context.getUser() != null;
    }

    public void onLogout() {
        context = null;
    }

}
