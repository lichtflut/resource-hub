/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
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
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.webck.behaviors.CssModifier;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.EditTypePropertyDeclDialog;
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
	/**
	 * @param id - wicket:id
	 * @param model to display
	 */
	public SchemaDetailPanel(String id, IModel<ResourceSchema> schema) {
		super(id);
		markedForEdit = new ListModel<PropertyDeclaration>(new ArrayList<PropertyDeclaration>());
		@SuppressWarnings("rawtypes")
		Form form = new Form("form");
		form.add(createListView(schema));
		form.add(createEditButton());
		add(form);
	}

	// ------------------------------------------------------

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

	private Component createListView(IModel<ResourceSchema> schema) {
		
		IModel<List<PropertyRow>> list = new PropertyRowListModel(schema);
		ListView<PropertyRow> view = new ListView<PropertyRow>("row", list) {

			@Override
			protected void populateItem(ListItem<PropertyRow> item) {
				PropertyRow decl = (PropertyRow) item.getModelObject();
				fillRow(decl, item);
				addColorCode(decl, item);
			}
		};
		return view;
	}

	/**
	 * Fills a {@link ListItem} with all the informations provided by a PropertyRow.
	 * @param decl
	 * @param item
	 */
	private void fillRow(PropertyRow decl, ListItem<PropertyRow> item) {
		addCheckBox(decl, item);
		addPropertyDecl(decl, item);
		addLabelDecl(decl, item);
		addMinimumDecl(decl, item);
		addMaximumDecl(decl, item);
		addDatatypeDecl(decl, item);
		addContraintsDecl(decl, item);
	}

	/**
	 * @param decl
	 * @param item
	 */
	private void addCheckBox(final PropertyRow decl, final ListItem<PropertyRow> item) {
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
	 * @param decl
	 * @param item 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected void addContraintsDecl(PropertyRow decl, ListItem<PropertyRow> item) {
		IModel model = new DerivedModel<String, List<String>>(decl.getLiteralConstraints()) {

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
	 * @param decl
	 * @param item
	 */
	protected void addDatatypeDecl(PropertyRow decl, ListItem<PropertyRow> item) {
		IModel<Datatype> model = Model.of(decl.getDataType());
		Label label = new Label("datatype", model);
		addTitleAttribute(model, label);
		item.add(label);
	}

	/**
	 * @param decl
	 * @param item
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void addMaximumDecl(PropertyRow decl, ListItem<PropertyRow> item) {
		IModel<Integer> model = Model.of(decl.getMax());
		// In case of reuse; create an extra model for html-view
		IModel labelModel = model;
		Label label = new Label("max", labelModel);
		if(model.getObject() == Integer.MAX_VALUE){
			labelModel.setObject("&#8734");
			label.setEscapeModelStrings(false);
		}
		addTitleAttribute(labelModel, label);
		item.add(label);
	}

	/**
	 * @param decl
	 * @param item
	 */
	protected void addMinimumDecl(PropertyRow decl, ListItem<PropertyRow> item) {
		IModel<String> model = Model.of(String.valueOf(decl.getMin()));
		Label label = new Label("min", model);
		addTitleAttribute(model, label);
		item.add(label);
	}

	/**
	 * @param decl
	 * @param item
	 */
	@SuppressWarnings("rawtypes")
	protected void addLabelDecl(PropertyRow decl, ListItem<PropertyRow> item) {
		IModel model =  new PropertyModel<ResourceID>(decl, "defaultLabel");
		Label label = new Label("label", model);
		addTitleAttribute(model, label);
		item.add(label);
	}

	/**
	 * @param decl
	 * @param item
	 */
	protected void addPropertyDecl(PropertyRow decl, ListItem<PropertyRow> item) {
		IModel<String> model = new DerivedModel<String, ResourceID>(decl.getPropertyDescriptor()) {
			@Override
			protected String derive(ResourceID original) {
				return ResourceLabelBuilder.getInstance().getFieldLabel(original, getLocale());
			}
		};
		Label label = new Label("property",model);
		addTitleAttribute(Model.of(decl.getPropertyDescriptor()), label);
		item.add(label);
	}

	/**
	 * @param valueOf
	 * @param label
	 * @return 
	 */
	private void addTitleAttribute(IModel<?> model, Label label) {
		label.add(new AttributeAppender("title", model));
	}
	
	/**
	 * Adds a colorcode to the table for better usability.
	 * @param decl
	 * @param item
	 */
	private void addColorCode(PropertyRow decl, ListItem<PropertyRow> item) {
		switch (decl.getDataType()) {
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
