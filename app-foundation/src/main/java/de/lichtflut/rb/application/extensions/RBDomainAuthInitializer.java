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