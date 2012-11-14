/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.schema;

import java.util.Arrays;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableChoiceLabel;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.EditPropertyDeclDialog;
import de.lichtflut.rb.webck.components.fields.AjaxEditablePanelLabel;
import de.lichtflut.rb.webck.components.fields.AjaxUpdateDataPickerFieldBehavior;
import de.lichtflut.rb.webck.components.fields.ClassPickerField;
import de.lichtflut.rb.webck.components.fields.PropertyPickerField;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;
import de.lichtflut.rb.webck.models.types.PropertyRowListModel;

/**
 * <p>
 * This Component lists a ResourceSchema's properties.
 * </p>
 * Created: Nov 9, 2012
 *
 * @author Ravi Knox
 */
public class SchemaPropertyDetailListPanel extends Panel {

	private final PropertyRowListModel listModel;
	private final IModel<ResourceSchema> schema;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * @param id
	 * @param schema
	 */
	public SchemaPropertyDetailListPanel(final String id, final IModel<ResourceSchema> schema) {
		super(id, schema);
		this.schema = schema;
		listModel = new PropertyRowListModel(schema);

		add(createPropertiesList("propertyrow", schema));

	}

	// ------------------------------------------------------

	/**
	 * Add ResourceSchemas PropertyDecalaration as a {@link ListView}
	 * @param id - wicket:id
	 * @param model The ResourceSchema
	 * @return A ListView
	 */
	private ListView<?> createPropertiesList(final String id, final IModel<ResourceSchema> model) {

		ListView<PropertyRow> listView = new ListView<PropertyRow>(id, listModel) {

			@Override
			protected void populateItem(final ListItem<PropertyRow> item) {
				fillRow(item);
			}

		};
		return listView;
	}

	/**
	 * Fills a {@link ListItem} with all the informations provided by a PropertyRow.
	 * 
	 * @param item
	 */
	private void fillRow(final ListItem<PropertyRow> item) {
		addPropertyDecl(item);
		addLabelDecl(item);
		addCardinality(item);
		addDatatypeDecl(item);
		addContraintsDecl(item);
		addUpDownButton(item);
		addEditButton(item);
		addDeleteButton(item);
	}

	/**
	 * Adds the PropertyDescriptor.
	 * 
	 * @param item
	 */
	private void addPropertyDecl(final ListItem<PropertyRow> item) {
		final IModel<ResourceID> model = new PropertyModel<ResourceID>(item.getModel(), "propertyDescriptor");
		AjaxEditablePanelLabel<ResourceID> field = new AjaxEditablePanelLabel<ResourceID>("property", model) {
			@Override
			protected WebComponent newLabel(final MarkupContainer parent, final String componentId, final IModel<ResourceID> model) {
				Label label = buildFieldLabelFromResourceID(componentId, model);
				label.add(new LabelAjaxBehavior(getLabelAjaxEvent()));
				label.setOutputMarkupId(true);
				return label;
			}

			@Override
			protected FormComponent<ResourceID> newEditor(final MarkupContainer parent, final String componentId,
					final IModel<ResourceID> model) {
				PropertyPickerField picker = new PropertyPickerField(componentId, model);
				picker.setOutputMarkupId(true);
				picker.setVisible(false);
				picker.add(new AjaxUpdateDataPickerFieldBehavior() {
					@Override
					public void onSubmit(final AjaxRequestTarget target) {
						updatePanel();
					};
				});
				return picker;
			}

			@Override
			protected void onSubmit(final AjaxRequestTarget target) {
				updatePanel();
				super.onSubmit(target);
			}
		};
		addTitleAttribute(model, field);
		item.add(field);
	}

	private void addLabelDecl(final ListItem<PropertyRow> item) {
		IModel<ResourceID> model = new PropertyModel<ResourceID>(item.getModel(), "defaultLabel");
		AjaxEditableLabel<ResourceID> label = new AjaxEditableLabel<ResourceID>("label", model) {
			@Override
			protected void onSubmit(final AjaxRequestTarget target) {
				updatePanel();
				super.onSubmit(target);
			}
		};
		addTitleAttribute(model, label);
		item.add(label);
	}

	private void addCardinality(final ListItem<PropertyRow> item) {
		IModel<String> model = new PropertyModel<String>(item.getModelObject(), "cardinality");
		AjaxEditableLabel<String> label = new AjaxEditableLabel<String>("cardinality", model) {
			@Override
			protected void onSubmit(final AjaxRequestTarget target) {
				updatePanel();
				super.onSubmit(target);
			}
		};
		addTitleAttribute(model, label);
		item.add(label);
	}

