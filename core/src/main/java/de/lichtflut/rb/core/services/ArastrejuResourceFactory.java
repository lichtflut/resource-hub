/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.Organizer;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.context.DomainIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

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
public class ArastrejuResourceFactory implements ConversationFactory {
	
	private final static Logger logger = LoggerFactory.getLogger(ArastrejuResourceFactory.class);

	private ServiceContext context;

    private ArastrejuGate openGate;

    private ModelingConversation conversation;

    private Map<Context, ModelingConversation> conversationMap = new HashMap<Context, ModelingConversation>();

    private DomainInitializer initializer;

	// ----------------------------------------------------
	
	/**
     * Constructor.
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
	
	// -- CONVERSATIONS -----------------------------------

    /**
     * Get the current conversation managed by this factory. This conversations is shared.
     * The caller may not change this conversation's state or close this conversation.
     * @return The current conversation.
     */
	@Override
    public ModelingConversation getConversation() {
        if (conversation == null) {
            conversation = gate().startConversation();
        }
        return conversation;
    }

    /**
     * Get a conversation for the given context managed by this factory. This conversations may be shared.
     * The caller may not change this conversation's state or close this conversation.
     * @param primary The primary context of this conversation.
     * @return The current conversation.
     */
    @Override
    public ModelingConversation getConversation(Context primary) {
        if (conversationMap.containsKey(primary)) {
            return conversationMap.get(primary);
        } else {
            ModelingConversation conversation = gate().startConversation(primary);
            conversationMap.put(primary, conversation);
            return conversation;
        }
    }

    /**
     * Start a new conversation. This conversations is not shared and has to be managed by the caller.
     * @return The new conversation.
     */
    @Override
    public ModelingConversation startConversation() {
        return gate().startConversation();
    }

    /**
     * Start a new conversation for the given context . This conversations is not shared and has to be managed by
     * the caller.
     * @param primary The primary context of this conversation.
     * @return The new conversation.
     */
    @Override
    public ModelingConversation startConversation(Context primary) {
        return gate().startConversation(primary);
    }

    // ----------------------------------------------------

    public Organizer getOrganizer() {
        return gate().getOrganizer();
    }

    // ----------------------------------------------------

    public void closeConversations() {
        if (conversation != null) {
            conversation.close();
            conversation = null;
        }
        for (Context ctx : conversationMap.keySet()) {
            conversationMap.get(ctx).close();
        }
        conversationMap.clear();
    }

    public void closeGate() {
        closeConversations();
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
