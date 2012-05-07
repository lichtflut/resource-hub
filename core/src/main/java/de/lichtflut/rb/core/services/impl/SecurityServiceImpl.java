/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.IdentityManagement;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.eh.ArastrejuException;
import org.arastreju.sge.eh.ArastrejuRuntimeException;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.security.Credential;
import org.arastreju.sge.security.Domain;
import org.arastreju.sge.security.PasswordCredential;
import org.arastreju.sge.security.Role;
import org.arastreju.sge.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.infra.Infra;
import de.lichtflut.infra.security.Crypt;
import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.eh.RBException;
import de.lichtflut.rb.core.messaging.EmailConfiguration;
import de.lichtflut.rb.core.security.ExternalAuthServer;
import de.lichtflut.rb.core.security.RBCrypt;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.security.authserver.DedicatedAuthDomain;
import de.lichtflut.rb.core.services.SecurityService;

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
public class SecurityServiceImpl extends AbstractService implements SecurityService {
	
	private final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);
	private ExternalAuthServer authServer;
	
	// ----------------------------------------------------
	
	/**
	 * @param provider
	 */
	public SecurityServiceImpl(AbstractServiceProvider provider) {
		super(provider);
		this.authServer = new DedicatedAuthDomain(provider);
	}

	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBUser createUser(String email, String username, String password, EmailConfiguration conf, Locale locale) throws RBException {
		final String crypted = RBCrypt.encryptWithRandomSalt(password);
		final Credential credential = new PasswordCredential(crypted);
		try {
			final IdentityManagement im = identityManagement();
			final User user = im.register(email, credential);
			SNOPS.assure(user, RBSystem.HAS_EMAIL, email);
			if (username != null) {
				im.registerAlternateID(user, username);
				SNOPS.assure(user, RBSystem.HAS_USERNAME, username);
			}
			final String domain = getProvider().getContext().getDomain();
			final RBUser rbUser = new RBUser(user);
			if (domain != null && !gate().getContext().isMasterDomain()) {
				registerUserInMasterDomain(rbUser, domain);
			}
			getProvider().getMessagingService().getEmailService().sendRegistrationConfirmation(rbUser, conf, locale);
			return rbUser;
		} catch(ArastrejuException e) {
			if(e.getErrCode().equals(org.arastreju.sge.eh.ErrorCodes.REGISTRATION_NAME_ALREADY_IN_USE)) {
				throw new RBException(ErrorCodes.SECURITYSERVICE_EMAIL_ALREADY_IN_USE, 
						"The ID (emailID) '" + email + "' is already in use.");
			} else {
				logger.error("Unexpected ArastrejuException while trying to register user: ", e);
				throw new RBException(ErrorCodes.UNKNOWN_ERROR, "User could not be registered.", e);

			}
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBUser createDomainAdmin(final Domain domain, String email, String username, String password) {
		final String crypted = RBCrypt.encryptWithRandomSalt(password);
		final Credential credential = new PasswordCredential(crypted);
		try {
			final ArastrejuGate gate = gate(domain.getUniqueName());
			final IdentityManagement im = gate.getIdentityManagement();
			final User admin = im.register(email, credential);
			SNOPS.assure(admin, RBSystem.HAS_EMAIL, email);
			if (username != null) {
				im.registerAlternateID(admin, username);
				SNOPS.assure(admin, RBSystem.HAS_USERNAME, username);
			}
			for (String roleName : rolesOfDomainAdmin()) {
				final Role role = im.registerRole(roleName);
				im.addUserToRoles(admin, role);
			}
			registerUserInMasterDomain(new RBUser(admin), domain.getUniqueName());
			logger.info("Created domain admin: " + admin);
			return new RBUser(admin);
		} catch(ArastrejuException e) {
			logger.error("Error while trying to create admin for domain {} : {}", domain, e.getMessage());
			throw new ArastrejuRuntimeException(org.arastreju.sge.eh.ErrorCodes.GENERAL_RUNTIME_ERROR, 
					"domain admin could not be created", e);
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void storeUser(RBUser updated) throws RBException {
		final ResourceNode attachedUser = mc().findResource(updated.getQualifiedName());
		final RBUser existing = new RBUser(attachedUser);
		if (!Infra.equals(existing.getEmail(), updated.getEmail())) {
			if (identityManagement().isIdentifierInUse(updated.getEmail())) {
				throw new RBException(ErrorCodes.SECURITYSERVICE_EMAIL_ALREADY_IN_USE, "Email already in use.");
			}
		}
		if (updated.getUsername() != null && !Infra.equals(existing.getUsername(), updated.getUsername())) {
			if (identityManagement().isIdentifierInUse(updated.getUsername())) {
				throw new RBException(ErrorCodes.SECURITYSERVICE_USERNAME_ALREADY_IN_USE, "Username already in use.");
			}
		}
		SNOPS.assure(attachedUser, RBSystem.HAS_EMAIL, updated.getEmail());
		if (updated.getUsername() != null) {
			SNOPS.assure(attachedUser, RBSystem.HAS_USERNAME, updated.getUsername());	
		} else {
			SNOPS.remove(attachedUser, RBSystem.HAS_USERNAME);
		}
		final List<SNText> identifiers = Arrays.asList(new SNText[] {
				new SNText(updated.getEmail()), 
				new SNText(updated.getUsername())
			});
		SNOPS.assure(attachedUser, Aras.IDENTIFIED_BY, identifiers, Aras.IDENT);
		final String domain = getProvider().getContext().getDomain();
		if (domain != null && !gate().getContext().isMasterDomain()) {
			updateUserInMasterDomain(existing, updated, domain);
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void deleteUser(RBUser user) {
		mc().remove(new SimpleResourceID(user.getQualifiedName()));
		logger.info("Deleted user: " + user);
		deleteUserInMasterDomain(user);
	}
	
	// ----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUserRoles(RBUser user, List<String> roles) {
		IdentityManagement im = identityManagement();
		User arasUser = im.findUser(user.getEmail());
		SNOPS.remove(arasUser, Aras.HAS_ROLE);
		for (String current : roles) {
			im.addUserToRoles(arasUser, im.registerRole(current));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAllUserRoles(RBUser user) {
		setUserRoles(user, Collections.<String>emptyList());
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getSalt(User user) {
		if (user == null) {
			return null;
		}
		final String credential = SNOPS.string(SNOPS.singleObject(user, Aras.HAS_CREDENTIAL));
		return RBCrypt.extractSalt(credential);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void changePassword(RBUser user, String currentPassword, String newPassword) throws RBException{
		verifyPassword(user, currentPassword);
		setPassword(user, newPassword);
	}

	/** 
	 * {@inheritDoc}
	 * @throws RBException 
	 */
	@Override
	public void resetPasswordForUser(RBUser user, EmailConfiguration conf, Locale locale) throws RBException {
		String generatedPwd = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
		setPassword(user, generatedPwd);
		logGeneratedPwd(generatedPwd);
		getProvider().getMessagingService().getEmailService().sendPasswordInformation(user, generatedPwd, conf, locale);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Collection<RBDomain> getAllDomains() {
		return authServer.getAllDomains();
	}
	
	// ----------------------------------------------------
	
	/**
	 * Can be implemented by sub classes.
	 * @return The roles to be added to domain admin.
	 */
	protected String[] rolesOfDomainAdmin() {
		return new String[0];
	}
	
	/**
	 * Can be implemented by sub classes to log the generated password.
	 * <p><b>!!WARNING!!<br/>
	 * <i>Implementation should check for DEVELOPMENT-Mode</i>
	 * <br/>!!WARNING!!</b></p>
	 */
	protected void logGeneratedPwd(String generatedPwd) {
	}
	
	// ----------------------------------------------------
	
	/**
	 * WARNING: Never forget to sync the users between current domain and auth server!
	 * The user's login IDs have to be mapped to the domain.
	 * @param user The user.
	 * @param domain The user's domain.
	 */
	private void registerUserInMasterDomain(RBUser user, String domain) {
		authServer.registerUser(user, domain);
	}
	
	/**
	 * WARNING: Never forget to sync the users between current domain and auth server!
	 * The user's login IDs have to be mapped to the domain.
	 * @param existing The existing user.
	 * @param updated The updated user.
	 * @param domain The user's domain.
	 */
	private void updateUserInMasterDomain(RBUser existing, RBUser updated, String domain) {
		authServer.updateUserIdentity(existing, updated, domain);
	}
	
	/**
	 * WARNING: Never forget to sync the users between current domain and auth server!
	 * The user's login IDs have to be mapped to the domain.
	 * @param user The user.
	 */
	private void deleteUserInMasterDomain(RBUser user) {
		authServer.deleteUser(user);
	}
	
	// ----------------------------------------------------

	/**
	 * Saves the newPassword to the user in the database.
	 * @param user The user.
	 * @param newPassword The new Password.
	 */
	private void setPassword(RBUser user, String newPassword) {
		String newCrypt = RBCrypt.encryptWithRandomSalt(newPassword);
		SNValue credential = new SNValue(ElementaryDataType.STRING, newCrypt);
		User arasUser = identityManagement().findUser(user.getEmail());
		SNOPS.assure(arasUser, Aras.HAS_CREDENTIAL, credential, RB.PRIVATE_CONTEXT);
	}
	
	/**
	 * Verifies a password against a {@link User}
	 * @param user
	 * @param password
	 * @throws RBException if password is invalid
	 */
	private void verifyPassword(RBUser user, String password) throws RBException{
		User arasUser = identityManagement().findUser(user.getEmail());
		final String checkCredential = SNOPS.string(SNOPS.singleObject(arasUser, Aras.HAS_CREDENTIAL));
		if(!RBCrypt.verify(password, checkCredential)) {
			if (Crypt.md5Hex(password).equals(checkCredential)) {
				logger.warn("User's password has been encrypted the old way.");
			} else {
				throw new RBException(ErrorCodes.INVALID_PASSWORD, "Password id not valid");
			}
		}
	}
	
	private IdentityManagement identityManagement() {
		return getProvider().getArastejuGate().getIdentityManagement();
	}
	
}
