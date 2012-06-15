/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.apriori.migrations;

import static org.arastreju.sge.SNOPS.singleObject;
import static org.arastreju.sge.SNOPS.string;

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryResult;
import org.arastreju.sge.spi.GateContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.security.UserManager;
import de.lichtflut.rb.core.security.authserver.EmbeddedAuthUserManager;
import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * <p>
 *  Migrates user info from domestic domains to root domain.
 * </p>
 *
 * <p>
 * 	Created May 4, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class Migration_0001_ExternalAuthServer {
	
	private static final Logger logger = LoggerFactory.getLogger(Migration_0001_ExternalAuthServer.class);
	
	// ----------------------------------------------------
	
	public void run(ServiceProvider sp) {
		logger.info("Starting migration " + getClass().getSimpleName());
		
		final ArastrejuGate masterGate = sp.getArastejuGate();
		assertDomain(masterGate, GateContext.MASTER_DOMAIN);
		
		final ModelingConversation mc = masterGate.startConversation();
		final Query query = mc.createQuery();
		query.addField(RDF.TYPE, Aras.USER);
		final QueryResult result = query.getResult();
		for (ResourceNode userNode : result) {
			final SemanticNode credential = SNOPS.fetchObject(userNode, Aras.HAS_CREDENTIAL);
			final String domain =  uniqueName(SNOPS.fetchObject(userNode, Aras.BELONGS_TO_DOMAIN));
			final String identifier = SNOPS.string(SNOPS.fetchObject(userNode, Aras.IDENTIFIED_BY));
			if (credential == null && domain != null) {
				mc.remove(userNode);
				moveToMasterDomain(identifier, domain, mc, sp);
			}
		}
		mc.close();
		logger.info("Migration done" + getClass().getSimpleName());
	}

	// ----------------------------------------------------

	private void moveToMasterDomain(String identifier, String domainName, ModelingConversation masterConversation, ServiceProvider sp) {
		logger.info("Migrating user " + identifier);
		
		sp.getContext().setDomain(domainName);
		final ArastrejuGate domainGate = sp.getArastejuGate();
		assertDomain(domainGate, domainName);
		
		final ResourceNode domain = findDomainNode(domainName, masterConversation);
		
		final ModelingConversation domainConversation = domainGate.startConversation();
		
		final UserManager um = new EmbeddedAuthUserManager(domainConversation, null);
		
		final RBUser domainUser = um.findUser(identifier);
		final ResourceNode user = domainConversation.findResource(domainUser.getQualifiedName());
		final String credential = SNOPS.string(SNOPS.fetchObject(user, Aras.HAS_CREDENTIAL));
		final String email = SNOPS.string(SNOPS.fetchObject(user, RBSystem.HAS_EMAIL));
		final String username = SNOPS.string(SNOPS.fetchObject(user, RBSystem.HAS_USERNAME));
		
		final SNResource masterUser = new SNResource(user.getQualifiedName());
		masterUser.addAssociation(RBSystem.HAS_EMAIL, new SNText(email));
		masterUser.addAssociation(Aras.IDENTIFIED_BY, new SNText(email));
		if (username != null) {
			masterUser.addAssociation(RBSystem.HAS_USERNAME, new SNText(username));
			masterUser.addAssociation(Aras.IDENTIFIED_BY, new SNText(username));
		}
		masterUser.addAssociation(Aras.HAS_CREDENTIAL, new SNText(credential));
		masterUser.addAssociation(Aras.BELONGS_TO_DOMAIN, domain);
		masterConversation.attach(masterUser);
		
		domainConversation.close();
		
		SNOPS.remove(user, Aras.IDENTIFIED_BY);
		SNOPS.remove(user, Aras.HAS_CREDENTIAL);
		SNOPS.remove(user, RBSystem.HAS_EMAIL);
		SNOPS.remove(user, RBSystem.HAS_USERNAME);
	}
	
	private void assertDomain(final ArastrejuGate gate, String domain) {
		if (!domain.equals(gate.getContext().getDomain())) {
			throw new IllegalStateException("Not in expected domain '" + domain + "' - it is '" + gate.getContext().getDomain());
		}
	}
	
	private String uniqueName(SemanticNode node) {
		if (node != null && node.isResourceNode()) {
			return string(singleObject(node.asResource(), Aras.HAS_UNIQUE_NAME));
		} else {
			return null;
		}
	}
	
	private ResourceNode findDomainNode(String domain, ModelingConversation conversation) {
		if (domain == null) {
			throw new IllegalArgumentException("Domain name may not be null.");
		}
		final Query query = conversation.createQuery()
				.addField(RDF.TYPE, Aras.DOMAIN)
				.and()
				.addField(Aras.HAS_UNIQUE_NAME, domain);
		final ResourceNode domainNode = query.getResult().getSingleNode();
		return domainNode;
	}

}
