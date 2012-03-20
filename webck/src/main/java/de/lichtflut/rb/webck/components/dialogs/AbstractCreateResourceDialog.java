/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.components.typesystem.CreateResourcePanel;

/**
 * <p>
 *  Basic dialog for creating of new Resources.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class AbstractCreateResourceDialog extends AbstractRBDialog {
	
	@SpringBean
	protected ServiceProvider provider;
	
	// ----------------------------------------------------
	
	/**
	 * @param id
	 */
	public AbstractCreateResourceDialog(final String id) {
		super(id);
		
		add(new CreateResourcePanel(CONTENT) {
			public void onCreate(QualifiedName qn, AjaxRequestTarget target) {
				AbstractCreateResourceDialog.this.onCreate(qn, target);
				close(target);
			};
			
			@Override
			public ServiceProvider getServiceProvider() {
				return provider;
			}
		});
		
		setModal(true);
		setWidth(600);
	}
	
	// ----------------------------------------------------
	
	public abstract void onCreate(QualifiedName qn, AjaxRequestTarget target);
	
}
