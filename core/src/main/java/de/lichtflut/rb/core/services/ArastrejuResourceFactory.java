/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.Organizer;
import org.arastreju.sge.spi.GateContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *  Factory used by Spring to create Arastreju resources.
 * </p>
 *
 * <p>
 * 	Created Jun 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ArastrejuResourceFactory {
	
	private final static Logger logger = LoggerFactory.getLogger(ArastrejuResourceFactory.class);

    protected final Set<String> initializedDomains = new HashSet<String>();

	private ServiceContext context;

    private ArastrejuGate openGate;

    private ModelingConversation conversation;

	// ----------------------------------------------------
	
	/**
	 * @param context
	 */
	public ArastrejuResourceFactory(ServiceContext context) {
		logger.info("ArastrejuResourceFactory has been created for context {}." , context);
        this.context = context;
	}

    /**
     * Constructor for spring.
     */
    public ArastrejuResourceFactory() {
    }
	
	// ----------------------------------------------------
	
	public ModelingConversation getConversation() {
		logger.info("Conversation has been requested.");
        if (conversation == null) {
            conversation = gate().startConversation();
        }
        return conversation;
    }

    public Organizer getOrganizer() {
        return gate().getOrganizer();
    }

    // ----------------------------------------------------

    /**
     * Hook for domain initialization.
     * @param gate The gate to this domain.
     * @param domainName The domain name.
     */
    protected void initializeDomain(ArastrejuGate gate, String domainName) {
    }

    protected synchronized ArastrejuGate gate() {
        if (openGate == null) {
            openGate = openGate();
        }
        return openGate;
    }

    // ----------------------------------------------------

    private ArastrejuGate openGate() {
        final String domain = context.getDomain();
        logger.debug("Opening Arastreju Gate for domain {} ", domain);

        final Arastreju aras = Arastreju.getInstance(context.getConfig().getArastrejuConfiguration());
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
