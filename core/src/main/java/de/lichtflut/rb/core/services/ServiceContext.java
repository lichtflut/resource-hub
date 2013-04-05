/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import java.io.Serializable;

import de.lichtflut.rb.core.config.RBConfig;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.security.RBUser;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.context.SimpleContextID;
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
        conversationContext = new SimpleContextID(Namespace.LOCAL_CONTEXTS, domain);
        readContexts= new Context[] {
                RBSystem.TYPE_SYSTEM_CTX,
                RBSystem.VIEW_SPEC_CTX,
                new SimpleContextID(Namespace.LOCAL_CONTEXTS, domain)
        };
    }


}
