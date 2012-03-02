/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.management;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.dialogs.AbstractRBDialog;

/**
 * <p>
 *  Modal dialog for editing an existing perspective.
 * </p>
 *
 * <p>
 * 	Created Feb 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class EditPerspectiveDialog extends AbstractRBDialog {

	/**
	 * @param id
	 */
	public EditPerspectiveDialog(String id, IModel<Perspective> model) {
		super(id);
		
		add(new PerspectiveEditPanel(CONTENT, model, Model.of(DisplayMode.EDIT)) {
			@Override
			protected void onCancel(AjaxRequestTarget target) {
				close(target);
			}
			@Override
			protected void onSuccess(AjaxRequestTarget target) {
				close(target);
			}
		});
		
		setWidth(600);
	}

}