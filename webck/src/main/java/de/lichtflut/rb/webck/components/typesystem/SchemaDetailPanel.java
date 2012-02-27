/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
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

	/**
	 * @param id - wicket:id
	 * @param model to display
	 */
	public SchemaDetailPanel(String id, IModel<ResourceSchema> schema) {
		super(id);
		this.add(createListView(schema));
	}

	// ------------------------------------------------------

	/**
	 * @param model 
	 * @return
	 */
	private Component createListView(IModel<ResourceSchema> schema) {
		
		IModel<List<PropertyRow>> list = new PropertyRowListModel(schema);
		ListView<PropertyRow> view = new ListView<PropertyRow>("row", list) {

			@Override
			protected void populateItem(ListItem<PropertyRow> item) {
				PropertyRow decl = (PropertyRow) item.getModelObject();
				fillRow(decl, item);
			}
		};
		return view;
	}


	private void fillRow(PropertyRow decl, ListItem<PropertyRow> item) {
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
		addTitle(model, label);
		item.add(label);
	}

	/**
	 * @param decl
	 * @param item
	 */
	protected void addDatatypeDecl(PropertyRow decl, ListItem<PropertyRow> item) {
		IModel<Datatype> model = Model.of(decl.getDataType());
		Label label = new Label("datatype", model);
		addTitle(model, label);
		item.add(label);
	}

	/**
	 * @param decl
	 * @param item
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void addMaximumDecl(PropertyRow decl, ListItem<PropertyRow> item) {
		IModel<Integer> model = Model.of(decl.getMax());
		IModel labelModel = model;
		Label label = new Label("max", labelModel);
		if(model.getObject() == Integer.MAX_VALUE){
			labelModel.setObject("&#8734");
			label.setEscapeModelStrings(false);
		}
		addTitle(model, label);
		item.add(label);
	}

	/**
	 * @param decl
	 * @param item
	 */
	protected void addMinimumDecl(PropertyRow decl, ListItem<PropertyRow> item) {
		IModel<String> model = Model.of(String.valueOf(decl.getMin()));
		Label label = new Label("min", model);
		addTitle(model, label);
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
		addTitle(model, label);
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
		addTitle(Model.of(decl.getPropertyDescriptor()), label);
		item.add(label);
	}

	/**
	 * @param valueOf
	 * @param label
	 * @return 
	 */
	private void addTitle(IModel<?> model, Label label) {
		label.add(new AttributeAppender("title", model));
	}
}
