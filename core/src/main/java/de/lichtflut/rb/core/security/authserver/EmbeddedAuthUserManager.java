/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security.authserver;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.ArastrejuGate;
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

import scala.actors.threadpool.Arrays;
import de.lichtflut.infra.Infra;
import de.lichtflut.infra.security.Crypt;
import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.eh.EmailAlreadyInUseException;
import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.eh.InvalidPasswordException;
import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.eh.UsernameAlreadyInUseException;
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
public class EmbeddedAuthUserManager implements UserManager {
	
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
		if (domain == null) {
			throw new RBAuthException(ErrorCodes.SECURITYSERVICE_DOMAIN_NOT_FOUND, "Domain unknown: " + domainName);
	}
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
	
	protected ResourceNode findUserNode(final String id) {
		return EmbeddedAuthFunctions.findUserNode(masterGate.createQueryManager(), id);
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
