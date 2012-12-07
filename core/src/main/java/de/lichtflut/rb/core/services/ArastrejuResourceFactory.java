/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;
import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ConversationContext;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.Organizer;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.context.DomainIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.lichtflut.rb.core.services.impl.SchemaManagerImpl;

import java.util.Arrays;
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

    private ModelingConversation conversation;

    private Map<Context, ModelingConversation> conversationMap = new HashMap<Context, ModelingConversation>();

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
    public ModelingConversation getConversation() {
        if (conversation == null) {
            conversation = gate().startConversation();
            conversation.getConversationContext().setReadContexts(context.getReadContexts());
        }
        assureActive(conversation);
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
            ModelingConversation conversation = conversationMap.get(primary);
            assureActive(conversation);
            return conversation;
        } else {
            ModelingConversation conversation = gate().startConversation(primary, context.getReadContexts());
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

    // ----------------------------------------------------

    public Organizer getOrganizer() {
        return gate().getOrganizer();
    }

    // ----------------------------------------------------

    public void closeConversations() {
        if (conversation != null) {
            conversation.close();
            LOGGER.debug("Closed conversation {}.", conversation.getConversationContext());
            conversation = null;
        }
        for (Context ctx : conversationMap.keySet()) {
            ModelingConversation conv = conversationMap.get(ctx);
            conv.close();
            LOGGER.debug("Closed conversation {}.", conv.getConversationContext());
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
            if (context.getConfig().getDomainValidator() != null) {
                context.getConfig().getDomainValidator().initializeDomain(gate, domain);
            }
        }
        Preinitializer initializer = context.getConfig().getPreinitializer();
        if(initializer!=null){
            initializer.init(gate);
        }
        return gate;
    }

    private void assureActive(ModelingConversation conversation) {
        ConversationContext cc = conversation.getConversationContext();
        if (!cc.isActive()) {
            throw new IllegalStateException("Got inactive conversation from factory: " + cc);
        }
    }

}
