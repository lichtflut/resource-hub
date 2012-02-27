/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.IdentityManagement;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.eh.ArastrejuRuntimeException;
import org.arastreju.sge.model.TimeMask;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNTimeSpec;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryResult;
import org.arastreju.sge.security.Credential;
import org.arastreju.sge.security.Domain;
import org.arastreju.sge.security.LoginException;
import org.arastreju.sge.security.PasswordCredential;
import org.arastreju.sge.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.actors.threadpool.Arrays;
import de.lichtflut.infra.security.Crypt;
import de.lichtflut.rb.core.RBSystem;
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
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ssZ");
	
	private final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	
	private final AbstractServiceProvider provider;
	
	// ----------------------------------------------------
	
	/**
	 * @param provider
	 */
	public AuthenticationServiceImpl(AbstractServiceProvider provider) {
		this.provider = provider;
	}

	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public User login(LoginData loginData) throws LoginException {
		final String id = normalize(loginData.getId());
		final Credential credential = toCredential(loginData.getPassword());
		final IdentityManagement im = getGateForUser(id).getIdentityManagement();
		final User user = im.login(id, credential);
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
		if (fields.length != 3 || StringUtils.isBlank(fields[0])) {
			return null;
		}
		try {
			final String id = fields[0];
			final User user = getGateForUser(id).getIdentityManagement().findUser(id);
			if (user != null && isValid(user, id, fields[1], fields[2])) {
				logger.info("User {} logged in by token.", user);
				setLastLogin(user);
				return user;
			}
		} catch (ArastrejuRuntimeException e) {
			logger.error("Failed to login by token " + Arrays.toString(fields), e);
		}
		logger.info("Login token is invalid: " + token);
		return null;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String createRememberMeToken(User user) {
		final String email = user.getEmail();
		final SemanticNode credential = SNOPS.singleObject(user, Aras.HAS_CREDENTIAL);
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 30);
		final String raw = email + ":" + DATE_FORMAT.format(cal.getTime()); 
		return raw + ":" + Crypt.md5Hex(raw + credential.asValue().getStringValue());
	}

	// ----------------------------------------------------
	
	/**
	 * @return the provider
	 */
	protected ServiceProvider getProvider() {
		return provider;
	}
	
	protected ArastrejuGate getGateForUser(String userID) {
		final ArastrejuGate masterGate = provider.getArastejuGate();
		final Query query = masterGate.createQueryManager().buildQuery().addField(Aras.IDENTIFIED_BY, userID);
		final QueryResult result = query.getResult();
		if (!result.isEmpty()) {
			final SemanticNode domainNode = SNOPS.fetchObject(result.getSingleNode(), Aras.BELONGS_TO_DOMAIN);
			if (domainNode != null && domainNode.isValueNode()) {
				String domain = domainNode.asValue().getStringValue();
				final Collection<Domain> allDomains = masterGate.getOrganizer().getDomains();
				assertContains(allDomains, domain);
				return provider.openGate(domain);
			}
		} else {
			logger.info("Login ID unknown: " + userID);
		}
		return masterGate;
	}
	
	// ----------------------------------------------------
	
	private void setLastLogin(final User user) {
		SNOPS.assure(user, RBSystem.HAS_LAST_LOGIN, new SNTimeSpec(new Date(), TimeMask.TIMESTAMP));
	}
	
	private boolean isValid(User user, String id, String validUntil, String token) {
		try {
			final Date date = DATE_FORMAT.parse(validUntil);
			if (date.before(new Date())){
				logger.info("Login token has been expired: " + token);
				return false;
			}
			final SemanticNode credential = SNOPS.singleObject(user, Aras.HAS_CREDENTIAL);
			if (credential != null) {
				final String raw = id + ":" + validUntil; 
				final String crypted = Crypt.md5Hex(raw + credential.asValue().getStringValue());
				return crypted.equals(token);
			}
		} catch (ParseException e) {
			logger.info("Login token could not be parsed: " + token);
		}
		return false;
	}
	
	private Credential toCredential(final String password) {
		if (password != null) {
			return new PasswordCredential(Crypt.md5Hex(password));
		} else {
			return new PasswordCredential(null);
		}
	}
	 
	private String normalize(String name) {
		if (name == null) {
			return null;
		} else {
			return name.trim().toLowerCase();
		}
	}
	
	private void assertContains(Collection<Domain> allDomains, String domain) {
		for (Domain current : allDomains) {
			if (current.getUniqueName().equals(domain)) {
				return;
			}
		}
		throw new IllegalStateException("Domain is not registered: " + domain);
	}

}
