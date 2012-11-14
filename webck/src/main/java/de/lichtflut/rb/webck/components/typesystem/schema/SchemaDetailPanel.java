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
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableChoiceLabel;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableMultiLineLabel;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.ExpressionBasedLabelBuilder;
import de.lichtflut.rb.core.schema.model.impl.LabelExpressionParseException;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.webck.behaviors.CssModifier;
import de.lichtflut.rb.webck.behaviors.DefaultButtonBehavior;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.ConfirmationDialog;
import de.lichtflut.rb.webck.components.dialogs.EditPropertyDeclDialog;
import de.lichtflut.rb.webck.components.fields.AjaxEditablePanelLabel;
import de.lichtflut.rb.webck.components.fields.AjaxUpdateDataPickerFieldBehavior;
import de.lichtflut.rb.webck.components.fields.ClassPickerField;
import de.lichtflut.rb.webck.components.fields.PropertyPickerField;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;
import de.lichtflut.rb.webck.components.typesystem.TypeHierarchyPanel;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.types.PropertyRowListModel;

/**
 * <p>
 * This {@link Panel} lists all Properties, FieldlabelExpression and Type Hierarchy for a given
 * {@link ResourceSchema}.
 * </p>
 * 
 * <p>
 * Created Feb 27, 2012
 * </p>
 * 
 * @author Ravi Knox
 */
public class SchemaDetailPanel extends Panel {

	@SpringBean
	private SchemaManager schemaManager;

	@SpringBean
	private TypeManager typeManager;

	private PropertyRowListModel listModel;
	private final IModel<ResourceSchema> schema;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * 
	 * @param id - wicket:id
	 * @param schema - to display
	 */
	public SchemaDetailPanel(final String id, final IModel<ResourceSchema> schema) {
		super(id);
		this.schema = schema;

		add(createTitleLabel("title"));
		add(new TypeHierarchyPanel("typeHierarchy", Model.of(schema.getObject().getDescribedType())));

		Form<?> form = new Form<Void>("form");
		form.add(createLabelExpressionBuilder());
		// TODO refactor wicket id to "schemaPropertyListPanel" and html tag to wicket-container
		form.add(createSchemaPropertyListView("listView", this.schema));

		form.add(new FeedbackPanel("feedback-top"));
		form.add(new FeedbackPanel("feedback-bottom"));

		addButtonBar(form);

		add(form);
		setOutputMarkupPlaceholderTag(true);
	}

	// ------------------------------------------------------

	/**
	 * Action to execute when <code>save</code>-Button is clicked.
	 */
	protected void saveAndUpdate() {
		saveSchema();
		updatePanel();
	}

	// ------------------------------------------------------

	/**
	 * Create a Component to display the described-type.
	 */
	private Component createTitleLabel(final String id) {
		return new Label(id, Model.of(schema.getObject().getDescribedType()));
	}

	/**
	 * Create a Component to display & edit the Label-builder.
	 */
	private Component createLabelExpressionBuilder() {
		final Model<String> model = Model.of(schema.getObject().getLabelBuilder().getExpression());
		AjaxEditableMultiLineLabel<String> editor = new AjaxEditableMultiLineLabel<String>("labelExpression", model) {
			@Override
			protected void onSubmit(final AjaxRequestTarget target) {
				if ((model.getObject() != null) && !model.getObject().isEmpty()) {
					ResourceSchemaImpl copy = (ResourceSchemaImpl) schema.getObject();
					try {
						copy.setLabelBuilder(new ExpressionBasedLabelBuilder(model.getObject()));
					} catch (LabelExpressionParseException e) {
						error(getString("error.label-exception"));
					}
					schema.setObject(copy);
				}
				getLabel().setVisible(true);
				getEditor().setVisible(false);
				target.add(this);
				target.appendJavaScript("window.status='';");
			}
		};
		return editor;
	}

	/**
	 * Adds Delete, Add, Save Buttons to the component
	 */
	private void addButtonBar(final Form<?> form) {
		form.add(createAddbutton("addButton"));
		form.add(createSaveButton("saveButton"));
		form.add(createDeleteTypeButton("deleteButton"));
	}

	// ------------------------------------------------------

	private Component createSchemaPropertyListView(final String id, final IModel<ResourceSchema> schema) {

		listModel = new PropertyRowListModel(schema);
		ListView<PropertyRow> view = new ListView<PropertyRow>(id, listModel) {
			@Override
			protected void populateItem(final ListItem<PropertyRow> item) {
				fillRow(item);
				addColorCode(item);
			}
		};
		view.setOutputMarkupId(true);
		return view;
	}

