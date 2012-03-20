/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.organizer.NamespaceDeclaration;
import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.components.organizer.CreateNamespacePanel;

/**
 * <p>
 *  Dialog for creating a new Namespace.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class CreateNamespaceDialog extends AbstractRBDialog {
	
	@SpringBean
	protected ServiceProvider provider;
	
	// ----------------------------------------------------
	
	/**
	 * @param id
	 */
	public CreateNamespaceDialog(final String id) {
		super(id);
		
		add(new CreateNamespacePanel(CONTENT) {
			@Override
			public DomainOrganizer getOrganizer() {
				return provider.getDomainOrganizer();
			}
			
			@Override
			public void onCancel(AjaxRequestTarget target) {
				close(target);
			}
			
			@Override
			public void onSuccess(AjaxRequestTarget target, NamespaceDeclaration decl) {
				close(target);
			}
		});
		
		setModal(true);
		setWidth(600);
	}
	
}
