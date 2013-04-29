/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
