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
package de.lichtflut.rb.webck.components.perceptions;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.perceptions.Perception;
import de.lichtflut.rb.core.perceptions.PerceptionOrder;
import de.lichtflut.rb.core.services.PerceptionDefinitionService;
import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.behaviors.CssModifier;
import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.ConfirmationDialog;
import de.lichtflut.rb.webck.components.dialogs.CreatePerceptionsWizzardDialog;
import de.lichtflut.rb.webck.components.entity.VisualizationMode;
import de.lichtflut.rb.webck.components.links.CrossLink;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.perceptions.PerceptionModel;
import de.lichtflut.rb.webck.models.resources.ResourceLabelModel;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * <p>
 * This panel lists all registered perceptions and allows creation and editing.
 * </p>
 * 
 * <p>
 * Created 16.11.12
 * </p>
 * 
 * @author Oliver Tigges
 */
public class PerceptionManagementPanel extends Panel {

	@SpringBean
	private PerceptionDefinitionService perceptionDefinitionService;

    @SpringBean
    ResourceLinkProvider linkProvider;

	// ----------------------------------------------------

	public PerceptionManagementPanel(final String id) {
		this(id, new PerceptionModel());
	}

	public PerceptionManagementPanel(final String id, final IModel<List<Perception>> model) {
		super(id, model);

		add(createTabelHeader("tableHeader", model));
		final ListView<Perception> perceptionView = createListView(model);
		add(perceptionView);

		add(createNoPerceptionsInfo("noPerceptions", model));

		add(createNewLink());
		add(createPerceptionWizzardLink("openWizzardLink"));

		setOutputMarkupId(true);
	}

	// ----------------------------------------------------

	private Component createTabelHeader(final String id, final IModel<List<Perception>> model) {
		// Contains static HTML. We determin its visibility only.
		Component container = new WebMarkupContainer(id);
		container.add(ConditionalBehavior.visibleIf(ConditionalModel.isNotEmpty(model)));
		return container;
	}

	private Component createNoPerceptionsInfo(final String id, final IModel<List<Perception>> model) {
		Label label = new Label(id, new ResourceModel("label.no-perceptions"));
		label.add(ConditionalBehavior.visibleIf(ConditionalModel.isEmpty(model)));
		return label;
	}

	private ListView<Perception> createListView(final IModel<List<Perception>> model) {
		ListView<Perception> view = new ListView<Perception>("perceptionView", model) {
			@Override
			protected void populateItem(final ListItem<Perception> item) {
				Perception perception = item.getModelObject();

				Label id = new Label("id", perception.getID());
				item.add(id);

				Label name = new Label("name", perception.getName());
				item.add(name);

				Label owner = new Label("owner", new ResourceLabelModel(perception.getOwner()));
				item.add(owner);

				Label color = new Label("color", "");
				color.add(CssModifier.appendStyle("background-color : #" + perception.getColor()));
				item.add(color);

				item.add(createViewLink(item.getModel()));
				item.add(createDeleteLink(item.getModel()));
				item.add(createUpLink(item.getModel(), model));
				item.add(createDownLink(item.getModel(), model));
			}
		};
		view.add(ConditionalBehavior.visibleIf(ConditionalModel.isNotEmpty(model)));
		return view;
	}

	// ----------------------------------------------------

	private AbstractLink createNewLink() {
		return new CrossLink("createPerceptionLink",
				linkProvider.getUrlToResource(RBSystem.PERCEPTION, VisualizationMode.DETAILS, DisplayMode.CREATE));
	}

	private AbstractLink createPerceptionWizzardLink(final String id) {
		return new AjaxLink<Void>(id) {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				final DialogHoster dialogHoster = findParent(DialogHoster.class);
				dialogHoster.openDialog(new CreatePerceptionsWizzardDialog(dialogHoster.getDialogID()) {
					@Override
					protected void onUpdate(final AjaxRequestTarget target, final Form<?> form) {
						dialogHoster.closeDialog(this);
					}
				});
			}
		};
	}

	private AjaxLink<?> createDeleteLink(final IModel<Perception> model) {
		final AjaxLink<?> link = new AjaxLink<Void>("delete") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				openConfirmationDialog(model);
			}

			private void openConfirmationDialog(final IModel<Perception> model) {
				final String confirmation = getString("dialog.confirmation.delete") + " '" + model.getObject().getID()
						+ "'";
				final DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new ConfirmationDialog(hoster.getDialogID(), Model.of(confirmation)) {
					@Override
					public void onConfirm() {
						removePerception(model);
						send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.PERCEPTION));
					}
					@Override
					public void onCancel() {
						hoster.closeDialog(this);
					}
				});
			}
		};
		return link;
	}

	private void removePerception(final IModel<Perception> model) {
		perceptionDefinitionService.delete(model.getObject());
		PerceptionModel perceptions = (PerceptionModel) getDefaultModel();
		perceptions.remove(model.getObject());
	}

	private Component createViewLink(final IModel<Perception> model) {
		return new CrossLink("view", new DerivedDetachableModel<String, Perception>(model) {
            @Override
            protected String derive(Perception perception) {
                return linkProvider.getUrlToResource(perception, VisualizationMode.DETAILS, DisplayMode.VIEW);
            }
        });
	}

	private AjaxLink<?> createUpLink(final IModel<Perception> model, final IModel<List<Perception>> perceptions) {
		final AjaxLink<?> link = new AjaxLink<Void>("up") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				swapAndStore(model, perceptions, -1);
				update();
			}

		};
		return link;
	}

	private AjaxLink<?> createDownLink(final IModel<Perception> model, final IModel<List<Perception>> perceptions) {
		final AjaxLink<?> link = new AjaxLink<Void>("down") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				swapAndStore(model, perceptions, +1);
				update();
			}
		};
		return link;
	}

	private void swapAndStore(final IModel<Perception> model, final IModel<List<Perception>> perceptions,
			final int positions) {
		List<Perception> list = perceptions.getObject();
		int pos = list.indexOf(model.getObject());
		if (checkRange(pos, positions, list)) {
			swap(positions, list, pos);
			perceptionDefinitionService.store(list);
		}
	}

	private void swap(final int positions, final List<Perception> list, final int pos) {
		Perception actual = list.get(pos);
		Perception wanted = list.get(pos + positions);
		new PerceptionOrder(list).swap(actual, wanted);
	}

	private boolean checkRange(final int pos, final int positions, final List<Perception> list) {
		boolean valid = false;
		if (pos > 0 || positions > pos) {
			if (list.size() - 1 > pos || positions < 1) {
				valid = true;
			}
		}
		return valid;
	}

	private void update() {
		RBAjaxTarget.add(PerceptionManagementPanel.this);
	}

	// ----------------------------------------------------

	@Override
	public void onEvent(final IEvent<?> event) {
		ModelChangeEvent<?> mce = ModelChangeEvent.from(event);
		if (mce.isAbout(ModelChangeEvent.PERCEPTION)) {
			update();
		}
	}

}
