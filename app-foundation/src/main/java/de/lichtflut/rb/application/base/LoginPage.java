/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.base;

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
import de.lichtflut.rb.webck.common.CookieAccess;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.components.login.LoginPanel;
import de.lichtflut.rb.webck.models.infra.VersionInfoModel;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.security.LoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * <p>
 *  Standard login page for RB applications.
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
	public LoginPage() {

		checkCookies();

		redirectIfAlreadyLoggedIn();

		add(new LoginPanel("loginPanel") {
            @Override
            public void onLogin(LoginData loginData) {
                tryLogin(loginData);
            }
        });

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

	@Override
	protected void onConfigure() {
		super.onConfigure();
		redirectIfAlreadyLoggedIn();
	}

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
                CookieAccess.getInstance().setRememberMeToken(token);
				logger.info("Added 'remember-me' cookie for {}", user.getName());
			}
		} catch (LoginException e) {
			error(getString("error.login.failed"));
		}
	}
	
	private void checkCookies() {
        final String cookie = CookieAccess.getInstance().getRememberMeToken();
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
        new ServiceContextInitializer().init(user, user.getDomesticDomain());

        WebRequest request = (WebRequest) RequestCycle.get().getRequest();
        HttpServletRequest httpRequest = (HttpServletRequest) request.getContainerRequest();
        httpRequest.getSession().setAttribute(AuthModule.COOKIE_SESSION_AUTH, token);
	}

}
