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
package de.lichtflut.rb.core.security.authserver;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.security.RBCrypt;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.arastreju.sge.SNOPS.date;
import static org.arastreju.sge.SNOPS.singleObject;
import static org.arastreju.sge.SNOPS.string;

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
		final String credential = SNOPS.string(SNOPS.singleObject(user, EmbeddedAuthModule.HAS_CREDENTIAL));
		return RBCrypt.extractSalt(credential);
	}
	
	public static ResourceNode findUserNode(Conversation conversation, String id) {
		final Query query = conversation.createQuery();
		query.addField(EmbeddedAuthModule.IDENTIFIED_BY, id);
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

    public static RBDomain toRBDomain(SemanticNode node) {
        if (!node.isResourceNode()) {
            throw new IllegalArgumentException("Value nodes can not represent a domain: " + node);
        }
        ResourceNode resource = node.asResource();
        RBDomain domain = new RBDomain(resource.getQualifiedName());
        domain.setName(uniqueName(resource));
        domain.setTitle(string(singleObject(resource, EmbeddedAuthModule.HAS_TITLE)));
        domain.setDescription(string(singleObject(resource, EmbeddedAuthModule.HAS_DESCRIPTION)));
        return domain;
    }

    public static RBUser toRBUser(final ResourceNode userNode) {
        RBUser user = new RBUser(userNode.getQualifiedName());
        user.setEmail(string(SNOPS.singleObject(userNode, RBSystem.HAS_EMAIL)));
        user.setUsername(string(SNOPS.singleObject(userNode, RBSystem.HAS_USERNAME)));
        user.setLastLogin(date(SNOPS.singleObject(userNode, RBSystem.HAS_LAST_LOGIN)));
        SemanticNode domain = SNOPS.singleObject(userNode, EmbeddedAuthModule.BELONGS_TO_DOMAIN);
        if(domain!=null){
            user.setDomesticDomain(string(singleObject(domain.asResource(), EmbeddedAuthModule.HAS_UNIQUE_NAME)));
        }
        return user;
    }

    public static String uniqueName(SemanticNode node) {
        if (node != null && node.isResourceNode()) {
            return string(singleObject(node.asResource(), EmbeddedAuthModule.HAS_UNIQUE_NAME));
        } else {
            return null;
        }
    }

}
