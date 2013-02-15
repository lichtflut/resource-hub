/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.schema;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableMultiLineLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
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
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.ConfirmationDialog;
import de.lichtflut.rb.webck.components.dialogs.EditPropertyDeclDialog;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;
import de.lichtflut.rb.webck.components.typesystem.TypeHierarchyPanel;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
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
		form.add(createSchemaPropertyListView("listView", this.schema));

		form.add(new FeedbackPanel("feedback-top"));

		addButtonBar(form);

		add(form);
		setOutputMarkupPlaceholderTag(true);
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
		form.add(createDeleteTypeButton("deleteButton"));
	}

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

	// ------------------------------------------------------

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
						saveAndUpdate();
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
						saveAndUpdate();
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

	/**
	 * Fills a {@link ListItem} with all the informations provided by a PropertyRow.
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

	private void addPropertyDecl(final ListItem<PropertyRow> item) {
		final IModel<ResourceID> model = new PropertyModel<ResourceID>(item.getModel(), "propertyDescriptor");
		Label label = buildFieldLabelFromResourceID("property", model);
		addTitleAttribute(model, label);
		item.add(label);
	}

	private void addLabelDecl(final ListItem<PropertyRow> item) {
		IModel<ResourceID> model = new PropertyModel<ResourceID>(item.getModel(), "defaultLabel");
		Label label = new Label("label", model);
		addTitleAttribute(model, label);
		item.add(label);
	}

	private void addCardinality(final ListItem<PropertyRow> item) {
		IModel<String> model = new PropertyModel<String>(item.getModelObject(), "cardinality");
		Label label = new Label("cardinality", model);
		addTitleAttribute(model, label);
		item.add(label);
	}

	private void addDatatypeDecl(final ListItem<PropertyRow> item) {
		final IModel<Datatype> model = new PropertyModel<Datatype>(item.getModel(), "dataType");
		Label label = new Label("datatype", model);
		addTitleAttribute(model, label);
		item.add(label);
	}

	private void addContraintsDecl(final ListItem<PropertyRow> item) {
		if(null != item.getModelObject().getResourceConstraint()){
			IModel<ResourceID> model = createModelForResourceContraint(item);
			Label label = buildFieldLabelFromResourceID("constraint", model);
			addTitleAttribute(model, label);
			item.add(label);
		}else{
			IModel<String> model = createModelForLiteralConstraint(item);
			Label label = new Label("constraint", model);
			item.add(label);
		}
	}

	private void addUpDownButton(final ListItem<PropertyRow> item) {
		item.add(new AjaxLink<Void>("up") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				listModel.moveUp(item.getIndex());
				saveAndUpdate();
			}
		});

		item.add(new AjaxLink<Void>("down") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				listModel.moveDown(item.getIndex());
				saveAndUpdate();
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
				hoster.openDialog(new EditPropertyDeclDialog(hoster.getDialogID(), item.getModel(), schema){
					@Override
					protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
						super.onSubmit(target, form);
						saveAndUpdate();
					}
				});
			}
		});
	}

	private void addDeleteButton(final ListItem<PropertyRow> item) {
		item.add(new AjaxLink<Void>("delete") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				DialogHoster hoster = findParent(DialogHoster.class);
				IModel<String> model = new StringResourceModel("confirmation-delete-property", SchemaDetailPanel.this, new Model<String>(), new PropertyModel<String>(item.getModel(), "defaultLabel"));
				ConfirmationDialog dialog = new ConfirmationDialog(hoster.getDialogID(), model) {
					@Override
					public void onConfirm() {
						listModel.getObject().remove(item.getIndex());
						saveAndUpdate();
						send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.TYPE));
					}

					@Override
					public void onCancel() {
						closeDialog();
					}
				};
				hoster.openDialog(dialog);
			}
		});
	}

	// ------------------------------------------------------

	private IModel<ResourceID> createModelForResourceContraint(final ListItem<PropertyRow> item) {
		final IModel<ResourceID> model = new PropertyModel<ResourceID>(item.getModel(), "resourceConstraint");
		return model;
	}

	private IModel<String> createModelForLiteralConstraint(final ListItem<PropertyRow> item) {
		final IModel<String> model = new PropertyModel<String>(item.getModel(), "literalConstraint");
		return model;
	}

	private Label buildFieldLabelFromResourceID(final String componentId, final IModel<ResourceID> model) {
		IModel<String> labelModel = new DerivedModel<String, ResourceID>(model) {

			@Override
			protected String derive(final ResourceID original) {
				return ResourceLabelBuilder.getInstance().getFieldLabel(model.getObject(), getLocale());
			}
		};
		return new Label(componentId, labelModel);
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
				break;
			default:
				break;
		}
	}

	private void saveAndUpdate() {
		saveSchema();
		updatePanel();
		info(getString("confirmation.schema-saved-successful"));
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

	// ------------------------------------------------------

	@Override
	public void onEvent(final IEvent<?> event) {
		final ModelChangeEvent<?> mce = ModelChangeEvent.from(event);
		if (mce.isAbout(ModelChangeEvent.SCHEMA)) {
			RBAjaxTarget.add(this);
		}
	}

}