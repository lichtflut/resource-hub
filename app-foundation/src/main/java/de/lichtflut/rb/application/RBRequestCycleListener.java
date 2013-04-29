/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
            arastrejuResourceFactory.closeGate();
        }
    }

}