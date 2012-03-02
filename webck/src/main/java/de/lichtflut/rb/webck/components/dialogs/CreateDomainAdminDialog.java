/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.security.Domain;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.components.identities.UserCreationPanel;

/**
 * <p>
 *  Modal dialog for creation of a new domain administrator.
 * </p>
 *
 * <p>
 * 	Created Feb 27, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class CreateDomainAdminDialog extends AbstractRBDialog {
	
	@SpringBean
	private ServiceProvider provider;
	
	// ----------------------------------------------------

	/**
	 * @param id
	 */
	public CreateDomainAdminDialog(final String id, final IModel<Domain> model) {
		super(id);
		
		setTitle(new ResourceModel("dialog.title"));
		
		add(new UserCreationPanel(CONTENT) {
			@Override
			public void onCreate(String email, String username, String password) {
				provider.getSecurityService().createDomainAdmin(model.getObject(), email, username, password);
				close(AjaxRequestTarget.get());
			}
			
			@Override
			public void onCancel() {
				close(AjaxRequestTarget.get());
			}
		});
		
	}
	
}