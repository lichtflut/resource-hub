/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.management;

import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.impl.SNPerspective;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.dialogs.AbstractRBDialog;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

/**
 * <p>
 *  Modal dialog for creating a new perspective.
 * </p>
 *
 * <p>
 * 	Created Feb 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class CreatePerspectiveDialog extends AbstractRBDialog {

	/**
	 * @param id
	 */
	public CreatePerspectiveDialog(String id) {
		super(id);
		
		add(new PerspectiveEditPanel(CONTENT, new Model<Perspective>(new SNPerspective()), Model.of(DisplayMode.CREATE)) {
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
