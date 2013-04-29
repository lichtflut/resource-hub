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
package de.lichtflut.rb.webck.components.identities;

import de.lichtflut.rb.webck.common.CookieAccess;
import org.apache.wicket.Application;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.protocol.http.WebSession;

/**
 * <p>
 *  Panel for session and session ticket debugging.
 * </p>
 *
 * <p>
 *  Created 14.09.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class SessionInfoPanel extends Panel {

    public SessionInfoPanel(String id) {
        super(id);

        String ticket = CookieAccess.getInstance().getSessionToken();
        add(new Label("ticket", ticket));

        add(new Label("sessionSize", calcSessionSize()));

        setVisible(Application.get().usesDevelopmentConfig());
    }

    // ----------------------------------------------------

    private String calcSessionSize() {
        if (!WebSession.exists()) {
            return "0";
        }

        WebSession webSession = WebSession.get();
        return webSession.getSizeInBytes() + " bytes";
    }

}
