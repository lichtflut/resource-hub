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
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableMultiLineLabel;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.ExpressionBasedLabelBuilder;
import de.lichtflut.rb.core.schema.model.impl.LabelExpressionParseException;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.behaviors.CssModifier;
import de.lichtflut.rb.webck.behaviors.DefaultButtonBehavior;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.EditPropertyDeclDialog;
import de.lichtflut.rb.webck.components.fields.AjaxEditablePanelLabel;
import de.lichtflut.rb.webck.components.fields.AjaxUpdateDataPickerField;
import de.lichtflut.rb.webck.components.fields.PropertyPickerField;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;
import de.lichtflut.rb.webck.components.typesystem.TypeHierarchyPanel;
import de.lichtflut.rb.webck.models.types.PropertyRowListModel;

/**
 * <p>
 *  This {@link Panel} lists all Properties for a given {@link ResourceSchema}.
 * </p>
 *
 * <p>
 * 	Created Feb 27, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class SchemaDetailPanel extends Panel{

	private IModel<ResourceSchema> schema;
	private PropertyRowListModel listModel;
	

	@SpringBean
	private ServiceProvider provider;
	
	// ---------------- Constructor -------------------------
	
	/**
	 * @param id - wicket:id
	 * @param model to display
	 */
	public SchemaDetailPanel(String id, IModel<ResourceSchema> schema) {
		super(id);
		this.schema = schema;
		@SuppressWarnings("rawtypes")
		Form form = new Form("form");
		add(createTitleLabel("title"));
		add(new TypeHierarchyPanel("typeHierarchy", Model.of(schema.getObject().getDescribedType())));
		form.add(createLabelExpressionBuilder());
		form.add(createListView("listView", this.schema));
		addButtonBar(form);
		add(form);
	}
	

	// ------------------------------------------------------
	
	protected void saveAndUpdate() {
		saveSchema();
		updatePanel();
	}

	protected void updatePanel() {
		RBAjaxTarget.add(SchemaDetailPanel.this);
	}
	
	protected void saveSchema(){
		ResourceSchemaImpl copy = new ResourceSchemaImpl();
		copy.setDescribedType(schema.getObject().getDescribedType());
		copy.setLabelBuilder(schema.getObject().getLabelBuilder());
		for (PropertyRow row : listModel.getObject()) {
			copy.addPropertyDeclaration(row.asPropertyDeclaration());
		}
		provider.getSchemaManager().store(copy);
	}

	// ------------------------------------------------------
	
	/**
	 * Create Title.
	 * @param id - wicket:id
	 * @return
	 */
	private Component createTitleLabel(String id) {
		return new Label(id, Model.of(schema.getObject().getDescribedType()));
	}
	
	private Component createLabelExpressionBuilder() {
		final Model<String> model = Model.of(schema.getObject().getLabelBuilder().getExpression());
		AjaxEditableMultiLineLabel<String> editor = new AjaxEditableMultiLineLabel<String>("labelExpression", model){
			@Override
			protected void onSubmit(final AjaxRequestTarget target)
			{
				ResourceSchemaImpl copy = (ResourceSchemaImpl) schema.getObject();
				try {
					copy.setLabelBuilder(new ExpressionBasedLabelBuilder(model.getObject()));
				} catch (LabelExpressionParseException e) {
					error(getString("error-label-exception"));
				}
				schema.setObject(copy);
				getLabel().setVisible(true);
				getEditor().setVisible(false);
				target.add(this);
				target.appendJavaScript("window.status='';");
			}
		};
		return editor;
	}
	
	/**
	 * Create {@link ListView} of PrepertyDecls.
	 * @param id
	 * @param schema
	 * @return
	 */
	private Component createListView(String id, IModel<ResourceSchema> schema) {
		listModel = new PropertyRowListModel(schema);
		ListView<PropertyRow> view = new ListView<PropertyRow>(id, listModel) {
			@Override
			protected void populateItem(ListItem<PropertyRow> item) {
				fillRow(item);
				addColorCode(item);
			}
		};
		view.setOutputMarkupId(true);
		return view;
	}

	/**
	 * Adds Delete, Add, Edit Buttons to the component
	 * @param form 
	 */
	private void addButtonBar(Form<?> form) {
		form.add(createSaveButton("saveButton"));
		form.add(createAddbutton("addButton"));
	}

	// ------------------------------------------------------
	
	private Component createAddbutton(String id) {
		Button button = new RBStandardButton(id) {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				IModel<PropertyRow> row = Model.of(new PropertyRow());
				listModel.getObject().add(row.getObject());
				openPropertyDeclDialog(row);
			}

			protected void openPropertyDeclDialog(final IModel<PropertyRow> row) {
				DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new EditPropertyDeclDialog(hoster.getDialogID(), row) {
					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						schema.getObject().addPropertyDeclaration(row.getObject().asPropertyDeclaration());
						updatePanel();
						close(target);
					}
					@Override
					protected void onCancel(AjaxRequestTarget target, Form<?> form) {
						setDefaultFormProcessing(false);
						close(target);
					}
				});
			}
		};
		return button;
	}

	private Component createSaveButton(String id) {
		Button button = new RBStandardButton(id) {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				saveAndUpdate();
			}
		};
		button.add(new DefaultButtonBehavior());
		return button;
	}

	/**
	 * Fills a {@link ListItem} with all the informations provided by a PropertyRow.
	 * @param item
	 */
	private void fillRow(ListItem<PropertyRow> item) {
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
	 * @param item
	 */
	private void addPropertyDecl(final ListItem<PropertyRow> item) {
		final IModel<ResourceID> model =   new PropertyModel<ResourceID>(item.getModel(), "propertyDescriptor");
		AjaxEditablePanelLabel<ResourceID> field = new AjaxEditablePanelLabel<ResourceID>("property", model){
			@Override
			protected WebComponent newLabel(final MarkupContainer parent, final String componentId,
					final IModel<ResourceID> model){
				Label label = buildFieldLabelFromResourceID(componentId, model);
				label.add(new LabelAjaxBehavior(getLabelAjaxEvent()));
				label.setOutputMarkupId(true);
				return label;
			}
			
			@Override
			protected FormComponent<ResourceID> newEditor(final MarkupContainer parent, final String componentId,
					final IModel<ResourceID> model)	{
				PropertyPickerField picker = new PropertyPickerField(componentId, model);
				picker.setOutputMarkupId(true);
				picker.setVisible(false);
				picker.add(new AjaxUpdateDataPickerField() {
					public void onSubmit(AjaxRequestTarget target) {
						updatePanel();
					}; 
				});
				return picker;
			}
			
			@Override
			protected void onSubmit(final AjaxRequestTarget target){
				updatePanel();
				super.onSubmit(target);
			}
		};
		addTitleAttribute(model, field);
		item.add(field);
	}
	
	private void addLabelDecl(final ListItem<PropertyRow> item) {
		IModel<ResourceID> model =  new PropertyModel<ResourceID>(item.getModel(), "defaultLabel");
		AjaxEditableLabel<ResourceID> label = new AjaxEditableLabel<ResourceID>("label", model){
			protected void onSubmit(final AjaxRequestTarget target){
				updatePanel();
				super.onSubmit(target);
			}
		};
		addTitleAttribute(model, label);
		item.add(label);
	}
	
	private void addCardinality(final ListItem<PropertyRow> item) {
		IModel<String> model = new PropertyModel<String>(item.getModelObject(), "cardinality");
		AjaxEditableLabel<String> label = new AjaxEditableLabel<String>("cardinality", model){
			@Override
			protected void onSubmit(final AjaxRequestTarget target){
				updatePanel();
				super.onSubmit(target);
			}
		};
		addTitleAttribute(model, label);
		item.add(label);
	}

	private void addDatatypeDecl(final ListItem<PropertyRow> item) {
		final IModel<Datatype> model = new PropertyModel<Datatype>(item.getModel(), "dataType");
		AjaxEditableChoiceLabel<Datatype> field = new AjaxEditableChoiceLabel<Datatype>("datatype", model,
				Arrays.asList(Datatype.values())) {
			@Override
			protected void onSubmit(final AjaxRequestTarget target) {
				updatePanel();
				super.onSubmit(target);
			}
		};
		addTitleAttribute(model, field);
		item.add(field);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addContraintsDecl(ListItem<PropertyRow> item) {
		IModel model = null;
		AjaxEditableLabel field = null;
		if(null != item.getModelObject().getResourceConstraint()) {
			model = createModelForResourceContraint(item);
			field = new AjaxEditableLabel<String>("constraints", model) {
				@Override
				protected WebComponent newLabel(final MarkupContainer parent, final String componentId,
						final IModel model) {
					Label label = buildFieldLabelFromResourceID(componentId, model);
					label.add(new LabelAjaxBehavior(getLabelAjaxEvent()));
					label.setOutputMarkupId(true);
					return label;
				}
			};
		} else {
			model = createModelForLiteralConstraint(item);
			field = new AjaxEditableLabel<String>("constraints", model);
		}
		addTitleAttribute(model, field);
		item.add(field);
	}
	
	private void addUpDownButton(final ListItem<PropertyRow> item) {
		item.add(new AjaxLink<Void>("up") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				listModel.moveUp(item.getIndex());
				updatePanel();
			}
		});
		
		item.add(new AjaxLink<Void>("down") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				listModel.moveDown(item.getIndex());
				updatePanel();
			}
		});
	}

	private void addEditButton(final ListItem<PropertyRow> item) {
		item.add(new AjaxLink<Void>("edit") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				openPropertyDeclDialog(item);
			}

			protected void openPropertyDeclDialog(final ListItem<PropertyRow> item) {
				DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new EditPropertyDeclDialog(hoster.getDialogID(), item.getModel()) {
					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						updatePanel();
						close(target);
					}
					@Override
					protected void onCancel(AjaxRequestTarget target, Form<?> form) {
						close(target);
					}
				});
			}
		});
	}

	private void addDeleteButton(final ListItem<PropertyRow> item) {
		item.add(new AjaxLink<Void>("delete") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				schema.getObject().getPropertyDeclarations().remove(item.getIndex());
				updatePanel();
			}
		});
	}
	
	// ------------------------------------------------------
	
	private IModel<ResourceID> createModelForResourceContraint(ListItem<PropertyRow> item) {
		final IModel<ResourceID> model = new PropertyModel<ResourceID>(item.getModel(), "resourceConstraint");
		return model;
	}

	private IModel<String> createModelForLiteralConstraint(ListItem<PropertyRow> item) {
		final IModel<String> model = new PropertyModel<String>(item.getModel(), "literalConstraint");
		return model;
	}

	private Label buildFieldLabelFromResourceID(final String componentId, final IModel<ResourceID> model) {
		String s = ResourceLabelBuilder.getInstance().getFieldLabel(model.getObject(), getLocale());
		return new Label(componentId, s);
	}

	private void addTitleAttribute(IModel<?> model, Component c) {
		c.add(new AttributeAppender("title", model));
	}

	/**
	 * Adds a colorcode to the table for better usability.
	 * @param item
	 */
	private void addColorCode(ListItem<PropertyRow> item) {
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
				item.add(CssModifier.appendClass(Model.of("key-text")));
				break;
			case RESOURCE:
				item.add(CssModifier.appendClass(Model.of("key-resource")));
			default:
				break;
		}
	}

}