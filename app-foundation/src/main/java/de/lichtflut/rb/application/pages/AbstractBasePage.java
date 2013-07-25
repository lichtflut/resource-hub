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
package de.lichtflut.rb.application.pages;

import de.lichtflut.rb.application.RBApplication;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.webck.common.CookieAccess;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.RBDialog;
import de.lichtflut.rb.webck.models.CurrentUserModel;
import org.apache.commons.lang3.Validate;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Locale;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;
import static de.lichtflut.rb.webck.models.CurrentUserModel.isLoggedIn;

/**
 * <p>
 * Abstract base for frontend and admin pages.
 * </p>
 * 
 * <p>
 * Created Dec 8, 2011
 * </p>
 * 
 * @author Oliver Tigges
 */
public class AbstractBasePage extends WebPage implements DialogHoster {

	private static final String MODALDIALOG = "modaldialog";

	// ----------------------------------------------------

	/**
	 * Default constructor.
	 */
	public AbstractBasePage() {
		this(null);
	}

	/**
	 * @param parameters
	 */
	public AbstractBasePage(final PageParameters parameters) {
		super(parameters);
	}

	// ----------------------------------------------------

	@Override
	protected void onConfigure() {
		securityCheck();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Link<Void>("logout") {
			@Override
			public void onClick() {
				WebSession.get().invalidate();
				CookieAccess.getInstance().removeAuthCookies();
				setResponsePage(RBApplication.get().getLoginPage());
			}
		}.add(visibleIf(isLoggedIn())));

		add(new Link<Void>("login") {
			@Override
			public void onClick() {
				WebSession.get().invalidate();
				CookieAccess.getInstance().removeAuthCookies();
				setResponsePage(RBApplication.get().getLoginPage());
			}
		}.add(visibleIf(not(isLoggedIn()))));

		addLanguageLinks();

		add(emptyDialog());
	}

	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);
		RBApplication.get().getLayout().addLayout(response);
		RBApplication.get().getStyle().addStyle(response);
        RBApplication.get().getScriptSupport().addScripts(response);
	}

	// ----------------------------------------------------

	protected void addLanguageLinks() {
		add(new Link<Void>("switchToEnglish") {
			@Override
			public void onClick() {
				WebSession.get().setLocale(Locale.ENGLISH);
				new CookieAccess().setLocale(Locale.ENGLISH);
			}
		});

		add(new Link<Void>("switchToGerman") {
			@Override
			public void onClick() {
				WebSession.get().setLocale(Locale.GERMAN);
				new CookieAccess().setLocale(Locale.GERMAN);
			}
		});
	}

	// ----------------------------------------------------

	protected boolean needsAuthentication() {
		return !RBApplication.get().supportsUnauthenticatedAccess();
	}

	protected boolean isAuthorized(final IModel<RBUser> user) {
		return true;
	}

	protected final boolean isAuthenticated() {
		return WebSession.exists() && RBWebSession.get().isAuthenticated();
	}

	// ----------------------------------------------------

	private void securityCheck() {
		if (needsAuthentication() && !isAuthenticated()) {
			throw new RestartResponseAtInterceptPageException(RBApplication.get().getLoginPage());
		}
		if (!isAuthorized(new CurrentUserModel())) {
			throw new RuntimeException("Not authorized to access this page.");
		}
	}

	// -- DIALOG ------------------------------------------

	/**
	 * @return the ID to assign to dialog.
	 */
	@Override
	public String getDialogID() {
		return MODALDIALOG;
	}

	@Override
	public void openDialog(final RBDialog dialog) {
		setDialog(dialog);
        dialog.open();
	}

	@Override
	public void closeDialog(final RBDialog dialog) {
		setDialog(emptyDialog());
		dialog.close();
	}

	protected void setDialog(final Component dialog) {
		Validate.isTrue(MODALDIALOG.equals(dialog.getId()));
		replace(dialog);
	}

	private Component emptyDialog() {
		return new WebMarkupContainer(MODALDIALOG).setOutputMarkupPlaceholderTag(true).setVisible(false);
	}

}
