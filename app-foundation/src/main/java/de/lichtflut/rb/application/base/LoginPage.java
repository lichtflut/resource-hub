/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.base;

import de.lichtflut.rb.application.RBApplication;
import de.lichtflut.rb.application.common.RBPermission;
import de.lichtflut.rb.application.custom.RequestAccountPage;
import de.lichtflut.rb.application.custom.ResetPasswordPage;
import de.lichtflut.rb.application.extensions.ServiceContextInitializer;
import de.lichtflut.rb.application.pages.AbstractBasePage;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.AuthenticationService;
import de.lichtflut.rb.core.security.LoginData;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.webck.common.CookieAccess;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.components.login.LoginPanel;
import de.lichtflut.rb.webck.models.infra.VersionInfoModel;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.security.LoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * <p>
 *  Standard login page for RB applications.
 * </p>
 * 
 * <p>
 * Created Dec 8, 2011
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
