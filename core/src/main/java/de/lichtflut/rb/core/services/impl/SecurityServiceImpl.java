/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.arastreju.sge.model.SimpleResourceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.eh.RBException;
import de.lichtflut.rb.core.messaging.EmailConfiguration;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBCrypt;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.security.UserManager;
import de.lichtflut.rb.core.services.SecurityService;

/**
 * <p>
 *  Implementation of {@link SecurityService}. 
 *  This service wrapps the {@link UserManager}.
 *
 * <p>
 * 	Created Jan 2, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SecurityServiceImpl extends AbstractService implements SecurityService {
	
	private final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);
	
	private final AuthModule authServer;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param The service provider
	 */
	public SecurityServiceImpl(AbstractServiceProvider provider, AuthModule authServer) {
		super(provider);
		this.authServer = authServer;
	}

	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBUser findUser(String identifier) {
		return authServer.getUserManagement().findUser(identifier);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBUser createUser(String email, String username, String password, EmailConfiguration conf, Locale locale) throws RBException {
		final RBUser rbUser = new RBUser(new SimpleResourceID().getQualifiedName());
		rbUser.setEmail(email);
		rbUser.setUsername(username);
		final String crypted = RBCrypt.encryptWithRandomSalt(password);
		authServer.getUserManagement().registerUser(rbUser, crypted, getProvider().getContext().getDomain());
		getProvider().getMessagingService().getEmailService().sendRegistrationConfirmation(rbUser, conf, locale);
		return rbUser;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBUser createDomainAdmin(final RBDomain domain, String email, String username, String password) throws RBAuthException {
		final RBUser rbUser = new RBUser(new SimpleResourceID().getQualifiedName());
		rbUser.setEmail(email);
		rbUser.setUsername(username);
		final String crypted = RBCrypt.encryptWithRandomSalt(password);
		authServer.getUserManagement().registerUser(rbUser, crypted, domain.getName());
		setUserRoles(rbUser, domain.getName(), Arrays.asList(rolesOfDomainAdmin()));
		return rbUser;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void storeUser(RBUser updated) throws RBException {
		authServer.getUserManagement().updateUser(updated);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void deleteUser(RBUser user) {
		mc().remove(new SimpleResourceID(user.getQualifiedName()));
		authServer.getUserManagement().deleteUser(user);
		logger.info("Deleted user: " + user);
	}
	
	// -- AUTHORIZATON ------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getUserRoles(RBUser user) {
		return authServer.getUserManagement().getUserRoles(user);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getUserPermissions(RBUser user) {
		return authServer.getUserManagement().getUserPermissions(user);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUserRoles(RBUser user, String domain, List<String> roles) throws RBAuthException {
		authServer.getUserManagement().setUserRoles(user, domain, roles);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAllUserRoles(RBUser user) throws RBAuthException {
		authServer.getUserManagement().removeAllUserRoles(user, null);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void changePassword(RBUser user, String currentPassword, String newPassword) throws RBException{
		authServer.getUserManagement().verifyPassword(user, currentPassword);
		authServer.getUserManagement().changePassword(user, newPassword);
	}

	/** 
	 * {@inheritDoc}
	 * @throws RBException 
	 */
	@Override
	public void resetPasswordForUser(RBUser user, EmailConfiguration conf, Locale locale) throws RBException {
		final String generatedPwd = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
		authServer.getUserManagement().changePassword(user, generatedPwd);
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
	
}
