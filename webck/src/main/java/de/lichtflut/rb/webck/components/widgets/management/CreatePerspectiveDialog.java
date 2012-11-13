/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.management;

import de.lichtflut.rb.core.common.Accessibility;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.impl.SNPerspective;
import de.lichtflut.rb.webck.behaviors.TitleModifier;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.dialogs.AbstractRBDialog;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

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

    // ----------------------------------------------------

	/**
     * Constructor.
     * @param id The component ID.
     * @param perspective The newly initialized perspective.
     */
	public CreatePerspectiveDialog(String id, Perspective perspective) {
		super(id);

        add(new PerspectiveEditPanel(CONTENT, new Model<Perspective>(perspective), Model.of(DisplayMode.CREATE)) {
            @Override
            protected void onCancel(AjaxRequestTarget target) {
                close(target);
            }

            @Override
            protected void onSuccess(AjaxRequestTarget target) {
                close(target);
            }
        });

        add(TitleModifier.title(new ResourceModel("title.create-perspective")));
		
		setWidth(600);
	}

}
