/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.persistence.ResourceResolver;
import org.arastreju.sge.spi.GateContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.MessagingService;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.core.services.ViewSpecificationService;

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
public abstract class AbstractServiceProvider implements ServiceProvider{
	
	private final Logger logger = LoggerFactory.getLogger(AbstractServiceProvider.class);

	private final Set<String> initializedDomains = new HashSet<String>();
	
	private final ServiceContext ctx;
	
	private ArastrejuGate openGate;
	
	private SchemaManager schemaManager;
	private EntityManager entityManager;
	private TypeManager typeManager;
	private SecurityService securityService;
	private MessagingService messagingService;
	private ViewSpecificationService viewSpecService;
	private DomainOrganizer domainOrganizer;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 */
	public AbstractServiceProvider(ServiceContext ctx) {
		this.ctx = ctx;
		schemaManager = new SchemaManagerImpl(this);
		entityManager = new EntityManagerImpl(this);
		typeManager = new TypeManagerImpl(this);
		messagingService = new MessagingServiceImpl(this);
		domainOrganizer = newDomainOrganizer();
		securityService = newSecurityService();
		viewSpecService = newViewSpecificationServiceImpl();
	}

	// ----------------------------------------------------
	
	/**
	 * @return
	 */
	protected DomainOrganizer newDomainOrganizer() {
		return new DomainOrganizerImpl(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArastrejuGate getArastejuGate() {
		if (openGate != null) {
			return openGate;
		}
		if (ctx.isAuthenticated()) {
			openGate = openGate(ctx.getDomain());
			return openGate;
		} else {
			logger.info("Creating default Arastreju Gate for unauthenticated user.");
			return openGate = openGate(GateContext.MASTER_DOMAIN);
		}
	}
	
	 /** 
     * {@inheritDoc}
     */
    @Override
    public ArastrejuGate getArastejuGate(String domain) {
    	return openGate(domain);
    }
	
	// ----------------------------------------------------
	
	/**
	 *{@inheritDoc}
	 */
	public ServiceContext getContext() {
		return ctx;
	};
	
	/**
	 *{@inheritDoc}
	 */
	@Override
	public EntityManager getEntityManager() {
 	    return entityManager;
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public SchemaManager getSchemaManager() {
	    return schemaManager;
	}

	/**
	 *{@inheritDoc}
	 */
	@Override
	public TypeManager getTypeManager() {
		return typeManager;
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public DomainOrganizer getDomainOrganizer() {
		return domainOrganizer;
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public SecurityService getSecurityService() {
		return securityService;
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public ResourceResolver getResourceResolver() {
		return getArastejuGate().startConversation();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public MessagingService getMessagingService(){
		return messagingService;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ViewSpecificationService getViewSpecificationService() {
		return viewSpecService;
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
	 * @return The security service.
	 */
	protected SecurityService newSecurityService() {
		return new SecurityServiceImpl(this);
	}
	
	/**
	 * @return The view specification service.
	 */
	protected ViewSpecificationService newViewSpecificationServiceImpl() {
		return new ViewSpecificationServiceImpl(this);
	}
	
	// ----------------------------------------------------
	
	protected synchronized ArastrejuGate openGate(String domain) {
		final Arastreju aras = Arastreju.getInstance(getContext().getConfig().getArastrejuConfiguration());
		logger.debug("Opening Arastreju Gate for domain {} ", domain);
		if (domain == null || GateContext.MASTER_DOMAIN.equals(domain)) {
			openGate = aras.rootContext();
		} else {
			getContext().setDomain(domain);
			openGate = aras.rootContext(domain);
			if (!initializedDomains.contains(domain)) {
				initializeDomain(openGate, domain);
				initializedDomains.add(domain);
			}
		}
		return openGate;
	}

}