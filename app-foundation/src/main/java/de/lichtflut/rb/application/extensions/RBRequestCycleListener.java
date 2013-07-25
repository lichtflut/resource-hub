/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.application.extensions;

import de.lichtflut.rb.application.base.errorpages.DefaultErrorPage;
import de.lichtflut.rb.application.base.errorpages.InternalErrorPage;
import de.lichtflut.rb.webck.common.RBWebSession;
import org.apache.wicket.Page;
import org.apache.wicket.core.request.handler.IPageProvider;
import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.protocol.http.PageExpiredException;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;

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

    @Override
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
