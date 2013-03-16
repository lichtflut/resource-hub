/*
 * Copyright 2013 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import de.lichtflut.rb.core.config.RBConfig;
import de.lichtflut.rb.core.system.DomainSupervisor;
import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.ConversationContext;
import org.arastreju.sge.organize.Organizer;
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
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ArastrejuResourceFactory.class);

	private ServiceContext context;

    private ArastrejuGate openGate;

    private Map<Context, Conversation> conversationMap = new HashMap<Context, Conversation>();

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
    protected ArastrejuResourceFactory() {
    }
	
	// -- CONVERSATIONS -----------------------------------

    /**
     * Get the current conversation managed by this factory. This conversations is shared.
     * The caller may not change this conversation's state or close this conversation.
     * @return The current conversation.
     */
	@Override
    public Conversation getConversation() {
        return getConversation(context.getConversationContext());
    }

    /**
     * Get a conversation for the given context managed by this factory. This conversations may be shared.
     * The caller may not change this conversation's state or close this conversation.
     * @param primary The primary context of this conversation.
     * @return The current conversation.
     */
    @Override
    public Conversation getConversation(Context primary) {
        if (conversationMap.containsKey(primary)) {
            Conversation conversation = conversationMap.get(primary);
            assureActive(conversation);
            return conversation;
        } else {
            Conversation conversation = gate().startConversation(primary, context.getReadContexts());
            conversationMap.put(primary, conversation);
            return conversation;
        }
    }

    /**
     * Start a new conversation. This conversations is not shared and has to be managed by the caller.
     * @return The new conversation.
     */
    @Override
    public Conversation startConversation() {
        return gate().startConversation();
    }

    // ----------------------------------------------------

    public Organizer getOrganizer() {
        return new Organizer(gate());
    }

    // ----------------------------------------------------

    public void closeConversations() {
        for (Context ctx : conversationMap.keySet()) {
            Conversation conversation = conversationMap.get(ctx);
            conversation.close();
            LOGGER.debug("Closed conversation {}.", conversation.getConversationContext());
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

    protected synchronized ArastrejuGate gate() {
        if (openGate == null) {
            openGate = openGate();
        }
        return openGate;
    }

    // ----------------------------------------------------

    private ArastrejuGate openGate() {
        final String domain = context.getDomain();
        LOGGER.debug("Opening Arastreju Gate for domain {} ", domain);
        ArastrejuGate gate = null;
        final Arastreju aras = Arastreju.getInstance(context.getConfig().getArastrejuProfile());
        if (domain == null || DomainIdentifier.MASTER_DOMAIN.equals(domain)) {
            gate = aras.openMasterGate();
        } else {
            gate = aras.openGate(domain);
            ensureInitialized(gate, domain);
        }
        return gate;
    }

    private void ensureInitialized(ArastrejuGate gate, String domain) {
        RBConfig config = context.getConfig();
        DomainSupervisor supervisor = config.getDomainSupervisor();
        supervisor.onOpen(gate, domain);

    }

    private void assureActive(Conversation conversation) {
        ConversationContext cc = conversation.getConversationContext();
        if (!cc.isActive()) {
            throw new IllegalStateException("Got inactive conversation from factory: " + cc);
        }
    }

}