	private Component createDeleteTypeButton(final String id) {
		Button button = new RBStandardButton(id) {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				DialogHoster hoster = findParent(DialogHoster.class);
				ConfirmationDialog dialog = new ConfirmationDialog(hoster.getDialogID(), Model.of(getString("confirmation.delete-schema"))) {
					@Override
					public void onConfirm() {
						schemaManager.removeSchemaForType(schema.getObject().getDescribedType());
						typeManager.removeType(schema.getObject().getDescribedType());
						SchemaDetailPanel.this.setVisible(false);
						updatePanel();
						send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.TYPE));
					}

					@Override
					public void onCancel() {
						setDefaultFormProcessing(false);
					}
				};
				hoster.openDialog(dialog);
			}
		};
		return button;
	}

	private Component createAddbutton(final String id) {
		final IModel<PropertyRow> row = Model.of(new PropertyRow());
		Button button = new RBStandardButton(id) {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				listModel.getObject().add(row.getObject());
				openPropertyDeclDialog(row);
			}

			protected void openPropertyDeclDialog(final IModel<PropertyRow> row) {
				DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new EditPropertyDeclDialog(hoster.getDialogID(), row, schema) {

					@Override
					protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
						updatePanel();
					}

					@Override
					protected void onCancel(final AjaxRequestTarget target, final Form<?> form) {
						setDefaultFormProcessing(false);
						listModel.getObject().remove(row.getObject());
						close(target);
					}
				});
			}
		};
		return button;
	}

	private Component createSaveButton(final String id) {
		Button button = new RBStandardButton(id) {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				info(getString("confirmation.schema-saved-successful"));
				saveAndUpdate();
			}
		};
		button.add(new DefaultButtonBehavior());
		return button;
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
						addTypeNotYetStoredInfo();
						updatePanel();
					};
				});
				return picker;
			}

			@Override
			protected void onSubmit(final AjaxRequestTarget target) {
				addTypeNotYetStoredInfo();
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
				addTypeNotYetStoredInfo();
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
				addTypeNotYetStoredInfo();
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
				addTypeNotYetStoredInfo();
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

	private AjaxEditableLabel<String> createStringPatternField(final ListItem<PropertyRow> item) {
		IModel<String> patternModel = createModelForLiteralConstraint(item);
		AjaxEditableLabel<String> patternField = new AjaxEditableLabel<String>("pattern", patternModel){
			@Override
			protected void onSubmit(final AjaxRequestTarget target) {
				addTypeNotYetStoredInfo();
				updatePanel();
				super.onSubmit(target);
			}
		};
		return patternField;
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
						addTypeNotYetStoredInfo();
						updatePanel();
					};
				});
				return picker;
			}
		};
		return reference;
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
				addTypeNotYetStoredInfo();
				updatePanel();
			}
		});
	}

	// ------------------------------------------------------

	private void addTypeNotYetStoredInfo(){
		error(getString("error.schema-not-yet-saved"));
	}

	private IModel<ResourceID> createModelForResourceContraint(final ListItem<PropertyRow> item) {
		final IModel<ResourceID> model = new PropertyModel<ResourceID>(item.getModel(), "resourceConstraint");
		return model;
	}

	private IModel<String> createModelForLiteralConstraint(final ListItem<PropertyRow> item) {
		final IModel<String> model = new PropertyModel<String>(item.getModel(), "literalConstraint");
		return model;
	}

	private Label buildFieldLabelFromResourceID(final String componentId, final IModel<ResourceID> model) {
		String s = ResourceLabelBuilder.getInstance().getFieldLabel(model.getObject(), getLocale());
		return new Label(componentId, s);
	}

	private void addTitleAttribute(final IModel<?> model, final Component c) {
		c.add(new AttributeAppender("title", model));
	}

	/**
	 * Adds a color-code to the table for better usability.
	 * 
	 * @param item
	 */
	private void addColorCode(final ListItem<PropertyRow> item) {
		switch (item.getModelObject().getDataType()) {
		case DATE:
		case TIME_OF_DAY:
		case TIMESTAMP:
			item.add(CssModifier.appendClass(Model.of("key-time")));
			break;
		case RICH_TEXT:
		case STRING:
		case TEXT:
		case BOOLEAN:
		case INTEGER:
		case DECIMAL:
		case URI:
			item.add(CssModifier.appendClass(Model.of("key-text")));
			break;
		case RESOURCE:
			item.add(CssModifier.appendClass(Model.of("key-resource")));
		default:
			break;
		}
	}

	private void updatePanel() {
		RBAjaxTarget.add(SchemaDetailPanel.this);
	}

	private void saveSchema() {
		ResourceSchemaImpl copy = new ResourceSchemaImpl();
		copy.setDescribedType(schema.getObject().getDescribedType());
		copy.setLabelBuilder(schema.getObject().getLabelBuilder());
		for (PropertyDeclaration qInfo : schema.getObject().getQuickInfo()) {
			copy.addQuickInfo(qInfo.getPropertyDescriptor());
		}
		for (PropertyRow row : listModel.getObject()) {
			copy.addPropertyDeclaration(row.asPropertyDeclaration());
		}
		schemaManager.store(copy);
	}

}