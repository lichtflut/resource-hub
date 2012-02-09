/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.IdentityManagement;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.eh.ArastrejuException;
import org.arastreju.sge.eh.ArastrejuRuntimeException;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
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
import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.eh.RBException;
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
	public User createUser(String emailID, String username, String password) throws RBException {
		final String crypted = Crypt.md5Hex(password);
		final Credential credential = new PasswordCredential(crypted);
		User registered = null;
		try {
			registered = identityManagement().register(emailID, credential);
			if (username != null) {
				try {
					setAlternateID(registered, username);
				} catch(RBException rbe) {
					// remove the new created user, if alternateID can't be set
					final ResourceID id = new SimpleResourceID(registered.getAssociatedResource().getQualifiedName());
					gate().startConversation().remove(id);
					throw rbe;
				}
			}
			SNOPS.assure(registered.getAssociatedResource(), Aras.HAS_EMAIL, new SNText(emailID), Aras.IDENT);
			final String domain = getProvider().getContext().getDomain();
			if (domain != null && !gate().getContext().isMasterDomain()) {
				registerUserInMasterDomain(registered, domain);
			}
		} catch(ArastrejuException e) {
			if(e.getErrCode().equals(org.arastreju.sge.eh.ErrorCodes.REGISTRATION_NAME_ALREADY_IN_USE)) {
				throw new RBException(ErrorCodes.SECURITYSERVICE_ID_ALREADY_IN_USE, 
						"The ID (emailID) '" + emailID + "' is already in use.");
			} else {
				logger.error("Unexpected ArastrejuException while trying to register user: ", e);
			}
		}
		return registered;
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
			throw new ArastrejuRuntimeException(org.arastreju.sge.eh.ErrorCodes.GENERAL_RUNTIME_ERROR);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void changePrimaryID(User user, String emailID) throws RBException {
		String newID = emailID.trim().toLowerCase();
		if(!newID.equals(user.getName())) {
			ModelingConversation mc = gate().startConversation();
			IdentityManagement im = identityManagement();
			ResourceNode userNode = user.getAssociatedResource();
			mc.attach(userNode);
			try {
				im.changeID(user, newID);
				SNOPS.assure(userNode, Aras.HAS_EMAIL, new SNText(newID), Aras.IDENT);
			} catch (ArastrejuException e) {
				if(e.getErrCode().equals(org.arastreju.sge.eh.ErrorCodes.REGISTRATION_NAME_ALREADY_IN_USE)) {
					throw new RBException(ErrorCodes.SECURITYSERVICE_ID_ALREADY_IN_USE, 
							"The ID (email) '" + newID + "' is already in use.");
				} else {
					logger.error("Unexpected ArastrejuException while trying to change primaryID/email: ", e);
				}
			}
			mc.close();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAlternateID(User user, String alternateID) throws RBException {
		if(!alternateID.equals(getAlternateID(user))) {
			ModelingConversation mc = gate().startConversation();
			IdentityManagement im = identityManagement();
			ResourceNode userNode = user.getAssociatedResource();
			mc.attach(userNode);
			SemanticNode oldAlternateID = getAlternateIDNode(userNode);
			try {
				im.registerAlternateID(user, alternateID.trim().toLowerCase());
			} catch (ArastrejuException e) {
				if(e.getErrCode().equals(org.arastreju.sge.eh.ErrorCodes.REGISTRATION_NAME_ALREADY_IN_USE)) {
					throw new RBException(ErrorCodes.SECURITYSERVICE_ALTERNATEID_ALREADY_IN_USE, 
							"The AlternateID (username) '" + alternateID + "' is already in use.");
				} else {
					logger.error("Unexpected ArastrejuException while trying to register alternateID: ", e);
				}
			}
			// oldID will only be removed if new ID is registered without exception thrown
			SNOPS.remove(userNode, Aras.IDENTIFIED_BY, oldAlternateID);
			mc.close();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAlternateID(User user) {
		String result = "";
		SemanticNode alternateIDNode = getAlternateIDNode(user.getAssociatedResource());
		if(alternateIDNode != null) {
			result = SNOPS.string(alternateIDNode);
		}
		return result;
	}
	
	private SemanticNode getAlternateIDNode(ResourceNode userNode) {
		if (userNode != null) {
			final SemanticNode uniqueNameNode = SNOPS.fetchObject(userNode, Aras.HAS_UNIQUE_NAME);
			Set<SemanticNode> identifications = SNOPS.objects(userNode, Aras.IDENTIFIED_BY);
		    for (SemanticNode identificationNode : identifications) {
		    	if(!uniqueNameNode.equals(identificationNode)) {
		    		return identificationNode;
		    	}
			}
		}
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUserRoles(User user, List<String> roles) {
		ModelingConversation mc = gate().startConversation();
		IdentityManagement im = identityManagement();
		mc.attach(user.getAssociatedResource());
		SNOPS.remove(user.getAssociatedResource(), Aras.HAS_ROLE);
		for (String current : roles) {
			im.addUserToRoles(user, im.registerRole(current));
		}
		mc.close();
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
	public void setNewPassword(User user, String currentPassword, String newPassword) throws RBException{
		verifyPassword(user, currentPassword);
		ResourceNode userNode = user.getAssociatedResource();
		setPassword(newPassword, userNode);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void resetPasswordForUser(User user) {
		//TODO change generated pwd 
		String newPwd = "changed";
		String generatedPwd = Crypt.md5Hex(newPwd);
		setPassword(generatedPwd, user.getAssociatedResource());
		getProvider().getMessagingService().getEmailService().sendPasswordInformation(user, newPwd);
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

	/**
	 * Verifies a password against a {@link User}
	 * @param user
	 * @param md5Password
	 * @throws RBException if password is invalid
	 */
	private void verifyPassword(User user, String md5Password) throws RBException{
		final SemanticNode credential = SNOPS.singleObject(user.getAssociatedResource(), Aras.HAS_CREDENTIAL);
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
