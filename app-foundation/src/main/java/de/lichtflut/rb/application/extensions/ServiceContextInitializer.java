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

import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.common.RBWebSession;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.spring.injection.annot.SpringBean;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Initialized the Service Provider after login with context information.
 * </p>
 * 
 * <p>
 * Created Feb 8, 2012
 * </p>
 * 
 * @author Oliver Tigges
 */
public class ServiceContextInitializer {

	@SpringBean
	private ServiceContext context;

	// ----------------------------------------------------

	public ServiceContextInitializer() {
		Injector.get().inject(this);
		Injector.get().inject(RBWebSession.get());
	}

	public void init(RBUser user, String domain) {
		context.setUser(user);
		context.setDomain(domain);
	}

}
