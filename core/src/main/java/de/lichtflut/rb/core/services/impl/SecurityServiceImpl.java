/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.IdentityManagement;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.eh.ArastrejuException;
import org.arastreju.sge.eh.ArastrejuRuntimeException;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.security.Credential;
import org.arastreju.sge.security.Domain;
import org.arastreju.sge.security.PasswordCredential;
import org.arastreju.sge.security.Role;
import org.arastreju.sge.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.actors.threadpool.Arrays;
import de.lichtflut.infra.Infra;
import de.lichtflut.infra.security.Crypt;
import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.eh.RBException;
import de.lichtflut.rb.core.messaging.EmailConfiguration;
import de.lichtflut.rb.core.security.RBUser;
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
	
	// ----------------------------------------------------
	
	/**
	 * @param provider
	 */
	public SecurityServiceImpl(AbstractServiceProvider provider) {
		super(provider);
	}

	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBUser createUser(String email, String username, String password, EmailConfiguration conf, Locale locale) throws RBException {
		final String crypted = Crypt.md5Hex(password);
		final Credential credential = new PasswordCredential(crypted);
		try {
			final ResourceNode userNode = identityManagement().register(email, credential);
			final RBUser user = new RBUser(userNode);
			user.setEmail(email);
			if (username != null) {
				identityManagement().registerAlternateID(user, username);
				user.setUsername(username);
			}
			final String domain = getProvider().getContext().getDomain();
			if (domain != null && !gate().getContext().isMasterDomain()) {
				registerUserInMasterDomain(user, domain);
			}
			getProvider().getMessagingService().getEmailService().sendRegistrationConfirmation(user, conf, locale);
			return user;
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
		final String crypted = Crypt.md5Hex(password);
		final Credential credential = new PasswordCredential(crypted);
		try {
			final ArastrejuGate gate = gate(domain.getUniqueName());
			final IdentityManagement im = gate.getIdentityManagement();
			final RBUser admin = new RBUser(im.register(email, credential));
			admin.setEmail(email);
			if (username != null) {
				im.registerAlternateID(admin, username);
				admin.setUsername(username);
			}
			for (String roleName : rolesOfDomainAdmin()) {
				final Role role = im.registerRole(roleName);
				im.addUserToRoles(admin, role);
			}
			registerUserInMasterDomain(admin, domain.getUniqueName());
			logger.info("Created domain admin: " + admin);
			return admin;
		} catch(ArastrejuException e) {
			logger.error("Error while trying to create admin for domain " + domain);
			throw new ArastrejuRuntimeException(org.arastreju.sge.eh.ErrorCodes.GENERAL_RUNTIME_ERROR);
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void storeUser(RBUser updated) throws RBException {
		final RBUser existing = new RBUser(mc().findResource(updated.getQualifiedName()));
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
		mc().attach(updated);
		final List<SNText> identifiers = Arrays.asList(new SNText[] {
				new SNText(updated.getEmail()), 
				new SNText(updated.getUsername())
			});
		SNOPS.assure(updated, Aras.IDENTIFIED_BY, identifiers, Aras.IDENT);
		final String domain = getProvider().getContext().getDomain();
		if (domain != null && !gate().getContext().isMasterDomain()) {
			registerUserInMasterDomain(updated, domain);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUserRoles(User user, List<String> roles) {
		ModelingConversation mc = mc();
		IdentityManagement im = identityManagement();
		mc.attach(user);
		SNOPS.remove(user, Aras.HAS_ROLE);
		for (String current : roles) {
			im.addUserToRoles(user, im.registerRole(current));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAllUserRoles(User user) {
		setUserRoles(user, new ArrayList<String>());
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setNewPassword(RBUser user, String currentPassword, String newPassword) throws RBException{
		verifyPassword(user, currentPassword);
		setPassword(newPassword, user);
	}

	/** 
	 * {@inheritDoc}
	 * @throws RBException 
	 */
	@Override
	public void resetPasswordForUser(RBUser user, EmailConfiguration conf, Locale locale) throws RBException {
		String generatedPwd = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
		String newCrypt = Crypt.md5Hex(generatedPwd);
		setPassword(newCrypt, user);
		logGeneratedPwd(generatedPwd);
		getProvider().getMessagingService().getEmailService().sendPasswordInformation(user, generatedPwd, conf, locale);
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
	 * WARNING: Very dangerous! Copying data between domain!
	 * The user's login IDs have to be mapped to the domain.
	 * @param user The user.
	 * @param domain The domain.
	 */
	private void registerUserInMasterDomain(User user, String domain) {
		final ModelingConversation mc = masterGate().startConversation();
		final ResourceNode userNode = new SNResource();
		userNode.addAssociation(RDF.TYPE, Aras.USER, Aras.IDENT);
		userNode.addAssociation(Aras.BELONGS_TO_DOMAIN, new SNText(domain), Aras.IDENT);
		Set<SemanticNode> ids = SNOPS.objects(user, Aras.IDENTIFIED_BY);
		for (SemanticNode node : ids) {
			final SNText copy = new SNText(node.asValue().getStringValue());
			SNOPS.associate(userNode, Aras.IDENTIFIED_BY, copy, Aras.IDENT);
		}
		mc.attach(userNode);
		logger.info("Registering user in master domain: " + user + " --> " + domain);
	}

	/**
	 * Verifies a password against a {@link User}
	 * @param user
	 * @param md5Password
	 * @throws RBException if password is invalid
	 */
	private void verifyPassword(User user, String md5Password) throws RBException{
		final SemanticNode credential = SNOPS.singleObject(user, Aras.HAS_CREDENTIAL);
		if(!credential.asValue().getStringValue().equals(md5Password)){
			throw new RBException(ErrorCodes.INVALID_PASSWORD, "Password id not valid");
		}
	}
	
	private IdentityManagement identityManagement() {
		return getProvider().getArastejuGate().getIdentityManagement();
	}

	/**
	 * @param newPassword
	 * @param userNode
	 */
	private void setPassword(String newPassword, ResourceNode userNode) {
		if(!userNode.isAttached()){
			userNode = getProvider().getResourceResolver().resolve(userNode);
		}
		SNValue password = new SNValue(ElementaryDataType.STRING, newPassword);
		SNOPS.assure(userNode, Aras.HAS_CREDENTIAL, password, RB.PRIVATE_CONTEXT);
	}
	
}
