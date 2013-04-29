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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.EnumLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.common.Accessibility;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.viewspecs.PerspectiveListModel;

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
public class PerspectiveManagementPanel extends Panel {

	@SpringBean
	private ViewSpecificationService viewSpecificationService;

	@SpringBean
	private AuthModule authModule;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 */
	@SuppressWarnings("rawtypes")
	public PerspectiveManagementPanel(final String id) {
		super(id);

		setOutputMarkupId(true);

		final PerspectiveListModel perspectives = new PerspectiveListModel();
		final ListView<Perspective> view = createListView(perspectives);
		add(view);

		final AjaxLink link = new AjaxLink("create") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				final Perspective perspective = viewSpecificationService.initializePerspective(new SimpleResourceID());
				final DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new CreatePerspectiveDialog(hoster.getDialogID(), perspective));
			}
		};
		add(link);

	}

	private ListView<Perspective> createListView(final PerspectiveListModel perspectives) {
		return new ListView<Perspective>("list", perspectives) {
			@Override
			protected void populateItem(final ListItem<Perspective> item) {
				final Perspective perspective = item.getModelObject();
				item.add(new Label("name", perspective.getName()));
				item.add(new Label("title", perspective.getTitle()));
				item.add(new EnumLabel<Accessibility>("visibility", perspective.getVisibility()));
				item.add(new Label("owner", new OwnerModel(item.getModel())));
				item.add(new AjaxLink<Void>("edit") {
					@Override
					public void onClick(final AjaxRequestTarget target) {
						final DialogHoster hoster = findParent(DialogHoster.class);
						hoster.openDialog(new EditPerspectiveDialog(hoster.getDialogID(), item.getModel()));
					}
				});
				item.add(new AjaxLink<Void>("delete") {
					@Override
					public void onClick(final AjaxRequestTarget target) {
						delete(item.getModelObject());
						perspectives.detach();
					}
				});
			}
		};
	}

	// ----------------------------------------------------

	@Override
	public void onEvent(final IEvent<?> event) {
		if (ModelChangeEvent.from(event).isAbout(ModelChangeEvent.VIEW_SPEC)) {
			RBAjaxTarget.add(this);
		}
	}

	// ----------------------------------------------------

	private void delete(final Perspective perspective) {
		viewSpecificationService.remove(perspective);
		RBAjaxTarget.add(this);
	}

	private class OwnerModel extends DerivedDetachableModel<String, Perspective> {

		private OwnerModel(final IModel<Perspective> original) {
			super(original);
		}

		@Override
		protected String derive(final Perspective perspective) {
			ResourceID owner = perspective.getOwner();
			if (owner != null) {
				RBUser user = authModule.getUserManagement().findUser(owner.toURI());
				if (user != null) {
					return user.getName();
				}
			}
			return "-";
		}
	}

}
