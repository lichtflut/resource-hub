package de.lichtflut.rb.application;

import de.lichtflut.rb.core.services.ArastrejuResourceFactory;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *  RB specific request cycle listener.
 * </p>
 *
 * <p>
 * 	Created Jun 29, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBRequestCycleListener extends AbstractRequestCycleListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RBRequestCycleListener.class);

    // ----------------------------------------------------

    @Override
    public void onEndRequest(RequestCycle cycle) {
        try {
            new ConversationCloser().detach();
        } catch (Exception e) {
            LOGGER.warn("Conversation could not be closed for request {} due to exception: {}",
                    cycle.getRequest().getUrl(), e.getMessage());
        }
    }

    // ----------------------------------------------------

    private static class ConversationCloser {

        @SpringBean
        private ArastrejuResourceFactory arastrejuResourceFactory;

        private ConversationCloser() {
            Injector.get().inject(this);
        }

        public void detach() {
            arastrejuResourceFactory.closeConversation();
        }
    }

}