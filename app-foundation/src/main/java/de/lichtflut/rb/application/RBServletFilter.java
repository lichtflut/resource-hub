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
		String token = getSessionToken(httpServletRequest);

		if (token != null) {
			LOGGER.debug("Incoming request with token {}.", token);
		} else {
			LOGGER.debug("Incoming request from unauthenticated user.");
		}

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}

	// ----------------------------------------------------

	private String getSessionToken(final HttpServletRequest httpServletRequest) {
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

	// ----------------------------------------------------

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
