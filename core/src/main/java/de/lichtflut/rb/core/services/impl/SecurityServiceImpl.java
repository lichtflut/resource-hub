/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.arastreju.sge.IdentityManagement;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.eh.ArastrejuException;
import org.arastreju.sge.model.TimeMask;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.model.nodes.views.SNTimeSpec;
import org.arastreju.sge.security.Credential;
import org.arastreju.sge.security.LoginException;
import org.arastreju.sge.security.PasswordCredential;
import org.arastreju.sge.security.Role;
import org.arastreju.sge.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.infra.security.Crypt;
import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.security.LoginData;
import de.lichtflut.rb.core.services.AuthenticationService;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * <p>
 *  Implementation of {@link SecurityService}.
 * </p>
 *
 * <p>
 * 	Created Jan 2, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SecurityServiceImpl implements SecurityService, AuthenticationService {
	
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ssZ");
	
	private final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);
	
	private final ServiceProvider provider;
	
	// ----------------------------------------------------
	
	/**
	 * @param provider
	 */
	public SecurityServiceImpl(ServiceProvider provider) {
		this.provider = provider;
	}

	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public User createUser(String emailID, String username, String password) {
		final String crypted = Crypt.md5Hex(password);
		final Credential credential = new PasswordCredential(crypted);
		try {
			final User registered = identityManagement().register(emailID, credential);
			if (username != null) {
				identityManagement().registerAlternateID(registered, username);
			}
			// Move to RB Security Service
			SNOPS.assure(registered.getAssociatedResource(), Aras.HAS_EMAIL, new SNText(emailID), Aras.IDENT);
			return registered;
		} catch(ArastrejuException e) {
			return null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addRolesToUser(final User user, final Role... roles) {
		identityManagement().addUserToRoles(user, roles);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role registerRole(String name) {
		return identityManagement().registerRole(name);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public User login(LoginData loginData) throws LoginException {
		final String id = normalize(loginData.getId());
		final Credential credential = toCredential(loginData.getPassword());
		final User user = identityManagement().login(id, credential);
		// root and anonymous may not have an associated resource.
		setLastLogin(user);
		logger.info("User {} logged in. ", user);
		return user;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public User loginByToken(String token) {
		final String[] fields = token.split(":");
		if (fields.length != 3) {
			return null;
		}
		final User user = provider.getArastejuGate().getIdentityManagement().findUser(fields[0]);
		if (user != null && isValid(user, fields[0], fields[1], fields[2])) {
			logger.info("User {} logged in by token.", user);
			setLastLogin(user);
			return user;
		} else {
			logger.info("Login token is invalid: " + token);
			return null;
		}
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String createRememberMeToken(User user) {
		if (user.getAssociatedResource() == null) {
			logger.warn("User has no associated resurce " + user);
			return null;
		}
		final String email = user.getEmail();
		final SemanticNode credential = SNOPS.singleObject(user.getAssociatedResource(), Aras.HAS_CREDENTIAL);
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 30);
		final String raw = email + ":" + DATE_FORMAT.format(cal.getTime()); 
		return raw + ":" + Crypt.md5Hex(raw + credential.asValue().getStringValue());
	}

	// ----------------------------------------------------
	
	private void setLastLogin(final User user) {
		if (user.getAssociatedResource() != null) {
			SNOPS.assure(user.getAssociatedResource(), RB.HAS_LAST_LOGIN, new SNTimeSpec(new Date(), TimeMask.TIMESTAMP));
		}
	}
	
	private boolean isValid(User user, String email, String validUntil, String token) {
		try {
			final Date date = DATE_FORMAT.parse(validUntil);
			if (date.before(new Date())){
				logger.info("Login token has been expired: " + token);
				return false;
			}
		} catch (ParseException e) {
			logger.info("Login token could not be parsed: " + token);
			return false;
		}
		final SemanticNode credential = SNOPS.singleObject(user.getAssociatedResource(), Aras.HAS_CREDENTIAL);
		final String raw = email + ":" + validUntil; 
		final String crypted = Crypt.md5Hex(raw + credential.asValue().getStringValue());
		return crypted.equals(token);
	}
	
	private Credential toCredential(final String password) {
		if (password != null) {
			return new PasswordCredential(Crypt.md5Hex(password));
		} else {
			return new PasswordCredential(null);
		}
	}
	 
	private IdentityManagement identityManagement() {
		return provider.getArastejuGate().getIdentityManagement();
	}
	
	private String normalize(String name) {
		if (name == null) {
			return null;
		} else {
			return name.trim().toLowerCase();
		}
	}

}
