/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.management;

import de.lichtflut.rb.core.viewspec.MenuItem;
import de.lichtflut.rb.core.viewspec.impl.SNMenuItem;
import de.lichtflut.rb.webck.behaviors.TitleModifier;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.dialogs.AbstractRBDialog;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

/**
 * <p>
 *  Modal dialog for creating a new menu item.
 * </p>
 *
 * <p>
 * 	Created Feb 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class CreateMenuItemDialog extends AbstractRBDialog {

	/**
	 * @param id The wicket id.
	 */
	public CreateMenuItemDialog(String id) {
		super(id);
		
		add(new MenuItemEditPanel(CONTENT, new Model<MenuItem>(new SNMenuItem()), Model.of(DisplayMode.CREATE)) {
			@Override
			protected void onCancel(AjaxRequestTarget target) {
				close(target);
			}
			@Override
			protected void onSuccess(AjaxRequestTarget target) {
				close(target);
			}
		});

        add(TitleModifier.title(new ResourceModel("title.create-menu-item")));
		
		setWidth(600);
	}

}
