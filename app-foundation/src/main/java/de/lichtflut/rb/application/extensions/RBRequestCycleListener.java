/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.extensions;

import de.lichtflut.rb.application.base.errorpages.DefaultErrorPage;
import de.lichtflut.rb.application.base.errorpages.InternalErrorPage;
import de.lichtflut.rb.webck.common.RBWebSession;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.PageExpiredException;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.IPageProvider;
import org.apache.wicket.request.handler.PageProvider;
import org.apache.wicket.request.handler.RenderPageRequestHandler;

/**
 * <p>
 *  Glasnost specific request cycle listener.
 * </p>
 *
 * <p>
 * 	Created Dec 2, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBRequestCycleListener extends AbstractRequestCycleListener {

    /**
	 * {@inheritDoc}
	 */
	public IRequestHandler onException(final RequestCycle cycle, Exception ex) {
		if (ex instanceof PageExpiredException) {
			return getTargetTo(new InternalErrorPage(ex));
		}
		if (WebSession.exists() && RBWebSession.get().isAuthenticated()) {
			return getTargetTo(new InternalErrorPage(ex));
		} else {
			return getTargetTo(new DefaultErrorPage(ex));
		}
	}

    // ----------------------------------------------------

    protected IRequestHandler getTargetTo(Page page) {
        final IPageProvider pageProvider = new PageProvider(page);
        return new RenderPageRequestHandler(pageProvider);
    }
	
}
