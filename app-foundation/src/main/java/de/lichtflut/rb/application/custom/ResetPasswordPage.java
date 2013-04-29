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
package de.lichtflut.rb.application.custom;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;

import de.lichtflut.rb.application.RBApplication;
import de.lichtflut.rb.application.pages.AbstractBasePage;
import de.lichtflut.rb.webck.components.settings.ResetPasswordPanel;

/**
 * <p>
 *  Allows a user to reset his/her password.
 * </p>
 *
 * <p>
 * 	Created Jan 17, 2012
 * </p>
 * @author Ravi Knox
 */
public class ResetPasswordPage extends AbstractBasePage {

	public ResetPasswordPage(){
		this.add(new ResetPasswordPanel("resetPassword"));
		this.add(new BookmarkablePageLink<Void>("backLink", RBApplication.get().getLoginPage()));
	}

	// ------------------------------------------------------

	@Override
	protected boolean needsAuthentication() {
		return false;
	}
}
