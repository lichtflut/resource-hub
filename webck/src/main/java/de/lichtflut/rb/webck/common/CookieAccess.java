package de.lichtflut.rb.webck.common;

import java.util.Locale;

import javax.servlet.http.Cookie;

import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;

import de.lichtflut.rb.core.security.AuthModule;

/**
 * <p>
 *  Simple alternative to wickets cookie utils.
 * </p>
 *
 * <p>
 * Created 13.07.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class CookieAccess {

	private static final CookieAccess instance = new CookieAccess();

	private static final String LOCALE_COOKIE = "locale";

	// ----------------------------------------------------

	public static CookieAccess getInstance() {
		return instance;
	}

	// -- COMMON ------------------------------------------

	public String load(final String key) {
		Cookie cookie = request().getCookie(key);
		if (cookie == null) {
			return null;
		} else {
			return cookie.getValue();
		}
	}

	public void add(final String key, final String value) {
		response().addCookie(new Cookie(key, value));
	}

	public void remove(final String key) {
		response().clearCookie(new Cookie(key, ""));
	}

	// -- AUTH SPECIFIC -----------------------------------

	public String getSessionToken() {
		return load(AuthModule.COOKIE_SESSION_AUTH);
	}

	public String getRememberMeToken() {
		return load(AuthModule.COOKIE_REMEMBER_ME);
	}

	public void setSessionToken(final String token) {
		final Cookie cookie = new Cookie(AuthModule.COOKIE_SESSION_AUTH, token);
		cookie.setMaxAge(3600);
		response().addCookie(cookie);
	}

	public void setRememberMeToken(final String token) {
		final Cookie cookie = new Cookie(AuthModule.COOKIE_REMEMBER_ME, token);
		cookie.setMaxAge(3600 * 24 * 30);
		response().addCookie(cookie);
	}

	public void removeAuthCookies() {
		remove(AuthModule.COOKIE_REMEMBER_ME);
		remove(AuthModule.COOKIE_SESSION_AUTH);
	}

	// --------- LANGUAGE SPECIFIC --------------------------

	public String getLocaleCookie(){
		return load(LOCALE_COOKIE);
	}

	public void setLocale(final Locale locale) {
		Cookie cookie = new Cookie(LOCALE_COOKIE, locale.getLanguage());
		cookie.setMaxAge(3600 * 24 * 30 * 365);
		response().addCookie(cookie);
	}

	// ----------------------------------------------------

	private WebResponse response() {
		return (WebResponse) RequestCycle.get().getResponse();
	}

	private WebRequest request() {
		return (WebRequest) RequestCycle.get().getRequest();
	}

}
