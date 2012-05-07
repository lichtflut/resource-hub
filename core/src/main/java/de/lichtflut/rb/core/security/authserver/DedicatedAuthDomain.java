/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security.authserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ArastrejuProfile;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryResult;
import org.arastreju.sge.security.Domain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.security.ExternalAuthServer;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ServiceProvider;

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
public class DedicatedAuthDomain implements ExternalAuthServer {
	
	private final Logger logger = LoggerFactory.getLogger(DedicatedAuthDomain.class);
	
	private final ServiceProvider provider;
	
	// ----------------------------------------------------
	
	/**
	 * @param provider
	 */
	public DedicatedAuthDomain(ServiceProvider provider) {
		this.provider = provider;
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void registerUser(RBUser user, String domain) {
		final ArastrejuGate masterGate = masterGate();
		final ModelingConversation mc = masterGate.startConversation();
		final ResourceNode userNode = new SNResource();
		userNode.addAssociation(RDF.TYPE, Aras.USER, Aras.IDENT);
		userNode.addAssociation(Aras.BELONGS_TO_DOMAIN, new SNText(domain), Aras.IDENT);
		
		SNOPS.associate(userNode, Aras.IDENTIFIED_BY, new SNText(user.getEmail()), Aras.IDENT);
		if (user.getUsername() != null) {
			SNOPS.associate(userNode, Aras.IDENTIFIED_BY, new SNText(user.getUsername()), Aras.IDENT);
		}
		
		mc.attach(userNode);
		logger.info("Registered user in master domain: " + user + " --> " + domain);
		masterGate.close();
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void updateUserIdentity(RBUser existing, RBUser updated, String domain) {
		final ArastrejuGate masterGate = masterGate();
		final Query query = masterGate.createQueryManager().buildQuery()
				.addField(Aras.IDENTIFIED_BY, existing.getEmail())
				.or()
				.addField(Aras.IDENTIFIED_BY, existing.getUsername());
		final QueryResult result = query.getResult();
		if (!result.isEmpty()) {
			final ResourceNode targetUser = result.getSingleNode();
			SNOPS.remove(targetUser, Aras.IDENTIFIED_BY);
			SNOPS.associate(targetUser, Aras.IDENTIFIED_BY, new SNText(updated.getEmail()), Aras.IDENT);
			if (updated.getUsername() != null) {
				SNOPS.associate(targetUser, Aras.IDENTIFIED_BY, new SNText(updated.getUsername()), Aras.IDENT);
			}
			logger.info("Updated user {} in master domain to {}.", existing, updated);
		} else {
			logger.info("User not found in master domain: " + existing + " --> " + domain);
			registerUser(updated, domain);
		}
		masterGate.close();
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void deleteUser(RBUser user) {
		final ArastrejuGate masterGate = masterGate();
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
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Collection<RBDomain> getAllDomains() {
		final List<RBDomain> result = new ArrayList<RBDomain>();
		final Collection<Domain> domains = masterGate().getOrganizer().getDomains();
		for (Domain current : domains) {
			final RBDomain rbDomain = new RBDomain(current.getQualifiedName());
			rbDomain.setName(current.getUniqueName());
			rbDomain.setTitle(current.getTitle());
			rbDomain.setDescription(current.getDescription());
			result.add(rbDomain);
		}
		return result;
	}
	
	// ----------------------------------------------------
	
	protected ArastrejuGate masterGate() {
		final ArastrejuProfile profile = provider.getContext().getConfig().getArastrejuConfiguration();
		return Arastreju.getInstance(profile).rootContext();
	}

	
	
}
