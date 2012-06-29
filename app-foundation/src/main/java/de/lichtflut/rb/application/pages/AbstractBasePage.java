/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.pages;

import de.lichtflut.rb.application.RBApplication;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.models.CurrentUserModel;
import org.apache.commons.lang3.Validate;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.cookies.CookieUtils;
import org.odlabs.wiquery.ui.dialog.Dialog;

import java.util.Locale;

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
	public AbstractBasePage(PageParameters parameters) {
		super(parameters);
	}

	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected void onConfigure() {
		securityCheck();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(new Link("logout") {
			@Override
			public void onClick() {
				WebSession.get().invalidate();
				new CookieUtils().remove(AuthModule.COOKIE_REMEMBER_ME);
				new CookieUtils().remove(AuthModule.COOKIE_SESSION_AUTH);
				setResponsePage(RBApplication.get().getLoginPage());
			}
		}.add(ConditionalBehavior.visibleIf(CurrentUserModel.isLoggedIn())));

		addLanguageLinks();
		
		add(emptyDialog());
	}
	
	// ----------------------------------------------------
	
	@SuppressWarnings("rawtypes")
	protected void addLanguageLinks() {
		add(new Link("switchToEnglish") {
			@Override
			public void onClick() {
				WebSession.get().setLocale(Locale.ENGLISH);
			}
		});

		add(new Link("switchToGerman") {
			@Override
			public void onClick() {
				WebSession.get().setLocale(Locale.GERMAN);
			}
		});
	}
	
	// ----------------------------------------------------

	protected boolean needsAuthentication() {
		return true;
	}
	
	protected boolean isAuthorized(IModel<RBUser> user) {
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
	public void openDialog(Dialog dialog) {
		setDialog(dialog);
		final AjaxRequestTarget target = AjaxRequestTarget.get();
		if (target != null) {
			dialog.open(target);
			target.add(dialog);
		} else {
			dialog.open();
		}
	}
	
	@Override
	public void closeDialog(Dialog dialog) {
		setDialog(emptyDialog());
		final AjaxRequestTarget target = AjaxRequestTarget.get();
		if (target != null) {
			dialog.close(target);
			target.add(dialog);
		} else {
			dialog.close();
		}
	}
	
	protected void setDialog(Component dialog) {
		Validate.isTrue(MODALDIALOG.equals(dialog.getId()));
		replace(dialog);
	}
	
	private Component emptyDialog() {
		return new WebMarkupContainer(MODALDIALOG).setOutputMarkupPlaceholderTag(true).setVisible(false);
	}

}
