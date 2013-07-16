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

import de.lichtflut.rb.core.perceptions.Perception;
import de.lichtflut.rb.core.services.PerceptionDefinitionService;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.colorpicker.ColorPickerPanel;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.perceptions.PerceptionWizzardListModel;
import de.lichtflut.rb.webck.models.resources.ResourceLabelModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.nodes.ResourceNode;

import java.util.List;

/**
 * <p>
 * This Panel allows you to create multiple perceptions at once for each perception category
 * </p>
 * Created: Jan 9, 2013
 * 
 * @author Ravi Knox
 */
public class CreatePerceptionsWizzardPanel extends Panel {

	@SpringBean
	private PerceptionDefinitionService perceptionDefinitionService;

	private PerceptionWizzardListModel model;
	private IModel<List<ResourceNode>> categories;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * 
	 * @param id Comonent id
	 */
	public CreatePerceptionsWizzardPanel(final String id) {
		super(id);
		initializeModel();

		Form<?> form = new Form<Void>("form");
		addPerceptionsContainer("list", form);

		add(form);
		addSaveButton("save", form);
		addCancelButton("cancel", form);

		setOutputMarkupId(true);
	}

	// ------------------------------------------------------

	protected void onCancel(final AjaxRequestTarget target, final Form<?> form) {
	}

	protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
		for (List<Perception> list : model.getObject()) {
			perceptionDefinitionService.store(list);
		}
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.PERCEPTION));
	}

	// ------------------------------------------------------

	private void addPerceptionsContainer(final String id, final Form<?> form) {
		ListView<List<Perception>> listView = new ListView<List<Perception>>(id, model) {
			@Override
			protected void populateItem(final ListItem<List<Perception>> item) {
				createPerceptionsList(item);
			}
		};
		form.add(listView);
	}

	private void createPerceptionsList(final ListItem<List<Perception>> item) {
		final ListView<Perception> list = createListView(item);
		final WebMarkupContainer container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		container.add(list);

		AjaxSubmitLink link = new AjaxSubmitLink("createPerception") {
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				model.addPerceptionFor(categories.getObject().get(item.getIndex()));
				target.add(container);
			}
		};
		IModel<String> labelModel = new ResourceLabelModel(categories.getObject().get(item.getIndex()));
		item.add(new Label("label", labelModel));

		item.add(link);
		item.add(container);
	}

	private PropertyListView<Perception> createListView(final ListItem<List<Perception>> perceptionList) {
		final PropertyListView<Perception> list = new PropertyListView<Perception>("list",
				perceptionList.getModelObject()) {
			@Override
			protected void populateItem(final ListItem<Perception> item) {
				item.add(new TextField<String>("ID"));
				item.add(new TextField<String>("name"));
				item.add(new ColorPickerPanel("color", new PropertyModel<String>(item.getModel(), "color")));

				AjaxSubmitLink deleteLink = new AjaxSubmitLink("deletePerception") {
					@Override
					protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
						perceptionList.getModelObject().remove(item.getModelObject());
						RBAjaxTarget.add(CreatePerceptionsWizzardPanel.this);
					}
				};
				item.add(deleteLink);
			}
		};
		return list;
	}

	private void addCancelButton(final String string, final Form<?> form) {
		RBCancelButton button = new RBCancelButton("cancel"){
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				CreatePerceptionsWizzardPanel.this.onCancel(target, form);
			}
		};
		button.add(new Label("cancelLabel", new ResourceModel("button.cancel")));

		form.add(button);
	}


	private void addSaveButton(final String id, final Form<?> form) {
		RBStandardButton button = new RBStandardButton(id){
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				CreatePerceptionsWizzardPanel.this.onSubmit(target, form);
			}
		};
		button.add(new Label("saveLabel", new ResourceModel("button.save")));
		form.add(button);
	}

	private void initializeModel() {
		categories = new LoadableDetachableModel<List<ResourceNode>>() {
			@Override
			protected List<ResourceNode> load() {
				return perceptionDefinitionService.findAllPerceptionCategories();
			}
		};
		model = new PerceptionWizzardListModel(categories);
	}

}
