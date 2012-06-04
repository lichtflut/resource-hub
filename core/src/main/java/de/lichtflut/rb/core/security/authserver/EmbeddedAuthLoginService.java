/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security.authserver;

import static org.arastreju.sge.SNOPS.singleObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.eh.ArastrejuRuntimeException;
import org.arastreju.sge.eh.ErrorCodes;
import org.arastreju.sge.model.TimeMask;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNTimeSpec;
import org.arastreju.sge.security.Credential;
import org.arastreju.sge.security.LoginException;
import org.arastreju.sge.security.PasswordCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.actors.threadpool.Arrays;
import de.lichtflut.infra.security.Crypt;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.security.AuthenticationService;
import de.lichtflut.rb.core.security.LoginData;
import de.lichtflut.rb.core.security.RBCrypt;
import de.lichtflut.rb.core.security.RBUser;

/**
 * <p>
 *  This is the login service of the embedded auth module.
 * </p>
 *
 * <p>
 * 	Created May 4, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class EmbeddedAuthLoginService implements AuthenticationService {
	
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ssZ");
	
	private final Logger logger = LoggerFactory.getLogger(EmbeddedAuthLoginService.class);
	
	private final ArastrejuGate masterGate;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param gate The Arastreju Gate.
	 */
	public EmbeddedAuthLoginService(ArastrejuGate gate) {
		this.masterGate = gate;
	}
	
	// -- LOGIN -------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBUser login(LoginData loginData) throws LoginException {
		final String id = normalize(loginData.getId());
		if (id == null) {
			throw new LoginException(ErrorCodes.LOGIN_INVALID_DATA, "No username given");	
		}
		
		logger.info("Trying to login user '" + id + "'.");

		final ResourceNode user = findUserNode(id);
		if (user == null){
			throw new LoginException(ErrorCodes.LOGIN_USER_NOT_FOUND, "User does not exist: " + id);	
		}
		
		verifyPassword(user, loginData.getPassword());
		setLastLogin(user);
		
		logger.info("User {} logged in. ", user);
		return new RBUser(user);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBUser loginByToken(String token) {
		final String[] fields = token.split(":");
		if (fields.length != 3 || StringUtils.isBlank(fields[0])) {
			return null;
		}
		try {
			final String id = fields[0];
			final ResourceNode arasUser = findUserNode(id);
			if (arasUser != null && isValid(arasUser, id, fields[1], fields[2])) {
				final RBUser user = new RBUser(arasUser);
				logger.info("User {} logged in by token.", user);
				setLastLogin(arasUser);
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
	public String createRememberMeToken(RBUser user, LoginData loginData) {
		final String email = user.getEmail();
		final String id = normalize(loginData.getId());
		final Credential credential = toCredential(loginData.getPassword(), findUserNode(id));
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 30);
		final String raw = email + ":" + DATE_FORMAT.format(cal.getTime()); 
		return raw + ":" + Crypt.md5Hex(raw + credential);
	}

	// ----------------------------------------------------
	
	protected void verifyPassword(final ResourceNode user, String password) throws LoginException {
		final Credential credential = toCredential(password, user);
		if (!credential.applies(singleObject(user, Aras.HAS_CREDENTIAL))){
			throw new LoginException(ErrorCodes.LOGIN_USER_CREDENTIAL_NOT_MATCH, "Wrong credential");
		}
	}
	
	protected ResourceNode findUserNode(final String id) {
		return EmbeddedAuthFunctions.findUserNode(masterGate.createQueryManager(), id);
	}
	
	private void setLastLogin(final ResourceNode user) {
		SNOPS.assure(user, RBSystem.HAS_LAST_LOGIN, new SNTimeSpec(new Date(), TimeMask.TIMESTAMP));
	}
	
	private boolean isValid(ResourceNode user, String id, String validUntil, String token) {
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
	
	private Credential toCredential(final String password, final ResourceNode user) {
		if (password != null) {
			String salt = EmbeddedAuthFunctions.getSalt(user);
			if (salt == null || salt.isEmpty()) {
				logger.warn("User has no salt, will use old password crypter.");
				return new PasswordCredential(Crypt.md5Hex(password));
			}
			return new PasswordCredential(RBCrypt.encrypt(password, salt));
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
	
}
