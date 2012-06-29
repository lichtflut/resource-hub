/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.webck.components.organizer.CreateContextPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * <p>
 *  Dialog for creating a new Context.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class CreateContextDialog extends AbstractRBDialog {
	
	@SpringBean
	protected DomainOrganizer domainOrganizer;
	
	// ----------------------------------------------------
	
	/**
	 * @param id
	 */
	public CreateContextDialog(final String id) {
		super(id);
		
		add(new CreateContextPanel(CONTENT) {
			@Override
			public void onCancel(AjaxRequestTarget target) {
				close(target);
			}
			
			@Override
			public void onSuccess(AjaxRequestTarget target) {
				close(target);
			}
		});
		
		setModal(true);
		setWidth(600);
	}
	
}
