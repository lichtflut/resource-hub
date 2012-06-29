package de.lichtflut.rb.application;

import de.lichtflut.rb.core.services.ArastrejuResourceFactory;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;


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

    @Override
    public void onDetach(RequestCycle cycle) {
          new ConversationCloser().detach();
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