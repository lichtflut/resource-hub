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
package de.lichtflut.rb.webck.common;

import java.util.Locale;

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
		trySettingLanguageFromCookie();
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

	// ------------------------------------------------------

	private void trySettingLanguageFromCookie() {
		String localeString = new CookieAccess().getLocaleCookie();
		if(null != localeString){
			String localeInfo[] = breakUp(localeString);
			setLocale(new Locale(localeInfo[0], localeInfo[1], localeInfo[2]));
		}
	}

	private String[] breakUp(final String localeString) {
		String emptyString = "";
		String[] info = {emptyString, emptyString, emptyString};
		int count = 0;
		for (String string : localeString.split("_")) {
			info[count] = string;
			count++;
		}
		return info;
	}

}
