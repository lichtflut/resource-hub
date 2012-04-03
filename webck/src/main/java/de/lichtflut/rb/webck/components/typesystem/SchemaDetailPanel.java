/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableChoiceLabel;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.behaviors.CssModifier;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.EditPropertyDeclDialog;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
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
// TODO save via model, implizit not explizit(line: 316)
public class SchemaDetailPanel extends Panel{

	private IModel<List<PropertyRow>> markedForEdit;
	private IModel<ResourceSchema> schema;

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
		markedForEdit = new ListModel<PropertyRow>(new ArrayList<PropertyRow>());
		@SuppressWarnings("rawtypes")
		Form form = new Form("form");
		this.add(createTitleLabel("title"));
		form.add(createListView("row", this.schema));
		form.add(createEditButton("editButton"));
		add(form);
	}

	// ------------------------------------------------------
	
	private Component createTitleLabel(String id) {
		return new Label(id, Model.of(schema.getObject().getDescribedType()));
		
	}
	
	private Component createListView(String id, IModel<ResourceSchema> schema) {
		IModel<List<PropertyRow>> list = new PropertyRowListModel(schema);
		ListView<PropertyRow> view = new ListView<PropertyRow>(id, list) {
			@Override
			protected void populateItem(ListItem<PropertyRow> item) {
				fillRow(item);
				addColorCode(item);
			}
		};
		return view;
	}

	private Component createEditButton(String id) {
		Button button = new RBStandardButton(id) {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				DialogHoster hoster = findParent(DialogHoster.class);
			    hoster.openDialog(new EditPropertyDeclDialog(hoster.getDialogID(), markedForEdit){
			    	@Override
			    	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
			    		saveSchema();
			    		RBAjaxTarget.add(SchemaDetailPanel.this);
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

	/**
	 * Fills a {@link ListItem} with all the informations provided by a PropertyRow.
	 * @param item
	 */
	private void fillRow(ListItem<PropertyRow> item) {
		addCheckBox(item);
		addPropertyDecl(item);
		addLabelDecl(item);
		addCardinality(item);
		addDatatypeDecl(item);
		addContraintsDecl(item);
	}

	/**
	 * Adds a {@link CheckBox} in case of multiple editing of Propertydecls.
	 * @param item
	 */
	private void addCheckBox(final ListItem<PropertyRow> item) {
		CheckBox checkbox = new CheckBox("checkbox", Model.of(false)){
			@Override
			protected void onSelectionChanged(Boolean newSelection){
				markedForEdit.getObject().add(item.getModelObject());
			}
			@Override
			protected boolean wantOnSelectionChangedNotifications(){
				return true;
			}
		};
		item.add(checkbox);
		
	}

	/**
	 * Adds the PropertyDescriptor.
	 * @param item
	 */
	protected void addPropertyDecl(final ListItem<PropertyRow> item) {
		final IModel<ResourceID> model =   new PropertyModel<ResourceID>(item.getModel(), "propertyType");
		AjaxEditableLabel<ResourceID> field = new AjaxEditableLabel<ResourceID>("property", model){
			@Override
			protected WebComponent newLabel(final MarkupContainer parent, final String componentId,
					final IModel<ResourceID> model){
				Label label = buildFieldLabelFromResourceID(componentId, model);
				label.add(new LabelAjaxBehavior(getLabelAjaxEvent()));
				label.setOutputMarkupId(true);
				return label;
			}
			@Override
			protected void onSubmit(final AjaxRequestTarget target){
				saveSchema(PropertyRow.toPropertyDeclaration(item.getModelObject()), item.getIndex());
				RBAjaxTarget.add(SchemaDetailPanel.this);
				super.onSubmit(target);
			}
		};
		addTitleAttribute(model, field);
		item.add(field);
	}
	
	/**
	 * Adds the default-label name.
	 * @param item
	 */
	protected void addLabelDecl(final ListItem<PropertyRow> item) {
		IModel<ResourceID> model =  new PropertyModel<ResourceID>(item.getModel(), "defaultLabel");
		AjaxEditableLabel<ResourceID> label = new AjaxEditableLabel<ResourceID>("label", model){
			protected void onSubmit(final AjaxRequestTarget target){
				saveSchema(PropertyRow.toPropertyDeclaration(item.getModelObject()), item.getIndex());
				super.onSubmit(target);
			}
		};
		addTitleAttribute(model, label);
		item.add(label);
	}
	
	/**
	 * Adds the cardinality in the following form <code>[2..n]</code>
	 * @param item
	 */
	protected void addCardinality(final ListItem<PropertyRow> item) {
		IModel<String> model = new PropertyModel<String>(item.getModelObject(), "cardinality");
		AjaxEditableLabel<String> label = new AjaxEditableLabel<String>("cardinality", model){
			@Override
			protected void onSubmit(final AjaxRequestTarget target){
				saveSchema(PropertyRow.toPropertyDeclaration(item.getModelObject()), item.getIndex());
				super.onSubmit(target);
			}
		};
		addTitleAttribute(model, label);
		item.add(label);
	}
	
	/**
	 * @param item
	 */
	protected void addDatatypeDecl(final ListItem<PropertyRow> item) {
		final IModel<Datatype> model = new PropertyModel<Datatype>(item.getModel(), "dataType");
		AjaxEditableChoiceLabel<Datatype> field = new AjaxEditableChoiceLabel<Datatype>("datatype", model,
				Arrays.asList(Datatype.values())) {
			@Override
			protected void onSubmit(final AjaxRequestTarget target) {
				saveSchema(PropertyRow.toPropertyDeclaration(item.getModelObject()), item.getIndex());
				super.onSubmit(target);
			}
		};
		addTitleAttribute(model, field);
		item.add(field);
	}
	
	/**
	 * Adds {@link Constraint}s if present.
	 * @param item 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void addContraintsDecl(ListItem<PropertyRow> item) {
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

	private IModel<ResourceID> createModelForResourceContraint(ListItem<PropertyRow> item) {
		final IModel<ResourceID> model = new PropertyModel<ResourceID>(item.getModel(), "resourceConstraint");
		return model;
	}

	protected IModel<String> createModelForLiteralConstraint(ListItem<PropertyRow> item) {
		final IModel<String> model = new PropertyModel<String>(item.getModel(), "literalConstraints");
		return model;
	}

	/**
	 * @param componentId
	 * @param model
	 * @return
	 */
	protected Label buildFieldLabelFromResourceID(final String componentId, final IModel<ResourceID> model) {
		String s = ResourceLabelBuilder.getInstance().getFieldLabel(model.getObject(), getLocale());
		return new Label(componentId, s);
	}
	
	/**
	 * @param valueOf
	 * @param label
	 * @return 
	 */
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
	
	private void saveSchema(PropertyDeclaration decl, int index) {
		schema.getObject().getPropertyDeclarations().set(index, decl);
		saveSchema();
	}
	
	private void saveSchema(){
		provider.getSchemaManager().store(schema.getObject());
	}
	
}