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
package de.lichtflut.rb.core.services;

import java.io.Serializable;

import de.lichtflut.rb.core.config.RBConfig;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.security.RBUser;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.context.ContextID;
import org.arastreju.sge.naming.Namespace;

/**
 * <p>
 *  Context of the backend services.
 * </p>
 *
 * <p>
 * 	Created Jan 19, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ServiceContext implements Serializable{
	
	private final RBConfig config;
	
	private RBUser user;
	
	private String domain = "public";

    private Context conversationContext;

    private Context[] readContexts;

	// ----------------------------------------------------
	
	/**
	 * @param config The config.
	 */
	public ServiceContext(RBConfig config) {
		this.config = config;
	}
	
	/**
	 * @param config The config.
	 * @param domain The current domain.
	 */
	public ServiceContext(RBConfig config, String domain) {
		this(config);
		this.domain = domain;
        initConversationContexts();
	}

    /**
     * @param config The config.
     * @param domain The current domain.
     * @param user The user.
     */
    public ServiceContext(RBConfig config, String domain, RBUser user) {
        this(config);
        this.domain = domain;
        this.user = user;
        initConversationContexts();
    }
	
	/**
	 *  Special constructor for spring.
	 */
	protected ServiceContext() {
		this.config = null;
	}
	
	// ----------------------------------------------------

	public boolean isAuthenticated() {
		return user != null;
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return the user
	 */
	public RBUser getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(RBUser user) {
		this.user = user;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
        initConversationContexts();
	}

    public Context getConversationContext() {
        return conversationContext;
    }

    public Context[] getReadContexts() {
        return readContexts;
    }

    // ----------------------------------------------------

	/**
	 * @return the config
	 */
	public RBConfig getConfig() {
		return config;
	}
	
	// ----------------------------------------------------
	
	@Override
	public String toString() {
		return user + " | " + domain;
	}

    // ----------------------------------------------------

    private void initConversationContexts() {
        conversationContext = ContextID.forContext(Namespace.LOCAL_CONTEXTS, domain);
        readContexts= new Context[] {
                RBSystem.TYPE_SYSTEM_CTX,
                RBSystem.VIEW_SPEC_CTX,
                ContextID.forContext(Namespace.LOCAL_CONTEXTS, domain)
        };
    }


}
