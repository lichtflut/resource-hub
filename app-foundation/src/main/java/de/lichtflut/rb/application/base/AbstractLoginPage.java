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

import de.lichtflut.rb.application.RBApplication;
import de.lichtflut.rb.RBPermission;
import de.lichtflut.rb.application.extensions.ServiceContextInitializer;
import de.lichtflut.rb.application.pages.AbstractBasePage;
import de.lichtflut.rb.core.config.RBConfig;
import de.lichtflut.rb.core.eh.LoginException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.AuthenticationService;
import de.lichtflut.rb.core.security.LoginData;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.webck.common.CookieAccess;
import de.lichtflut.rb.webck.common.RBWebSession;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Set;

/**
 * <p>
 *  Abstract base for login pages.
 * </p>
 *
 * <p>
 *  Created Nov 9, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class AbstractLoginPage extends AbstractBasePage {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);

    @SpringBean
    private AuthModule authModule;

    @SpringBean
    private AuthenticationService authService;

    private String trustedAuthHeader;

    // ----------------------------------------------------

    public AbstractLoginPage() {
        trustedAuthHeader = System.getProperty(RBConfig.TRUSTED_AUTH_HEADER);
        checkCookies();
        redirectIfAlreadyLoggedIn();
    }

    // ----------------------------------------------------

    @Override
    protected void onConfigure() {
        super.onConfigure();
        if (!checkCookies()) {
            checkTrustedHeader();
        }
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
    protected void tryLogin(final LoginData loginData) {
        try {
            final RBUser user = authService.login(loginData);
            checkAndInitialize(user);
            if (loginData.getStayLoggedIn()) {
                final String token = authService.createRememberMeToken(user, loginData);
                CookieAccess.getInstance().setRememberMeToken(token);
                LOGGER.info("Added 'remember-me' cookie for {}", user.getName());
            }
        } catch (LoginException e) {
            error(getString("error.login.failed"));
        }
    }

    protected boolean checkCookies() {
        final String cookie = CookieAccess.getInstance().getRememberMeToken();
        if (cookie != null) {
            final RBUser user = authService.loginByToken(cookie);
            if (user != null) {
                return checkAndInitialize(user);
            } else {
                CookieAccess.getInstance().removeAuthCookies();
            }
        }
        return false;
    }

    protected void checkTrustedHeader() {
        if (trustedAuthHeader == null) {
            return;
        }
        WebRequest request = (WebRequest) RequestCycle.get().getRequest();
        HttpServletRequest httpRequest = (HttpServletRequest) request.getContainerRequest();
        LOGGER.debug("Headers: " + Collections.list(httpRequest.getHeaderNames()));
        try {
            if (httpRequest.getHeader(trustedAuthHeader) != null) {
                String username = httpRequest.getHeader(trustedAuthHeader);
                RBUser user = authService.login(username);
                checkAndInitialize(user);
            }
        } catch (LoginException e) {
            error(getString("error.login.failed"));
        }
    }

    protected void redirectIfAlreadyLoggedIn() {
        if (isAuthenticated()) {
            continueToOriginalDestination();
            // this line is only reached when there is no original destination
            throw new RestartResponseException(RBApplication.get().getHomePage());
        }
    }

    // ----------------------------------------------------

    private boolean checkAndInitialize(RBUser user) {
        final Set<String> permissions = authModule.getUserManagement().getUserPermissions(user, user.getDomesticDomain());
        if (permissions.contains(RBPermission.LOGIN.name())) {
            LOGGER.info("User {} logged in.", user.getName());
            RBWebSession.get().replaceSession();
            initializeUserSession(user);
            return true;
        } else {
            LOGGER.info("Login aborted - User {} is lack of permission: {}", user.getName(), RBPermission.LOGIN.name());
            error(getString("error.login.activation"));
            return false;
        }
    }

    /**
     * Write the Token in the session. So it can be rewritten as cookie from servlet filter.
     */
    private void initializeUserSession(RBUser user) {
        String token = authService.createSessionToken(user);
        new ServiceContextInitializer().init(user, user.getDomesticDomain());

        WebRequest request = (WebRequest) RequestCycle.get().getRequest();
        HttpServletRequest httpRequest = (HttpServletRequest) request.getContainerRequest();
        httpRequest.getSession().setAttribute(AuthModule.COOKIE_SESSION_AUTH, token);
    }

}
