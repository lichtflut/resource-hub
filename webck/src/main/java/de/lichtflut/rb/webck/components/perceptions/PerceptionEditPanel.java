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

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.perceptions.Perception;
import de.lichtflut.rb.core.services.PerceptionDefinitionService;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.fields.color.ColorPickerPanel;
import de.lichtflut.rb.webck.components.common.PanelTitle;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.fields.AjaxEditableUploadField;
import de.lichtflut.rb.webck.components.fields.EntityPickerField;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
import de.lichtflut.rb.webck.models.fields.FileUploadModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import java.util.UUID;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.isNotNull;

/**
 * <p>
 *  Panel for editing of a perception.
 * </p>
 *
 * <p>
 *  Created 16.11.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class PerceptionEditPanel extends TypedPanel<Perception> {

	@SpringBean
	private PerceptionDefinitionService service;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component id.
	 * @param model The model containing the perception.
	 */
	public PerceptionEditPanel(final String id, final IModel<Perception> model) {
		super(id, model);

		setOutputMarkupId(true);
		setOutputMarkupPlaceholderTag(true);

		Form<?> form = new Form<Void>("form");
		form.setMultiPart(true);

		form.add(new FeedbackPanel("feedback"));
		form.add(new PanelTitle("title", new ResourceModel("title")));

		createFields(model, form);

		form.add(createCancelButton());
		form.add(createSaveButton());

		add(form);

		add(visibleIf(isNotNull(model)));
	}


	// ------------------------------------------------------

	/**
	 * Gets called when form gets submitted.
	 * @param target
	 * @param form
	 */
	protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
		store();
		onUpdate(target, form);
	}

	protected void onUpdate(final AjaxRequestTarget target, final Form<?> form) {
	}

	protected void onCancel(final AjaxRequestTarget target, final Form<?> form) {
	}

	// ------------------------------------------------------

	/**
	 * Add input fields to form.
	 * @param model IModel containing the perception
	 * @param form
	 */
	protected void createFields(final IModel<Perception> model, final Form<?> form) {
		TextField<String> idField = new TextField<String>("id", new PropertyModel<String>(model, "ID"));
		form.add(idField);

		TextField<String> nameField = new TextField<String>("name", new PropertyModel<String>(model, "name"));
		form.add(nameField);

		EntityPickerField entityPicker = new EntityPickerField("type", new PropertyModel<ResourceID>(model, "type"),
                RBSystem.PERCEPTION_CATEGORY);
		form.add(entityPicker);

		ColorPickerPanel colorPicker = new ColorPickerPanel("color", new PropertyModel<String>(model, "color"));
		form.add(colorPicker);

		addUploadField(model, form);

		EntityPickerField ownerPicker = new EntityPickerField("owner", new PropertyModel<ResourceID>(model, "owner"), RB.PERSON);
		form.add(ownerPicker);

		EntityPickerField personResponsiblePicker = new EntityPickerField("personResponsible", new PropertyModel<ResourceID>(model, "personResponsible"), RB.PERSON);
		form.add(personResponsiblePicker);
	}


	private void addUploadField(final IModel<Perception> model, final Form<?> form) {
		IModel<String> prefix = Model.<String>of(UUID.randomUUID().toString());
		FileUploadModel uploadModel = new FileUploadModel(new PropertyModel<Object>(model, "imagePath"), prefix);
		final AjaxEditableUploadField fileUpload = new AjaxEditableUploadField("image", uploadModel);
		form.add(fileUpload);

		AjaxSubmitLink link = new AjaxSubmitLink("deleteFile") {
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				model.getObject().setImagePath(null);
				RBAjaxTarget.add(fileUpload);
			}
		};
		form.add(link);
	}

	protected Button createSaveButton() {
		return new RBDefaultButton("save") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				PerceptionEditPanel.this.onSubmit(target, form);
			}
		};
	}


	protected Button createCancelButton() {
		return new RBCancelButton("cancel") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				onCancel(target, form);
			}
		};
	}

	protected void store() {
		Perception perception = getModelObject();
		service.store(perception);
	}

}
