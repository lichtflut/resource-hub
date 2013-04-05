/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.relationships;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.defaultButtonIf;
import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.enableIf;
import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.isFalse;
import static de.lichtflut.rb.webck.models.ConditionalModel.isNull;
import static de.lichtflut.rb.webck.models.ConditionalModel.isTrue;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.DetachedStatement;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.webck.components.fields.EntityPickerField;
import de.lichtflut.rb.webck.components.fields.PropertyPickerField;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.entity.RBEntityLabelModel;
import de.lichtflut.rb.webck.models.resources.ResourceLabelModel;

/**
 * <p>
 *  Panel for creation of new relationships.
 * </p>
 *
 * <p>
 * 	Created Dec 9, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class CreateRelationshipPanel extends Panel {

	@SpringBean
	private SemanticNetworkService service;

	private final IModel<RBEntity> subjectModel;

	private final IModel<ResourceID> predicateModel = new Model<ResourceID>();

	private final IModel<ResourceID> objectModel = new Model<ResourceID>();

	private final IModel<Boolean> objectSelected = Model.of(Boolean.FALSE);

	// ----------------------------------------------------

	/**
	 * @param id
	 * @param subjectModel
	 */
	@SuppressWarnings("rawtypes")
	public CreateRelationshipPanel(final String id, final IModel<RBEntity> subjectModel) {
		super(id);
		this.subjectModel = subjectModel;

		final Form form = new Form("form");
		form.add(new FeedbackPanel("feedback"));

		final EntityPickerField entityPicker = new EntityPickerField("objectPicker", objectModel);
		entityPicker.add(enableIf(isNull(objectModel)));
		entityPicker.getSuggestLink().setVisible(false);
		form.add(entityPicker);

		final PropertyPickerField predicatePicker = new PropertyPickerField("predicatePicker", predicateModel);
		predicatePicker.add(visibleIf(isTrue(objectSelected)));
		predicatePicker.setRequired(true);
		form.add(predicatePicker);

		final Label subjectLabel = new Label("subject", new RBEntityLabelModel(subjectModel));
		form.add(subjectLabel);

		final Label objectLabel = new Label("object", new ResourceLabelModel(objectModel));
		form.add(objectLabel);

		final AjaxButton selectButton = new RBStandardButton("select") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				if (objectModel.getObject() != null) {
					objectSelected.setObject(Boolean.TRUE);
					resolve(objectModel);
					target.focusComponent(predicatePicker.getDisplayComponent());
				}
				// add root form due to default button change
				target.add(form.getRootForm());
			}
		};
		selectButton.add(visibleIf(isFalse(objectSelected)));
		selectButton.add(defaultButtonIf(isFalse(objectSelected)));
		form.add(selectButton);

		final AjaxButton createButton = new RBStandardButton("create") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				createRelationshipTo(objectModel.getObject(), predicateModel.getObject());
				resetModels();
				target.add(form);
			}
		};
		createButton.add(defaultButtonIf(isTrue(objectSelected)));
		form.add(createButton);

		final AjaxButton cancelButton = new RBCancelButton("cancel") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				resetModels();
				target.add(form);
			}
		};
		form.add(cancelButton);

		add(form);

	}

	// ----------------------------------------------------

	protected void createRelationshipTo(final ResourceID object, final ResourceID predicate) {
		final ResourceID subject = subjectModel.getObject().getID();
		service.add(new DetachedStatement(subject, predicate, object));
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.RELATIONSHIP));
	}

	// ----------------------------------------------------

	protected void resetModels() {
		objectSelected.setObject(Boolean.FALSE);
		objectModel.setObject(null);
		predicateModel.setObject(null);
	}

	private void resolve(final IModel<ResourceID> objectModel) {
		final ResourceNode resolved = service.resolve(objectModel.getObject());
		objectModel.setObject(resolved);
	}

}
