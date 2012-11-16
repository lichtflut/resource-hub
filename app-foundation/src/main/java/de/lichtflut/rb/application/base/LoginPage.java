/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.base;

import de.lichtflut.rb.application.custom.RequestAccountPage;
import de.lichtflut.rb.application.custom.ResetPasswordPage;
import de.lichtflut.rb.core.security.LoginData;
import de.lichtflut.rb.webck.components.login.LoginPanel;
import de.lichtflut.rb.webck.models.infra.VersionInfoModel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;

/**
 * <p>
 *  Standard login page for RB applications.
 * </p>
 * 
 * <p>
 *  Created Dec 8, 2011
 * </p>
 * 
 * @author Oliver Tigges
 */
public class LoginPage extends AbstractLoginPage {

    // ----------------------------------------------------

	/**
	 * Constructor.
	 */
	public LoginPage() {

		add(new LoginPanel("loginPanel") {
            @Override
            public void onLogin(LoginData loginData) {
                tryLogin(loginData);
            }
        });

		add(new Link<String>("resetEmail") {
			@Override
			public void onClick() {
				setResponsePage(ResetPasswordPage.class);
			}
		});

		add(new Link<String>("requestAccount") {
			@Override
			public void onClick() {
				setResponsePage(RequestAccountPage.class);
			}
		});

        final VersionInfoModel model = new VersionInfoModel();
        add(new Label("version", new PropertyModel<String>(model, "version")));
        add(new Label("build", new PropertyModel<String>(model, "buildTimestamp")));
	}

}
