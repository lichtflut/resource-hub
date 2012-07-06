/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.Organizer;
import org.arastreju.sge.context.DomainIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private ServiceContext context;

    private ArastrejuGate openGate;

    private ModelingConversation conversation;

    private DomainInitializer initializer;

	// ----------------------------------------------------
	
	/**
	 * @param context The service context.
	 */
	public ArastrejuResourceFactory(ServiceContext context) {
        this.context = context;
	}

    /**
     * Constructor for spring.
     */
    public ArastrejuResourceFactory() {
    }
	
	// ----------------------------------------------------
	
	public ModelingConversation getConversation() {
        if (conversation == null) {
            conversation = gate().startConversation();
        }
        return conversation;
    }

    public Organizer getOrganizer() {
        return gate().getOrganizer();
    }

    // ----------------------------------------------------

    public void closeConversation() {
        if (conversation != null) {
            conversation.close();
            conversation = null;
        }
    }

    public void closeGate() {
        closeConversation();
        if (openGate != null) {
            openGate.close();
            openGate = null;
        }
    }

    // ----------------------------------------------------

    public void setDomainInitializer(DomainInitializer initializer) {
        this.initializer = initializer;
    }

    // ----------------------------------------------------

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
        if (domain == null || DomainIdentifier.MASTER_DOMAIN.equals(domain)) {
            return aras.openMasterGate();
        } else {
            final ArastrejuGate gate = aras.openGate(domain);
            if (initializer != null) {
                initializer.initializeDomain(gate, domain);
            }
            return gate;
        }
    }

}
