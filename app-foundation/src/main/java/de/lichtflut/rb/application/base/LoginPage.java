/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.base;

import java.util.Set;

import org.apache.wicket.Application;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.cookies.CookieUtils;
import org.arastreju.sge.security.LoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.application.RBApplication;
import de.lichtflut.rb.application.common.RBPermission;
import de.lichtflut.rb.application.custom.RequestAccountPage;
import de.lichtflut.rb.application.custom.ResetPasswordPage;
import de.lichtflut.rb.application.extensions.ServiceContextInitializer;
import de.lichtflut.rb.application.pages.AbstractBasePage;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.AuthenticationService;
import de.lichtflut.rb.core.security.LoginData;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.components.fields.FieldLabel;
import de.lichtflut.rb.webck.models.infra.VersionInfoModel;

/**
 * <p>
 * Login page.
 * </p>
 * 
 * <p>
 * Created Dec 8, 2011
 * </p>
 * 
 * @author Oliver Tigges
 */
public class LoginPage extends AbstractBasePage {

	private final Logger logger = LoggerFactory.getLogger(LoginPage.class);

	@SpringBean
	private AuthModule authModule;

	@SpringBean
	private AuthenticationService authService;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 */
	public LoginPage(final PageParameters params) {

		checkCookies();

		redirectIfAlreadyLoggedIn();

		add(createLoginForm());

		add(new Link<String>("resetEmail") {
			@Override
			public void onClick() {
				setResponsePage(ResetPasswordPage.class);
			}
		});

		add(new Link<String>("requestAccount") {
			@Override
			public void onClick() {
				setResponsePage(RequestAccountPage.class);
			}
		});

		addVersionInfo();
	}

	// ----------------------------------------------------
	
	protected Form<LoginData> createLoginForm() {
		final LoginData loginData = new LoginData();
		final Form<LoginData> form = new Form<LoginData>("form", new CompoundPropertyModel<LoginData>(loginData));

		form.add(new FeedbackPanel("feedback"));

		final TextField<String> idField = new TextField<String>("id");
		idField.setRequired(true);
		idField.setLabel(new ResourceModel("label.id"));
		form.add(idField, new FieldLabel(idField));

		final PasswordTextField passwordField = new PasswordTextField("password");
		passwordField.setRequired(true);
		passwordField.setLabel(new ResourceModel("label.password"));
		form.add(passwordField, new FieldLabel(passwordField));

		form.add(new CheckBox("stayLoggedIn"));

		final Button button = new Button("login") {
			@Override
			public void onSubmit() {
				tryLogin(loginData);
			}
		};
		form.add(button);
		form.setDefaultButton(button);
		return form;
	}
	
	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onConfigure() {
		super.onConfigure();
		redirectIfAlreadyLoggedIn();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean needsAuthentication() {
		return false;
	}
	
	// ----------------------------------------------------

	/**
	 * Try to log the user in.
	 * 
	 * @param loginData The login data.
	 */
	private void tryLogin(final LoginData loginData) {
		try {
			final RBUser user = authService.login(loginData);
			final Set<String> permissions = authModule.getUserManagement().getUserPermissions(user, user.getDomesticDomain());
			if (!permissions.contains(RBPermission.LOGIN.name())) {
				logger.info("Login aborted - User {} is lack of permission: {}", user.getName(), RBPermission.LOGIN.name());
				error(getString("error.login.activation"));
			} else {
				logger.info("User {} logged in.", user.getName());
				RBWebSession.get().replaceSession();
				initializeUserSession(user);
			}
			if (loginData.getStayLoggedIn()) {
				final String token = authService.createRememberMeToken(user, loginData);
				cookieUtils().save(AuthModule.COOKIE_REMEMBER_ME, token);
				logger.info("Added 'remember-me' cookie for {}", user.getName());
			}
		} catch (LoginException e) {
			error(getString("error.login.failed"));
		}
	}
	
	private void checkCookies() {
		final String cookie = new CookieUtils().load(AuthModule.COOKIE_REMEMBER_ME);
		if (cookie != null) {
			final RBUser user = authService.loginByToken(cookie);
			if (user != null) {
				final Set<String> permissions = authModule.getUserManagement().getUserPermissions(user, user.getDomesticDomain());
				if (permissions.contains(RBPermission.LOGIN.name())) {
					initializeUserSession(user);
				} else {
					logger.info("Login aborted - User {} is lack of permission: {}", user.getName(), RBPermission.LOGIN.name());
					error(getString("error.login.activation"));
				}
			}
		}
	}
	
	// ----------------------------------------------------

	private void addVersionInfo() {
		final VersionInfoModel model = new VersionInfoModel();
		add(new Label("version", new PropertyModel<String>(model, "version")));
		add(new Label("build", new PropertyModel<String>(model, "buildTimestamp")));
	}

	private void redirectIfAlreadyLoggedIn() {
		if (isAuthenticated()) {
			if (!continueToOriginalDestination()) {
				throw new RestartResponseException(RBApplication.get().getHomePage());
			}
		}
	}

	private void initializeUserSession(RBUser user) {
        String token = authService.createSessionToken(user);
        new ServiceContextInitializer().init(user, user.getDomesticDomain(), token);
		cookieUtils().save(AuthModule.COOKIE_SESSION_AUTH, token);
	}
	
	private CookieUtils cookieUtils() {
		//boolean prodMode = RuntimeConfigurationType.DEPLOYMENT.equals(Application.get().getConfigurationType());
		CookieUtils cu = new CookieUtils();
		//cu.getSettings().setSecure(prodMode);
		return cu;
	}

}
