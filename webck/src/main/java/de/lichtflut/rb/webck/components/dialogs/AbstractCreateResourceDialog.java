/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.arastreju.sge.naming.QualifiedName;

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

	/**
	 * @param id
	 */
	public AbstractCreateResourceDialog(final String id) {
		super(id);

		add(new CreateResourcePanel(CONTENT) {
			@Override
			public void onCreate(final QualifiedName qn, final AjaxRequestTarget target) {
				AbstractCreateResourceDialog.this.onCreate(qn, target);
				close(target);
			}
		});

		setModal(true);
		setWidth(600);
	}

	// ----------------------------------------------------

	public abstract void onCreate(QualifiedName qn, AjaxRequestTarget target);

}
