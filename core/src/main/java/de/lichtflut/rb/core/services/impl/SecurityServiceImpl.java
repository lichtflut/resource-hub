/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.core.services.impl;

import de.lichtflut.rb.core.config.RBConfig;
import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.eh.RBException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBCrypt;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.security.UserManager;
import de.lichtflut.rb.core.services.MessagingService;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.ServiceContext;
import org.arastreju.sge.model.SimpleResourceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

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

    private final RBConfig config;

	private AuthModule authModule;

	private MessagingService messagingService;

	private ServiceContext serviceContext;

    // ----------------------------------------------------

	/**
	 * Constructor.
	 */
	public SecurityServiceImpl(RBConfig config) {
        this.config = config;
    }

	/**
	 * Constructor.
	 */
	public SecurityServiceImpl(final ServiceContext context, final AuthModule authModule) {
        this.config = context.getConfig();
		this.serviceContext = context;
		this.authModule = authModule;
	}

	// ----------------------------------------------------

	@Override
	public RBUser findUser(final String identifier) {
		return authModule.getUserManagement().findUser(identifier);
	}

    @Override
    public void updateUser(final RBUser updated) throws RBException {
        authModule.getUserManagement().updateUser(updated);
    }

    @Override
    public void deleteUser(final RBUser user) {
        authModule.getUserManagement().deleteUser(user);
    }

    // ----------------------------------------------------

    @Override
	public RBUser createUser(final String email, final String username, final String password, final Locale locale) throws RBException {
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

	@Override
	public RBUser createDomainAdmin(final RBDomain domain, final String email, final String username, final String password) throws RBAuthException {
		logger.info("Creating domain admin {} for domain {}.", email, domain.getName());
		final RBUser rbUser = new RBUser(new SimpleResourceID().getQualifiedName());
		rbUser.setEmail(email);
		rbUser.setUsername(username);
		final String crypted = RBCrypt.encryptWithRandomSalt(password);

		authModule.getUserManagement().registerUser(rbUser, crypted, domain.getName());

		makeDomainAdmin(domain, rbUser);

		return rbUser;
	}

	@Override
	public void makeDomainAdmin(final RBDomain domain, final RBUser user) throws RBAuthException {
		final List<String> roles = Arrays.asList(rolesOfDomainAdmin());
		logger.info("Adding roles {} to user {}.", roles, user);
		setUserRoles(user, domain.getName(), roles);
	}

	// -- AUTHORIZATON ------------------------------------

	@Override
	public List<String> getUserRoles(final RBUser user, final String domain) {
		return authModule.getUserManagement().getUserRoles(user, domain);
	}

	@Override
	public Set<String> getUserPermissions(final RBUser user, final String domain) {
		return authModule.getUserManagement().getUserPermissions(user, domain);
	}

	@Override
	public void setUserRoles(final RBUser user, final String domain, final List<String> roles) throws RBAuthException {
		authModule.getUserManagement().setUserRoles(user, domain, roles);
	}

	@Override
	public void removeAllUserRoles(final RBUser user) throws RBAuthException {
		authModule.getUserManagement().removeAllUserRoles(user, null);
	}

	// ----------------------------------------------------

	@Override
	public void changePassword(final RBUser user, final String currentPassword, final String newPassword) throws RBException{
		authModule.getUserManagement().verifyPassword(user, currentPassword);
		authModule.getUserManagement().changePassword(user, newPassword);
	}

	@Override
	public void resetPasswordForUser(final RBUser user, final Locale locale) throws RBException {
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
	public void setMessagingService(final MessagingService messagingService) {
		this.messagingService = messagingService;
	}

	public void setServiceContext(final ServiceContext serviceContext) {
		this.serviceContext = serviceContext;
	}

	public void setAuthModule(final AuthModule authServer) {
		this.authModule = authServer;
	}

	// ----------------------------------------------------

	/**
	 * Can be implemented by sub classes.
	 * @return The roles to be added to domain admin.
	 */
	protected String[] rolesOfDomainAdmin() {
		if (config.getSecurityConfiguration() != null) {
			return config.getSecurityConfiguration().getRolesOfDomainAdmin();
		}
		return new String[0];
	}

	/**
	 * Can be implemented by sub classes to log the generated password.
	 * <p><b>!!WARNING!!<br/>
	 * <i>Implementation should check for DEVELOPMENT-Mode</i>
	 * <br/>!!WARNING!!</b></p>
	 */
	protected void logGeneratedPwd(final String generatedPwd) {
	}

}
