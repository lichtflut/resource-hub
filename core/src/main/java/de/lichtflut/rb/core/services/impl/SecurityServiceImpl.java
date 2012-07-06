/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.arastreju.sge.ModelingConversation;
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
import de.lichtflut.rb.core.security.SecurityConfiguration;
import de.lichtflut.rb.core.security.UserManager;
import de.lichtflut.rb.core.services.MessagingService;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.ServiceContext;

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
public class SecurityServiceImpl implements SecurityService {
	
	private final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);
	
	private AuthModule authModule;

    private ModelingConversation conversation;
	
	private MessagingService messagingService;

    private ServiceContext serviceContext;

    private SecurityConfiguration securityConfiguration;

	// ----------------------------------------------------

    /**
     * Default constructor.
     */
    public SecurityServiceImpl() {
    }

    /**
	 * Constructor.
	 */
	public SecurityServiceImpl(ServiceContext context, ModelingConversation conversation, AuthModule authModule) {
		this.conversation = conversation;
        this.serviceContext = context;
		this.authModule = authModule;
	}

	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBUser findUser(String identifier) {
		return authModule.getUserManagement().findUser(identifier);
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
		authModule.getUserManagement().registerUser(rbUser, crypted, serviceContext.getDomain());
		if(messagingService != null){
			messagingService.getEmailService().sendRegistrationConfirmation(rbUser, locale);
		}
		return rbUser;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBUser createDomainAdmin(final RBDomain domain, String email, String username, String password) throws RBAuthException {
		logger.info("Creating domain admin {} for domain {}.", email, domain.getName());
		final RBUser rbUser = new RBUser(new SimpleResourceID().getQualifiedName());
		rbUser.setEmail(email);
		rbUser.setUsername(username);
		final String crypted = RBCrypt.encryptWithRandomSalt(password);
		
		authModule.getUserManagement().registerUser(rbUser, crypted, domain.getName());
		
		makeDomainAdmin(domain, rbUser);
		
		return rbUser;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void makeDomainAdmin(RBDomain domain, RBUser user) throws RBAuthException {
		final List<String> roles = Arrays.asList(rolesOfDomainAdmin());
		logger.info("Adding roles {} to user {}.", roles, user);
		setUserRoles(user, domain.getName(), roles);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void storeUser(RBUser updated) throws RBException {
		authModule.getUserManagement().updateUser(updated);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void deleteUser(RBUser user) {
		conversation.remove(new SimpleResourceID(user.getQualifiedName()));
		authModule.getUserManagement().deleteUser(user);
		logger.info("Deleted user: " + user);
	}
	
	// -- AUTHORIZATON ------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getUserRoles(RBUser user, String domain) {
		return authModule.getUserManagement().getUserRoles(user, domain);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getUserPermissions(RBUser user, String domain) {
		return authModule.getUserManagement().getUserPermissions(user, domain);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUserRoles(RBUser user, String domain, List<String> roles) throws RBAuthException {
		authModule.getUserManagement().setUserRoles(user, domain, roles);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAllUserRoles(RBUser user) throws RBAuthException {
		authModule.getUserManagement().removeAllUserRoles(user, null);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void changePassword(RBUser user, String currentPassword, String newPassword) throws RBException{
		authModule.getUserManagement().verifyPassword(user, currentPassword);
		authModule.getUserManagement().changePassword(user, newPassword);
	}

	/** 
	 * {@inheritDoc}
	 * @throws RBException 
	 */
	@Override
	public void resetPasswordForUser(RBUser user, Locale locale) throws RBException {
		final String generatedPwd = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
		authModule.getUserManagement().changePassword(user, generatedPwd);
		logGeneratedPwd(generatedPwd);
		if (messagingService != null) {
			messagingService.getEmailService().sendPasswordInformation(user, generatedPwd, locale);	
		}
	}
	
	// -- DEPENDENCIES ------------------------------------

	/**
	 * Inject messaging service.
	 * @param messagingService the messagingService to set
	 */
	public void setMessagingService(MessagingService messagingService) {
		this.messagingService = messagingService;
	}

    public void setConversation(ModelingConversation conversation) {
        this.conversation = conversation;
    }

    public void setServiceContext(ServiceContext serviceContext) {
        this.serviceContext = serviceContext;
    }

    public void setAuthModule(AuthModule authServer) {
        this.authModule = authServer;
    }

    public void setSecurityConfiguration(SecurityConfiguration securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
    }

    // ----------------------------------------------------
	
	/**
	 * Can be implemented by sub classes.
	 * @return The roles to be added to domain admin.
	 */
	protected String[] rolesOfDomainAdmin() {
        if (securityConfiguration != null) {
            return securityConfiguration.getRolesOfDomainAdmin();
        }
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