	private void addDatatypeDecl(final ListItem<PropertyRow> item) {
		final IModel<Datatype> model = new PropertyModel<Datatype>(item.getModel(), "dataType");
		AjaxEditableChoiceLabel<Datatype> field = new AjaxEditableChoiceLabel<Datatype>("datatype", model, Arrays.asList(Datatype.values())) {
			@Override
			protected void onSubmit(final AjaxRequestTarget target) {
				item.getModelObject().clearConstraint();
				updatePanel();
				super.onSubmit(target);
			}
		};
		addTitleAttribute(model, field);
		item.add(field);
	}

	private void addContraintsDecl(final ListItem<PropertyRow> item) {
		AjaxEditablePanelLabel<ResourceID> resourceField = createResourceConstraintField(item);
		AjaxEditableLabel<String> patternField = createStringPatternField(item);
		if (null != item.getModelObject().getResourceConstraint()) {
			String title = item.getModelObject().getResourceConstraint().getQualifiedName().toURI();
			addTitleAttribute(Model.of(title), resourceField);
			patternField.setVisible(false);
		} else {
			addTitleAttribute(Model.of(item.getModelObject().getLiteralConstraint()), patternField);
			resourceField.setVisible(false);
		}
		item.add(resourceField, patternField);
	}

	private AjaxEditablePanelLabel<ResourceID> createResourceConstraintField(final ListItem<PropertyRow> item) {
		IModel<ResourceID> resourceModel = createModelForResourceContraint(item);
		AjaxEditablePanelLabel<ResourceID> reference = new AjaxEditablePanelLabel<ResourceID>("resource", resourceModel) {
			@Override
			protected WebComponent newLabel(final MarkupContainer parent, final String componentId, final IModel<ResourceID> model) {
				Label label = buildFieldLabelFromResourceID(componentId, model);
				label.add(new LabelAjaxBehavior(getLabelAjaxEvent()));
				label.setOutputMarkupId(true);
				return label;
			}

			@Override
			protected FormComponent<ResourceID> newEditor(final MarkupContainer parent, final String componentId,
					final IModel<ResourceID> model) {
				ClassPickerField picker = new ClassPickerField(componentId, model);
				picker.setOutputMarkupId(true);
				picker.setVisible(false);
				picker.add(new AjaxUpdateDataPickerFieldBehavior() {
					@Override
					public void onSubmit(final AjaxRequestTarget target) {
						updatePanel();
					};
				});
				return picker;
			}
		};
		return reference;
	}

	private Label buildFieldLabelFromResourceID(final String componentId, final IModel<ResourceID> model) {
		String s = ResourceLabelBuilder.getInstance().getFieldLabel(model.getObject(), getLocale());
		return new Label(componentId, s);
	}

	private void addUpDownButton(final ListItem<PropertyRow> item) {
		item.add(new AjaxLink<Void>("up") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				listModel.moveUp(item.getIndex());
				updatePanel();
			}
		});

		item.add(new AjaxLink<Void>("down") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				listModel.moveDown(item.getIndex());
				updatePanel();
			}
		});
	}

	private void addEditButton(final ListItem<PropertyRow> item) {
		item.add(new AjaxLink<Void>("edit") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				openPropertyDeclDialog(item);
			}

			protected void openPropertyDeclDialog(final ListItem<PropertyRow> item) {
				DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new EditPropertyDeclDialog(hoster.getDialogID(), item.getModel(), schema));
			}
		});
	}

	private void addDeleteButton(final ListItem<PropertyRow> item) {
		item.add(new AjaxLink<Void>("delete") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				listModel.getObject().remove(item.getIndex());
				updatePanel();
			}
		});
	}

	private void updatePanel() {
		RBAjaxTarget.add(SchemaPropertyDetailListPanel.this);
	}

	private void addTitleAttribute(final IModel<?> model, final Component component) {
		component.add(new AttributeAppender("title", model));
	}

	private AjaxEditableLabel<String> createStringPatternField(final ListItem<PropertyRow> item) {
		IModel<String> patternModel = createModelForLiteralConstraint(item);
		AjaxEditableLabel<String> patternField = new AjaxEditableLabel<String>("pattern", patternModel){
			@Override
			protected void onSubmit(final AjaxRequestTarget target) {
				updatePanel();
				super.onSubmit(target);
			}
		};
		return patternField;
	}

	private IModel<ResourceID> createModelForResourceContraint(final ListItem<PropertyRow> item) {
		final IModel<ResourceID> model = new PropertyModel<ResourceID>(item.getModel(), "resourceConstraint");
		return model;
	}

	private IModel<String> createModelForLiteralConstraint(final ListItem<PropertyRow> item) {
		final IModel<String> model = new PropertyModel<String>(item.getModel(), "literalConstraint");
		return model;
	}
}
