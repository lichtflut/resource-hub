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

import de.lichtflut.rb.core.services.MenuService;
import de.lichtflut.rb.core.viewspec.MenuItem;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.webck.common.OrderedNodesContainer;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.common.impl.OrderedModelContainer;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.CastingModel;
import de.lichtflut.rb.webck.models.viewspecs.UsersMenuItemListModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>
 *  Panel for management of perspectives.
 * </p>
 *
 * <p>
 * 	Created Feb 6, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class MenuManagementPanel extends Panel {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuManagementPanel.class);

	@SpringBean
	private MenuService menuService;

	private final UsersMenuItemListModel listModel = new UsersMenuItemListModel();

	private final OrderedNodesContainer container;

	// ----------------------------------------------------

	/**
	 * @param id The component ID
	 */
	public MenuManagementPanel(final String id) {
		super(id);

		setOutputMarkupId(true);

		final IModel<List<? extends ResourceNode>> orderModel =
				new CastingModel<List<? extends ResourceNode>>(listModel);

		container = new OrderedModelContainer(orderModel);

		final ListView<MenuItem> view = new ListView<MenuItem>("list", listModel) {
			@Override
			protected void populateItem(final ListItem<MenuItem> item) {
				final MenuItem menuItem = item.getModelObject();
				item.add(new Label("name", menuItem.getName()));
				item.add(new Label("perspective", perspectiveName(menuItem)));
				item.add(createEditLink(item.getModel()));
				item.add(createDeleteLink(menuItem));
				item.add(createUpLink(menuItem));
				item.add(createDownLink(menuItem));
			}
		};
		add(view);

		add(createAddLink());

	}

	// ----------------------------------------------------

	@Override
	public void onEvent(final IEvent<?> event) {
		if (ModelChangeEvent.from(event).isAbout(ModelChangeEvent.VIEW_SPEC)) {
			updatePanel();
		}
	}

	// ----------------------------------------------------

	@SuppressWarnings("rawtypes")
	private AjaxLink createAddLink() {
		final AjaxLink link = new AjaxLink("create") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				final DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new CreateMenuItemDialog(hoster.getDialogID()));
			}
		};
		return link;
	}

	@SuppressWarnings("rawtypes")
	private AjaxLink createDeleteLink(final MenuItem item) {
		final AjaxLink link = new AjaxLink("delete") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				removeMenuItem(item);
			}
		};
		return link;
	}

	@SuppressWarnings("rawtypes")
	private AjaxLink createEditLink(final IModel<MenuItem> item) {
		final AjaxLink link = new AjaxLink("edit") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				final DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new EditMenuItemDialog(hoster.getDialogID(), item));
			}
		};
		return link;
	}

	@SuppressWarnings("rawtypes")
	private AjaxLink createUpLink(final MenuItem item) {
		final AjaxLink link = new AjaxLink("up") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				container.moveUp(item, 1);
				updatePanel();
			}
		};
		return link;
	}

	@SuppressWarnings("rawtypes")
	private AjaxLink createDownLink(final MenuItem item) {
		final AjaxLink link = new AjaxLink("down") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				container.moveDown(item, 1);
				updatePanel();
			}
		};
		return link;
	}


	private String perspectiveName(final MenuItem menuItem) {
		Perspective perspective = menuItem.getPerspective();
		if (perspective == null) {
			return "-- undefined --";
		} else {
			return perspective.getName();
		}
	}

	// ----------------------------------------------------

	private void removeMenuItem(final MenuItem item) {
		menuService.removeUsersItem(item);
		updatePanel();
	}

	// ----------------------------------------------------

	@SuppressWarnings("rawtypes")
	protected void updatePanel() {
		LOGGER.debug("Updating MenuManagementPanel: " + listModel.getObject());
		listModel.detach();
		RBAjaxTarget.add(this);
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent(ModelChangeEvent.MENU));
	}

}
