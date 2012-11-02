/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.extensions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.lichtflut.rb.application.common.RBRole;
import de.lichtflut.rb.core.security.AuthDomainInitializer;
import de.lichtflut.rb.core.security.DomainManager;
import de.lichtflut.rb.core.security.RBDomain;

/**
 * <p>
 * Initializes roles and permissions for new domains in Auth Module. This class can be used to
 * initialize an AuthModule, usually wired by spring.
 * </p>
 * 
 * <p>
 * Created May 11, 2012
 * </p>
 * 
 * @author Oliver Tigges
 */
public class RBDomainAuthInitializer implements AuthDomainInitializer {

	@Override
	public void initialize(RBDomain domain, DomainManager domainManager) {
		final List<String> roles = new ArrayList<String>();
		for (de.lichtflut.rb.application.common.RBRole role : RBRole.values()) {
			final Set<String> permissions = new HashSet<String>();
			for (de.lichtflut.rb.application.common.RBPermission permission : role.getPermissions()) {
				permissions.add(permission.name());
			}
			roles.add(role.name());
			domainManager.registerRole(domain.getName(), role.name(), permissions);
		}
	}

}