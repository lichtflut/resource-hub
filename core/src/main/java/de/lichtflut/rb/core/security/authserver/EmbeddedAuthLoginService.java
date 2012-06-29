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
import org.arastreju.sge.ModelingConversation;
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
	
	private static final String SESSION_TOKEN_PREFIX = "s:";
	
	private static final String REMEMBER_TOKEN_PREFIX = "r:";
	
	private static final String randomSecret = RBCrypt.random(20);
	
	private final Logger logger = LoggerFactory.getLogger(EmbeddedAuthLoginService.class);
	
	private final ModelingConversation conversation;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param conversation The Arastreju conversation.
	 */
	public EmbeddedAuthLoginService(ModelingConversation conversation) {
		this.conversation = conversation;
		this.conversation.getConversationContext().setWriteContext(EmbeddedAuthModule.IDENT);
		this.conversation.getConversationContext().setReadContexts(EmbeddedAuthModule.IDENT);
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
		if (token == null) {
			return null;
		}
		final String[] fields = token.split(":");
		if (!isValid(fields)) {
			return null;
		}
		if (token.startsWith(SESSION_TOKEN_PREFIX)) {
			return loginBySessionToken(fields);
		} else {
			return loginByRembemberMeToken(fields);
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String createSessionToken(RBUser user) {
		final String email = user.getEmail();
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, 12);
		final String raw = SESSION_TOKEN_PREFIX + email + ":" + DATE_FORMAT.format(cal.getTime()); 
		return raw + ":" + Crypt.md5Hex(raw + randomSecret);
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
		final String raw = REMEMBER_TOKEN_PREFIX + email + ":" + DATE_FORMAT.format(cal.getTime()); 
		return raw + ":" + Crypt.md5Hex(raw + credential.stringRepesentation());
	}

	// ----------------------------------------------------
	
	protected void verifyPassword(final ResourceNode user, String password) throws LoginException {
		final Credential credential = toCredential(password, user);
		if (!credential.applies(singleObject(user, Aras.HAS_CREDENTIAL))){
			throw new LoginException(ErrorCodes.LOGIN_USER_CREDENTIAL_NOT_MATCH, "Wrong credential");
		}
	}
	
	protected ResourceNode findUserNode(final String id) {
		return EmbeddedAuthFunctions.findUserNode(conversation, id);
	}

	protected RBUser loginByRembemberMeToken(String[] fields) {
		try {
			final String id = fields[1];
			final ResourceNode arasUser = findUserNode(id);
			if (arasUser == null ) {
				logger.info("User with id {} not found.", id);
			} else if (!isValid(arasUser, id, fields[2], fields[3])){
				logger.info("RememberMe ticket for user {} is not valid.", arasUser);
			} else {
				final RBUser user = new RBUser(arasUser);
				logger.info("User {} logged in by remember me token.", user);
				setLastLogin(arasUser);
				return user;
			}
		} catch (ArastrejuRuntimeException e) {
			logger.error("Failed to login by token " + Arrays.toString(fields), e);
			return null;
		}
		return null;
	}

	protected RBUser loginBySessionToken(String[] fields) {
		try {
			final String id = fields[1];
			if (isValid(id, fields[2], fields[3])) {
				final ResourceNode arasUser = findUserNode(id);
				logger.info("User {} logged in by session token.", id);
				return new RBUser(arasUser);
			} else {
				logger.info("Session token is invalid: " + StringUtils.join(fields, ":"));
			}
		} catch (ArastrejuRuntimeException e) {
			logger.error("Failed to login by token " + Arrays.toString(fields), e);
		}
		return null;
	}
	
	// ----------------------------------------------------
	
	private void setLastLogin(final ResourceNode user) {
		SNOPS.assure(user, RBSystem.HAS_LAST_LOGIN, new SNTimeSpec(new Date(), TimeMask.TIMESTAMP));
	}
	
	/**
	 * Check if ticket structure is O.K. and date not expired.
	 */
	private boolean isValid(String[] fields) {
		if (fields.length != 4) {
			logger.info("Login token has wrong structure: " + StringUtils.join(fields, ":"));
			return false;
		} else if (StringUtils.isBlank(fields[1])) {
			logger.info("Login token has no user ID: " + StringUtils.join(fields, ":"));
			return false;
		} else if (isExpired(fields[2])){
			logger.info("Login token has expired: " + StringUtils.join(fields, ":"));
			return false;
		} else {
			return true;
		}
	}
	
	private boolean isValid(ResourceNode user, String id, String validUntil, String fingerprint) {
		final SemanticNode credential = SNOPS.singleObject(user, Aras.HAS_CREDENTIAL);
		if (credential != null) {
			final String raw = REMEMBER_TOKEN_PREFIX + id + ":" + validUntil; 
			final String crypted = Crypt.md5Hex(raw + credential.asValue().getStringValue());
			return crypted.equals(fingerprint);
		} else {
			return false;	
		}
	}
	
	private boolean isValid(String id, String validUntil, String fingerprint) {
		if (isExpired(validUntil)) {
			logger.info("Login token has been expired: " + fingerprint);
			return false;
		}
		final String raw = SESSION_TOKEN_PREFIX + id + ":" + validUntil; 
		final String crypted = Crypt.md5Hex(raw + randomSecret);
		return crypted.equals(fingerprint);
	}

	protected boolean isExpired(String validUntil) {
		try {
			final Date date = DATE_FORMAT.parse(validUntil);
			return date.before(new Date());
		} catch (ParseException e) {
			logger.info("Login token date info could not be parsed: " + validUntil);
			return false;
		}
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