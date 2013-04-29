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
package de.lichtflut.rb.application.base;

import de.lichtflut.rb.webck.common.CookieAccess;
import org.apache.wicket.protocol.http.WebSession;

import de.lichtflut.rb.application.RBApplication;
import de.lichtflut.rb.application.pages.AbstractBasePage;
import de.lichtflut.rb.webck.common.RBWebSession;

/**
 * <p>
 *  Logout page.
 * </p>
 *
 * <p>
 * 	Created Apr 13, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class LogoutPage extends AbstractBasePage {

	/**
	 * Constructor.
	 */
	public LogoutPage() {
        CookieAccess.getInstance().removeAuthCookies();
		if (WebSession.exists()) {
			RBWebSession.get().onLogout();
			WebSession.get().invalidate();
		}

		setResponsePage(RBApplication.get().getLoginPage());
	}

}
