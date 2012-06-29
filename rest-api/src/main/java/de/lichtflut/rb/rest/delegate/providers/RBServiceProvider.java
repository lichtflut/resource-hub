/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.delegate.providers;

import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.services.ArastrejuResourceFactory;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.core.services.impl.SchemaManagerImpl;
import de.lichtflut.rb.core.services.impl.SecurityServiceImpl;
import de.lichtflut.rb.core.services.impl.TypeManagerImpl;
import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.spi.GateContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *  Abstract base for Service Providers.
 * </p>
 *
 * <p>
 * 	Created Jan 10, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBServiceProvider implements ServiceProvider {
	
	private final Logger logger = LoggerFactory.getLogger(RBServiceProvider.class);

	protected final Set<String> initializedDomains = new HashSet<String>();

    // ----------------------------------------------------
	
	private final ServiceContext ctx;

    private final AuthModule authModule;

    private final ArastrejuResourceFactory arastrejuResourceFactory;

    // ----------------------------------------------------
	
	private ArastrejuGate openGate;
	
	private ModelingConversation conversation;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 */
	public RBServiceProvider(ServiceContext ctx, AuthModule authModule) {
		this.ctx = ctx;
        this.authModule = authModule;
        this.arastrejuResourceFactory = new ArastrejuResourceFactory(ctx);
	}

	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArastrejuGate getArastejuGate() {
		if (openGate != null && openGate.getContext().getDomain().equals(ctx.getDomain())) {
			return openGate;
		}
		if (ctx.getDomain() != null) {
			openGate = openGate(ctx.getDomain());
			return openGate;
		} else {
			logger.warn("Creating default Arastreju Gate for unauthenticated user.");
			return openGate = openGate(GateContext.MASTER_DOMAIN);
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ModelingConversation getConversation() {
		if (conversation == null) {
			conversation = getArastejuGate().startConversation();
		} 
		return conversation;
	}
	
	// ----------------------------------------------------
	
	/**
	 *{@inheritDoc}
	 */
	public ServiceContext getContext() {
		return ctx;
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public SchemaManager getSchemaManager() {
	    return new SchemaManagerImpl(getConversation());
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public SecurityService getSecurityService() {
		return new SecurityServiceImpl(getContext(), getConversation(), authModule);
	}

    @Override
    public TypeManager getTypeManager() {
        return new TypeManagerImpl(getConversation(), getSchemaManager());
    }

    // ----------------------------------------------------

	/**
	 * Hook for domain initialization.
	 * @param gate The gate to this domain.
	 * @param domainName The domain name.
	 */
	protected void initializeDomain(ArastrejuGate gate, String domainName) {
	}
	
	// ----------------------------------------------------
	
	/**
	 * @param authModule
	 * @return The security service.
	 */
	protected SecurityService newSecurityService(AuthModule authModule) {
		return new SecurityServiceImpl(getContext(), getConversation(), authModule);
	}

	// ----------------------------------------------------
	
	protected synchronized ArastrejuGate openGate(String domain) {
		final Arastreju aras = Arastreju.getInstance(getContext().getConfig().getArastrejuConfiguration());
		logger.debug("Opening Arastreju Gate for domain {} ", domain);
		if (domain == null || GateContext.MASTER_DOMAIN.equals(domain)) {
			return aras.rootContext();
		} else {
			final ArastrejuGate gate = aras.rootContext(domain);
			if (!initializedDomains.contains(domain)) {
				initializedDomains.add(domain);
				initializeDomain(gate, domain);
			}
			return gate;	
		}
	}
	
}