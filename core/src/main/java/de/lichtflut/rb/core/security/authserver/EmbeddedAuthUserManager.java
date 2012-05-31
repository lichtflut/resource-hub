/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security.authserver;

import static org.arastreju.sge.SNOPS.singleObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.eh.ArastrejuRuntimeException;
import org.arastreju.sge.eh.ErrorCodes;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.TimeMask;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.model.nodes.views.SNTimeSpec;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryResult;
import org.arastreju.sge.security.Credential;
import org.arastreju.sge.security.LoginException;
import org.arastreju.sge.security.PasswordCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.actors.threadpool.Arrays;
import de.lichtflut.infra.Infra;
import de.lichtflut.infra.security.Crypt;
import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.eh.EmailAlreadyInUseException;
import de.lichtflut.rb.core.eh.InvalidPasswordException;
import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.eh.UsernameAlreadyInUseException;
import de.lichtflut.rb.core.security.AuthenticationService;
import de.lichtflut.rb.core.security.LoginData;
import de.lichtflut.rb.core.security.RBCrypt;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.security.UserManager;

/**
 * <p>
 *  There are several possible implementations of an authentications service, e.g. an external LDAP service, or 
 *  an Open ID server. 
 *  In the most simple scenario the RB application itself provides the authentication services.
 *  Therefore the root domain will act as the store for users and credentials.
 * </p>
 *
 * <p>
 * 	Created May 4, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class EmbeddedAuthUserManager implements AuthenticationService, UserManager {
	
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ssZ");
	
	private final Logger logger = LoggerFactory.getLogger(EmbeddedAuthUserManager.class);
	
	private final ArastrejuGate masterGate;
	
	private final EmbeddedAuthAuthorizationManager authorization;

	private final EmbeddedAuthDomainManager domainManager;

	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param gate The Arastreju Gate.
	 * @param domainManager The domain manager.
	 */
	public EmbeddedAuthUserManager(ArastrejuGate gate, EmbeddedAuthDomainManager domainManager) {
		this.masterGate = gate;
		this.domainManager = domainManager;
		this.authorization = new EmbeddedAuthAuthorizationManager(masterGate, domainManager);
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

	
	// -- USER MANAGEMENT ---------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBUser findUser(String identifier) {
		final ResourceNode node = findUserNode(identifier);
		if (node == null) {
			return null;
		} else {
			return new RBUser(node);
		}
	};
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void registerUser(RBUser user, String credential, String domainName) throws RBAuthException {
		if (isIdentifierInUse(user.getEmail())) {
				throw new EmailAlreadyInUseException("Email already in use.");
		}
		if (user.getUsername() != null && isIdentifierInUse(user.getUsername())) {
				throw new UsernameAlreadyInUseException("Username already in use.");
		}
		final ResourceNode domain = domainManager.findDomainNode(domainName);
		final ModelingConversation mc = masterGate.startConversation();
		final ResourceNode userNode = new SNResource(user.getQualifiedName());
		userNode.addAssociation(RDF.TYPE, Aras.USER, Aras.IDENT);
		userNode.addAssociation(Aras.BELONGS_TO_DOMAIN, domain, Aras.IDENT);
		userNode.addAssociation(Aras.HAS_CREDENTIAL, new SNText(credential), Aras.IDENT);

		SNOPS.assure(userNode, RBSystem.HAS_EMAIL, user.getEmail());
		SNOPS.associate(userNode, Aras.IDENTIFIED_BY, new SNText(user.getEmail()), Aras.IDENT);
		SNOPS.associate(userNode, Aras.IDENTIFIED_BY, new SNText(user.getQualifiedName().toURI()), Aras.IDENT);
		if (user.getUsername() != null) {
			SNOPS.assure(userNode, RBSystem.HAS_USERNAME, user.getUsername());
			SNOPS.associate(userNode, Aras.IDENTIFIED_BY, new SNText(user.getUsername()), Aras.IDENT);
		}
		
		mc.attach(userNode);
		logger.info("Registered user: " + user + " --> " + domain);
		mc.close();
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void updateUser(RBUser updated) throws RBAuthException {
		final ResourceNode attachedUser = masterGate.startConversation().findResource(updated.getQualifiedName());
		final RBUser existing = new RBUser(attachedUser);
		if (!Infra.equals(existing.getEmail(), updated.getEmail())) {
			if (isIdentifierInUse(updated.getEmail())) {
				throw new EmailAlreadyInUseException("Email already in use.");
			}
		}
		if (updated.getUsername() != null && !Infra.equals(existing.getUsername(), updated.getUsername())) {
			if (isIdentifierInUse(updated.getUsername())) {
				throw new UsernameAlreadyInUseException("Username already in use.");
			}
		}
		SNOPS.assure(attachedUser, RBSystem.HAS_EMAIL, updated.getEmail());
		if (updated.getUsername() != null) {
			SNOPS.assure(attachedUser, RBSystem.HAS_USERNAME, updated.getUsername());	
		} else {
			SNOPS.remove(attachedUser, RBSystem.HAS_USERNAME);
		}
		@SuppressWarnings("unchecked")
		final List<SNText> identifiers = Arrays.asList(new SNText[] {
				new SNText(updated.getEmail()), 
				new SNText(updated.getUsername())
			});
		SNOPS.assure(attachedUser, Aras.IDENTIFIED_BY, identifiers, Aras.IDENT);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void deleteUser(RBUser user) {
		final Query query = masterGate.createQueryManager().buildQuery()
				.addField(Aras.IDENTIFIED_BY, user.getEmail())
				.or()
				.addField(Aras.IDENTIFIED_BY, user.getUsername());
		final QueryResult result = query.getResult();
		if (!result.isEmpty()) {
			for (ResourceNode id : result) {
				masterGate.startConversation().remove(id);
				logger.info("User deleted user {} from master domain; ID: {} ", user, id);	
			}
		} else {
			logger.warn("User could not be deleted from master domain: " + user);
		}
		masterGate.close();
	}
	
	// -- PASSWORD HANDLING -------------------------------
	
	/**
	 * Saves the newPassword to the user in the database.
	 * @param user The user.
	 * @param newPassword The new Password.
	 */
	@Override
	public void changePassword(RBUser user, String newPassword) {
		String newCrypt = RBCrypt.encryptWithRandomSalt(newPassword);
		SNValue credential = new SNValue(ElementaryDataType.STRING, newCrypt);
		ResourceNode arasUser = findUserNode(user.getEmail());
		SNOPS.assure(arasUser, Aras.HAS_CREDENTIAL, credential, RB.PRIVATE_CONTEXT);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void verifyPassword(RBUser user, String currentPassword) throws RBAuthException {
		ResourceNode arasUser = findUserNode(user.getEmail());
		final String checkCredential = SNOPS.string(SNOPS.singleObject(arasUser, Aras.HAS_CREDENTIAL));
		if(!RBCrypt.verify(currentPassword, checkCredential)) {
			if (Crypt.md5Hex(currentPassword).equals(checkCredential)) {
				logger.warn("User's password has been encrypted the old way.");
			} else {
				throw new InvalidPasswordException("Password is not valid.");
			}
		}
	}
	
	// -- AUTHORIZATON ------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getUserRoles(RBUser user, String domain) {
		return authorization.getUserRoles(user, domain);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getUserPermissions(RBUser user, String domain) {
		return authorization.getUserPermissions(user.getQualifiedName(), domain);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUserRoles(RBUser user, String domain, List<String> roles) throws RBAuthException {
		authorization.setUserRoles(user, domain, roles);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAllUserRoles(RBUser user, String domain) throws RBAuthException {
		authorization.setUserRoles(user, user.getDomesticDomain(), Collections.<String>emptyList());
	}

	// ----------------------------------------------------
	
	protected void verifyPassword(final ResourceNode user, String password) throws LoginException {
		final Credential credential = toCredential(password, user);
		if (!credential.applies(singleObject(user, Aras.HAS_CREDENTIAL))){
			throw new LoginException(ErrorCodes.LOGIN_USER_CREDENTIAL_NOT_MATCH, "Wrong credential");
		}
	}
	
	protected ResourceNode findUserNode(final String id) {
		final Query query = masterGate.createQueryManager().buildQuery();
		query.addField(Aras.IDENTIFIED_BY, id);
		final QueryResult result = query.getResult();
		if (result.size() > 1) {
			logger.error("More than one user with name '" + id + "' found.");
			throw new IllegalStateException("More than on user with name '" + id + "' found.");
		} else if (result.isEmpty()) {
			return null;
		} else {
			return result.getSingleNode();
		}
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
			String salt = getSalt(user);
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
	
	private String getSalt(ResourceNode user) {
		if (user == null) {
			return null;
		}
		final String credential = SNOPS.string(SNOPS.singleObject(user, Aras.HAS_CREDENTIAL));
		return RBCrypt.extractSalt(credential);
	}
	
	private boolean isIdentifierInUse(String identifier) {
		final Query query = masterGate.createQueryManager().buildQuery();
		query.addField(Aras.IDENTIFIED_BY, identifier);
		if (query.getResult().isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
}
