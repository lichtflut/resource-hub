package de.lichtflut.rb.webck.components.identities;

import de.lichtflut.rb.webck.common.CookieAccess;
import org.apache.wicket.Application;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

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

        setVisible(Application.get().usesDevelopmentConfig());
    }

}
