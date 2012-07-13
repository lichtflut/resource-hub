/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security.authserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.infra.Infra;
import de.lichtflut.infra.security.Crypt;
import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.common.SearchTerm;
import de.lichtflut.rb.core.eh.EmailAlreadyInUseException;
import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.eh.InvalidPasswordException;
import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.eh.UsernameAlreadyInUseException;
import de.lichtflut.rb.core.security.RBCrypt;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.security.SearchResult;
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
public class EmbeddedAuthUserManager implements UserManager {
	
	private final Logger logger = LoggerFactory.getLogger(EmbeddedAuthUserManager.class);
	
	private final ModelingConversation conversation;
	
	private final EmbeddedAuthAuthorizationManager authorization;

	private final EmbeddedAuthDomainManager domainManager;

	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param conversation The conversation.
	 * @param domainManager The domain manager.
	 */
	public EmbeddedAuthUserManager(ModelingConversation conversation, EmbeddedAuthDomainManager domainManager) {
		this.domainManager = domainManager;
		this.conversation = conversation;
		this.authorization = new EmbeddedAuthAuthorizationManager(conversation, domainManager);
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
	public SearchResult<RBUser> searchUsers(String term) {
		final SearchTerm searchTerm = new SearchTerm(term);
		final Query query = conversation.createQuery()
				.addField(Aras.IDENTIFIED_BY, searchTerm.prepareTerm());
		return new QueryBasedSearchResult<RBUser>(query.getResult()) {
			@Override
			protected RBUser map(ResourceNode node) {
				return new RBUser(node);
			}
			
		};
	}
	
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
		if (domain == null) {
			throw new RBAuthException(ErrorCodes.SECURITY_DOMAIN_NOT_FOUND, "Domain unknown: " + domainName);
		}
		final ResourceNode userNode = new SNResource(user.getQualifiedName());
		userNode.addAssociation(RDF.TYPE, Aras.USER);
		userNode.addAssociation(Aras.BELONGS_TO_DOMAIN, domain);
		userNode.addAssociation(Aras.HAS_CREDENTIAL, new SNText(credential));

		SNOPS.assure(userNode, RBSystem.HAS_EMAIL, user.getEmail());
		SNOPS.associate(userNode, Aras.IDENTIFIED_BY, new SNText(user.getEmail()));
		SNOPS.associate(userNode, Aras.IDENTIFIED_BY, new SNText(user.getQualifiedName().toURI()));
		if (user.getUsername() != null) {
			SNOPS.assure(userNode, RBSystem.HAS_USERNAME, user.getUsername());
			SNOPS.associate(userNode, Aras.IDENTIFIED_BY, new SNText(user.getUsername()));
		}
		
		conversation.attach(userNode);
		logger.info("Registered user: " + user + " --> " + domain);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void updateUser(RBUser updated) throws RBAuthException {
		final ResourceNode attachedUser = conversation.findResource(updated.getQualifiedName());
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
		final List<SNText> identifiers = new ArrayList<SNText>();
		SNOPS.assure(attachedUser, RBSystem.HAS_EMAIL, updated.getEmail());
		identifiers.add(new SNText(updated.getEmail()));
		if (updated.getUsername() != null) {
			SNOPS.assure(attachedUser, RBSystem.HAS_USERNAME, updated.getUsername());
			identifiers.add(new SNText(updated.getUsername()));
		} else {
			SNOPS.remove(attachedUser, RBSystem.HAS_USERNAME);
		}
		SNOPS.assure(attachedUser, Aras.IDENTIFIED_BY, identifiers);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void deleteUser(RBUser user) {
		final Query query = conversation.createQuery()
				.addField(Aras.IDENTIFIED_BY, user.getEmail())
				.or()
				.addField(Aras.IDENTIFIED_BY, user.getUsername());
		final QueryResult result = query.getResult();
		if (!result.isEmpty()) {
			for (ResourceNode id : result) {
				conversation.remove(id);
				logger.info("User deleted user {} from master domain; ID: {} ", user, id);	
			}
		} else {
			logger.warn("User could not be deleted from master domain: " + user);
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void grantAccessToDomain(RBUser user, RBDomain domain) throws RBAuthException {
		final ResourceNode attachedUser = conversation.findResource(user.getQualifiedName());
		final ResourceNode attachedDomain = domainManager.findDomainNode(domain.getName());
		attachedUser.addAssociation(Aras.HAS_ALTERNATE_DOMAIN, attachedDomain);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void revokeAccessToDomain(RBUser user, RBDomain domain) throws RBAuthException {
		final ResourceNode attachedUser = conversation.findResource(user.getQualifiedName());
		final ResourceNode attachedDomain = domainManager.findDomainNode(domain.getName());
		SNOPS.remove(attachedUser, Aras.HAS_ALTERNATE_DOMAIN, attachedDomain);
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
		SNOPS.assure(arasUser, Aras.HAS_CREDENTIAL, credential);
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
	
	protected ResourceNode findUserNode(final String id) {
		return EmbeddedAuthFunctions.findUserNode(conversation, id);
	}
	
	private boolean isIdentifierInUse(String identifier) {
		final Query query = conversation.createQuery();
		query.addField(Aras.IDENTIFIED_BY, identifier);
		if (query.getResult().isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
}
