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
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.behaviors.CssModifier;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.EditTypePropertyDeclDialog;
import de.lichtflut.rb.webck.components.fields.AjaxEditablePanelLabel;
import de.lichtflut.rb.webck.components.fields.DataPickerField;
import de.lichtflut.rb.webck.components.fields.PropertyPickerField;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import de.lichtflut.rb.webck.models.types.PropertyRowListModel;

/**
 * <p>
 *  This {@link Panel} lists all Properties for a given Type.
 * </p>
 *
 * <p>
 * 	Created Feb 27, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class SchemaDetailPanel extends Panel{

	IModel<List<PropertyDeclaration>> markedForEdit;
	
	@SpringBean
	ServiceProvider provider;

	// ---------------- Constructor -------------------------
	
	/**
	 * @param id - wicket:id
	 * @param model to display
	 */
	public SchemaDetailPanel(String id, IModel<ResourceSchema> schema) {
		super(id, schema);
		markedForEdit = new ListModel<PropertyDeclaration>(new ArrayList<PropertyDeclaration>());
		@SuppressWarnings("rawtypes")
		Form form = new Form("form");
		form.add(createListView(schema));
		form.add(createEditButton());
		add(form);
	}

	// ------------------------------------------------------
	
	private Component createListView(IModel<ResourceSchema> schema) {
		
		IModel<List<PropertyRow>> list = new PropertyRowListModel(schema);
		ListView<PropertyRow> view = new ListView<PropertyRow>("row", list) {
			
			@Override
			protected void populateItem(ListItem<PropertyRow> item) {
				fillRow(item);
				addColorCode(item);
			}
		};
		return view;
	}

	private Component createEditButton() {
		Button button = new RBStandardButton("button") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				DialogHoster hoster = findParent(DialogHoster.class);
			    hoster.openDialog(new EditTypePropertyDeclDialog(hoster.getDialogID(), markedForEdit));
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
	 * @param item
	 */
	private void addCheckBox(final ListItem<PropertyRow> item) {
		CheckBox checkbox = new CheckBox("checkbox", Model.of(false)){
		
			@Override
			protected void onSelectionChanged(Boolean newSelection)
			{
				markedForEdit.getObject().add(PropertyRow.toPropertyDeclaration(item.getModelObject()));
			}

			@Override
			protected boolean wantOnSelectionChangedNotifications()
			{
				return true;
			}
		};
		item.add(checkbox);
		
	}

	/**
	 * @param item
	 */
	protected void addPropertyDecl(ListItem<PropertyRow> item) {
		final IModel<ResourceID> model = new PropertyModel<ResourceID>(item.getModel(), "propertyType");
		final AjaxEditablePanelLabel<ResourceID> field = new AjaxEditablePanelLabel<ResourceID>("property", model){
			@Override
			protected WebComponent newLabel(final MarkupContainer parent, final String componentId,
					final IModel<ResourceID> model){
				Label label = new Label(componentId, ResourceLabelBuilder.getInstance().getLabel(model.getObject(), getLocale()));
				label.setOutputMarkupId(true);
				label.add(new LabelAjaxBehavior(getLabelAjaxEvent()));
				return label;
			}
			@Override
			protected FormComponent<ResourceID> newEditor(final MarkupContainer parent, final String componentId,
					final IModel<ResourceID> model)
				{
					PropertyPickerField editor = new PropertyPickerField(componentId, model);
					editor.setOutputMarkupId(true);
					editor.setVisible(false);
					editor.getDisplayComponent().add(new EditorAjaxBehavior());
					return editor;
				}

			protected void onSubmit(final AjaxRequestTarget target)
			{
				System.out.println( " SUBMIT :" + getEditor().getValue() + " + " + getEditor().getInput() + " DONE");
				getLabel().setVisible(true);
				getEditor().setVisible(false);
				target.add(this);

				target.appendJavaScript("window.status='';");
			}
		};
		field.setDefaultModel(model);
		field.setType(ResourceID.class);
		addTitleAttribute(model, field);
		item.add(field);
	}
	

	/**
	 * Adds the default-label name.
	 * @param item
	 */
	protected void addLabelDecl(ListItem<PropertyRow> item) {
		IModel<ResourceID> model =  new PropertyModel<ResourceID>(item.getModel(), "defaultLabel");
		AjaxEditableLabel<ResourceID> label = new AjaxEditableLabel<ResourceID>("label", model){
			protected void onSubmit(final AjaxRequestTarget target)
			{
				saveSchema();
				super.onSubmit(target);
			}

		};
		addTitleAttribute(model, label);
		item.add(label);
	}
	
	/**
	 * @param item 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected void addContraintsDecl(ListItem<PropertyRow> item) {
		IModel model = new DerivedModel<String, List<String>>(item.getModelObject().getLiteralConstraints()) {

			@Override
			protected String derive(List<String> original) {
				StringBuilder sb = new StringBuilder();
				for (String s : original) {
					sb.append(s + ", ");
				}
				if(sb.length() == 0){
					return "";
				}
				return sb.substring(0, (sb.length() -2));
			}
		};
		Label label = new Label("constraints", model);
		addTitleAttribute(model, label);
		item.add(label);
	}

	/**
	 * @param item
	 */
	protected void addDatatypeDecl(ListItem<PropertyRow> item) {
		IModel<Datatype> model = new PropertyModel<Datatype>(item.getModel(), "dataType");
		AjaxEditableChoiceLabel<Datatype> field = new AjaxEditableChoiceLabel<Datatype>("datatype", model,
				Arrays.asList(Datatype.values())) {

			@Override
			protected void onSubmit(final AjaxRequestTarget target) {
				saveSchema();
				super.onSubmit(target);
			}
		};
		addTitleAttribute(model, field);
		item.add(field);
	}

	/**
	 * @param item
	 */
	protected void addCardinality(ListItem<PropertyRow> item) {
		IModel<String> model = new PropertyModel<String>(item.getModel(), "cardinality");
		AjaxEditableLabel<String> label = new AjaxEditableLabel<String>("cardinality", model){
			
			@Override
			protected void onSubmit(final AjaxRequestTarget target)
			{
				saveSchema();
				super.onSubmit(target);
			}
		};
		addTitleAttribute(model, label);
		item.add(label);
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
	

	private void saveSchema() {
		provider.getSchemaManager().store((ResourceSchema)getDefaultModelObject());
	}
}
