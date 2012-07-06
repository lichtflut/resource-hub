/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.custom;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.application.base.LoginPage;
import de.lichtflut.rb.application.pages.AbstractBasePage;
import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.eh.RBException;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.components.identities.UserCreationPanel;

/**
 * <p>
 *  Page to request a new user account.
 * </p>
 *
 * <p>
 * 	Created Jan 27, 2012
 * </p>
 * @author Erik Aderhold
 */
public class RequestAccountPage extends AbstractBasePage {

	@SpringBean
	private SecurityService securityService;
	
	// ---------------- Constructor -------------------------
	
	public RequestAccountPage() {
		final UserCreationPanel registerPanel = new UserCreationPanel("registerPanel") {
			@Override
			public void onCreate(String userID, String username, String password) {
				try {
					securityService.createUser(userID, username, password, getLocale());
					info(getString("global.message.user-created"));
					for (Component component : visitChildren()) {
						if(component instanceof RBStandardButton) {
							if(!(component instanceof RBCancelButton)) {
								component.setVisible(false);
							}
						} else {
							if(component instanceof TextField) {
								component.setEnabled(false);
							}
						}
					}
				} catch (RBException e) {
					if(e.getErrorCode() == ErrorCodes.SECURITY_EMAIL_ALREADY_IN_USE) {
						error(getString("global.message.duplicate.ID"));
					} else if (e.getErrorCode() == ErrorCodes.SECURITY_EMAIL_ALREADY_IN_USE){
						error(getString("global.message.duplicate.username"));
					} else if (e.getErrorCode() == ErrorCodes.EMAIL_SERVICE_EXCEPTIO){
						error(getString("error.send.email"));
					}
				}
				RBAjaxTarget.add(this);
			}

			@Override
			public void onCancel() {
				setResponsePage(LoginPage.class);
			}
		};
		this.add(registerPanel);
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	protected boolean needsAuthentication() {
		return false;
	}
}
