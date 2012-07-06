/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.extensions;

import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.common.RBWebSession;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;

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
