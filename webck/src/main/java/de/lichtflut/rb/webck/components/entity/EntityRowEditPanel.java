/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.and;
import static de.lichtflut.rb.webck.models.ConditionalModel.lessThan;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.behaviors.CssModifier;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.form.RBSubmitLink;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.fields.FieldCardinalityModel;
import de.lichtflut.rb.webck.models.fields.FieldLabelModel;
import de.lichtflut.rb.webck.models.fields.FieldSizeModel;
import de.lichtflut.rb.webck.models.fields.RBFieldLabelCssClassModel;
import de.lichtflut.rb.webck.models.fields.RBFieldValueModel;
import de.lichtflut.rb.webck.models.fields.RBFieldValuesListModel;

/**
 * <p>
 *  Panel containing one field of a entity, which consists of
 *  <ul>
 *  	<li> a label </li>
 *  	<li> one or more values </li>
 *  </ul>
 *  for editing.
 * </p>
 *
 * <p>
 * 	Created Nov 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings({ "rawtypes" })
public class EntityRowEditPanel extends Panel {

	/**
	 * Constructor.
	 * @param id The ID.
	 * @param model The field model.
	 */
	public EntityRowEditPanel(final String id, final IModel<RBField> model) {
		super(id, model);

		final WebMarkupContainer labelContainer = new WebMarkupContainer("labelContainer");
		labelContainer.add(CssModifier.appendClass(new RBFieldLabelCssClassModel(model)));
		final Label label = new Label("label", new FieldLabelModel(model));
		labelContainer.add(label);
		add(labelContainer);

		final FieldEditorFactory factory = new FieldEditorFactory(this);

		final WebMarkupContainer container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);

		final RBFieldValuesListModel listModel = new RBFieldValuesListModel(model);
		final ListView<RBFieldValueModel> view = new ListView<RBFieldValueModel>("values", listModel) {
			@Override
			protected void populateItem(final ListItem<RBFieldValueModel> item) {
				final RBFieldValueModel fieldValueModel = item.getModelObject();

				final Component field = factory.createField(fieldValueModel);
				item.add(field);

				final int idx = fieldValueModel.getIndex();
				item.add(createRemoveLink(idx));
				item.add(createAddValueLink(model, idx));
				item.add(createCreateLink(idx));
				item.setOutputMarkupId(true);
			}
		};
		view.setReuseItems(true);
		container.add(view);

		add(container);
	}

	// ----------------------------------------------------

	protected AjaxSubmitLink createRemoveLink(final int index) {
		final AjaxSubmitLink link = new AjaxSubmitLink("removeLink") {
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				getField().removeSlot(index);
				rebuildListView();
				updateView();
			}

			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form) {
				updateView();
			}
		};
		link.add(new AttributeModifier("title", new ResourceModel("link.title.remove-field-value")));
		if (Datatype.BOOLEAN.equals(getField().getDataType())) {
			link.setVisible(false);
		} else {
			link.add(visibleIf(new IsBeneathFormConditional()));
		}
		return link;
	}

	protected Component createAddValueLink(final IModel<RBField> model, final int index) {
		if (index > 0) {
			return new WebComponent("addValueLink").setVisible(false);
		}
		final RBSubmitLink link = new RBSubmitLink("addValueLink") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				getField().addValue(null);
				updateView();
			}
		};
		link.add(new AttributeModifier("title", new ResourceModel("link.title.add-field-value")));
		link.add(visibleIf(and(
				new IsBeneathFormConditional(),
				lessThan(new FieldSizeModel(model), new FieldCardinalityModel(model)))));
		return link;
	}

	protected Component createCreateLink(final int index) {
		if (index > 0) {
			return new WebComponent("createLink").setVisible(false);
		}

		final AjaxSubmitLink link = new AjaxSubmitLink("createLink") {
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				final EntityHandle handle = EntityHandle.forType(getTypeConstraint());
				findParent(IBrowsingHandler.class).createReferencedEntity(handle, getField().getPredicate());
				Page page = getPage();
				send(page, Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.ENTITY));
				send(page, Broadcast.BREADTH, new ModelChangeEvent<ResourceID>(getTypeConstraint(),ModelChangeEvent.RELATIONSHIP));
			}

			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form) {
				updateView();
			}
		};
		link.add(new AttributeModifier("title", new ResourceModel("link.title.create-field-value")));
		if (getField().isResourceReference()){
			link.add(ConditionalBehavior.visibleIf(new IsBeneathFormConditional()));
		} else {
			link.setVisible(false);
		}
		return link;
	}

	// ----------------------------------------------------

	/**
	 * Extracts the resourceTypeConstraint of this {@link RBField}.
	 * @return the resourceTypeConstraint as an {@link ResourceID}
	 */
	private ResourceID getTypeConstraint() {
		final RBField field = getField();
		if(field.getDataType().equals(Datatype.RESOURCE)){
			return field.getConstraint().getTypeConstraint().asResource();
		}
		return null;
	}

	private RBField getField() {
		return (RBField) getDefaultModelObject();
	}

	private void rebuildListView() {
		MarkupContainer view = (MarkupContainer) get("container:values");
		view.removeAll();
	}

	private void updateView() {
		RBAjaxTarget.add(get("container"));
	}

	// -- INNER CLASSES -----------------------------------

	private class IsBeneathFormConditional extends ConditionalModel {
		@Override
		public boolean isFulfilled() {
			return findParent(Form.class) != null;
		}
	}

}
