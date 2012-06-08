/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security.authserver;

import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.security.RBCrypt;

/**
 * <p>
 *  Collection of static utility functions.
 * </p>
 *
 * <p>
 * 	Created May 21, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class EmbeddedAuthFunctions {
	
	private static final Logger logger = LoggerFactory.getLogger(EmbeddedAuthFunctions.class);
	
	// ----------------------------------------------------
	
	public static String getSalt(ResourceNode user) {
		if (user == null) {
			return null;
		}
		final String credential = SNOPS.string(SNOPS.singleObject(user, Aras.HAS_CREDENTIAL));
		return RBCrypt.extractSalt(credential);
	}
	
	public static ResourceNode findUserNode(ModelingConversation conversation, String id) {
		final Query query = conversation.createQuery();
		query.addField(Aras.IDENTIFIED_BY, id);
		final QueryResult result = query.getResult();
		if (result.size() > 1) {
			logger.error("More than one user with name '" + id + "' found.");
			throw new IllegalStateException("More than on user with name '" + id + "' found.");
		} else if (result.isEmpty()) {
			return null;
		} else {
			return result.getSingleNode();
		}
	}

}
