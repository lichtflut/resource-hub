/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.util.Set;

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.IdentityManagement;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.eh.ArastrejuException;
import org.arastreju.sge.eh.ArastrejuRuntimeException;
import org.arastreju.sge.eh.ErrorCodes;
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

import de.lichtflut.infra.security.Crypt;
import de.lichtflut.rb.core.RB;
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
public class SecurityServiceImpl extends AbstractService implements SecurityService {
	
	private final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	
	// ----------------------------------------------------
	
	/**
	 * @param provider
	 */
	public SecurityServiceImpl(ServiceProvider provider) {
		super(provider);
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
			SNOPS.assure(registered.getAssociatedResource(), Aras.HAS_EMAIL, new SNText(emailID), Aras.IDENT);
			final String domain = getProvider().getContext().getDomain();
			if (domain != null && !gate().getContext().isMasterDomain()) {
				registerUserInMasterDomain(registered, domain);
			}
			return registered;
		} catch(ArastrejuException e) {
			return null;
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public User createDomainAdmin(final Domain domain) {
		final String password = Crypt.md5Hex("admin");
		final String emailID = "admin@" + domain.getUniqueName();
		final Credential credential = new PasswordCredential(password);
		try {
			final ArastrejuGate gate = gate(domain.getUniqueName());	
			final IdentityManagement im = gate.getIdentityManagement();
			final User admin = im.register(emailID, credential);
			for (String roleName : rolesOfDomainAdmin()) {
				final Role role = im.registerRole(roleName);
				im.addUserToRoles(admin, role);
			}
			registerUserInMasterDomain(admin, domain.getUniqueName());
			logger.info("created domain admin: " + admin);
			return admin;
		} catch(ArastrejuException e) {
			logger.error("Error while trying to create admin for domain " + domain);
			throw new ArastrejuRuntimeException(ErrorCodes.GENERAL_RUNTIME_ERROR);
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
	public void setNewPassword(User user, String currentPassword, String newPassword) {
		if(verifyPassword(user, currentPassword)){
			ResourceNode userNode = user.getAssociatedResource();
			if(!userNode.isAttached()){
				userNode = getProvider().getResourceResolver().resolve(userNode);
			}
			setNewPassword(newPassword, userNode);
		}
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void resetPasswordForUser(User user) {
		String generatedPwd = Crypt.md5Hex("changed");
		setNewPassword(generatedPwd, user.getAssociatedResource());
		getProvider().getMessagingService().getEmailService().sendNewPasswordforUser(user);
	}
	
	// ----------------------------------------------------
	
	/**
	 * Can be implemented by sub classes.
	 * @return The roles to be added to domain admin.
	 */
	protected String[] rolesOfDomainAdmin() {
		return new String[0];
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
		Set<SemanticNode> ids = SNOPS.objects(user.getAssociatedResource(), Aras.IDENTIFIED_BY);
		for (SemanticNode node : ids) {
			final SNText copy = new SNText(node.asValue().getStringValue());
			SNOPS.associate(userNode, Aras.IDENTIFIED_BY, copy, Aras.IDENT);
		}
		userNode.addAssociation(Aras.BELONGS_TO_DOMAIN, new SNText(domain), Aras.IDENT);
		mc.attach(userNode);
		logger.info("Registering user in master domain: " + user.getName() + " --> " + domain);
		mc.close();
	}
	
	private Boolean verifyPassword(User user, String md5Password){
		final SemanticNode credential = SNOPS.singleObject(user.getAssociatedResource(), Aras.HAS_CREDENTIAL);
		return credential.asValue().getStringValue().equals(md5Password);
	}
	
	private IdentityManagement identityManagement() {
		return getProvider().getArastejuGate().getIdentityManagement();
	}

	/**
	 * @param newPassword
	 * @param userNode
	 */
	private void setNewPassword(String newPassword, ResourceNode userNode) {
		SNValue password = new SNValue(ElementaryDataType.STRING, newPassword);
		SNOPS.assure(userNode, Aras.HAS_CREDENTIAL, password, RB.PRIVATE_CONTEXT);
	}
	
}
