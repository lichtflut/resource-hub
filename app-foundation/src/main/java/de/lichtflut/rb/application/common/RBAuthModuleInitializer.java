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
package de.lichtflut.rb.application.common;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.model.SimpleResourceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import de.lichtflut.rb.application.extensions.RBDomainAuthInitializer;
import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.DomainManager;
import de.lichtflut.rb.core.security.RBCrypt;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.security.UserManager;

/**
 * <p>
 *  Glasnost specific model initializers.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBAuthModuleInitializer implements ApplicationListener<ContextRefreshedEvent> {
	
	public static final String ROOT_USER = "root";
	
	private final static Logger logger = LoggerFactory.getLogger(RBAuthModuleInitializer.class);
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public RBAuthModuleInitializer() {
		logger.info("RBAuthModuleInitializer created.");
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("Application context started.");
		AuthModule authModule = event.getApplicationContext().getBean(AuthModule.class);
		createRootDomain(authModule.getDomainManager());
		createRootUser(authModule.getUserManagement());
	}

	// ----------------------------------------------------
	
	public RBDomain createRootDomain(DomainManager domainManager) {
		RBDomain domain = domainManager.findDomain(AuthModule.MASTER_DOMAIN);
		if (domain != null) {
			return domain;
		}
		domain = new RBDomain();
		domain.setName(AuthModule.MASTER_DOMAIN);
		domain.setDescription("The master domain");
		domain.setTitle("Master Domain");
		domainManager.registerDomain(domain);
		new RBDomainAuthInitializer().initialize(domain, domainManager);
		return domain;
	}
	
	public RBUser createRootUser(UserManager userMgmt) {
		RBUser root = userMgmt.findUser(ROOT_USER);
		if (root != null) {
			return root;
		}
		try {
			logger.info("Registering root user");
			root = new RBUser(new SimpleResourceID().getQualifiedName());
			root.setEmail("root@system");
			root.setUsername(ROOT_USER);
			userMgmt.registerUser(root, RBCrypt.encrypt("root"), AuthModule.MASTER_DOMAIN);
			userMgmt.setUserRoles(root, AuthModule.MASTER_DOMAIN, rootRoles());
			return root;
		} catch (RBAuthException e) {
			logger.error("Root user could not be registered", e);
			throw new RuntimeException("Root domain could not be initialized (user creation failed)");
		}
	}
	
	// ----------------------------------------------------
	
	private List<String> rootRoles() {
		List<String> roles = new ArrayList<String>();
		for (RBRole current : RBRole.values()) {
			roles.add(current.name());
		}
		return roles;
	}

}
