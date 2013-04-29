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
package de.lichtflut.rb.application;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.security.AuthModule;

/**
 * <p>
 *  Servlet filter. Currently just for ensuring UTF-8 encoding.
 * </p>
 *
 * <p>
 * 	Created Dec 14, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBServletFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(RBServletFilter.class);

	// ----------------------------------------------------

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		checkSessionCookie(httpServletRequest, httpServletResponse);

		chain.doFilter(request, response);

	}

	// ----------------------------------------------------

	private void checkSessionCookie(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
		String existingToken = getTokenFromRequest(httpServletRequest);
		if (existingToken != null) {
			LOGGER.trace("Incoming request with existingToken {}.", existingToken);
		} else {
			LOGGER.trace("Incoming request from unauthenticated user.");
		}

		String newToken = getTokenInSession(httpServletRequest);
		if (newToken != null && !newToken.equals(existingToken)) {
			// a token has been deposited in session
			final Cookie cookie = new Cookie(AuthModule.COOKIE_SESSION_AUTH, newToken);
			cookie.setMaxAge(3600);
			cookie.setPath("/");
			cookie.setMaxAge(-1);
			cookie.setSecure(false);
			httpServletResponse.addCookie(cookie);
			LOGGER.debug("Setting session cookie {}.", newToken);
		}
	}

	private String getTokenFromRequest(final HttpServletRequest httpServletRequest) {
		Cookie[] cookies = httpServletRequest.getCookies();
		if (cookies == null || cookies.length == 0) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (AuthModule.COOKIE_SESSION_AUTH.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}

	private String getTokenInSession(final HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession(false);
		if (session != null && session.getAttribute(AuthModule.COOKIE_SESSION_AUTH) != null) {
			return session.getAttribute(AuthModule.COOKIE_SESSION_AUTH).toString();
		}
		return null;
	}

	// ----------------------------------------------------

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
